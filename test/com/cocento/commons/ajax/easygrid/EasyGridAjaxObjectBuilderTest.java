package com.cocento.commons.ajax.easygrid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cocento.commons.ajax.easygrid.demonstration.ClassA;
import com.cocento.commons.ajax.easygrid.demonstration.ClassB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class EasyGridAjaxObjectBuilderTest {

	private static final ClassA a = new ClassA();
	private static final ClassB b = new ClassB();
	private static final Gson gson = new GsonBuilder().serializeNulls().create();
	private static final String cleanJson = "{\"page\":1,\":0,\"records\":0,\"rows\":[id:\"\", cell[]]}";

	@BeforeClass
	public static void setUpBefore() {
		a.setAnotherTestString("Unit Test");
		a.setNumber(100);
		a.setTestString("Test String");

		b.setName("Name");
		a.setClassB(b);
	}

	@Test
	public void shouldBeReturnEmptyObject() {

		EasyGridObjectBuilder builder = new EasyGridObjectBuilder();

		EasyGridObject obj = builder.create();

		assertNotNull(obj);
	}

	@Test
	public void shouldBeReturnEmptyFormattedJsonObject() {
		EasyGridObjectBuilder builder = new EasyGridObjectBuilder();

		EasyGridObject obj = builder.create();

		String json = gson.toJson(obj);

		assertEquals("Json esperado está incorreto.", cleanJson, json);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldBeConstructNullObject() {
		EasyGridObjectBuilder builder = new EasyGridObjectBuilder(null);
	}
}
