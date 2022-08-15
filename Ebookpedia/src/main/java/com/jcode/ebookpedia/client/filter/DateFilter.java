package com.jcode.ebookpedia.client.filter;

import java.util.Date;

/**
 * Class <strong>DateFilter</strong>
 * 
 * @author Jesus David
 * @version 0.1
 */
public class DateFilter extends AbstractFilter<Date> {

	// For serialization
	protected DateFilter() {
	}

	public DateFilter(String fieldName, FilterOperator comparison, Date value) {
		super(fieldName, comparison, value);
	}

}
