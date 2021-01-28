package org.etlt.expression.function;

import org.etlt.expression.IllegalExpressionException;
import org.etlt.expression.SettingReader;
import org.etlt.expression.VariableContext;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FunctionFactory implements FunctionExecutor {

    private Object[] innerFunctionInstances = new Object[]{
            new StringFunctions(),
            new MathFunctions(),
            new DateFunctions(),
            new AggregationFunctions()
    };

    private static FunctionFactory INSTANCE;

    static {
        try {
            INSTANCE = new FunctionFactory();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private FunctionSetting setting;

    public static FunctionFactory getInstance() {
        return INSTANCE;
    }

    private FunctionFactory() throws IOException {
        initInnerFunctions();
        loadUserDefinedFunctions();
    }

    private static final String CONFIGURATION = "ud-functions.json";

    private final Map<String, FunctionInvoker> functionInvokerMap = new HashMap<String, FunctionInvoker>();

    @Override
    public Object invokeFunction(VariableContext context, String functionName, Object[] parameters) {
        FunctionInvoker functionInvoker = getFunction(functionName);
        return functionInvoker.invoke(context, parameters);
    }

    @Override
    public FunctionInvoker getFunction(String functionName) {
        String _functionName = toUpper(functionName);
        FunctionInvoker functionInvoker = this.functionInvokerMap.get(_functionName);
        if (functionInvoker == null) {
            throw new IllegalExpressionException("function not found: " + _functionName);
        }
        return functionInvoker;
    }

    protected void loadUserDefinedFunctions() throws IOException {
        SettingReader reader = new SettingReader();
        InputStream udConfig = getClass().getClassLoader().getResourceAsStream(CONFIGURATION);
        if (udConfig != null) {
            this.setting = reader.read(udConfig, FunctionSetting.class);
            Map<String, Class> udFunctions = this.setting.getUserDefined();
            Set<String> names = udFunctions.keySet();
            for (String name : names) {
                String functionName = toUpper(name);
                Class clazz = udFunctions.get(name);
                if (clazz.isAssignableFrom(FunctionActor.class)) {
                    throw new IllegalExpressionException("user defined class is not a FunctionActor: " + clazz.getName());
                }
                try {
                    FunctionActor functionActor = (FunctionActor) clazz.getDeclaredConstructor(null).newInstance();
                    checkExist(functionName);
                    this.functionInvokerMap.put(functionName, new FunctionInvoker(functionActor));
                } catch (Exception e) {
                    throw new IllegalExpressionException("user defined class is not a FunctionActor or cannot be instantiated: " + clazz.getName(), e);
                }
            }
        }
    }

    protected void initInnerFunctions() {
        for (Object object : this.innerFunctionInstances) {
            initInnerFunction(object);
        }
    }

    protected void initInnerFunction(Object instance) {
        Method[] methods = instance.getClass().getMethods();
        for (Method method : methods) {
            if (Modifier.isPublic(method.getModifiers())) {
                FunctionEnabled function = method.getAnnotation(FunctionEnabled.class);
                if (function != null) {
                    String functionName = toUpper(function.value());
                    checkExist(functionName);
                    this.functionInvokerMap.put(functionName, new FunctionInvoker(method, instance));
                }
            }
        }
    }

    /**
     * check if there is an existing function with this functionName
     *
     * @param functionName
     */
    protected void checkExist(String functionName) {
        String _functionName = toUpper(functionName);
        if (this.functionInvokerMap.containsKey(_functionName))
            throw new IllegalStateException("function exists: " + _functionName);
    }

    private String toUpper(String functionName){
        return functionName.toUpperCase();
    }
}
