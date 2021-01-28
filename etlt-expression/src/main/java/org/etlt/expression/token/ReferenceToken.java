package org.etlt.expression.token;

import org.etlt.expression.ExpressionToken;
import org.etlt.expression.datameta.Literal;
import org.etlt.expression.datameta.Reference;

public class ReferenceToken extends ExpressionToken {

    public ReferenceToken(Reference reference){
        this.constant = new Literal(reference);
        setTokenType(TokenType.TOKEN_TYPE_LITERAL);
        if(reference != null){
            setTokenText(constant.getDataValueText());
        }
    }

    private final Literal constant ;

    public Literal getConstant(){
        return this.constant;
    }
}
