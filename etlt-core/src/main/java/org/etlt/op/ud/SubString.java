package org.etlt.op.ud;

import org.etlt.expression.VariableContext;
import org.etlt.expression.function.FunctionActor;

public class SubString implements FunctionActor {
    @Override
    public Object operate(VariableContext context, Object... args) {
        checkArgs(args);
        String s1 = args[0].toString();
        int from = Integer.parseInt(args[1].toString());
        if(args.length > 2){
            int to = Integer.parseInt(args[2].toString());
            return s1.substring(from, to);
        }else
        return s1.substring(from);
    }

    @Override
    public void checkArgs(Object... args) {
        int length = args.length;
        if(length != 2 && length != 3)
            throw new IllegalArgumentException("substring function need 2 or 3 args.");
    }
}
