package org.etlt.op.ud;

import org.etlt.expression.VariableContext;
import org.etlt.expression.function.FunctionActor;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Divide implements FunctionActor {
    @Override
    public Object operate(VariableContext context, Object... args) {
        checkArgs(args);
        BigDecimal v1 = new BigDecimal(String.valueOf(args[0]));
        BigDecimal v2 = new BigDecimal(String.valueOf(args[1]));
        int scale = Integer.parseInt(args[2].toString());
        RoundingMode roundingMode = RoundingMode.valueOf(Integer.parseInt(args[3].toString()));
        return v1.divide(v2,scale, roundingMode);
    }

    @Override
    public void checkArgs(Object... args) {
        if(args.length != 4){
            throw new IllegalArgumentException("divide function need 4 arguments.");
        }
    }
}
