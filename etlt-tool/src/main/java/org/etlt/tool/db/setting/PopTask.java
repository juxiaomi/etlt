package org.etlt.tool.db.setting;

import java.util.ArrayList;
import java.util.List;

public class PopTask {
	private String name;
	private int number = 1;
	private int parallel = 1;

	public int getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(int batchNum) {
		this.batchNum = batchNum;
	}

	private int batchNum = 2000;
	private List<Table> tables = new ArrayList<Table>();

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	public int getParallel() {
		return parallel;
	}

	public void setParallel(int parallel) {
		this.parallel = parallel;
	}

	@Override
	public String toString() {
		return "PopTask{" +
				"name='" + name + '\'' +
				", number=" + number +
				'}';
	}
}
