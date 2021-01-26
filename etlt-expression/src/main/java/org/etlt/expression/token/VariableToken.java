package org.etlt.expression.token;

import org.etlt.expression.ExpressionToken;
import org.etlt.expression.datameta.Variable;

public class VariableToken extends ExpressionToken {

    public VariableToken(String name){
        this.variable = new Variable(name);
        setTokenType(TokenType.TOKEN_TYPE_VARIABLE);
        setTokenText(name);
    }

    private final Variable variable ;

    public Variable getVariable(){
        return this.variable;
    }
}
