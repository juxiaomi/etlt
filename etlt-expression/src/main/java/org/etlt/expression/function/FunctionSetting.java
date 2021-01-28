package org.etlt.expression.function;

import java.util.List;
import java.util.Map;

public class FunctionSetting {

    private Map<String, Class> userDefined ;

    private List<Class> functions;

    public Map<String, Class> getUserDefined() {
        return userDefined;
    }

    public void setUserDefined(Map<String, Class> userDefined) {
        this.userDefined = userDefined;
    }

    public List<Class> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Class> functions) {
        this.functions = functions;
    }
}
