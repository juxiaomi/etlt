package org.etlt.expression.format;

import org.etlt.expression.ExpressionToken;
import org.junit.Test;

import java.util.List;

public class TestParser {
    @Test
    public void testParse()  {
        String expression = "$function($SYSDATE() ,0,0 , 7,0 ,0,aa ) > [2008-10-01]";
        ExpressionParser ep = new ExpressionParser();
        List<ExpressionToken> list = ep.getExpressionTokens(expression);
    }
}
