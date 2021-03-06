/**
 * 
 */
package org.etlt.expression.op.define;

import org.etlt.expression.IllegalExpressionException;
import org.etlt.expression.datameta.BaseDataMeta;
import org.etlt.expression.datameta.Literal;
import org.etlt.expression.datameta.Reference;
import org.etlt.expression.op.IOperatorExecution;
import org.etlt.expression.op.Operator;

/**
 * 逻辑大于等于操作
 * @version 2.0 
 */
public class Op_GE implements IOperatorExecution {

	public static final Operator THIS_OPERATOR = Operator.GE;
	
	public Literal execute(Literal[] args) throws IllegalExpressionException {
		if(args == null || args.length != 2){
			throw new IllegalArgumentException("操作符\"" + THIS_OPERATOR.getToken() + "参数个数不匹配");
		}

		Literal first = args[1];
		if(null == first || null == first.getDataValue()){
			//抛NULL异常
			throw new NullPointerException("操作符\"" + THIS_OPERATOR.getToken() + "\"参数为空");
		}
		
		Literal second = args[0];
		if(null == second || null == second.getDataValue()){
			//抛NULL异常
			throw new NullPointerException("操作符\"" + THIS_OPERATOR.getToken() + "\"参数为空");
		}	
		
		//如果第一参数为引用，则执行引用
		if(first.isReference()){
			Reference firstRef = (Reference)first.getDataValue();
			first = firstRef.execute();
		}
		//如果第二参数为引用，则执行引用
		if(second.isReference()){
			Reference secondRef = (Reference)second.getDataValue();
			second = secondRef.execute();
		}		
		
		
		if(BaseDataMeta.DataType.DATATYPE_DATE ==  first.getDataType()
				&& BaseDataMeta.DataType.DATATYPE_DATE ==  second.getDataType()){
			//日期类型比较	
			int result = first.getDateValue().compareTo(second.getDateValue());
			if(result >= 0){
				return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.TRUE);
			}else{
				return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.FALSE);
			}
			
		}else if(BaseDataMeta.DataType.DATATYPE_STRING ==  first.getDataType()
				&& BaseDataMeta.DataType.DATATYPE_STRING ==  second.getDataType()){
			//字串类型比较
			int result = first.getStringValue().compareTo(second.getStringValue());
			if(result >= 0){
				return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.TRUE);
			}else{
				return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.FALSE);
			}
			
		}else if( (BaseDataMeta.DataType.DATATYPE_DOUBLE == first.getDataType()
					|| BaseDataMeta.DataType.DATATYPE_FLOAT == first.getDataType()
					|| BaseDataMeta.DataType.DATATYPE_LONG == first.getDataType()
					|| BaseDataMeta.DataType.DATATYPE_INT == first.getDataType())
				&& 
				(BaseDataMeta.DataType.DATATYPE_DOUBLE == second.getDataType()
						|| BaseDataMeta.DataType.DATATYPE_FLOAT == second.getDataType()
						|| BaseDataMeta.DataType.DATATYPE_LONG == second.getDataType()
						|| BaseDataMeta.DataType.DATATYPE_INT == second.getDataType())
				
				){
			//数值类型比较，全部转换成double	
			int result = Double.compare(first.getDoubleValue(), second.getDoubleValue());
			if(result >= 0){
				return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.TRUE);
			}else{
				return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.FALSE);
			}
		}else{
			//GE操作不支持其他类型，抛异常
			throw new IllegalArgumentException("操作符\"" + THIS_OPERATOR.getToken() + "\"参数类型错误"	);
			
		}
	}

	/* (non-Javadoc)
	 * @see org.wltea.expression.op.IOperatorExecution#verify(int, org.wltea.expression.ExpressionToken[])
	 */
	public Literal verify(int opPositin, BaseDataMeta[] args)
			throws IllegalExpressionException {
		
		if(args == null){
			throw new IllegalArgumentException("运算操作符参数为空");
		}
		if(args.length != 2){
			//抛异常
			throw new IllegalExpressionException("操作符\"" + THIS_OPERATOR.getToken() + "\"参数个数不匹配"
						, THIS_OPERATOR.getToken()
						, opPositin
					);
		}
		
		BaseDataMeta first = args[1];
		BaseDataMeta second = args[0];
		if(first == null || second == null){
			throw new NullPointerException("操作符\"" + THIS_OPERATOR.getToken() + "\"参数为空");
		}		

		if(BaseDataMeta.DataType.DATATYPE_DATE ==  first.getDataType()
				&& BaseDataMeta.DataType.DATATYPE_DATE ==  second.getDataType()){
			//日期类型比较	
			return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN ,Boolean.FALSE);
			
		}else if(BaseDataMeta.DataType.DATATYPE_STRING ==  first.getDataType()
				&& BaseDataMeta.DataType.DATATYPE_STRING ==  second.getDataType()){
			//字串类型比较
			return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN ,Boolean.FALSE);
			
		}else if((BaseDataMeta.DataType.DATATYPE_DOUBLE == first.getDataType()
					|| BaseDataMeta.DataType.DATATYPE_FLOAT == first.getDataType()
					|| BaseDataMeta.DataType.DATATYPE_LONG == first.getDataType()
					|| BaseDataMeta.DataType.DATATYPE_INT == first.getDataType())
				&& 
				(BaseDataMeta.DataType.DATATYPE_DOUBLE == second.getDataType()
						|| BaseDataMeta.DataType.DATATYPE_FLOAT == second.getDataType()
						|| BaseDataMeta.DataType.DATATYPE_LONG == second.getDataType()
						|| BaseDataMeta.DataType.DATATYPE_INT == second.getDataType())
				
				){
			//数值类型比较
			return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN ,Boolean.FALSE);

		}else{
			//GE操作不支持其他类型，抛异常
			throw new IllegalExpressionException("操作符\"" + THIS_OPERATOR.getToken() + "\"参数类型错误"	);
			
		}
	}


}