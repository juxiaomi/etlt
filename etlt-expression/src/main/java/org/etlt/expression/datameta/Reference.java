/**
 * 
 */
package org.etlt.expression.datameta;

import org.etlt.expression.ExpressionToken;
import org.etlt.expression.IllegalExpressionException;
import org.etlt.expression.VariableContext;
import org.etlt.expression.datameta.BaseDataMeta.DataType;
import org.etlt.expression.function.FunctionExecution;
import org.etlt.expression.op.Operator;

/**
 * Reference object
 * @author juxiaomi
 * @version 2.0
 */
public class Reference {

	private ExpressionToken token;

	private FunctionExecution functionExecution;

	private Literal[] arguments;
	// 引用对象实际的数据类型
	private DataType dataType;

	public Reference(FunctionExecution functionExecution, ExpressionToken token, Literal[] args)
			throws IllegalExpressionException {
		setFunctionExecution(functionExecution);
		this.token = token;
		this.arguments = args;
		// 计算Reference实际的数据类型
		if (ExpressionToken.TokenType.TOKEN_TYPE_FUNCTION == token.getTokenType()) {
			Literal result = getFunctionExecution().verify(token.getFunctionName(), token.getStartPosition(),
					this.arguments);
			dataType = result.getDataType();
		} else if (ExpressionToken.TokenType.TOKEN_TYPE_OPERATOR == token.getTokenType()) {
			Operator op = token.getOperator();
			Literal result = op.verify(token.getStartPosition(), this.arguments);
			dataType = result.getDataType();
		}
	}

	public FunctionExecution getFunctionExecution() {
		return functionExecution;
	}

	public void setFunctionExecution(FunctionExecution functionExecution) {
		this.functionExecution = functionExecution;
	}

	public DataType getDataType() {
		return dataType;
	}

	public Literal[] getArgs() {
		return arguments;
	}

	public void setArgs(Literal[] args) {
		this.arguments = args;
	}

	public ExpressionToken getToken() {
		return token;
	}

	public void setToken(ExpressionToken token) {
		this.token = token;
	}

	/**
	 * 执行引用对象指待的表达式（操作符或者函数）
	 *
	 * @return
	 */
	public Literal execute() throws IllegalExpressionException {
		return execute(null);
	}
	/**
	 * 执行引用对象指待的表达式（操作符或者函数）
	 * 
	 * @return
	 */
	public Literal execute(VariableContext context) throws IllegalExpressionException {
		// 记录Reference实际的数据类型
		/*
		 * if (ExpressionToken.ETokenType.ETOKEN_TYPE_FUNCTION == token.getTokenType())
		 * { Constant result = getFunctionExecution().verify(token.getFunctionName(),
		 * token.getStartPosition(), this.arguments); dataType = result.getDataType(); }
		 * else if (ExpressionToken.ETokenType.ETOKEN_TYPE_OPERATOR ==
		 * token.getTokenType()) { Operator op = token.getOperator(); Constant result =
		 * op.verify(token.getStartPosition(), this.arguments); dataType =
		 * result.getDataType(); }
		 */
		if (ExpressionToken.TokenType.TOKEN_TYPE_OPERATOR == token.getTokenType()) {
			// 执行操作符
			Operator op = token.getOperator();
			return op.execute(arguments);

		} else if (ExpressionToken.TokenType.TOKEN_TYPE_FUNCTION == token.getTokenType()) {
			// 执行函数
			return getFunctionExecution().execute(context, token.getFunctionName(), token.getStartPosition(), arguments);

		} else {
			throw new IllegalExpressionException("不支持的Reference执行异常");
		}
	}

}
