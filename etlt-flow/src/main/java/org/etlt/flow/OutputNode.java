package org.etlt.flow;

public class OutputNode extends Node {
	
	private String target;
	
	public OutputNode(String container,String name) {
		super(container);
		setName(name);
		setType(NodeType.OUTPUT);
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
}
