package com.jcode.ebookpedia.criteria.page;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Page {

	// Fields
	
	private final int offest;
	
	private final int size;
	
	// Constructor

	@JsonCreator
	public Page(@JsonProperty("offset") int offest, 
				@JsonProperty("size") int size) {
		super();
		this.offest = offest;
		this.size = size;
	}

	public int getOffest() {
		return offest;
	}

	public int getSize() {
		return size;
	}
	
}
