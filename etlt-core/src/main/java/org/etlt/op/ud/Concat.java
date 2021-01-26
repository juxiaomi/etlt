package org.etlt.op.ud;

import org.etlt.expression.VariableContext;
import org.etlt.expression.function.FunctionActor;

public class Concat implements FunctionActor {
    @Override
    public Object operate(VariableContext context, Object... args) {
        checkArgs(args);
        StringBuilder sb = new StringBuilder();
        for (Object obj : args)
            sb.append(obj);
        return sb.toString();
    }

    @Override
    public void checkArgs(Object... args) {
        if(args.length != 2){
            throw new IllegalArgumentException("concat function need 2 arguments and they need to be 2 strings.");
        }
    }
}
