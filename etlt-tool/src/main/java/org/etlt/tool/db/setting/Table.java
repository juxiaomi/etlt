package org.etlt.tool.db.setting;

public class Table {

	public Table(String text) {
		if (text.startsWith("#")) {
			this.skipped = true;
			this.table = null;
			this.configFile = null;
		} else {
			int index = text.indexOf(':');
			if (index != -1) {
				this.table = text.substring(0, index);
				this.configFile = text.substring(index + 1);
			} else {
				this.table = text;
				this.configFile = text;
			}
		}
	}


	public Table(String table, String configFile) {
		this.table = table;
		this.configFile = configFile;
	}

	private boolean skipped = false;

	private final String configFile;

	private final String table;

	public String getConfigFile() {
		return configFile;
	}

	public String getTable() {
		return table;
	}

	public boolean isSkipped() {
		return skipped;
	}

	public PopTask getTask() {
		return task;
	}

	public PopTask task;

	public void setTask(PopTask task) {
		this.task = task;
	}

	@Override
	public String toString() {
		return "Table{" +
				"skipped=" + skipped +
				", table='" + table + '\'' +
				", task=" + task +
				'}';
	}
}
