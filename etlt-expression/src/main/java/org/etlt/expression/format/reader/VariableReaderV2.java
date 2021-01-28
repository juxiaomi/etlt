
package org.etlt.expression.format.reader;

import org.etlt.expression.IllegalExpressionException;
import org.etlt.expression.format.Element;
import org.etlt.expression.format.Element.ElementType;
import org.etlt.expression.format.ExpressionReader;

import java.io.IOException;

/**
 * read a variable or a function
 * @version 2.1
 */
public class VariableReaderV2 implements ElementReader {

	public static final String STOP_CHAR = "+-*/%^<>=&|!?:#$),[]'\" \r\n\t";//end char of variable, without (, if ( found, it's a function

	public static final char IDENTIFIER_CHAR_OF_FUNCTION = '(';//函数

	public static final String TRUE_WORD = "true";
	public static final String FALSE_WORD = "false";
	@Deprecated
	public static final String NULL_WORD = "null";

	private ElementType elementType = ElementType.VARIABLE;
	/**
	 * 
	 * @param reader
	 * @return
	 */
	private String readWord(ExpressionReader reader) throws  IOException {
		StringBuffer sb = new StringBuffer();
		boolean readStart = true;
		int b = -1;
		while ((b = reader.read()) != -1) {
			elementType = ElementType.VARIABLE;
			char c = (char)b;
			if(c == IDENTIFIER_CHAR_OF_FUNCTION){// function found
				elementType = ElementType.FUNCTION;
				reader.reset();
				return sb.toString();
			}
			if (STOP_CHAR.indexOf(c) >= 0 && !readStart) {//单词停止符,并且忽略第一个字符
				reader.reset();
				return sb.toString();
			}
			if (!Character.isJavaIdentifierPart(c) && c != '.') {// only support java identifier character or .
				throw new IllegalExpressionException("illegal character found：" + c);
			}
			if (readStart) {
				if (!Character.isJavaIdentifierStart(c)) {
					throw new IllegalExpressionException("illegal character found：" + c);
				}
				readStart = false;
			}
			sb.append(c);
			reader.mark(0);
		}
		return sb.toString();
	}
	
	public Element read(ExpressionReader sr) throws  IOException {
		int index = sr.getCurrentIndex();
		String word = readWord(sr);

		if (TRUE_WORD.equals(word) || FALSE_WORD.equals(word)) {
			return new Element(word, index, ElementType.BOOLEAN);
		} else if (NULL_WORD.equals(word)) {
			return new Element(word, index, ElementType.NULL);
		} else {
			return new Element(word, index, elementType);
		}
	}
	
}
