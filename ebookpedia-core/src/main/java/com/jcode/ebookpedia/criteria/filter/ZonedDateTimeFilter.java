package com.jcode.ebookpedia.criteria.filter;

import java.time.ZonedDateTime;


/**
 * Class <strong>ZonedDateTimeFilter</strong>
 * 
 * @author Jesus David
 * @version 0.1
 */
public class ZonedDateTimeFilter extends AbstractFilter<ZonedDateTime> {

	// For serialization
	protected ZonedDateTimeFilter() {
	}

	public ZonedDateTimeFilter(String fieldName, FilterOperator comparison, ZonedDateTime value) {
		super(fieldName, comparison, value);
	}

}
