
package org.etlt.expression.format.reader;

import org.etlt.expression.IllegalExpressionException;
import org.etlt.expression.format.Element;
import org.etlt.expression.format.Element.ElementType;
import org.etlt.expression.format.ExpressionReader;

import java.io.IOException;

/**
 * read variable name
 * @version 2.0 
 */
public class VariableTypeReader implements ElementReader {

	public static final String STOP_CHAR = "+-*/%^<>=&|!?:#$(),[]'\" \r\n\t";//词段的结束符
	
	public static final String TRUE_WORD = "true";
	public static final String FALSE_WORD = "false";
	
	public static final String NULL_WORD = "null";
	/**
	 * 
	 * @param sr
	 * @return
	 */
	private String readWord(ExpressionReader sr) throws  IOException {
		StringBuffer sb = new StringBuffer();
		boolean readStart = true;
		int b = -1;
		while ((b = sr.read()) != -1) {
			char c = (char)b;
			if (STOP_CHAR.indexOf(c) >= 0 && !readStart) {//单词停止符,并且忽略第一个字符
				sr.reset();
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
			sr.mark(0);
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
			return new Element(word, index, ElementType.VARIABLE);
		}
	}
	
}
