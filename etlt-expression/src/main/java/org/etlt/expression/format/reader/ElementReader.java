/**
 * 
 */
package org.etlt.expression.format.reader;

import org.etlt.expression.format.Element;
import org.etlt.expression.format.ExpressionReader;

import java.io.IOException;

/**
 * @version 2.0 
 */
public interface ElementReader {
	Element read(ExpressionReader sr) throws IOException;
}
