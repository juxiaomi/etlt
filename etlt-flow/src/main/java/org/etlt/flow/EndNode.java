package org.etlt.flow;

public class EndNode extends Node {
	public EndNode(String container,String name) {
		super(container);
		setName(name);
		setType(NodeType.END);
	}
	
	public void setNext(NodeRef previous) {
		throw new UnsupportedOperationException();
	}
}
