/**
 * 
 */
package org.etlt.expression.format.reader;

import org.etlt.expression.IllegalExpressionException;
import org.etlt.expression.format.Element;
import org.etlt.expression.format.ExpressionReader;

import java.io.IOException;

/**
 * Read separator type
 * @version 2.0 
 */
public class SplitorTypeReader implements ElementReader {

	public static final String SPLITOR_CHAR ="(),";//所有分割符
	
	/**
	 * 从流中读取分割符类型的ExpressionToken
	 * @param sr
	 * @return
	 * @throws IOException
	 */
	public Element read(ExpressionReader sr) throws  IOException {
		int index = sr.getCurrentIndex();
		int b = sr.read();
		char c = (char)b;
		if (b == -1 || SPLITOR_CHAR.indexOf(c) == -1) {
			throw new IllegalExpressionException("不是有效的分割字符");
		}
		return new Element(Character.toString(c), index,
				Element.ElementType.SPLITTER);
	}
}
