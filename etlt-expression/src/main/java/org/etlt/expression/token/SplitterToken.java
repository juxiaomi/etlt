package org.etlt.expression.token;

import org.etlt.expression.ExpressionToken;

public class SplitterToken extends ExpressionToken {

    public SplitterToken(String name){
        setTokenType(TokenType.TOKEN_TYPE_SPLITTER);
        setTokenText(name);
    }

    public String getSplitter(){
        return getTokenText();
    }
}
