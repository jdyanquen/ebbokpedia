package com.jcode.ebookpedia.criteria.filter;

public abstract class AbstractFilter<T> implements Filter {

	protected String fieldName;

	protected FilterOperator comparison;

	protected T value;

	// For serialization
	protected AbstractFilter() {
	}

	protected AbstractFilter(String fieldName, FilterOperator comparison, T value) {
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

	@SuppressWarnings("unchecked")
	@Override
	public T getValue() {
		return value;
	}

}
