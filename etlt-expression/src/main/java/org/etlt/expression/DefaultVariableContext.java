package org.etlt.expression;

import org.etlt.expression.datameta.Variable;

import java.util.HashMap;
import java.util.Map;

public class DefaultVariableContext implements VariableContext{

    private Map<String, Object> dataContainer = new HashMap<>();

    @Override
    public Variable getVariable(String name) {
        return Variable.createVariable(name, dataContainer.get(name));
    }

    public void setData(String key, Object value){
        this.dataContainer.put(key, value);
    }
}
