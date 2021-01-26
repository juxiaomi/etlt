/**
 * 
 */
package org.etlt.expression.format.reader;

import org.etlt.expression.IllegalExpressionException;
import org.etlt.expression.format.ExpressionReader;

import java.io.IOException;

/**
 * Word element reader factory
 * @version 2.0
 */
public class ElementReaderFactory {
	
	/**
	 * Construct different read word elements according to the beginning of the stream,
	 * Stream should not start with a space
	 * @param reader
	 * @return ElementReader
	 * @throws IOException
	 */
	public static ElementReader createElementReader(ExpressionReader reader) throws IOException {
		//读一个char
		reader.mark(0);
		int b = reader.read();
		reader.reset();
		if (b != -1) {
			char c = (char)b;
			try{	
				if (c == StringTypeReader.START_MARK) {//"开头，构造字符串读取器
					return StringTypeReader.class.newInstance();
				} else if (c == DateTypeReader.START_MARK) {//[开头，构造日期读取器
					return DateTypeReader.class.newInstance();
				} else if (c == FunctionTypeReader.START_MARK) {//$开头，构造函数读取器
					return FunctionTypeReader.class.newInstance();
				} else if (SplitorTypeReader.SPLITOR_CHAR.indexOf(c) >= 0) {//如果是分隔符，构造分隔符读取器
					return SplitorTypeReader.class.newInstance();
				} else if (NumberTypeReader.NUMBER_CHARS.indexOf(c) >= 0) {//以数字开头，构造数字类型读取器
					return NumberTypeReader.class.newInstance();
				} else if (OperatorTypeReader.isOperatorStart(reader)) {//如果前缀是运算符，构造运算符读取器
					return OperatorTypeReader.class.newInstance();
				} else {
					return VariableTypeReader.class.newInstance();//否则构造一个变量读取器
				}
			} catch (Exception e) {
				throw new IllegalExpressionException(e);
			}
			
		} else {
			throw new IllegalExpressionException("io stream ended.");
		}
	}
}
