package org.etlt.expression.function;

import java.util.List;
import java.util.Map;

public class FunctionSetting {

    private List<Class> userDefined ;

    private List<Class> functions;

    public List<Class> getUserDefined() {
        return userDefined;
    }

    public void setUserDefined(List<Class> userDefined) {
        this.userDefined = userDefined;
    }

    public List<Class> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Class> functions) {
        this.functions = functions;
    }
}
