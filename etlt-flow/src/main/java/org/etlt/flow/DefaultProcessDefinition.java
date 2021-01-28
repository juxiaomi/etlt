package org.etlt.flow;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.List;
import java.util.Set;

public class DefaultProcessDefinition implements ProcessDefinition {

	private String name;

	private Set<Node> nodes;
	
	private Graph<Node, DefaultEdge> graph;

	@Override
	public List<Node> getSources() {
		return null;
	}

	@Override
	public List<Node> getTransformers() {
		return null;
	}

	@Override
	public List<Node> getTargets() {
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Node> getNodes() {
		return nodes;
	}

	public void setNodes(Set<Node> nodes) {
		this.nodes = nodes;
	}

	/**
	 * 装配nodes，将所有节点组装成一个有向无环图
	 */
	public void assembly() {
		graph = new DefaultDirectedGraph<>(DefaultEdge.class);
		for (Node node : nodes) {
			NodeRef pref = node.getPrevious();
			NodeRef pnext = node.getNext();
			if (pref != null) {
				pref.setNode(findNodeByName(pref.getName()));
			}
			if (pnext != null) {
				pnext.setNode(findNodeByName(pnext.getName()));
			}
			graph.addVertex(node);

		}

		for (Node self : nodes) {
			NodeRef pref = self.getPrevious();
			NodeRef pnext = self.getNext();
			if (pref != null && pnext != null) {
				Node from = pref.getNode();
				graph.addEdge(from, self);
				Node to = pnext.getNode();
				graph.addEdge(self, to);
			} else if (pref != null && pnext == null) {
				Node from = pref.getNode();
				graph.addEdge(from, self);
			} else if (pref == null && pnext != null) {
				Node to = pnext.getNode();
				graph.addEdge(self, to);
			} else if (pref == null && pnext == null) {
				throw new IllegalArgumentException("wrong process definition: isolated node.");
			}

		}
	}

	private Node findNodeByName(String nodeName) {
		for (Node node : nodes) {
			if (node.getName().equals(nodeName))
				return node;
		}
		return null;
	}
}
