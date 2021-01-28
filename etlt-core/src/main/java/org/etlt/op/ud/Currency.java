package org.etlt.op.ud;

import org.etlt.expression.VariableContext;
import org.etlt.expression.function.FunctionActor;

public class Currency implements FunctionActor {
    @Override
    public Object operate(VariableContext context, Object... args) {
        checkArgs(args);
        String prdId = args[0].toString();
        return (prdId.equalsIgnoreCase("XAGCNY") || prdId.equalsIgnoreCase("XAUCNY"))
                ? "CNY"
                : "USD";
    }

    @Override
    public void checkArgs(Object... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("currency function need 1 argument(2): productId.");
        }
    }
}
