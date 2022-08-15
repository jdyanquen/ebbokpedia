package com.jcode.ebookpedia.client.filter;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Class <strong>Filter</strong>
 * 
 * @author Jesus David
 * @version 0.1
 */
public interface Filter<T> extends IsSerializable {

	public String getFieldName();

	public FilterOperator getFilterOperator();

	public T getValue();

}
