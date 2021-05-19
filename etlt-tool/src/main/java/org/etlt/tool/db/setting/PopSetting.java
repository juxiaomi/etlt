package org.etlt.tool.db.setting;

import java.util.List;

public class PopSetting {
	private List<PopTask> dataPopTasks;

	public List<PopTask> getDataPopTasks() {
		return dataPopTasks;
	}

	public void setDataPopTasks(List<PopTask> dataPopTasks) {
		this.dataPopTasks = dataPopTasks;
	}
	
	@Override
	public String toString() {
		return "PopSetting [dataPopTasks=" + dataPopTasks + "]";
	}

	public void validate() {
		
	}
}
