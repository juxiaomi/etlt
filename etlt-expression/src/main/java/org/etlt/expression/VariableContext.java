package org.etlt.expression;

import org.etlt.expression.datameta.Variable;

public interface VariableContext {

    Variable getVariable(String name);
}
