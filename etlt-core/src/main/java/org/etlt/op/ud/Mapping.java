package org.etlt.op.ud;

import org.etlt.job.JobContext;
import org.etlt.expression.VariableContext;
import org.etlt.expression.function.FunctionActor;

public class Mapping implements FunctionActor {

    @Override
    public Object operate(VariableContext context, Object... args) {
        checkArgs(args);
        String catalog = args[0].toString();
        String key = args[1].toString();
        Object value = ((JobContext)context).mapping(catalog, key);
        return (value == null && args.length > 2) ?
                args[2] :
                value;
    }

    @Override
    public void checkArgs(Object... args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("mapping function need 2 argument at least: mapping catalog , mapping key and possible default value");
        }
    }
}
