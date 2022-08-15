package com.jcode.ebookpedia.server.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "user_profile")
public class UserProfile implements Identifiable {

	// Properties

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_id_seq")
	@SequenceGenerator(name = "roles_id_seq", sequenceName = "roles_id_seq", allocationSize = 1)
	private Integer id;

	@Column(name = "TYPE", length = 15, unique = true, nullable = false)
	private String type = RoleType.GUEST.name();
	
	@NotEmpty
	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "USER_PROFILE_PERMISSION", 
        joinColumns = { @JoinColumn(name = "USER_PROFILE_ID") }, 
        inverseJoinColumns = { @JoinColumn(name = "PERMISSION_ID") }
    )
	private Set<Permission> permissions = new HashSet<>();

	

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		return obj instanceof UserProfile && Objects.equals(this.getId(), ((UserProfile)obj).getId());
	}

}