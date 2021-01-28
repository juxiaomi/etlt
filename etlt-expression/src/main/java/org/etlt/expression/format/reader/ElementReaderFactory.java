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
				if (c == StringReader.START_MARK) {//"开头，构造字符串读取器
					return new StringReader();
				} else if (c == DateReader.START_MARK) {//[开头，构造日期读取器 // todo: should be deprecated
					return DateReader.class.newInstance();
				}
				//$开头，构造函数读取器// todo: function should like functionName(), identified by ()
				/*
				else if (c == FunctionReader.START_MARK) {
					return FunctionReader.class.newInstance();
				}
				 */
				else if (SplitorReader.SPLITOR_CHAR.indexOf(c) >= 0) {//如果是分隔符，构造分隔符读取器
					return new SplitorReader();
				} else if (NumberReader.NUMBER_CHARS.indexOf(c) >= 0) {//以数字开头，构造数字类型读取器
					return new NumberReader();
				} else if (OperatorReader.isOperatorStart(reader)) {//如果前缀是运算符，构造运算符读取器
					return new OperatorReader();
				} else {
					return new VariableReaderV2();//否则构造一个变量读取器
				}
			} catch (Exception e) {
				throw new IllegalExpressionException(e);
			}
			
		} else {
			throw new IllegalExpressionException("io stream ended.");
		}
	}
}
