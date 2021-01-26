/**
 *
 */
package org.etlt.expression.format;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.etlt.expression.ExpressionToken;
import org.etlt.expression.IllegalExpressionException;
import org.etlt.expression.format.Element.ElementType;
import org.etlt.expression.op.Operator;

/**
 * @version 2.0
 */
public class ExpressionParser {

    private static Map<String, Operator> operators = new HashMap<String, Operator>();

    static {
        operators.put(Operator.NOT.getToken(), Operator.NOT);

        //	operators.put("-", NG); 负号和减号的差异通过上下文区分
        operators.put(Operator.MUTI.getToken(), Operator.MUTI);
        operators.put(Operator.DIV.getToken(), Operator.DIV);
        operators.put(Operator.MOD.getToken(), Operator.MOD);

        operators.put(Operator.PLUS.getToken(), Operator.PLUS);
        operators.put(Operator.MINUS.getToken(), Operator.MINUS);


        operators.put(Operator.LT.getToken(), Operator.LT);
        operators.put(Operator.LE.getToken(), Operator.LE);
        operators.put(Operator.GT.getToken(), Operator.GT);
        operators.put(Operator.GE.getToken(), Operator.GE);

        operators.put(Operator.EQ.getToken(), Operator.EQ);
        operators.put(Operator.NEQ.getToken(), Operator.NEQ);

        operators.put(Operator.AND.getToken(), Operator.AND);

        operators.put(Operator.OR.getToken(), Operator.OR);

        operators.put(Operator.APPEND.getToken(), Operator.APPEND);

        operators.put(Operator.SELECT.getToken(), Operator.SELECT);
        operators.put(Operator.QUES.getToken(), Operator.QUES);
        operators.put(Operator.COLON.getToken(), Operator.COLON);

    }

    /**
     * Get operator by name
     * @param name
     * @return
     */
    public static Operator getOperator(String name) {
        return operators.get(name);
    }

    private Stack<String> parenthesis = new Stack<String>();//匹配圆括号的栈

    public List<ExpressionToken> getExpressionTokens(String expression) {
        ExpressionReader eReader = new ExpressionReader(expression);
        List<ExpressionToken> list = new ArrayList<ExpressionToken>();
        try {
            ExpressionToken lastToken = null;
            for (Element element = eReader.readToken(); element != null; element = eReader.readToken()) {
                lastToken = convert(lastToken, element);
                //如果是括号，则记录下来，最后进行最后进行匹配
                pushParenthesis(element);
                addToken(list, lastToken);
            }
        } catch (IOException e) {
            throw new IllegalExpressionException("Expression lemma format is abnormal");
        } finally {
            eReader.close();
        }
        if (!parenthesis.isEmpty()) {
            throw new IllegalExpressionException("Bracket match error");
        }
        return list;
    }

    /**
     * If it is parentheses, record it, and perform the last match
     * @param element
     */
    public void pushParenthesis(Element element) {
        if (ElementType.SPLITTER == element.getType()) {
            if (element.getText().equals("(")) {
                parenthesis.push("(");
            } else if (element.getText().equals(")")) {
                if (parenthesis.isEmpty() || !parenthesis.peek().equals("(")) {
                    throw new IllegalExpressionException("Bracket match error");
                } else {
                    parenthesis.pop();
                }
            }
        }
    }

    public void addToken(List<ExpressionToken> list, ExpressionToken token) {
        //todo check nearby splitter token
//		if(!list.isEmpty()){
//			ExpressionToken lastToken = list.get(list.size() - 1);
//			if(lastToken.getTokenType() == ExpressionToken.TokenType.TOKEN_TYPE_SPLITTER
//					&& token.getTokenType() == ExpressionToken.TokenType.TOKEN_TYPE_SPLITTER){
//				if(!(lastToken.getSplitter().equals("(") && token.getSplitter().equals(")")))
//					throw new IllegalExpressionException("nearby splitter token found.", token.toString(), token.getStartPosition());
//			}
//		}
        list.add(token);
    }

    /**
     * Convert the segmented elements into ExpressionToken and verify whether the minus or the negative
     * @param previousToken
     * @param element
     * @return
     * @throws ParseException
     */
    public ExpressionToken convert(ExpressionToken previousToken, Element element) {
        return ExpressionToken.buildToken(previousToken, element);
    }

}
