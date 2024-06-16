package com.jcode.ebookpedia.criteria.filter;

public class NumberFilter extends AbstractFilter<Number> {

	// For serialization
	protected NumberFilter() {
	}

	public NumberFilter(String fieldName, FilterOperator comparison, Number value) {
		super(fieldName, comparison, value);
	}

}
