package org.etlt.expression;

import org.junit.Assert;
import org.junit.Test;

public class TestOperator {

    ExpressionCompiler expressionCompiler = new ExpressionCompiler();

    @Test
    public void testSimpleOperator(){
        String expression = "1 + 2";
        Assert.assertEquals(3, expressionCompiler.evaluate(expression));
    }

    @Test
    public void testPriority(){
        String expression = "1 + 2 * 3";
        Assert.assertEquals(7, expressionCompiler.evaluate(expression));

        expression = "1 + 2 * 3 / 5";
        Assert.assertEquals(2, expressionCompiler.evaluate(expression));

        expression = "1 + 2 * 3 / 4";
        Assert.assertEquals(2, expressionCompiler.evaluate(expression));

        expression = "(1 + 2) * 3";
        Assert.assertEquals(9, expressionCompiler.evaluate(expression));
    }
}
