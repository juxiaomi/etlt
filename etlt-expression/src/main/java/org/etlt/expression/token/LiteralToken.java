package org.etlt.expression.token;

import org.etlt.expression.ExpressionToken;
import org.etlt.expression.datameta.BaseDataMeta;
import org.etlt.expression.datameta.Literal;

public class LiteralToken extends ExpressionToken {

    public LiteralToken(BaseDataMeta.DataType dataType, Object data) {
        constant = new Literal(dataType, data);
        setTokenType(TokenType.TOKEN_TYPE_LITERAL);
        setTokenText(constant.getDataValueText());
    }

    public LiteralToken(Literal literal) {
        constant = literal;
        setTokenType(TokenType.TOKEN_TYPE_LITERAL);
        if (constant.getDataValue() != null)
            setTokenText(constant.getDataValueText());
    }

    private final Literal constant;

    public Literal getConstant() {
        return this.constant;
    }
}
