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
 * 逻辑不等于操作
 * @version 2.0 
 */
public class Op_NEQ implements IOperatorExecution {


	public static final Operator THIS_OPERATOR = Operator.NEQ;

	/* (non-Javadoc)
	 * @see org.wltea.expression.op.IOperatorExecution#execute(org.wltea.expression.ExpressionToken[])
	 */
	public Literal execute(Literal[] args) throws IllegalExpressionException {
		if(args == null || args.length != 2){
			throw new IllegalArgumentException("操作符\"" + THIS_OPERATOR.getToken() + "参数个数不匹配");
		}

		Literal first = args[1];
		Literal second = args[0];
		if(first == null || second == null){
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

		//集合类型EQ运算单独处理
		if(BaseDataMeta.DataType.DATATYPE_LIST ==  first.getDataType()
				|| BaseDataMeta.DataType.DATATYPE_LIST ==  second.getDataType()){
			//目前不支持集合EQ比较，（太麻烦鸟）.考虑使用后期使用函数实现
			throw new IllegalArgumentException("操作符\"" + THIS_OPERATOR.getToken() + "\"参数类型错误");

		}
		
		//NEQ支持不同类型数据的null比较，专门对null的判断
		if(BaseDataMeta.DataType.DATATYPE_UNKNOWN ==  first.getDataType()){
			if(null != second.getDataValue()){
				return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.TRUE);
			}else{
				return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.FALSE);
			}
			
		}else if(BaseDataMeta.DataType.DATATYPE_UNKNOWN ==  second.getDataType()){
			if(null != first.getDataValue()){
				return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.TRUE);
			}else{
				return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.FALSE);
			}
			
		}else{
			if(BaseDataMeta.DataType.DATATYPE_BOOLEAN == first.getDataType()
					&& BaseDataMeta.DataType.DATATYPE_BOOLEAN == second.getDataType()){
				Boolean firstValue = first.getBooleanValue();
				Boolean secondValue = second.getBooleanValue();
				if(firstValue != null){
					return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , !firstValue.equals(secondValue));
				}else if(secondValue == null){
					return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.FALSE);
				}else{
					return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.TRUE);
				}			
				
			}else if(BaseDataMeta.DataType.DATATYPE_DATE == first.getDataType()
					&& BaseDataMeta.DataType.DATATYPE_DATE == second.getDataType()){
				//日期比较精确到秒
				String firstValue = first.getDataValueText();
				String secondValue = second.getDataValueText();
				if(firstValue != null){
					return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , !firstValue.equals(secondValue));
				}else if(secondValue == null){
					return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.FALSE);
				}else{
					return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.TRUE);
				}
				
			}else if(BaseDataMeta.DataType.DATATYPE_STRING == first.getDataType()
					&& BaseDataMeta.DataType.DATATYPE_STRING == second.getDataType()){
				String firstValue = first.getStringValue();
				String secondValue = second.getStringValue();
				if(firstValue != null){
					return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , !firstValue.equals(secondValue));
				}else if(secondValue == null){
					return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.FALSE);
				}else{
					return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.TRUE);
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
				Double firstValue = first.getDoubleValue();
				Double secondValue = second.getDoubleValue();
				if(firstValue != null && secondValue != null){
					int result = Double.compare(firstValue, secondValue);
					if(result != 0){
						return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.TRUE);
					}else{
						return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.FALSE);
					}					
				}else if(firstValue == null && secondValue == null){
					return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.FALSE);
				}else{
					return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.TRUE);
				}
		
			}else if(BaseDataMeta.DataType.DATATYPE_OBJECT == first.getDataType()
					&& BaseDataMeta.DataType.DATATYPE_OBJECT == second.getDataType()){
				Object firstValue = first.getDataValue();
				Object secondValue = second.getDataValue();
				if(firstValue != null){
					return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , !firstValue.equals(secondValue));
				}else if(secondValue == null){
					return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.FALSE);
				}else{
					return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.TRUE);
				}
				
			}else{
				//如果操作数没有NULL型，且类型不同，抛异常（如果有校验，校验时就应该抛异常）
				throw new IllegalArgumentException("操作符\"" + THIS_OPERATOR.getToken() + "\"参数类型错误");
			}
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
		
		//集合类型NEQ运算单独处理
		if(BaseDataMeta.DataType.DATATYPE_LIST ==  first.getDataType()
				|| BaseDataMeta.DataType.DATATYPE_LIST ==  second.getDataType()){
			//目前不支持集合EQ比较，（太麻烦鸟）.考虑使用后期使用函数实现
			throw new IllegalExpressionException("操作符\"" + THIS_OPERATOR.getToken() + "\"参数类型错误"
					, THIS_OPERATOR.getToken()
					, opPositin);

		}

		if( BaseDataMeta.DataType.DATATYPE_UNKNOWN ==  first.getDataType()
				|| BaseDataMeta.DataType.DATATYPE_UNKNOWN ==  second.getDataType()){
			return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.FALSE);
				
		}else if(BaseDataMeta.DataType.DATATYPE_BOOLEAN == first.getDataType()
				&& BaseDataMeta.DataType.DATATYPE_BOOLEAN == second.getDataType()){
			return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.FALSE);
			
		}else if(BaseDataMeta.DataType.DATATYPE_DATE == first.getDataType()
				&& BaseDataMeta.DataType.DATATYPE_DATE == second.getDataType()){
			return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.FALSE);
			
		}else if(BaseDataMeta.DataType.DATATYPE_STRING == first.getDataType()
				&& BaseDataMeta.DataType.DATATYPE_STRING == second.getDataType()){
			return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.FALSE);

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
			//数值类型比较
			return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.FALSE);
	
		}else if(BaseDataMeta.DataType.DATATYPE_OBJECT == first.getDataType()
				&& BaseDataMeta.DataType.DATATYPE_OBJECT == second.getDataType()){
			return new Literal(BaseDataMeta.DataType.DATATYPE_BOOLEAN , Boolean.FALSE);
			
		}else {
			//抛异常
			throw new IllegalExpressionException("操作符\"" + THIS_OPERATOR.getToken() + "\"参数类型错误"
					, THIS_OPERATOR.getToken()
					, opPositin);
		}
	}


}
