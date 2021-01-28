package org.etlt.flow;

public class TransformerNode extends Node {
	public TransformerNode(String container, String name) {
		super(container);
		setName(name);
		setType(NodeType.TRANSFORMER);
	}
}
