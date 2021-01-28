/**
 * 
 */
package org.etlt.expression;

import org.etlt.expression.datameta.Literal;
import org.etlt.expression.datameta.Variable;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;


/**
 * Expression compiler, it's multi-thread safe.
 * @author juxiaomi
 * @version 2.0
 */
@Deprecated
public class ExpressionEvaluator {

	private ExpressionExecutor expressionExecutor = new ExpressionExecutor();

	public ExpressionExecutor getExpressionExecutor() {
		return expressionExecutor;
	}

	protected void check(String expression){
		if(StringUtils.isBlank(expression))
			throw new IllegalExpressionException("empty expression.");
	}
	/**
	 * 验证表达式
	 * 
	 * @param expression
	 * @return
	 */
	public String compile(String expression) {
		return compile(expression, null);
	}
	/**
	 * 验证表达式
	 * 
	 * @param expression
	 * @param variables
	 * @return
	 * @throws IllegalExpressionException
	 */
	public String compile(String expression, Collection<Variable> variables) {
		check(expression);
		try {
			// 获取上下文的变量，设置到脚本执行器中
			if (variables != null && variables.size() > 0) {
				for (Variable var : variables) {
					// 添加变来到脚本变量容器
					VariableContainer.addVariable(var);
				}
			}
			// 解析表达式词元
			List<ExpressionToken> expressionTokens = getExpressionExecutor().analyze(expression);
			// 转化RPN，并验证
			expressionTokens = getExpressionExecutor().compile(expressionTokens, null);
			// 以字符串形式输出RPN
			return getExpressionExecutor().tokensToString(expressionTokens);
		} catch (IllegalExpressionException e) {
			e.printStackTrace();
			throw new RuntimeException("表达式：\"" + expression + "\" 编译期检查异常");
		} finally {
			// 释放脚本变量容器
			VariableContainer.removeVariableMap();
		}
	}

	/**
	 * 获取预编译的表达式对象
	 * 
	 * @param expression 表达式的字符串表示
	 * @param variables  表达式的参数集合
	 * @return PreparedExpression 编译的表达式对象
	 * @throws IllegalExpressionException
	 */
	public PreparedExpression preparedCompile(String expression, Collection<Variable> variables) {
		check(expression);
		try {
			// 获取上下文的变量，设置到脚本执行器中
			if (variables != null && variables.size() > 0) {
				for (Variable var : variables) {
					// 添加变来到脚本变量容器
					VariableContainer.addVariable(var);
				}
			}
			// 解析表达式词元
			List<ExpressionToken> expTokens = getExpressionExecutor().analyze(expression);
			// 转化RPN，并验证
			expTokens = getExpressionExecutor().compile(expTokens, null);
			// 生成预编译表达式
			PreparedExpression pe = new PreparedExpression(expression, expTokens, VariableContainer.getVariableMap());
			return pe;
		} catch (IllegalExpressionException e) {
			e.printStackTrace();
			throw new RuntimeException("表达式：\"" + expression + "\" 预编译异常");
		} finally {
			// 释放脚本变量容器
			VariableContainer.removeVariableMap();
		}
	}

	/**
	 * 执行无变量表达式
	 * 
	 * @param expression
	 * @return
	 */
	public Object evaluate(String expression) {
		return evaluate(expression, null);
	}

	/**
	 * 根据流程上下文，执行公式语言
	 * 
	 * @param expression
	 * @param variables
	 * @return
	 */
	public Object evaluate(String expression, Collection<Variable> variables) {
		check(expression);
		try {
			// 获取上下文的变量，设置到脚本执行器中
			if (variables != null && variables.size() > 0) {
				for (Variable var : variables) {
					// 添加变量到脚本变量容器
					VariableContainer.addVariable(var);
				}
			}
			// 解析表达式词元
			List<ExpressionToken> expTokens = getExpressionExecutor().analyze(expression);
			// 转化RPN，并验证
			expTokens = getExpressionExecutor().compile(expTokens, null);
			// 执行RPN
			Literal constant = getExpressionExecutor().execute(expTokens, null);
			return constant.toJavaObject();

		} catch (IllegalExpressionException e) {
			e.printStackTrace();
			throw new RuntimeException("表达式：\"" + expression + "\" 执行异常");
		}  finally {
			// 释放脚本变量容器
			VariableContainer.removeVariableMap();
		}
	}

	/**
	 * 逐个添加表达式上下文变量
	 * 
	 * @param variable
	 */
	public static void addVarible(Variable variable) {
		// 添加变来到脚本变量容器
		VariableContainer.addVariable(variable);
	}

	/**
	 * 批量添加表达式上下文变量
	 * 
	 * @param variables
	 */
	public static void addVaribles(Collection<Variable> variables) {
		// 获取上下文的变量，设置到脚本执行器中
		if (variables != null && variables.size() > 0) {
			for (Variable var : variables) {
				// 添加变来到脚本变量容器
				VariableContainer.addVariable(var);
			}
		}
	}

}
