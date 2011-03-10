package com.cocento.commons.ajax.easygrid.demonstration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.joda.time.LocalDate;

import com.cocento.commons.ajax.easygrid.EasyGridObject;
import com.cocento.commons.ajax.easygrid.EasyGridObjectBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ClassB classB = new ClassB();
		classB.setId(1L);
		classB.setName("Class B");

		ClassA classA = new ClassA();
		classA.setId(2L);
		classA.setTestString("Test String");
		classA.setAnotherTestString("Another Test String");
		classA.setNumber(100);
		classA.setClassB(classB);

		Objeto obj = new Objeto();
		obj.setId(10L);
		obj.setName("Object 1");
		obj.setDate(new Date());
		obj.setDate2(new Date());
		obj.setInteiro(999);
		obj.setLongo(200L);
		obj.setPrice(new BigDecimal("999.99"));
		obj.setPrice2(999.99);
		obj.setClassA(classA);
		obj.setDataJoda(new LocalDate().plusDays(1));

		ClassB classB1 = new ClassB();
		classB1.setId(100L);
		classB1.setName("Another Class B");

		ClassA classA1 = new ClassA();
		classA1.setId(101L);
		classA1.setTestString("Test String Object 2");
		classA1.setAnotherTestString("Another Test String Object 2");
		classA1.setNumber(200);
		classA1.setClassB(classB1);

		Objeto obj2 = new Objeto();
		obj2.setId(103L);
		obj2.setName("Object 2");
		obj2.setDate(new Date());
		obj2.setDate2(new Date());
		obj2.setInteiro(500);
		obj2.setLongo(400L);
		obj2.setPrice(new BigDecimal("5900.99"));
		obj2.setPrice2(5900.99);
		obj2.setClassA(classA1);
		obj2.setDataJoda(new LocalDate().plusDays(2));

		List<Objeto> lst = new ArrayList<Objeto>(2);

		lst.add(obj);
		lst.add(obj2);

		EasyGridObject ajaxObject = new EasyGridObjectBuilder<Objeto>(lst, 2L).setDateColumn("dataJoda", "dd/MM/yyyy")
				.setColumn("classA.classB.name").setColumn("classA.testString").setColumn("name").setColumn("inteiro")
				.setDateColumn("date", "dd/MM/yyyy").setDateColumn("date2", "dd/MM/yyyy HH:mm").setColumn("longo")
				.setCurrencyColumn("price", new Locale("pt", "BR")).setCurrencyColumn("price2", new Locale("en", "US")).create();

		Gson gson = new GsonBuilder().create();

		System.out.println(new Gson().toJson(ajaxObject));

		System.out.println(gson.toJson(new EasyGridObjectBuilder<Objeto>().empty()));

	}
}