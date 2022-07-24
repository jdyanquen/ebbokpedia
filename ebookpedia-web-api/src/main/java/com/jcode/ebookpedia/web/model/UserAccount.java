package com.jcode.ebookpedia.web.model;

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

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "USER_ACCOUNTS")
public class UserAccount implements BaseBean {
	
	/** Serial Version UUID */
	private static final long serialVersionUID = 1L;

	// Properties

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
	@SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
	private Integer id;
	
	@NotEmpty
	@Column(name = "SSO_ID", unique = true, nullable = false)
	private String ssoId;

	@NotEmpty
	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@NotEmpty
	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;

	@NotEmpty
	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;

	@NotEmpty
	@Column(name = "EMAIL", unique = true, nullable = false)
	private String email;
	
	@Column(name = "AVATAR")
	private String avatar;

	@Column(name = "WEBSITE")
	private String website;

	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(name = "BIRTH_DATE")
	private Date birthDate;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "LAST_ACTIVITY")
	private Date lastActivity;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@NotEmpty
	@Column(name = "MEMBER_FROM", nullable = false)
	private Date memberFrom;
	
	@NotEmpty
	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "USER_ACCOUNT_USER_PROFILE", 
        joinColumns = { @JoinColumn(name = "USER_ACCOUNT_ID") }, 
        inverseJoinColumns = { @JoinColumn(name = "USER_PROFILE_ID") }
    )
	private Set<UserProfile> userProfiles = new HashSet<>();

	

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		return obj instanceof UserAccount && Objects.equals(this.getId(), ((UserAccount)obj).getId());
	}

}