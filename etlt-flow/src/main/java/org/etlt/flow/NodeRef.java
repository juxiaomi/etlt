package org.etlt.flow;

public class NodeRef {
	private Node node;
	
	private String name;
	
	public NodeRef(String name) {
		this.name = name;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
