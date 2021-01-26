/**
 *
 */
package org.etlt.expression;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 *
 */
public class TestFunction {

    ExpressionCompiler expressionCompiler = new ExpressionCompiler();

    protected ExpressionCompiler getExpressionCompiler() {
        return this.expressionCompiler;
    }

    /**
     * Simply test the operators in the expression
     *
     * @throws Exception
     */
    @Test
    public void testOperators() throws Exception {
        ExpressionExecutor expressionExecutor = getExpressionCompiler().getExpressionExecutor();
//		// APPEND deprecated
//		expressions.add("'a String ' # true # 111 # [2009-10-10 10:10:10] # null");
        // 算术符
        String expression = "-1 + 2 - 3 * 4 / 5 % 6";
        List<ExpressionToken> parsedList = expressionExecutor.analyze(expression);
        List<ExpressionToken> compiledList = expressionExecutor.compile(parsedList, null);
        Assert.assertEquals(-1, expressionExecutor.execute(compiledList).toJavaObject());
        //
        expression = " -1  + 3 * 2";
        parsedList = expressionExecutor.analyze(expression);
        compiledList = expressionExecutor.compile(parsedList, null);
        Assert.assertEquals(5, expressionExecutor.execute(compiledList).toJavaObject());
        //
        expression = "'12345' <= '223'";
        parsedList = expressionExecutor.analyze(expression);
        compiledList = expressionExecutor.compile(parsedList, null);
        Assert.assertEquals(true, expressionExecutor.execute(compiledList).toJavaObject());
        //
        expression = "[2007-01-01] <= [2008-01-01]";
        parsedList = expressionExecutor.analyze(expression);
        compiledList = expressionExecutor.compile(parsedList, null);
        Assert.assertEquals(true, expressionExecutor.execute(compiledList).toJavaObject());
    }

    /**
     *
     * @throws Exception
     */
	@Test
    public void testInnerFunctionSysdate() throws Exception {
        ExpressionExecutor expressionExecutor = getExpressionCompiler().getExpressionExecutor();
        String expression = "$SYSDATE()";
        List<ExpressionToken> parsedList = expressionExecutor.analyze(expression);
        List<ExpressionToken> compiledList = expressionExecutor.compile(parsedList, null);
        Assert.assertTrue(expressionExecutor.execute(compiledList).toJavaObject() instanceof Date);
    }

    @Test
    public void testUdFunctionConcat() throws Exception {
        ExpressionExecutor expressionExecutor = getExpressionCompiler().getExpressionExecutor();
        String expression = "$concat('123','456', someObject)";
        List<ExpressionToken> parsedList = expressionExecutor.analyze(expression);
        List<ExpressionToken> compiledList = expressionExecutor.compile(parsedList, null);
        DefaultVariableContext context = new DefaultVariableContext();
        context.setData("someObject", "unknown");
        Assert.assertEquals("123456unknown", expressionExecutor.execute(compiledList, context).toJavaObject());
        expression = "$CONCAT('123',$CONCAT('456', '789'))";
        Assert.assertEquals("123456789", getExpressionCompiler().evaluate(expression));
    }

}
