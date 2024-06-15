package com.jcode.ebookpedia.post.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "file_type")
public class FileType {

	@Id
	private Long id;
	
	@Column(name = "name", unique = true, nullable = false)
	private String name;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		return obj instanceof FileType && Objects.equals(this.getId(), ((FileType)obj).getId());
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getId());
	}
	
}
