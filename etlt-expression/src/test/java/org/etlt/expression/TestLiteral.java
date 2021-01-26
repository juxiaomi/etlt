package org.etlt.expression;

import org.junit.Assert;
import org.junit.Test;

public class TestLiteral {

    ExpressionCompiler expressionCompiler = new ExpressionCompiler();

    @Test
    public void testLiteral(){
        String expression = "'1'";
        Assert.assertEquals("1", expressionCompiler.evaluate(expression));

        expression = "'\\'abc\\''";
        Assert.assertEquals("'abc'", expressionCompiler.evaluate(expression));
    }
}
