package org.etlt.flow;

import java.util.List;

public interface ProcessDefinition {

	String getName();
	
	List<Node> getSources();
	
	List<Node> getTransformers();
	
	List<Node> getTargets();
}
