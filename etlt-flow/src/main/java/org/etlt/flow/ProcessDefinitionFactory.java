package org.etlt.flow;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProcessDefinitionFactory {
	@SuppressWarnings("unchecked")
	public ProcessDefinition createDefinition(InputStream input) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> definition = objectMapper.readValue(input, Map.class);
			return createDefinition(definition);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public ProcessDefinition createDefinition(Map<String, Object> definition) {
		DefaultProcessDefinition defaultProcessDefinition = new DefaultProcessDefinition();
		String name = getObject(definition, "name").toString().trim();
		defaultProcessDefinition.setName(name.toString().trim());
		Object _nodes = getObject(definition, "nodes");
		List<Map<String, Object>> nodeDefs = (List<Map<String, Object>>) _nodes;
		Set<Node> nodes = new HashSet<Node>();
		NodeFactory nodeFactory = new NodeFactory();
		for(Map<String, Object> _node : nodeDefs) {
			nodes.add(nodeFactory.createNode(name, _node));
		}
		defaultProcessDefinition.setNodes(nodes);
		defaultProcessDefinition.assembly();
		return defaultProcessDefinition;
	}

	protected Object getObject(Map<String, Object> definition, String key) {
		Object object = definition.get(key);
		if (object == null) {
			throw new RuntimeException(key + " is mandatory.");
		}
		return object;
	}
}
