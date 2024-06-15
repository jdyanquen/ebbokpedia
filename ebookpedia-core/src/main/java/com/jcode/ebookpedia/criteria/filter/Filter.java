package com.jcode.ebookpedia.criteria.filter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jcode.ebookpedia.criteria.filter.deserializer.FilterDeserializer;

/**
 * Class <strong>Filter</strong>
 * 
 * @author Jesus David
 * @version 0.1
 */
@JsonDeserialize(using = FilterDeserializer.class)
public interface Filter {

	String getFieldName();

	FilterOperator getFilterOperator();

	<T> T getValue();

}
