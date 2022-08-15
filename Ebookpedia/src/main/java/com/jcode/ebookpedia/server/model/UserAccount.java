package com.jcode.ebookpedia.server.model;

import java.util.Date;
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
@Table(name = "user_account")
public class UserAccount implements Identifiable {

	// Properties

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_account_id_seq")
	@SequenceGenerator(name = "user_account_id_seq", sequenceName = "user_account_id_seq", allocationSize = 1)
	private Long id;
	
	@NotEmpty
	@Column(name = "sso_id", unique = true, nullable = false)
	private String ssoId;

	@NotEmpty
	@Column(name = "password", nullable = false)
	private String password;

	@NotEmpty
	@Column(name = "first_name", nullable = false)
	private String firstName;

	@NotEmpty
	@Column(name = "last_name", nullable = false)
	private String lastName;

	@NotEmpty
	@Column(name = "email", unique = true, nullable = false)
	private String email;
	
	@Column(name = "avatar")
	private String avatar;

	@Column(name = "website")
	private String website;

	@Column(name = "birth_date")
	private Date birthDate;

	@Column(name = "last_activity")
	private Date lastActivity;

	@NotEmpty
	@Column(name = "member_from", nullable = false)
	private Date memberFrom;
	
	@NotEmpty
	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "user_account_user_profile", 
        joinColumns = { @JoinColumn(name = "user_account_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "user_profile_id") }
    )
	private Set<UserProfile> userProfiles = new HashSet<>();
	

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		return obj instanceof UserAccount && Objects.equals(this.getId(), ((UserAccount)obj).getId());
	}

}