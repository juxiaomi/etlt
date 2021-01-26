package org.etlt.flow;

import java.util.Map;
import java.util.Optional;

public class NodeFactory {
	public Node createNode(String container,Map<String, Object> nodeDef) {
		String name = nodeDef.get("name").toString().trim();
		NodeType type = NodeType.valueOf(nodeDef.get("type").toString().trim());
		Optional<Object> _prev = Optional.ofNullable(nodeDef.get("previous"));
		Optional<Object> _next = Optional.ofNullable(nodeDef.get("next"));
		Node node = null;
		switch (type) {
		case START:
			node = new StartNode(container,name);
			NodeRef next = new NodeRef(_next.get().toString().trim());
			node.setNext(next);
			break;
		case END:
			node = new EndNode(container,name);
			NodeRef prev = new NodeRef(_prev.get().toString().trim());
			node.setPrevious(prev);
			break;
		case INPUT:
			node = new InputNode(container,name);
			node.setPrevious(new NodeRef(_prev.get().toString().trim()));
			node.setNext(new NodeRef(_next.get().toString().trim()));
			break;
		case OUTPUT:
			node = new OutputNode(container,name);
			node.setPrevious(new NodeRef(_prev.get().toString().trim()));
			node.setNext(new NodeRef(_next.get().toString().trim()));
			break;
		case TRANSFORMER:
			node = new TransformerNode(container,name);
			node.setPrevious(new NodeRef(_prev.get().toString().trim()));
			node.setNext(new NodeRef(_next.get().toString().trim()));
			break;
		}
		return node;
	}
}
