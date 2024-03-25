package com.jcode.ebookpedia.server.model;

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
@Table(name = "category")
public class Category implements Identifiable {
		
	//Art, Biology, Chemical, Computation, Economy, Engineering, History, Laws, Literature, Mathematics, Medicine, Physical, Religion, Telecommunications, Other;
	
	@Id
	private Long id;
	
	@Column(name = "name", unique = true, nullable = false)
	private String name;
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		return obj instanceof Category && Objects.equals(this.getId(), ((Category)obj).getId());
	}
}