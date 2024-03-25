package com.jcode.ebookpedia.criteria;

import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcode.ebookpedia.criteria.filter.Filter;
import com.jcode.ebookpedia.criteria.page.Page;
import com.jcode.ebookpedia.criteria.sort.Sort;


public class Criteria {
	
	// Fields

	private final List<Filter> filters;
	
	private final List<Sort> sorts;
	
	private final Page page;
	
	// Constructor
	
	@JsonCreator
	public Criteria(@JsonProperty("filters") List<Filter> filters, 
					@JsonProperty("sorts") List<Sort> sorts, 
					@JsonProperty("page") Page page) {
		super();
		this.filters = filters;
		this.sorts = sorts;
		this.page = page;
	}

	public List<Filter> getFilters() {
		return filters;
	}

	public List<Sort> getSorts() {
		return sorts;
	}

	public Page getPage() {
		return page;
	}

	public static Criteria fromBase64String(String base64String) {
		String jsonString = new String(Base64.getUrlDecoder().decode(base64String));
		ObjectMapper mapper = new ObjectMapper();
	    try {
			return mapper.readValue(jsonString, Criteria.class);
		} catch (JsonProcessingException ex) {
			throw new IllegalArgumentException("Cannot parse base64 string", ex);
		}
	}

}
