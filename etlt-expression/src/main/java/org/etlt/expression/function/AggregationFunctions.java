package org.etlt.expression.function;

import java.math.BigDecimal;

/**
 * system default functions for Aggregation
 * @version 2.0
 */
public class AggregationFunctions {

    @FunctionEnabled(value = "max", help = "get max element of all input numbers")
    public Object max(Object ... args){
        return null;
    }

    @FunctionEnabled("sum")
    public Object sum(Object... args){
        if(args.length < 2){
            throw new IllegalArgumentException("at least 2 number is necessary.");
        }
        BigDecimal result = new BigDecimal(0);
        for(Object obj : args){
            result = result.add(new BigDecimal(String.valueOf(obj)));
        }
        return result;
    }
}
