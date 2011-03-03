package com.cocento.commons.ajax.easygrid;

import java.util.ArrayList;
import java.util.List;

public class EasyGridRow {
	private Long id;
	private List<String> cell = new ArrayList<String>();

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setCell(List<String> cell) {
		this.cell = cell;
	}

	public List<String> getCell() {
		return cell;
	}
}
