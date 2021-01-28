/**
 * 
 */
package org.etlt.expression;

import org.etlt.expression.datameta.Literal;
import org.etlt.expression.datameta.Reference;
import org.etlt.expression.datameta.Variable;
import org.etlt.expression.datameta.BaseDataMeta.DataType;
import org.etlt.expression.format.Element;
import org.etlt.expression.format.ExpressionParser;
import org.etlt.expression.op.Operator;
import org.etlt.expression.token.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Expression parsing word element object
 * @version 2.0 
 */
public abstract class ExpressionToken {
	

	//词元的语法类型
	public enum TokenType {
		//常量
		TOKEN_TYPE_LITERAL,
		//变量
		TOKEN_TYPE_VARIABLE,
		//操作符
		TOKEN_TYPE_OPERATOR,
		//函数
		TOKEN_TYPE_FUNCTION,
		//分隔符
		TOKEN_TYPE_SPLITTER,
		;
	}
	
	//Token的词元类型：常量，变量，操作符，函数，分割符
	private TokenType tokenType ;
//	private Literal constant;
//	private Variable variable ;
//	private Operator operator;
	private String tokenText ;
	//词元在表达式中的起始位置
	private int startPosition = -1;

	protected void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}

	protected void setTokenText(String text){
		this.tokenText = text;
	}

	protected String getTokenText(){
		return this.tokenText;
	}

	public static ExpressionToken buildToken(ExpressionToken previousToken, Element element){
		ExpressionToken token = null;
		if (Element.ElementType.NULL == element.getType()) {
			token =  ExpressionToken.createLiteralToken(DataType.DATATYPE_UNKNOWN, null);
		} else if (Element.ElementType.STRING == element.getType()) {
			token =  ExpressionToken.createLiteralToken(DataType.DATATYPE_STRING, element.getText());
		} else if (Element.ElementType.BOOLEAN == element.getType()) {
			token =  ExpressionToken.createLiteralToken(DataType.DATATYPE_BOOLEAN, Boolean.valueOf(element.getText()));
		} else if (Element.ElementType.INT == element.getType()) {
			token =  ExpressionToken.createLiteralToken(DataType.DATATYPE_INT, Integer.valueOf(element.getText()));
		} else if (Element.ElementType.LONG == element.getType()) {
			token =  ExpressionToken.createLiteralToken(DataType.DATATYPE_LONG, Long.valueOf(element.getText()));
		} else if (Element.ElementType.FLOAT == element.getType()) {
			token =  ExpressionToken.createLiteralToken(DataType.DATATYPE_FLOAT, Float.valueOf(element.getText()));
		} else if (Element.ElementType.DOUBLE == element.getType()) {
			token =  ExpressionToken.createLiteralToken(DataType.DATATYPE_DOUBLE, Double.valueOf(element.getText()));
		} else if (Element.ElementType.DATE == element.getType()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				token =  ExpressionToken.createLiteralToken(DataType.DATATYPE_DATE, sdf.parse(element.getText()));
			} catch (ParseException e) {
				throw new IllegalExpressionException("parse error: ", element.getText(), element.getIndex());
			}
		} else if (Element.ElementType.VARIABLE == element.getType()) {
			token =  ExpressionToken.createVariableToken(element.getText());
		} else if (Element.ElementType.OPERATOR == element.getType()) {
			//区分负号
			if (element.getText().equals("-") && (
					previousToken == null //以“-”开头肯定是负号
							|| previousToken.getTokenType() == ExpressionToken.TokenType.TOKEN_TYPE_OPERATOR //运算符后面肯定是负号
							|| previousToken.getTokenType() == ExpressionToken.TokenType.TOKEN_TYPE_SPLITTER //“(”或“,”后面肯定是负号
							&& !")".equals(previousToken.getSplitter())
			)) {
				token = ExpressionToken.createOperatorToken(Operator.NG);
			} else {
				token =  ExpressionToken.createOperatorToken(ExpressionParser.getOperator(element.getText()));
			}
		} else if (Element.ElementType.FUNCTION == element.getType()) {
			token =  ExpressionToken.createFunctionToken(element.getText());
		} else if (Element.ElementType.SPLITTER == element.getType()) {
			token =  ExpressionToken.createSplitterToken(element.getText());
		}
		token.setStartPosition(element.getIndex());
		return token;
	}

	public static ExpressionToken buildToken(ExpressionToken token, DataType dataType, Object data){
		switch (token.getTokenType()){
			case TOKEN_TYPE_LITERAL:
				return new LiteralToken(dataType, data);
			default:
				break;
		}
		throw new UnsupportedOperationException();
	}

	@Deprecated
	public static LiteralToken createLiteralToken(DataType dataType , Object dataValue){
//		ExpressionToken instance = new ExpressionToken();
//		instance.constant = new Literal(dataType , dataValue);
//		instance.tokenType = TokenType.TOKEN_TYPE_LITERAL;
//		if(dataValue != null){
//			instance.tokenText=  instance.constant.getDataValueText();
//		}
//		return instance;
		return new LiteralToken(dataType, dataValue);
	}

	@Deprecated
	public static LiteralToken createLiteralToken(Literal constant){
//		if(constant == null){
//			throw new IllegalArgumentException("非法参数异常：常量为null" );
//		}
//		ExpressionToken instance = new ExpressionToken();
//		instance.constant = constant;
//		instance.tokenType = TokenType.TOKEN_TYPE_LITERAL;
//		if(constant.getDataValue() != null){
//			instance.tokenText=  constant.getDataValueText();
//		}
//		return instance;
		return new LiteralToken(constant);
	}

	@Deprecated
	public static VariableToken createVariableToken(String variableName){
//		ExpressionToken instance = new ExpressionToken();
//		instance.variable = new Variable(variableName);
//		instance.tokenType = TokenType.TOKEN_TYPE_VARIABLE;
//		instance.tokenText = variableName;
//		return instance;
		return new VariableToken(variableName);
	}
	
	public static ReferenceToken createReference(Reference reference){
//		ExpressionToken instance = new ExpressionToken();
//		instance.constant = new Literal(ref);
//		instance.tokenType = TokenType.TOKEN_TYPE_LITERAL;
//		if(ref != null){
//			instance.tokenText=  instance.constant.getDataValueText();
//		}
//		return instance;
		return new ReferenceToken(reference);
	}	

	@Deprecated
	public static FunctionToken createFunctionToken(String functionName){
//		if(functionName == null){
//			throw new IllegalExpressionException("Illegal parameter: empty function name");
//		}
//		ExpressionToken instance = new ExpressionToken();
//		instance.tokenText = functionName;
//		instance.tokenType = TokenType.TOKEN_TYPE_FUNCTION;
//		return instance;
		return new FunctionToken(functionName);
	}

	@Deprecated
	public static OperatorToken createOperatorToken(Operator operator){
//		if(operator == null){
//			throw new IllegalExpressionException("Illegal parameter: empty operator");
//		}
//		ExpressionToken instance = new ExpressionToken();
//		instance.operator = operator;
//		instance.tokenText = operator.getToken();
//		instance.tokenType = TokenType.TOKEN_TYPE_OPERATOR;
//		return instance;
		return new OperatorToken(operator);
	}

	@Deprecated
	public static SplitterToken createSplitterToken(String splitterText){
//		if(splitterText == null){
//			throw new IllegalExpressionException("Illegal parameter: empty splitter");
//		}
//		ExpressionToken instance = new ExpressionToken();
//		instance.tokenText = splitterText;
//		instance.tokenType = TokenType.TOKEN_TYPE_SPLITTER;
//		return instance;
		return new SplitterToken(splitterText);
	}
	
	/**
	 */
	protected ExpressionToken(){
	}
	
	/**
	 * Gets the token's lexical type
	 * @return
	 */
	public TokenType getTokenType() {
		return tokenType;
	}

	
	/**
	 * Gets the constant description of the token
	 * @return
	 */
	public Literal getConstant(){
		throw new UnsupportedOperationException();
	};

	/**
	 * Gets the constant description of the token
	 * @return
	 */
	public Variable getVariable(){
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Gets the operator type value of token
	 * @return
	 */
	public Operator getOperator(){
		throw new UnsupportedOperationException();
	};
	
	/**
	 * Gets the method name type value of token
	 * @return
	 */
	public String getFunctionName(){
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Gets the value of the token's separator type
	 * @return
	 */	
	public String getSplitter(){
		throw new UnsupportedOperationException();
	}

	public int getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(int startPosition) {
		this.startPosition = startPosition;
	}
	
	@Override
	public String toString(){
		return tokenText;
	}
}
