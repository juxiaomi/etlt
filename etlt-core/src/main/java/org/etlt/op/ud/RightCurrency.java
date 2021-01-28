package org.etlt.op.ud;

import org.etlt.expression.VariableContext;

public class RightCurrency extends LeftCurrency {
    @Override
    public Object operate(VariableContext context, Object... args) {
        checkArgs(args);
        LeftAmount.ProductFactory productFactory = new LeftAmount.ProductFactory();
        LeftAmount.Product product = productFactory.getProduct(args[0].toString(), args[1].toString());
        if (product == null)
            throw new IllegalArgumentException("unsupported product: " + args[0] + args[1]);
        return product.rightCurrency;
    }
}
