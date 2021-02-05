package org.etlt.op.ud;

import org.etlt.expression.VariableContext;
import org.etlt.expression.function.FunctionEnabled;

@FunctionEnabled("rightAmount")
public class RightAmount extends LeftAmount {
    @Override
    public Object operate(VariableContext context, Object... args) {
        checkArgs(args);
        ProductAmount productAmount = new ProductAmount(args);
        ProductFactory productFactory = new ProductFactory();
        Product product = productFactory.getProduct(productAmount.buyCurrency, productAmount.sellCurrency);
        if (product == null)
            throw new IllegalArgumentException("unsupported product: " + productAmount.buyCurrency + productAmount.sellCurrency);
        return (productAmount.buyCurrency.equalsIgnoreCase(product.leftCurrency))
                ? productAmount.sellAmount : productAmount.buyAmount;
    }
}
