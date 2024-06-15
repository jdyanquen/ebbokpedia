package com.jcode.ebookpedia.criteria.filter;

import java.util.Collection;

public class CollectionFilter extends AbstractFilter<Collection<?>> {

	// For serialization
	public CollectionFilter() {
	}

	public CollectionFilter(String fieldName, FilterOperator comparison, Collection<?> value) {
		super(fieldName, comparison, value);
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public void setComparison(FilterOperator comparison) {
		this.comparison = comparison;
	}

	public void setValue(Collection<?> value) {
		this.value = value;
	}

}
