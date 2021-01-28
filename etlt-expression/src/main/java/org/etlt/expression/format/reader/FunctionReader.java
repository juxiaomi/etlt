/**
 * 
 */
package org.etlt.expression.format.reader;

import org.etlt.expression.IllegalExpressionException;
import org.etlt.expression.format.Element;
import org.etlt.expression.format.ExpressionReader;

import java.io.IOException;

/**
 * Read function type
 * @version 2.0 
 */
public class FunctionReader implements ElementReader {
	public static final char START_MARK = '$';//函数开始
	public static final char END_MARK = '(';//函数结束
	
	/**
	 * Read the element of the function type from the stream
	 * @param sr
	 * @return
	 * @throws IOException
	 */
	public Element read(ExpressionReader sr) throws  IOException {
		int index = sr.getCurrentIndex();
		StringBuffer sb = new StringBuffer();
		int b = sr.read();
		if (b == -1 || b != FunctionReader.START_MARK) {
			throw new IllegalExpressionException("not valid start of a function.");
		}
		boolean readStart = true;
		while ((b = sr.read()) != -1) {
			char c = (char)b;
			if (c == FunctionReader.END_MARK) {
				if (sb.length() == 0) {
					throw new IllegalExpressionException("function name can not be empty.");
				}
				sr.reset();
				return new Element(sb.toString(), index, Element.ElementType.FUNCTION);
			}
			if (!Character.isJavaIdentifierPart(c)) {
				throw new IllegalExpressionException("invalid character in function name：" + c);
			}
			if (readStart) {
				if (!Character.isJavaIdentifierStart(c)) {
					throw new IllegalExpressionException("function name can not start with a number character：" + c);
				}
				readStart = false;
			}
			sb.append(c);
			sr.mark(0);
		}
		throw new IllegalExpressionException("invalid function ending.");
	}
}
