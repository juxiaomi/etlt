/**
 * 
 */
package org.etlt.expression.op;

import org.etlt.expression.IllegalExpressionException;
import org.etlt.expression.datameta.BaseDataMeta;
import org.etlt.expression.datameta.Literal;

/**
 * 操作符执行接口
 * @version 2.0 
 */
public interface IOperatorExecution {
	/**
	 * 执行操作符运算
	 * @param args 注意args中的参数由于是从栈中按LIFO顺序弹出的，所以必须从尾部倒着取数
	 * @return Constant 常量型的执行结果
	 * @throws IllegalExpressionException
	 */
	public Literal execute(Literal[] args) throws IllegalExpressionException;
	
	/**
	 * 验证操作符参数是否合法
	 * @param opPositin
	 * @param args 注意args中的参数由于是从栈中按LIFO顺序弹出的，所以必须从尾部倒着取数
	 * @return Constant 常量型的执行结果
	 * @throws IllegalExpressionException
	 */
	public Literal verify(int opPositin , BaseDataMeta[] args) throws IllegalExpressionException;
}
