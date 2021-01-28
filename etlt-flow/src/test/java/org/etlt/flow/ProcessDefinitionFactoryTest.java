package org.etlt.flow;

import org.etlt.flow.ProcessDefinition;
import org.etlt.flow.ProcessDefinitionFactory;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 */
public class ProcessDefinitionFactoryTest {

	private ProcessDefinitionFactory factory = new ProcessDefinitionFactory();
	
	@Test
	public void testCreate() {
		InputStream input = getClass().getResourceAsStream("simple-process-definition.json");
		ProcessDefinition processDefinition = factory.createDefinition(input);
		assertNotNull(processDefinition);
		assertEquals("simple-process-definition", processDefinition.getName());
	}
}
