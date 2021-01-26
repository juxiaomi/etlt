package org.etlt.flow;
/**
 * 执行流，从{@link #processDefinition}中读取执行流定义
 * @author juxiaomi
 * @date 2020年10月20日 - 下午5:32:01
 *
 */
public class Process {
	
	private ProcessDefinition processDefinition;
	
	public void execute() {
		
	}

	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}

	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}
}
