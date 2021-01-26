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
 * Expression compiler, it's multi-thread safe.<br>
 * compile progress is separated from execution
 * @author juxiaomi
 * @version 2.0
 */
public class ExpressionCompiler {

    private ExpressionExecutor expressionExecutor = new ExpressionExecutor();

    public ExpressionExecutor getExpressionExecutor() {
        return expressionExecutor;
    }

    public void setExpressionExecutor(ExpressionExecutor expressionExecutor) {
        this.expressionExecutor = expressionExecutor;
    }

    protected void check(String expression) {
        if (StringUtils.isBlank(expression))
            throw new IllegalExpressionException("empty expression.");
    }

    /**
     * Validation expression
     *
     * @param expression
     * @return
     */
    public String compile(String expression) {
        return compile(expression, null);
    }

    /**
     * Validation expression
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
            List<ExpressionToken> expTokens = getExpressionExecutor().analyze(expression);
            // transfer to RPN(suffix expression list)
            expTokens = getExpressionExecutor().compile(expTokens, null);
            // 以字符串形式输出RPN
            return getExpressionExecutor().tokensToString(expTokens);
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
        if (StringUtils.isBlank(expression)) {
            throw new IllegalExpressionException("expression is empty.");
        }

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
            throw new RuntimeException("expression " + expression + " precompile error.");
        } finally {
            // 释放脚本变量容器
            VariableContainer.removeVariableMap();
        }
    }

    /**
     * Executing a expression without variable
     *
     * @param expression
     * @return
     */
    public Object evaluate(String expression) {
        return evaluate(expression, (VariableContext) null);
    }

    /**
     * Execute the expression according to the process context
     *
     * @param expression
     * @param variables
     * @return
     */
    @Deprecated
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
            throw new RuntimeException("expression ：" + expression + " execution error.");
        } finally {
            // 释放脚本变量容器
            VariableContainer.removeVariableMap();
        }
    }

    /**
     *
     * @param expression
     * @param context
     * @return
     */
    public Object evaluate(String expression, VariableContext context) {
        check(expression);
        // 解析表达式词元
        List<ExpressionToken> expTokens = getExpressionExecutor().analyze(expression);
        // 转化RPN，并验证
        expTokens = getExpressionExecutor().compile(expTokens, context);
        // 执行RPN
        Literal constant = getExpressionExecutor().execute(expTokens, context);
        return constant.toJavaObject();
    }


}
