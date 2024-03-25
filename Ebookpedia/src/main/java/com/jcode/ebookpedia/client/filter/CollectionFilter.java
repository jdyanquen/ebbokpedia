package com.jcode.ebookpedia.client.filter;

import java.util.Collection;

public class CollectionFilter extends AbstractFilter<Collection<?>> {

	// For serialization
	protected CollectionFilter() {
	}

	public CollectionFilter(String fieldName, FilterOperator comparison,
			Collection<?> value) {
		super(fieldName, comparison, value);
	}

}
