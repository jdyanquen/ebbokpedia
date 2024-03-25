package com.jcode.ebookpedia.criteria.sort;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Specifies the field and direction to order the result set
 */
public class Sort {
	
	
	// Fields
	
	private final String field;
	
	private final Direction direction;
	
	// Constructor

	@JsonCreator
	public Sort(@JsonProperty("field") String field, 
				@JsonProperty("direction") Direction direction) {
		super();
		this.field = field;
		this.direction = direction;
	}

	public String getField() {
		return field;
	}

	public Direction getDirection() {
		return direction;
	}


	/**
	 * Defines order direction
	 */
	public enum Direction {
		ASC, DESC
	}
}
