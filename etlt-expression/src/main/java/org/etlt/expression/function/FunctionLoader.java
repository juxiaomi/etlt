/**
 * 
 */
package org.etlt.expression.function;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.etlt.expression.VariableContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * The function loader in the expression loads the available external methods from the configuration file
 * 
 */
@Deprecated
public class FunctionLoader implements FunctionExecutor {

	private static final String FILE_NAME = "function.json";

	private static FunctionLoader single = new FunctionLoader();

	// 所有方法Map
	private HashMap<String, FunctionInvoker> functionMap = new HashMap<String, FunctionInvoker>();

	/**
	 */
	private FunctionLoader() {
		try {
			init();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Initialization, parsing configuration
	 * 
	 * @throws ParserConfigurationException
	 * @throws Exception
	 */
	private void init() throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(FunctionLoader.class.getResourceAsStream(FILE_NAME));
		NodeList rootNodes = doc.getElementsByTagName("function-configuration");
		if (rootNodes.getLength() < 1) {
			return;
		}
		rootNodes = rootNodes.item(0).getChildNodes();
		for (int i = 0; i < rootNodes.getLength(); i++) {
			Node beanNode = rootNodes.item(i);
			if (!beanNode.getNodeName().equals("bean")) {
				continue;
			}
			// 读class类
			String className = beanNode.getAttributes().getNamedItem("class").getNodeValue();
			Class<?> _class = Class.forName(className);

			NodeList subNodes = beanNode.getChildNodes();
			List<Parameter> constructorArgs = null;
			HashSet<Function> functions = new HashSet<Function>();
			for (int j = 0; j < subNodes.getLength(); j++) {
				Node subNode = subNodes.item(j);
				if (subNode.getNodeName().equals("constructor-args") && constructorArgs == null) {
					// 读取类的构造参数
					constructorArgs = parseConstructorArgs(subNode);
				} else if (subNode.getNodeName().equals("function")) {
					// 读取方法描述
					if (!functions.add(parseFunctions(subNode))) {
						throw new SAXException("方法名不能重复");
					}
				}
			}
			if (functions.size() <= 0) {
				continue;
			}
			Object ins = null;
			if (constructorArgs == null || constructorArgs.size() <= 0) {
				// 使用默认构造函数
				ins = _class.newInstance();
			} else {
				// 使用给定的构造函数
				Class<?>[] cs = getParameterTypes(constructorArgs);
				Object[] ps = getParameterValues(constructorArgs);
				Constructor<?> c = _class.getConstructor(cs);
				ins = c.newInstance(ps);
			}
			// 封装FunctionInvoker放入Map中
			for (Function f : functions) {
				Method m = _class.getMethod(f.methodName, getParameterTypes(f.types));
				functionMap.put(f.name, new FunctionInvoker(m, ins));
			}

		}

	}

	/**
	 * 解析构参数法的描述
	 * 
	 * @param argRootNode
	 * @return
	 */
	private List<Parameter> parseConstructorArgs(Node argRootNode) {
		NodeList argsNode = argRootNode.getChildNodes();
		List<Parameter> args = new ArrayList<Parameter>();
		for (int i = 0; i < argsNode.getLength(); i++) {
			Node argNode = argsNode.item(i);
			if (argNode.getNodeName().equals("constructor-arg")) {
				// 参数类型
				String type = argNode.getAttributes().getNamedItem("type").getNodeValue();
				// 参数值
				String value = argNode.getTextContent();
				args.add(new Parameter(type, value));
			}
		}
		return args;
	}

	/**
	 * 解析方法的描述
	 * 
	 * @param funRootNode
	 * @return
	 */
	private Function parseFunctions(Node funRootNode) {
		String name = funRootNode.getAttributes().getNamedItem("name").getNodeValue();
		String methodName = funRootNode.getAttributes().getNamedItem("method").getNodeValue();
		Function f = new Function(name, methodName);
		NodeList argsNode = funRootNode.getChildNodes();
		for (int i = 0; i < argsNode.getLength(); i++) {
			Node argNode = argsNode.item(i);
			if (argNode.getNodeName().equals("parameter-type")) {
				// 参数类型
				f.addType(argNode.getTextContent());
			}
		}
		return f;
	}

	/**
	 * 取得参数列表的类型
	 * 
	 * @param parameters
	 * @return
	 */
	private Class<?>[] getParameterTypes(List<Parameter> parameters) {
		if (parameters == null) {
			return null;
		}
		Class<?>[] types = new Class[parameters.size()];
		for (int i = 0; i < parameters.size(); i++) {
			types[i] = parameters.get(i).type;
		}
		return types;
	}

	/**
	 * 取得参数列表的值
	 * 
	 * @param parameters
	 * @return
	 */
	private Object[] getParameterValues(List<Parameter> parameters) {
		if (parameters == null) {
			return null;
		}
		Object[] values = new Object[parameters.size()];
		for (int i = 0; i < parameters.size(); i++) {
			values[i] = parameters.get(i).value;
		}
		return values;
	}

	/**
	 * 参数定义
	 * 
	 */
	private class Parameter {
		Class<?> type;// 参数类型
		Object value;// 参数值

		@SuppressWarnings("rawtypes")
		public Parameter(String _type, String _value) {
			try {
				type = getTypeClass(_type);
				Constructor c = type.getConstructor(new Class[] { String.class });
				value = c.newInstance(_value);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		public Parameter(String _type) {
			try {
				type = getTypeClass(_type);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		@SuppressWarnings("rawtypes")
		private Class getTypeClass(String _type) throws ClassNotFoundException {
			if ("boolean".equals(_type)) {
				return boolean.class;
			} else if ("byte".equals(_type)) {
				return byte.class;
			} else if ("char".equals(_type)) {
				return char.class;
			} else if ("double".equals(_type)) {
				return double.class;
			} else if ("float".equals(_type)) {
				return float.class;
			} else if ("int".equals(_type)) {
				return int.class;
			} else if ("long".equals(_type)) {
				return long.class;
			} else if ("short".equals(_type)) {
				return short.class;
			}
			return Class.forName(_type);

		}
	}

	/**
	 * 方法定义
	 * 
	 */
	private class Function {
		String name;// 表达式使用的方法名称
		String methodName;// 类是的实际方法名称
		List<Parameter> types;// 方法的参数类型

		public Function(String _name, String _methodName) {
			if (_name == null || _methodName == null) {
				throw new IllegalArgumentException();
			}
			name = _name;
			methodName = _methodName;
			types = new ArrayList<Parameter>();
		}

		public void addType(String type) {
			types.add(new Parameter(type));
		}

		@Override
		public int hashCode() {
			return name.hashCode();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final Function other = (Function) obj;
			return name.equals(other.name);
		}
	}

	/**
	 * 表达式可用函数除了从配置文件“functionConfig.xml”加载外， 还可以通过此方法运行时添加
	 * 
	 * @param functionName 方法别名，表达式使用的名称
	 * @param instance     调用的实例名
	 * @param method       调用的方法
	 */
	public static void addFunction(String functionName, Object instance, Method method) {
		if (functionName == null || instance == null || method == null) {
			return;
		}
		single.functionMap.put(functionName, new FunctionInvoker(method, instance));
	}

	/**
	 * 取得方法
	 * 
	 * @param functionName
	 * @return
	 * @throws NoSuchMethodException
	 */
	public static Method loadFunction(String functionName) throws NoSuchMethodException {
		FunctionInvoker f = single.functionMap.get(functionName);
		if (f == null) {
			throw new NoSuchMethodException();
		}
		return f.getMethod();
	}

	/**
	 * 执行方法
	 * 
	 * @param functionName
	 * @param parameters
	 * @return
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Override
	public Object invokeFunction(VariableContext context, String functionName, Object[] parameters)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		FunctionInvoker f = single.functionMap.get(functionName);
		if (f == null) {
			throw new NoSuchMethodException();
		}
		return f.invoke(null, parameters);
	}

	@Override
	public FunctionInvoker getFunction(String functionName) {
		return single.functionMap.get(functionName);
	}

}
