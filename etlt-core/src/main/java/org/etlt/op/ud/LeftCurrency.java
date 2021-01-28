package org.etlt.op.ud;

import org.etlt.expression.VariableContext;
import org.etlt.expression.function.FunctionActor;

public class LeftCurrency implements FunctionActor {
    @Override
    public Object operate(VariableContext context, Object... args) {
        checkArgs(args);
        LeftAmount.ProductFactory productFactory = new LeftAmount.ProductFactory();
        LeftAmount.Product product = productFactory.getProduct(args[0].toString(), args[1].toString());
        if (product == null)
            throw new IllegalArgumentException("unsupported product: " + args[0] + args[1]);
        return product.leftCurrency;
    }

    @Override
    public void checkArgs(Object... args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("currency function need 2 argument(2): currency1, currency2.");
        }
    }
}
