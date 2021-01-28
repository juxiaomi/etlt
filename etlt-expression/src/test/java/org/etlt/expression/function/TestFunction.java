package org.etlt.expression.function;

import org.junit.Assert;
import org.junit.Test;

public class TestFunction {
    FunctionFactory functionFactory = FunctionFactory.getInstance();

    @Test
    public void testUdConcat(){
        FunctionInvoker functionInvoker = this.functionFactory.getFunction("concat1");
        Assert.assertNotNull(functionInvoker);
        Assert.assertEquals("abc + def", functionInvoker.invoke(null, "abc + ", "def"));
    }

    @Test
    public void testInnerConcat(){
        FunctionInvoker functionInvoker = this.functionFactory.getFunction("CONCAT");
        Assert.assertNotNull(functionInvoker);
        Assert.assertEquals("abc + def", functionInvoker.invoke(null, "abc + ", "def"));
    }
}
