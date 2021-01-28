package org.etlt.flow;

public class InputNode extends Node {
	public InputNode(String container, String name) {
		super(container);
		setName(name);
		setType(NodeType.INPUT);
	}
}
