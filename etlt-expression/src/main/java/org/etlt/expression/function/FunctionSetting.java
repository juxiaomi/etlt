package org.etlt.expression.function;

import java.util.List;

public class FunctionSetting {

    private List<Class> functionByClass;

    private List<Class> functionByMethod;

    public List<Class> getFunctionByClass() {
        return functionByClass;
    }

    public void setFunctionByClass(List<Class> functionByClass) {
        this.functionByClass = functionByClass;
    }

    public List<Class> getFunctionByMethod() {
        return functionByMethod;
    }

    public void setFunctionByMethod(List<Class> functionByMethod) {
        this.functionByMethod = functionByMethod;
    }
}
