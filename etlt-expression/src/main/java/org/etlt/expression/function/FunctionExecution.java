/**
 *
 */
package org.etlt.expression.function;

import org.etlt.expression.IllegalExpressionException;
import org.etlt.expression.VariableContext;
import org.etlt.expression.datameta.BaseDataMeta;
import org.etlt.expression.datameta.Literal;
import org.etlt.expression.datameta.Reference;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;


/**
 * Operator and method call
 *
 * @version 2.0
 */
public class FunctionExecution {

    private FunctionExecutor functionExecutor = FunctionFactory.getInstance();

    public FunctionExecution() {
    }

    public FunctionExecutor getFunctionExecutor() {
        return functionExecutor;
    }

    public void setFunctionExecutor(FunctionExecutor functionExecutor) {
        this.functionExecutor = functionExecutor;
    }

    /**
     * According to the function name and parameter array, execute the operation and return the result token
     *
     * @param functionName
     * @param position
     * @param args Note that since the parameters in args are ejected from the stack in LIFO order, they must be fetched from the tail backwards
     * @return
     * @throws IllegalExpressionException
     */
    public Literal execute(VariableContext context, String functionName, int position, Literal[] args) throws IllegalExpressionException {
        if (functionName == null) {
            throw new IllegalArgumentException("The function name is empty");
        }
        if (args == null) {
            throw new IllegalArgumentException("The function parameter list is empty.");
        }
        for (int i = 0; i < args.length; i++) {
            // 如果参数为引用类型，则执行引用
            if (args[i].isReference()) {
                Reference ref = (Reference) args[i].getDataValue();
                ref.setFunctionExecution(this);
                args[i] = ref.execute(context);
            }
        }

        // 转化方法参数
        Object[] parameters;
        try {
            parameters = convertParameters(functionName, position, args);
        } catch (IllegalExpressionException e) {
            throw new IllegalArgumentException("函数\"" + functionName + "\"运行时参数类型错误");
        }

        try {
            Object result = getFunctionExecutor().invokeFunction(context, functionName, parameters);

            if (result instanceof Boolean) {
                return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN, result);

            } else if (result instanceof Date) {
                return new Literal(BaseDataMeta.DataType.DATATYPE_DATE, result);

            } else if (result instanceof Double) {
                return new Literal(BaseDataMeta.DataType.DATATYPE_DOUBLE, result);

            } else if (result instanceof Float) {
                return new Literal(BaseDataMeta.DataType.DATATYPE_FLOAT, result);

            } else if (result instanceof Integer) {
                return new Literal(BaseDataMeta.DataType.DATATYPE_INT, result);

            } else if (result instanceof Long) {
                return new Literal(BaseDataMeta.DataType.DATATYPE_LONG, result);

            } else if (result instanceof String) {
                return new Literal(BaseDataMeta.DataType.DATATYPE_STRING, result);

            } else if (result instanceof List) {
                return new Literal(BaseDataMeta.DataType.DATATYPE_LIST, result);

            } else {
                return new Literal(BaseDataMeta.DataType.DATATYPE_OBJECT, result);

            }
        } catch (NoSuchMethodException | IllegalAccessException e) {
            // 抛异常
            e.printStackTrace();
            throw new IllegalStateException("function \"" + functionName + "\" not exists or wrong wrong parameters match");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new IllegalExpressionException("function \"" + functionName + "\" wrong parameters match");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new IllegalExpressionException(
                    "function \"" + functionName + "\" execution error:" + e.getTargetException().getMessage(), e.getTargetException());

        }
    }

    /**
     * Check whether the functions and parameters are legal and executable. If they are legal,
     * the token containing the execution result type will be returned. If not, null will be returned
     *
     * @param functionName
     * @param position
     * @param args         Note that since the parameters in args are ejected from the stack in LIFO order,
     *                        they must be fetched from the tail backwards
     * @return
     * @throws IllegalExpressionException
     */
    public Literal verify(String functionName, int position, BaseDataMeta[] args) throws IllegalExpressionException {
        if (functionName == null) {
            throw new IllegalArgumentException("Function name is empty");
        }
        // 通过方法名和参数数组，获取方法，及方法的返回值，并转化成ExpressionToken
        try {
            FunctionInvoker functionInvoker = getFunctionExecutor().getFunction(functionName);
            if (functionInvoker.getMethod() != null) {
                Method function = functionInvoker.getMethod();
                // 校验方法参数类型
                Class<?>[] parametersType = function.getParameterTypes();
                if (args.length == parametersType.length) {
                    // 注意，传入参数的顺序是颠倒的
                    for (int i = args.length - 1; i >= 0; i--) {
                        Class<?> javaType = args[i].mapTypeToJavaClass();
                        if (javaType != null) {
                            if (!isCompatibleType(parametersType[parametersType.length - i - 1], javaType)) {
                                // 抛异常
                                throw new IllegalExpressionException("函数\"" + functionName + "\"参数类型不匹配,函数参数定义类型为："
                                        + parametersType[parametersType.length - i - 1].getName() + " 传入参数实际类型为："
                                        + javaType.getName(), functionName, position);
                            }
                        } else {
                            // 传入参数为null，忽略类型校验
                        }
                    }
                }
                Class<?> returnType = function.getReturnType();

                // 转换成ExpressionToken
                if (boolean.class == returnType || Boolean.class == returnType) {
                    return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN, Boolean.FALSE);

                } else if (Date.class == returnType) {
                    return new Literal(BaseDataMeta.DataType.DATATYPE_DATE, null);

                } else if (double.class == returnType || Double.class == returnType) {
                    return new Literal(BaseDataMeta.DataType.DATATYPE_DOUBLE, Double.valueOf(0.0));

                } else if (float.class == returnType || Float.class == returnType) {
                    return new Literal(BaseDataMeta.DataType.DATATYPE_FLOAT, Float.valueOf(0.0f));

                } else if (int.class == returnType || Integer.class == returnType) {
                    return new Literal(BaseDataMeta.DataType.DATATYPE_INT, Integer.valueOf(0));

                } else if (long.class == returnType || Long.class == returnType) {
                    return new Literal(BaseDataMeta.DataType.DATATYPE_LONG, Long.valueOf(0L));

                } else if (String.class == returnType) {
                    return new Literal(BaseDataMeta.DataType.DATATYPE_STRING, null);

                } else if (List.class == returnType) {
                    return new Literal(BaseDataMeta.DataType.DATATYPE_LIST, null);

                } else if (Object.class == returnType) {
                    return new Literal(BaseDataMeta.DataType.DATATYPE_OBJECT, null);

                } else if (void.class == returnType || Void.class == returnType) {
                    return new Literal(BaseDataMeta.DataType.DATATYPE_OBJECT, null);
                } else {
                    throw new IllegalStateException("Parser internal error: Unsupported function return type");
                }
            } else if (functionInvoker.getInstance() != null) {
                return new Literal(BaseDataMeta.DataType.DATATYPE_OBJECT, null);
            } else {
                // 抛异常
                throw new IllegalExpressionException("function \"" + functionName + "\" not exists.", functionName, position);
            }

        } catch (SecurityException e) {
            // 抛异常
            throw new IllegalExpressionException("function \"" + functionName + "\"not exists or wrong parameters match", functionName, position);
        }
    }

    /**
     * Function parameter transformation
     *
     * @param args
     * @return
     * @throws IllegalExpressionException
     */
    private static Object[] convertParameters(String functionName, int position, Literal[] args)
            throws IllegalExpressionException {
        // 参数为空，返回空数组
        if (args == null) {
            return new Object[0];
        }

        // 转化方法参数类型数组
        Object[] parameters = new Object[args.length];
        for (int i = args.length - 1; i >= 0; i--) {
            parameters[args.length - 1 - i] = args[i].toJavaObject();
        }
        return parameters;
    }

    /**
     * 检查数据类型的兼容性 类型相同，一定兼容 如果parametersType 为Object 则兼容所有类型 如果parametersType
     * 为double 则兼容 int ，long ，float 如果parametersType 为float 则兼容 int ，long
     * 如果parametersType 为long 则兼容 int
     *
     * @param parametersType 方法定义的参数类型
     * @param argType        实际参数类型
     * @return
     */
    private static boolean isCompatibleType(Class<?> parametersType, Class<?> argType) {
        if (Object.class == parametersType) {
            return true;

        } else if (parametersType == argType) {
            return true;

        } else if (double.class == parametersType) {
            return float.class == argType || long.class == argType || int.class == argType;

        } else if (Double.class == parametersType) {
            return double.class == argType;

        } else if (float.class == parametersType) {
            return long.class == argType || int.class == argType;

        } else if (Float.class == parametersType) {
            return float.class == argType;

        } else if (long.class == parametersType) {
            return int.class == argType;

        } else if (Long.class == parametersType) {
            return long.class == argType;

        } else if (Integer.class == parametersType) {
            return int.class == argType;

        } // 参数类型为数组，参数值为List
        else if (parametersType.isArray() && List.class.isAssignableFrom(argType)) {
            return true;
        } else if (parametersType.isArray()) {
            return isCompatibleType(parametersType.getComponentType(), argType);
        }
        return false;

    }

}
