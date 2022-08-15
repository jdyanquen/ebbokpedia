package com.jcode.ebookpedia.client.mvp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ReportDTO implements BaseBean {

	public enum Type {
		BROKEN_LINKS, INCONSISTENT, WRONG;

		public String toString() {
			String str = "";
			switch (this) {
			case BROKEN_LINKS:
				str = "Broken links";
				break;
			case INCONSISTENT:
				str = "Inconsistent";
				break;
			case WRONG:
				str = "Wrong";
				break;
			}
			return str;
		};
	}

	// Properties

	private Long id;

	private PostDTO postKey;

	private Type type;

	private String content;

	private Date createdAt;
	
	private transient Set<String> fts;

	// Constructor

	public ReportDTO() {
	}

	// Getters and setters

	public String getContent() {
		return content;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Set<String> getFts() {
		return fts == null ? fts = new HashSet<String>() : fts;
	}

	public Long getId() {
		return id;
	}

	public PostDTO getPostKey() {
		return postKey;
	}

	public Type getType() {
		return type;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setFts(Set<String> fts) {
		this.fts = fts;
	}

	public void setPostKey(PostDTO postKey) {
		this.postKey = postKey;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
