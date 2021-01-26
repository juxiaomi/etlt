/**
 * 
 */
package org.etlt.expression.datameta;

import java.util.ArrayList;

/**
 * Literal data description
 * @version 2.0 
 */
public class Literal extends BaseDataMeta {
	
	public Literal(DataType dataType , Object value){
		super(dataType , value);
		
		if(dataType == null){
			throw new IllegalArgumentException("Illegal parameter: the data type is empty");
		}
		//如果为集合类型，生成集合容器
		if(DataType.DATATYPE_LIST == dataType){
			if(dataValue == null){
				dataValue = new ArrayList<Object>(0);
			}
		}

	}
	
	
	public Literal(Reference ref){
		super(null , ref);
		this.setReference(true);
	}
}
