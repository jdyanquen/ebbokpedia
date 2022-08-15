package com.jcode.ebookpedia.client.mvp.model;

public class DocumentDetailsDTO implements BaseBean {

	// Properties

	private Long id;

	private String description;

	private String link;

	// Constructor

	public DocumentDetailsDTO() {
	}

	// Getters and setters

	public String getDescription() {
		return description;
	}

	public Long getId() {
		return id;
	}

	public String getLink() {
		return link;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
