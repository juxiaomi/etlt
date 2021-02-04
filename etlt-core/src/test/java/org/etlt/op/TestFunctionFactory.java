package org.etlt.op;

import org.etlt.Constants;
import org.etlt.job.JobContext;
import org.etlt.expression.function.FunctionFactory;
import org.etlt.expression.function.FunctionInvoker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

public class TestFunctionFactory {


    JobContext context;

    @Before
    public void init() throws IOException {
        File file = new File(Constants.CONFIG_DIRECTORY);
        context = new JobContext(file);
        context.init();
    }
    /**
     * test function concat
     * @throws IOException
     */
    @Test
    public void testConcat() throws IOException {
        FunctionFactory functionFactory = FunctionFactory.getInstance();
        FunctionInvoker function = functionFactory.getFunction("concat");
        Assert.assertNotNull(function);
        Assert.assertEquals("123456789",function.invoke(this.context,"1234", "56789"));
    }

    /**
     * test function concat
     * @throws IOException
     */
    @Test
    public void testConcatV2() throws IOException {
        FunctionFactory functionFactory = FunctionFactory.getInstance();
        FunctionInvoker function = functionFactory.getFunction("concat");
        Assert.assertNotNull(function);
        Assert.assertEquals("123456789",function.invoke(this.context,"1234", "56789"));
    }

    /**
     * test function substring
     */
    @Test
    public void testSubString(){
        FunctionFactory functionFactory = FunctionFactory.getInstance();
        FunctionInvoker function = functionFactory.getFunction("subString");
        Assert.assertNotNull(function);
        String s1= "0123456789";
        Assert.assertEquals("123456789",function.invoke(this.context,s1, 1));
        Assert.assertEquals("1234",function.invoke(this.context, s1, 1, 5));
    }
    @Test
    public void testMappingV2(){
        FunctionFactory functionFactory = FunctionFactory.getInstance();
        FunctionInvoker mapping = functionFactory.getFunction("mapping");
        Assert.assertEquals("03", mapping.invoke(this.context,"clientStatus","U"));
        Assert.assertEquals("02", mapping.invoke(this.context,"clientStatus","S"));
        Assert.assertEquals("CT1", mapping.invoke(this.context,"channel","105"));
        Assert.assertEquals("PC1", mapping.invoke(this.context,"channel","204"));
        Assert.assertEquals("1", mapping.invoke(this.context,"checkFlag","C"));
        Assert.assertEquals("2", mapping.invoke(this.context,"checkFlag","K"));
        Assert.assertEquals("2", mapping.invoke(this.context,"accountStatus","D"));
        Assert.assertEquals("1", mapping.invoke(this.context,"accountStatus","I"));
        Assert.assertEquals("1", mapping.invoke(this.context,"accountStatus","N"));
        Assert.assertEquals("B", mapping.invoke(this.context,"direction","B"));
        Assert.assertEquals("S", mapping.invoke(this.context,"direction","O"));
        Assert.assertEquals("P", mapping.invoke(this.context,"orderMode","0"));
        Assert.assertEquals("S", mapping.invoke(this.context,"orderMode","1"));
        Assert.assertEquals("O", mapping.invoke(this.context,"orderMode","2"));
        Assert.assertEquals("O", mapping.invoke(this.context,"orderMode","2", "UNKNOWN"));
        Assert.assertEquals("UNKNOWN", mapping.invoke(this.context,"orderMode","5", "UNKNOWN"));
    }

    @Test
    public void testMapping(){
        FunctionFactory functionFactory = FunctionFactory.getInstance();
        FunctionInvoker mapping = functionFactory.getFunction("mapping");
        Assert.assertEquals("03", mapping.invoke(this.context,"clientStatus","U"));
        Assert.assertEquals("02", mapping.invoke(this.context,"clientStatus","S"));
        Assert.assertEquals("CT1", mapping.invoke(this.context,"channel","105"));
        Assert.assertEquals("PC1", mapping.invoke(this.context,"channel","204"));
        Assert.assertEquals("1", mapping.invoke(this.context,"checkFlag","C"));
        Assert.assertEquals("2", mapping.invoke(this.context,"checkFlag","K"));
        Assert.assertEquals("2", mapping.invoke(this.context,"accountStatus","D"));
        Assert.assertEquals("1", mapping.invoke(this.context,"accountStatus","I"));
        Assert.assertEquals("1", mapping.invoke(this.context,"accountStatus","N"));
        Assert.assertEquals("B", mapping.invoke(this.context,"direction","B"));
        Assert.assertEquals("S", mapping.invoke(this.context,"direction","O"));
        Assert.assertEquals("P", mapping.invoke(this.context,"orderMode","0"));
        Assert.assertEquals("S", mapping.invoke(this.context,"orderMode","1"));
        Assert.assertEquals("O", mapping.invoke(this.context,"orderMode","2"));
        Assert.assertEquals("O", mapping.invoke(this.context,"orderMode","2", "UNKNOWN"));
        Assert.assertEquals("UNKNOWN", mapping.invoke(this.context,"orderMode","5", "UNKNOWN"));
    }

    @Test
    public void testCurrency(){
        FunctionFactory functionFactory = FunctionFactory.getInstance();
        FunctionInvoker function = functionFactory.getFunction("currency");
        Assert.assertEquals("USD", function.invoke(this.context,"USDJPY"));
        Assert.assertEquals("CNY", function.invoke(this.context,"XAGCNY"));
        Assert.assertEquals("CNY", function.invoke(this.context,"XAUCNY"));
    }

    @Test
    public void testLeftCurrency(){
        FunctionFactory functionFactory = FunctionFactory.getInstance();
        FunctionInvoker function = functionFactory.getFunction("leftCurrency");
        Assert.assertEquals("USD", function.invoke(this.context, "USD", "JPY"));
    }

    @Test
    public void testRightCurrency(){
        FunctionFactory functionFactory = FunctionFactory.getInstance();
        FunctionInvoker function = functionFactory.getFunction("rightCurrency");
        Assert.assertEquals("JPY", function.invoke(this.context, "USD", "JPY"));
    }

    @Test
    public void testLeftAmount(){
        FunctionFactory functionFactory = FunctionFactory.getInstance();
        FunctionInvoker function = functionFactory.getFunction("leftAmount");
        try {
            Assert.assertEquals("100", function.invoke(this.context, "B", "USD", 100, "JPT", 90000));
            throw new IllegalArgumentException();
        }catch (Exception e){
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
        Assert.assertEquals("100", function.invoke(this.context, "B", "USD",100, "JPY",90000));
    }
    @Test
    public void testRightAmount(){
        FunctionFactory functionFactory = FunctionFactory.getInstance();
        FunctionInvoker function = functionFactory.getFunction("rightAmount");
        Assert.assertEquals("90000", function.invoke(this.context, "B", "USD",100, "JPY",90000));
    }

    @Test
    public void testDivide(){
        FunctionFactory functionFactory = FunctionFactory.getInstance();
        FunctionInvoker function = functionFactory.getFunction("divide");
        Assert.assertEquals(new BigDecimal("40.00"), function.invoke(this.context, "1000", "25", 2 , 4));
        Assert.assertEquals(new BigDecimal("40"), function.invoke(this.context, "1000", "25", 0 , 4));
    }
}
