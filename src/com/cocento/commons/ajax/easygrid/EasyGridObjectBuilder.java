package com.cocento.commons.ajax.easygrid;

import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.vidageek.mirror.dsl.Mirror;

import org.joda.time.base.BaseLocal;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Class responsible for building the EasyGridAjaxObject to Json or XML
 * serialization used in EasyGrid
 * 
 * <p>
 * It serializes the informed columns by the methods
 * </p>
 * 
 * @see EasyGridObjectBuilder#setColumn(String)
 * @see EasyGridObjectBuilder#setCurrencyColumn(String, Locale)
 * @see EasyGridObjectBuilder#setDateColumn(String, String)
 * 
 * 
 * @author Bruno Alvares da Costa
 * 
 * @see EasyGridObject
 */
public class EasyGridObjectBuilder<T> {

	private final List<String> columns = new ArrayList<String>(10);
	private final EasyGridObject result = new EasyGridObject();
	private final Map<String, String> patternDate = new HashMap<String, String>(3);
	private final Map<String, Locale> patternCurrency = new HashMap<String, Locale>(3);
	private transient Object sharedObject;

	private final Collection<T> objects;

	private final Long totalRecords;

	private final String ESCAPE_DOT = "\\.";

	private final Mirror mirror = new Mirror();

	@SuppressWarnings("unchecked")
	public EasyGridObjectBuilder() {
		objects = new ArrayList();
		this.totalRecords = 0L;
	}

	@SuppressWarnings("unchecked")
	public EasyGridObjectBuilder(T object) {
		if (object == null) {
			throw new IllegalArgumentException("objeto não pode ser nulo");
		}
		objects = Arrays.asList(object);
		this.totalRecords = 1L;
	}

	public EasyGridObjectBuilder(Collection<T> objects, Long totalRecords) {
		if (objects == null) {
			throw new IllegalArgumentException("coleção não pode ser nula");
		}
		this.objects = objects;
		this.totalRecords = totalRecords;
	}

	/**
	 * Method to create the object from a List of ValueObject
	 * 
	 * @return Populated EasyGridAjaxObject
	 * 
	 */
	public EasyGridObject create() {
		for (T obj : objects) {
			EasyGridRow row = new EasyGridRow();
			row.setId((Long) mirror.on(obj).get().field("id"));
			row.setCell(returnRow(obj));

			result.addRow(row);
		}
		result.setRecords(totalRecords);
		result.calculateTotalPages();
		return result;
	}

	public EasyGridObject empty() {
		EasyGridRow row = new EasyGridRow();

		result.addRow(row);
		result.setRecords(0);

		return result;
	}

	/**
	 * Method that informs column for serialization
	 * 
	 * @param method
	 *            Name of property
	 * @return EasyGridAjaxObjectBuilder
	 */
	public EasyGridObjectBuilder<T> setColumn(String method) {
		columns.add(formatMethodName(method));
		return this;
	}

	/**
	 * Method that informs column for serialization with Date formatter
	 * 
	 * @param method
	 *            Name of property
	 * @param dateFormat
	 *            Pattern date format
	 *            <p>
	 *            Example: MM/dd/yyyy
	 *            </p>
	 * @return EasyGridAjaxObjectBuilder
	 */
	public EasyGridObjectBuilder<T> setDateColumn(String method, String dateFormat) {
		String methodName = formatMethodName(method);
		columns.add(methodName);
		patternDate.put(methodName, dateFormat);
		return this;
	}

	/**
	 * Method that informs column for serialization with Currency formatter
	 * 
	 * @param method
	 *            Name of property
	 * @param locale
	 *            Locale
	 *            <p>
	 *            Example: en_US
	 *            </p>
	 * @return EasyGridAjaxObjectBuilder
	 */
	public EasyGridObjectBuilder<T> setCurrencyColumn(String method, Locale locale) {
		String methodName = formatMethodName(method);
		columns.add(methodName);
		patternCurrency.put(methodName, locale);
		return this;
	}

	/**
	 * Set the current page of pagination
	 * 
	 * @param page
	 * @return EasyGridAjaxObjectBuilder
	 */
	public EasyGridObjectBuilder<T> setPage(int page) {
		result.setPage(page);
		return this;
	}

	/**
	 * Set the number of show rows in the grid
	 * 
	 * @param itensPerPage
	 * @return EasyGridAjaxObjectBuilder
	 */
	public EasyGridObjectBuilder<T> setItensPerPage(int itensPerPage) {
		this.result.setItensPerPage(itensPerPage);
		return this;
	}

	@SuppressWarnings("unchecked")
	private Method getMethod(String[] methodName) {
		Method method = null;
		for (int i = 1; i < methodName.length; i++) {
			if (methodName.length != (i + 1)) {
				sharedObject = mirror.on(sharedObject).invoke().method(methodName[i]).withoutArgs();
				continue;
			}
			method = mirror.on(sharedObject.getClass()).reflect().method(methodName[i]).withoutArgs();
		}
		return method;
	}

	@SuppressWarnings("unchecked")
	private List<String> returnRow(Object obj) {
		List<String> row = new ArrayList<String>(columns.size());
		for (String methodName : columns) {
			// verify exists complex type
			if (methodName.contains(".")) {
				String[] methodNames = methodName.split(ESCAPE_DOT);
				sharedObject = mirror.on(obj).invoke().method(methodNames[0]).withoutArgs();
				Method method = getMethod(methodNames);
				row.add(getValue(method, sharedObject));
			} else {
				Method method = mirror.on(obj.getClass()).reflect().method(methodName).withoutArgs();

				row.add(getValue(method, obj));
			}
		}
		return row;
	}

	private String getValue(Method method, Object obj) {
		Object result = mirror.on(obj).invoke().method(method).withoutArgs();
		if (result == null) {
			return "";
		}

		if (patternDate.containsKey(method.getName())) {
			if (result instanceof BaseLocal) {
				DateTimeFormatter formatter = DateTimeFormat.forPattern(patternDate.get(method.getName()));
				BaseLocal baseDate = (BaseLocal) result;
				return baseDate.toString(formatter);
			}
			return new SimpleDateFormat(patternDate.get(method.getName())).format(result);
		}
		if (patternCurrency.containsKey(method.getName())) {
			return NumberFormat.getCurrencyInstance(patternCurrency.get(method.getName())).format(result);
		}
		return result.toString();
	}

	private String formatMethodName(String method) {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		for (String str : method.split(ESCAPE_DOT)) {
			if (i > 0) {
				builder.append(".");
			}
			builder.append("get".concat(str.substring(0, 1).toUpperCase()).concat(str.substring(1)));
			i++;
		}
		return builder.toString();
	}
}
