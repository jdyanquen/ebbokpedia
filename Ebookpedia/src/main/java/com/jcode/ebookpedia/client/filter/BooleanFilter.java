package com.jcode.ebookpedia.client.filter;

/**
 * Class <strong>BooleanFilter</strong>
 * 
 * @author Jesus David
 * @version 0.1
 */
public class BooleanFilter extends AbstractFilter<Boolean> {

	// For serialization
	protected BooleanFilter() {
	}

	public BooleanFilter(String fieldName, FilterOperator comparison,
			Boolean value) {
		super(fieldName, comparison, value);
	}
}
