package org.etlt.expression.function;

import org.etlt.expression.VariableContext;

import java.lang.reflect.InvocationTargetException;

public interface FunctionExecutor {
	public Object invokeFunction(VariableContext context, String functionName, Object[] parameters)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

	FunctionInvoker getFunction(String functionName);
}
