package org.etlt.op.ud;

import org.etlt.expression.VariableContext;
import org.etlt.expression.function.FunctionActor;
import org.etlt.expression.function.FunctionEnabled;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@FunctionEnabled("leftAmount")
public class LeftAmount implements FunctionActor {

    @Override
    public Object operate(VariableContext context, Object... args) {
        checkArgs(args);
        ProductAmount productAmount = new ProductAmount(args);
        ProductFactory productFactory = new ProductFactory();
        Product product = productFactory.getProduct(productAmount.buyCurrency, productAmount.sellCurrency);
        if (product == null)
            throw new IllegalArgumentException("unsupported product: " + productAmount.buyCurrency + productAmount.sellCurrency);
        return (productAmount.buyCurrency.equalsIgnoreCase(product.leftCurrency))
                ? productAmount.buyAmount : productAmount.sellAmount;
    }

    @Override
    public void checkArgs(Object... args) {
        if (args.length != 5) {
            throw new IllegalArgumentException("concat function need 5 argument(s): direction, buy currency by bank, buy amount by bank, sell currency by bank, sell amount by bank");
        }
    }

    protected static class ProductAmount {
        /**
         * direction by bank
         */
        final String direction;
        final String buyCurrency;
        final String buyAmount;
        final String sellCurrency;
        final String sellAmount;

        ProductAmount(Object... args) {
            this.direction = args[0].toString();
            this.buyCurrency = args[1].toString();
            this.buyAmount = args[2].toString();
            this.sellCurrency = args[3].toString();
            this.sellAmount = args[4].toString();
        }
    }

    protected static class ProductFactory {

        Map<String, Product> products = new HashMap<>();

        ProductFactory() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("products")));
                for (String text = reader.readLine(); text != null; text = reader.readLine()) {
                    String productId = text.trim();
                    if(productId.length() == 6)
                        addProduct(new Product(text.trim()));
                }
            }catch (IOException e){
                throw new IllegalArgumentException(e);
            }
        }

        Product getProduct(String currency1, String currency2) {
            Product product = this.products.get(currency1 + currency2);
            if (product != null)
                return product;
            else
                return this.products.get(currency2 + currency1);
        }

        void addProduct(Product product) {
            this.products.put(product.getName(), product);
        }
    }

    protected static class Product {
        String leftCurrency;
        String rightCurrency;

        Product(String productId){
            this(productId.substring(0,3), productId.substring(3));
        }

        Product(String leftCurrency, String rightCurrency) {
            this.leftCurrency = leftCurrency.toUpperCase();
            this.rightCurrency = rightCurrency.toUpperCase();
        }

        String getName() {
            return this.leftCurrency + this.rightCurrency;
        }
    }
}
