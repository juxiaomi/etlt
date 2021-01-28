/**
 * 
 */
package org.etlt.expression.format.reader;

import org.etlt.expression.IllegalExpressionException;
import org.etlt.expression.format.Element;
import org.etlt.expression.format.Element.ElementType;
import org.etlt.expression.format.ExpressionReader;

import java.io.IOException;

/**
 * Read string type
 * @version 2.0 
 */
public class StringReader implements ElementReader {
	public static final char START_MARK = '\'';//字符串开始标志
	public static final char END_MARK = '\'';//字符串结束标志
	
	public static final char ESCAPE_MARK = '\\';//转义符号
	
	/**
	 * 从流中读取字符串类型的ExpressionToken
	 * @param sr
	 * @return ExpressionToken
	 * @throws IOException
	 */
	public Element read(ExpressionReader sr) throws  IOException {
		int index = sr.getCurrentIndex();
		StringBuffer sb = new StringBuffer();
		int b = sr.read();
		if (b == -1 || b != START_MARK) {
			throw new IllegalExpressionException("不是有效的字符串开始");
		}
		
		while ((b = sr.read()) != -1) {
			char c = (char)b;
			if (c == ESCAPE_MARK) {//遇到转义字符
				c = getEscapeValue((char)sr.read());
			} else if (c == END_MARK) {//遇到非转义的引号
				return new Element(sb.toString(), index, ElementType.STRING);
			}
			sb.append(c);
		}
		throw new IllegalExpressionException("不是有效的字符串结束");
	}
	
	/**
	 * some characters should be escapted: ',",\
	 * @param c
	 * @return
	 */
	private static char getEscapeValue(char c)  {
		if (c == '\\' || c == '\"' || c == '\'') {
			return c;
		} else if (c == 'n') {
			return '\n';
		} else if (c == 'r') {
			return '\r';
		} else if (c == 't') {
			return '\t';
		}
		throw new IllegalExpressionException("character escape error.");
	}
}
