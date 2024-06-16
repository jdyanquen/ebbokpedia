package com.jcode.ebookpedia.criteria.filter;

/**
 * Class <strong>StringFilter</strong>
 * 
 * @author Jesus David
 * @version 0.1
 */
public class StringFilter extends AbstractFilter<String> {

	// For serialization
	protected StringFilter() {
	}

	public StringFilter(String fieldName, FilterOperator comparison, String value) {
		super(fieldName, comparison, value);
	}
	
}
