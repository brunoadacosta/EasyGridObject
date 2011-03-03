package com.cocento.commons.ajax.easygrid.demonstration;

import java.io.Serializable;

public class ClassB implements Serializable {

	private static final long serialVersionUID = -4563588954858902341L;
	private Long id;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

}
