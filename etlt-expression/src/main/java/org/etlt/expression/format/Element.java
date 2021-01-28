/**
 * 
 */
package org.etlt.expression.format;

/**
 * Expression element
 * @version 2.0 
 * 
 */
public class Element {
	
	public enum ElementType {
		//NULL类型
		NULL ,
		//字符串
		STRING ,
		//布尔类
		BOOLEAN ,
		//整数
		INT ,
		//长整数
		LONG ,
		//浮点数
		FLOAT ,
		//双精度浮点
		DOUBLE ,
		//日期时间
		@Deprecated
		DATE ,
		//变量
		VARIABLE ,
		//操作符
		OPERATOR ,
		//函数
		FUNCTION ,
		//分隔符
		SPLITTER
	}
	
	private String text;
	private ElementType type;//类型
	private int index;//元素在表达式中的起始索引号，从0算起
	
	/**
	 * @param text
	 * @param index
	 * @param type
	 */
	public Element(String text, int index, ElementType type) {
		this.text = text;
		this.index = index;
		this.type = type;
	}
	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ElementType getType() {
		return type;
	}

	public void setType(ElementType type) {
		this.type = type;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
