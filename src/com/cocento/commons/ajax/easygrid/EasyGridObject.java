package com.cocento.commons.ajax.easygrid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto que representa o json esperado pelo EasyGrid
 * 
 * @author Bruno Alvares da Costa
 */
public class EasyGridObject implements Serializable {

	private static final long serialVersionUID = 4387790711175701433L;

	private int page = 1;
	private transient int pagesize;
	private long records;
	private int currentRecords;
	private transient int itensPerPage = 10;
	private final List<EasyGridRow> rows = new ArrayList<EasyGridRow>();

	public void addRow(EasyGridRow row) {
		currentRecords++;
		rows.add(row);
	}

	public int getPage() {
		return page;
	}

	public int getPagesize() {
		return pagesize;
	}

	public long getRecords() {
		return records;
	}

	public void setRecords(long records) {
		this.records = records;
	}

	public int getCurrentRecords() {
		return currentRecords;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getShowRows() {
		return itensPerPage;
	}

	public void setItensPerPage(int showRows) {
		this.itensPerPage = showRows;
		calculateTotalPages();
	}

	public List<EasyGridRow> getRows() {
		return rows;
	}

	public void calculateTotalPages() {
		this.pagesize = getCurrentRecords() / itensPerPage + getRecords() % itensPerPage == 0 ? 0 : 1;
	}

}