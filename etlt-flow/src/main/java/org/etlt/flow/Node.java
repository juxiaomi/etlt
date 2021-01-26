package org.etlt.flow;

public abstract class Node {

	private final String container;

	private String name;

	private NodeType type;

	private NodeRef previous;

	private NodeRef next;

	protected Node(String container) {
		this.container = container;
	}

	public NodeType getType() {
		return type;
	}

	public void setType(NodeType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public NodeRef getPrevious() {
		return previous;
	}

	public void setPrevious(NodeRef previous) {
		this.previous = previous;
	}

	public NodeRef getNext() {
		return next;
	}

	public void setNext(NodeRef next) {
		this.next = next;
	}

	public String getContainer() {
		return container;
	}

	public int hashCode() {
		return (getContainer() + "#" + getName()).hashCode();
	}
	
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other.getClass() == getClass()) {
			Node otherNode = (Node) other;
			return getContainer().equals(otherNode.getContainer()) && getName().equals(otherNode.getName());
		}
		return false;
	}
	
	public String toString() {
		return getContainer() + "#" + getName();
	}
}
