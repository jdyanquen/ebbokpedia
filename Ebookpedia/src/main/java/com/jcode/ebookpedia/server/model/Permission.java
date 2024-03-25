package com.jcode.ebookpedia.server.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "permission")
public class Permission implements Identifiable {

	// Properties

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permissions_id_seq")
	@SequenceGenerator(name = "permissions_id_seq", sequenceName = "permissions_id_seq", allocationSize = 1)
	private Integer id;
	
	@NotEmpty
	@Column(name = "NAME", unique = true, nullable = false)
	private String name;

	@NotEmpty
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;


	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		return obj instanceof UserAccount && Objects.equals(this.getId(), ((Permission)obj).getId());
	}
	
}