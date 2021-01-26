package org.etlt.expression.token;

import org.etlt.expression.ExpressionToken;

public class FunctionToken extends ExpressionToken {

    public FunctionToken(String name){
        setTokenType(TokenType.TOKEN_TYPE_FUNCTION);
        setTokenText(name);
    }

    @Override
    public String getFunctionName(){
        return getTokenText();
    }
}
