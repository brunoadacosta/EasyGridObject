package com.cocento.commons.ajax.easygrid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto que representa o json esperado pelo EasyGrid
 *
 * @author Bruno Alvares da Costa
 */
public class EasyGridAjaxObject implements Serializable {

	private static final long serialVersionUID = 4387790711175701433L;

	private int page;
	private int total;
	private int records;
	private int itensPerPage = 10;
	private final List<List<String>> rows = new ArrayList<List<String>>();

	public void addRow(List<String> row) {
		records++;
		rows.add(row);
	}

	public int getPage() {
		return page;
	}

	public int getTotal() {
		return total;
	}

	public int getRecords() {
		return records;
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

	public List<List<String>> getRows() {
		return rows;
	}

	public void calculateTotalPages() {
		this.total = records / itensPerPage + records % itensPerPage == 0 ? 0 : 1;
	}

}