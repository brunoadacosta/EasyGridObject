package com.cocento.commons.ajax.easygrid.demonstration;

import java.io.Serializable;

public class ClassA implements Serializable {

	private static final long serialVersionUID = 6987633533541983505L;

	private Long id;
	private String testString;
	private String anotherTestString;
	private Integer number;
	private ClassB classB;

	public String getTestString() {
		return testString;
	}

	public void setTestString(String testString) {
		this.testString = testString;
	}

	public String getAnotherTestString() {
		return anotherTestString;
	}

	public void setAnotherTestString(String anotherTestString) {
		this.anotherTestString = anotherTestString;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public ClassB getClassB() {
		return classB;
	}

	public void setClassB(ClassB classB) {
		this.classB = classB;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
