package org.etlt.expression.function;

import org.etlt.expression.IllegalExpressionException;
import org.etlt.expression.VariableContext;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


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

    public Object invoke(Object... args) {
        return invoke(null, args);
    }

    /**
     * @param actualArgs args list
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public Object invoke(VariableContext context, Object... actualArgs) {
        if (this.method == null) {
            return ((FunctionActor) instance).operate(context, actualArgs);
        }
        Class<?> parameterTypes[] = method.getParameterTypes();
        if(actualArgs == null){
            try {
                return method.invoke(instance, actualArgs);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalExpressionException(e);
            }
        }
        if (parameterTypes.length != actualArgs.length) {
        //needed parameter number not equal to parameters provided
            if(parameterTypes.length != 1){
                throw new IllegalArgumentException("wrong number of parameters, needed is " + parameterTypes.length + ", provided is " + actualArgs.length);
            }
            Class<?> clazz = parameterTypes[0];
            if(clazz.isArray()){// Array is supported
                try {
                    return method.invoke(instance, new Object[]{actualArgs});
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new IllegalExpressionException(e);
                }
            }
            if(clazz.isAssignableFrom(List.class)){ // List is supported
                List<Object> list = new ArrayList<>();
                for (Object obj : actualArgs) {
                    list.add(obj);
                }
                try {
                    return method.invoke(instance, list);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new IllegalExpressionException(e);
                }
            }
            throw new IllegalArgumentException("unsupported now, please expect.");
        } else {
            for (int i = 0; i < actualArgs.length; i++) {
                if (!parameterTypes[i].isAssignableFrom(actualArgs[i].getClass())) {
                    // list 转 数组
                    if (parameterTypes[i].isArray() && (List.class.isAssignableFrom(actualArgs[i].getClass()))) {
                        @SuppressWarnings("unchecked")
                        List<Object> list = (List<Object>) actualArgs[i];
                        Object array = Array.newInstance(parameterTypes[i].getComponentType(), list.size());
                        for (int j = 0; j < list.size(); j++) {
                            Array.set(array, j, list.get(j));
                        }
                        actualArgs[i] = array;
                    } // 对象 转 数组
                    else if (parameterTypes[i].isArray() && parameterTypes[i].getComponentType().isAssignableFrom(actualArgs[i].getClass())) {
                        Object array = Array.newInstance(parameterTypes[i].getComponentType(), 1);
                        Array.set(array, 0, actualArgs[i]);
                        actualArgs[i] = array;
                    } // 对象 转 list
                    else if (parameterTypes[i].isAssignableFrom(List.class)) {
                        List<Object> list = new ArrayList<>();
                        list.add(actualArgs[i]);
                        actualArgs[i] = list;
                    }
                }
            }
            try {
                return method.invoke(instance, actualArgs);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalExpressionException(e);
            }
        }
    }
}
