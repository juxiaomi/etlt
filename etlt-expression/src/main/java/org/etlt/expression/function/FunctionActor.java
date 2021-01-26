package org.etlt.expression.function;

import org.etlt.expression.VariableContext;

public interface FunctionActor {

    Object operate(VariableContext context, Object ... args);

    void checkArgs(Object ... args);

    default String help(){
        return "unfinished help message.";
    }
}
