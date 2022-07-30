package com.jcode.ebookpedia.web.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class FileType implements BaseBean {
	
	/** Serial Version UUID */
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	
	@Column(name = "NAME", unique = true, nullable = false)
	private String name;
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		return obj instanceof FileType && Objects.equals(this.getId(), ((FileType)obj).getId());
	}
}