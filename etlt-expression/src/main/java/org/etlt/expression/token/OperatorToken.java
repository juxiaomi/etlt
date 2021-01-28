package org.etlt.expression.token;

import org.etlt.expression.ExpressionToken;
import org.etlt.expression.format.ExpressionParser;
import org.etlt.expression.op.Operator;

public class OperatorToken extends ExpressionToken {

    public OperatorToken(String name){
        setTokenType(TokenType.TOKEN_TYPE_OPERATOR);
        setTokenText(name);
        this.operator = ExpressionParser.getOperator(name);
    }

    public OperatorToken(Operator operator){
        setTokenType(TokenType.TOKEN_TYPE_OPERATOR);
        setTokenText(operator.getToken());
        this.operator = operator;
    }

    private final Operator operator;

    public Operator getOperator(){
        return this.operator;
    }
}
