package org.etlt.flow;

public class StartNode extends Node {
	public StartNode(String container,String name) {
		super(container);
		setName(name);
		setType(NodeType.START);
		super.setPrevious(null);
	}
	
	public void setPrevious(NodeRef previous) {
		throw new UnsupportedOperationException();
	}
}
