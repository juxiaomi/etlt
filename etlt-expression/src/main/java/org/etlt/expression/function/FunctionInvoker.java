package org.etlt.expression.function;

import org.etlt.expression.IllegalExpressionException;
import org.etlt.expression.VariableContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class FunctionInvoker {
    private Method method;
    final private Object instance;

    public FunctionInvoker(Method method, Object instance) {
        this.method = method;
        this.instance = instance;
    }

    public FunctionInvoker(FunctionActor functionActor) {
        this.instance = functionActor;
    }

    public Method getMethod() {
        return method;
    }

    public Object getInstance() {
        return instance;
    }

    /**
     *
     * @param args 参数值列表
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public Object invoke(VariableContext context, Object ... args) {
        if (this.method == null) {
            return ((FunctionActor) instance).operate(context, args);
        }
        Class<?> types[] = method.getParameterTypes();
//        for (int i = 0; i < args.length; i++) {
//            if (!types[i].isAssignableFrom(args[i].getClass())) {
//                // list 转 数组
//                if (types[i].isArray() && (List.class.isAssignableFrom(args[i].getClass()))) {
//                    @SuppressWarnings("unchecked")
//                    List<Object> list = (List<Object>) args[i];
//                    Object array = Array.newInstance(types[i].getComponentType(), list.size());
//                    for (int j = 0; j < list.size(); j++) {
//                        Array.set(array, j, list.get(j));
//                    }
//                    args[i] = array;
//                } // 对象 转 数组
//                else if (types[i].isArray() && types[i].getComponentType().isAssignableFrom(args[i].getClass())) {
//                    Object array = Array.newInstance(types[i].getComponentType(), 1);
//                    Array.set(array, 0, args[i]);
//                    args[i] = array;
//                } // 对象 转 list
//                else if (types[i].isAssignableFrom(List.class)) {
//                    List<Object> list = new ArrayList<>();
//                    list.add(args[i]);
//                    args[i] = list;
//                }
//            }
//        }
        try {
            return method.invoke(instance, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalExpressionException(e);
        }
    }
}
