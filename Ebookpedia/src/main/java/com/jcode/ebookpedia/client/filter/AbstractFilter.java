package com.jcode.ebookpedia.client.filter;

public abstract class AbstractFilter<T> implements Filter<T> {

	private String fieldName;

	private FilterOperator comparison;

	private T value;

	// For serialization
	protected AbstractFilter() {
	}

	public AbstractFilter(String fieldName, FilterOperator comparison, T value) {
		this.fieldName = fieldName;
		this.comparison = comparison;
		this.value = value;
	}

	@Override
	public String getFieldName() {
		return fieldName;
	}

	@Override
	public FilterOperator getFilterOperator() {
		return comparison;
	}

	@Override
	public T getValue() {
		return value;
	}

}
