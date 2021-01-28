package org.etlt.expression.ud;

import org.etlt.expression.VariableContext;
import org.etlt.expression.function.FunctionActor;

public class Concat implements FunctionActor {
    @Override
    public Object operate(VariableContext context, Object... args) {
        StringBuilder stringBuilder = new StringBuilder();
        for(Object obj : args){
            stringBuilder.append(obj);
        }
        return stringBuilder.toString();
    }

    @Override
    public void checkArgs(Object... args) {

    }
}
