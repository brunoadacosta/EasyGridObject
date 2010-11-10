package com.cocento.commons.ajax.easygrid;

import java.lang.reflect.InvocationTargetException;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class responsible for building the EasyGridAjaxObject to Json or XML
 * serialization used in EasyGrid
 * 
 * <p>
 * It serializes the informed columns by the methods
 * </p>
 * 
 * @see EasyGridAjaxObjectBuilder#setColumn(String)
 * @see EasyGridAjaxObjectBuilder#setCurrencyColumn(String, Locale)
 * @see EasyGridAjaxObjectBuilder#setDateColumn(String, String)
 * 
 * 
 * @author Bruno Alvares da Costa
 * 
 * @see EasyGridAjaxObject
 */
public class EasyGridAjaxObjectBuilder<T> {

	private final List<String> columns = new ArrayList<String>(10);
	private final EasyGridAjaxObject result = new EasyGridAjaxObject();
	private final Map<String, String> patternDate = new HashMap<String, String>(3);
	private final Map<String, Locale> patternCurrency = new HashMap<String, Locale>(3);
	private transient Object sharedObject;

	private final Collection<T> objects;

	private final String ESCAPE_DOT = "\\.";

	public EasyGridAjaxObjectBuilder() {
		objects = new ArrayList();
	}

	@SuppressWarnings("unchecked")
	public EasyGridAjaxObjectBuilder(T object) {
		if (object == null) {
			throw new IllegalArgumentException("objeto não pode ser nulo");
		}
		objects = Arrays.asList(object);
	}

	public EasyGridAjaxObjectBuilder(Collection<T> objects) {
		if (objects == null) {
			throw new IllegalArgumentException("coleção não pode ser nula");
		}
		this.objects = objects;
	}

	/**
	 * Method to create the object from a List of ValueObject
	 * 
	 * @return Populated EasyGridAjaxObject
	 * 
	 */
	public EasyGridAjaxObject create() {
		for (T obj : objects) {
			result.addRow(returnRow(obj));
		}
		
		result.calculateTotalPages();
		return result;
	}

	/**
	 * Method that informs column for serialization
	 * 
	 * @param method
	 *            Name of property
	 * @return EasyGridAjaxObjectBuilder
	 */
	public EasyGridAjaxObjectBuilder<T> setColumn(String method) {
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
	public EasyGridAjaxObjectBuilder<T> setDateColumn(String method, String dateFormat) {
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
	public EasyGridAjaxObjectBuilder<T> setCurrencyColumn(String method, Locale locale) {
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
	public EasyGridAjaxObjectBuilder<T> setPage(int page) {
		result.setPage(page);
		return this;
	}

	/**
	 * Set the number of show rows in the grid
	 * 
	 * @param itensPerPage
	 * @return EasyGridAjaxObjectBuilder
	 */
	public EasyGridAjaxObjectBuilder<T> setItensPerPage(int itensPerPage) {
		this.result.setItensPerPage(itensPerPage);
		return this;
	}

	private Method getMethod(String[] methodName) throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Method method = null;
		for (int i = 1; i < methodName.length; i++) {
			if (methodName.length != (i + 1)) {
				sharedObject = sharedObject.getClass().getMethod(methodName[i]).invoke(sharedObject);
				continue;
			}
			method = sharedObject.getClass().getMethod(methodName[i]);
		}
		return method;
	}

	private List<String> returnRow(Object obj) {
		List<String> row = new ArrayList<String>(columns.size());
		try {
			for (String methodName : columns) {
				Method method;
				// verify exists complex type
				if (methodName.contains(".")) {
					String[] methodNames = methodName.split(ESCAPE_DOT);
					method = obj.getClass().getMethod(methodNames[0]);
					sharedObject = method.invoke(obj);
					method = getMethod(methodNames);
					row.add(getValue(method, sharedObject));
				} else {
					method = obj.getClass().getMethod(methodName);

					row.add(getValue(method, obj));
				}
			}
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		return row;
	}

	private String getValue(Method method, Object obj) throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Object result = method.invoke(obj);
		if (result == null) {
			return "";
		}
		if (patternDate.containsKey(method.getName())) {
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
		// method.replaceAll(ESCAPE_DOT, ".get");

		Pattern pattern = Pattern.compile("\\.(a-zA-Z)");
		Matcher matcher = pattern.matcher(method);
		if (matcher.matches()) {
			System.out.println(matcher.group());
			System.out.println(matcher.groupCount());
		}

		return builder.toString();
	}
}
