package org.etlt.expression.function;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class TestFunction {
    FunctionFactory functionFactory = FunctionFactory.getInstance();

    @Test
    public void testUdConcat(){
        FunctionInvoker functionInvoker = this.functionFactory.getFunction("concat1");
        Assert.assertNotNull(functionInvoker);
        Assert.assertEquals("abc + def", functionInvoker.invoke(null, "abc + ", "def"));
    }

    @Test
    public void testInnerStringFunctions(){
        FunctionInvoker functionInvoker = this.functionFactory.getFunction("CONCAT0");
        Assert.assertNotNull(functionInvoker);
        Assert.assertEquals("abc + def", functionInvoker.invoke(null, "abc + ", "def"));
    }

    @Test
    public void testInnerDateFunctions(){
        FunctionInvoker functionInvoker = this.functionFactory.getFunction("date");
        Assert.assertNotNull(functionInvoker.invoke(null, null));

        functionInvoker = this.functionFactory.getFunction("current_timestamp");
        Assert.assertNotNull(functionInvoker.invoke(null, null));
    }

    @Test
    public void testInnerAggregationFunctions(){
        FunctionInvoker functionInvoker = this.functionFactory.getFunction("sum");
        Assert.assertEquals(new BigDecimal(3), functionInvoker.invoke(1, 1, 1));
    }
}
