package org.etlt.expression;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.etlt.expression.datameta.Variable;
import org.junit.Assert;
import org.junit.Test;

public class TestVariable {

    ExpressionCompiler expressionCompiler = new ExpressionCompiler();

    protected ExpressionCompiler getExpressionCompiler() {
        return this.expressionCompiler;
    }

    /**
     * Simple test of expression operators with variables
     *
     * @throws Exception
     */
    @Test
    public void testOperators() throws Exception {
//		//算术符
//		expressions.add("vInt + 2 - 3 * 4 / 5 % 6");
////		//LE
//		expressions.add("vString <= '223'");
////		//GE
//		expressions.add("vDate >= [2008-01-01]");
////		//EQ
//		expressions.add("223 == vDouble");
//		expressions.add("223 == vNull");
////		//NEQ
//		expressions.add("vBoolean != false");
//		expressions.add("vNull != null");
//		expressions.add("vNull != 'a string'");
////		//AND
//		expressions.add("true && vBoolean");
////		//OR
//		expressions.add("vBoolean || false");
////		//NOT
//		expressions.add("!vBoolean");
////		//String +
//		expressions.add("vDate + vBoolean + vInt + vString + vNull  + vDouble + vBoolean");
//		//SELECT
//		expressions.add("false ? true ? vString_p1 : vString_p3 : vBoolean ? vString_p3 : vString_p4 ");
//		//APPEND
//		expressions.add("vString # vBoolean # vInt # vDate # vNull");

        //设置上下文变量
//        ArrayList<Variable> variables = new ArrayList<Variable>();
//        variables.add(Variable.createVariable("vInt", new Integer(-1)));
//        variables.add(Variable.createVariable("vString", "12345"));
//        variables.add(Variable.createVariable("vDate", new Date()));
//        variables.add(Variable.createVariable("vDouble", new Double(223.0)));
//        variables.add(Variable.createVariable("vBoolean", new Boolean(true)));
//        variables.add(Variable.createVariable("vNull", null));
//        variables.add(Variable.createVariable("vString_p1", "路径1"));
//        variables.add(Variable.createVariable("vString_p2", "路径2"));
//        variables.add(Variable.createVariable("vString_p3", "路径3"));
//        variables.add(Variable.createVariable("vString_p4", "路径4"));

        DefaultVariableContext context = new DefaultVariableContext();
        context.setData("vInt", new Integer(-1));

        String expression = "vInt + 2 - 3 * 4 / 5 % 6";
        Object result = getExpressionCompiler().evaluate(expression, context);
        Assert.assertEquals(-1, result);


        expression = "vString <= '223'";
        context.setData("vString", "23456");
        result = getExpressionCompiler().evaluate(expression, context);
        Assert.assertEquals(false, result);
//
        context.setData("vString", "123");
        result = getExpressionCompiler().evaluate(expression, context);
        Assert.assertEquals(true, result);

        expression = "entity.column == 'unknown'";
        context.setData("entity.column", "unknown");
        result = getExpressionCompiler().evaluate(expression, context);
        Assert.assertEquals(true, result);
    }

    /**
     * 测试带变量的内部函数
     *
     * @throws Exception
     */
//	@Test
    public void testInnerFunctions() throws Exception {
        System.out.println("testInnerFunctions");

        List<String> expressions = new ArrayList<String>();
        //$CONTAINS
        expressions.add("CONTAINS(vString1 ,'abc')");
        expressions.add("CONTAINS(vString2 ,'abc')");
        //$STARTSWITH
        expressions.add("STARTSWITH(vString2 ,'abc')");
        expressions.add("STARTSWITH(vString3 ,'abc')");
        //$ENDSWITH
        expressions.add("ENDSWITH(vString2 ,'abc')");
        expressions.add("ENDSWITH(vString3 ,'bcc')");
        //$CALCDATE
        expressions.add("CALCDATE(vDate,1,1,1,1,1,1)");
        expressions.add("CALCDATE(vDate,0,0,0,0,0,0)");
        expressions.add("CALCDATE(vDate,-1,-1,-1,-1,-1,-1)");
        expressions.add("CALCDATE(vDate,0,0,0,0,0,60)");
        expressions.add("CALCDATE(vDate,0,0,0,0,60,0)");
        expressions.add("CALCDATE(vDate,0,0,0,24,0,0)");
        expressions.add("CALCDATE(vDate,0,0,31,0,0,0)");
        expressions.add("CALCDATE(vDate,0,12,0,0,0,0)");
        //$DAYEQUALS
        expressions.add("DAYEQUALS(vDate,[2008-01-01])");

        //设置上下文变量
        List<Variable> variables = new ArrayList<Variable>();
        variables.add(Variable.createVariable("vString1", "aabbcc"));
        variables.add(Variable.createVariable("vString2", "aabcbcc"));
        variables.add(Variable.createVariable("vString3", "abccbcc"));
        variables.add(Variable.createVariable("vDate", new Date()));


        for (String expression : expressions) {
            System.out.println("expression : " + expression);
            Object result = getExpressionCompiler().evaluate(expression, variables);
            System.out.println("result = " + result);
            System.out.println();
        }
        System.out.println("----------------------------------------------------");
        System.out.println("--------------testInnerFunctions over---------------");
        System.out.println("----------------------------------------------------");
    }

}
