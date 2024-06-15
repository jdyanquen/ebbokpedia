package com.jcode.ebookpedia.post.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "language")
public class Language {
		
	@Id
	private Long id;
	
	@Column(name = "iso_code", unique = true, nullable = false)
	private String isoCode;
	
	@Column(name = "iso_name", unique = true, nullable = false)
	private String isoName;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public String getIsoName() {
		return isoName;
	}

	public void setIsoName(String isoName) {
		this.isoName = isoName;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		return obj instanceof Language && Objects.equals(this.getId(), ((Language)obj).getId());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getId());
	}
}