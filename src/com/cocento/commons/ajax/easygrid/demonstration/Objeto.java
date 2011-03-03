package com.cocento.commons.ajax.easygrid.demonstration;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Objeto implements Serializable {

	private static final long serialVersionUID = -582070988424624954L;

	private Long id;

	private String name;

	private Integer inteiro;

	private Date date;

	private Date date2;

	private Long longo;

	private BigDecimal price;

	private Double price2;

	private ClassA classA;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getInteiro() {
		return inteiro;
	}

	public void setInteiro(Integer inteiro) {
		this.inteiro = inteiro;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate2() {
		return date2;
	}

	public void setDate2(Date date2) {
		this.date2 = date2;
	}

	public Long getLongo() {
		return longo;
	}

	public void setLongo(Long longo) {
		this.longo = longo;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Double getPrice2() {
		return price2;
	}

	public void setPrice2(Double price2) {
		this.price2 = price2;
	}

	public ClassA getClassA() {
		return classA;
	}

	public void setClassA(ClassA classA) {
		this.classA = classA;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
