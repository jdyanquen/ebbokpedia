package com.jcode.ebookpedia.post.dto;

import java.util.List;

public class PostListingResponse {

	private Long totalRecords;
	private List<PostData> records;
	
	public PostListingResponse() {
		// Default constructor
	}

	public Long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public List<PostData> getRecords() {
		return records;
	}

	public void setRecords(List<PostData> records) {
		this.records = records;
	}
	
}
