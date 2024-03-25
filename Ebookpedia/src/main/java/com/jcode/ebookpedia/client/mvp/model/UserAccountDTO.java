package com.jcode.ebookpedia.client.mvp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UserAccountDTO implements BaseBean {

	// Properties

	private Long id;
	
	private String emailAddress;

	private String nickname;

	private String firstName;

	private String lastName;

	private String photoId;

	private String websiteURL;

	private Date birthDate;

	private Date lastActivity;

	private Date memberFrom;
	
	private RoleEnum role;
	
	private UserStatusEnum status;
	
	private boolean signedIn;
	
	private String loginURL;
	
	private String logoutURL;

	private int rating;
	
	private transient Set<String> keywords;

	// Constructor

	public UserAccountDTO() {
		setMemberFrom(new Date());
		setRole(RoleEnum.POSTER);
		setUserStatus(UserStatusEnum.ACTIVE);
	}

	// Getters and setters

	public Date getBirthDate() {
		return birthDate;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getFullName() {
		if (firstName == null || lastName == null) {
			return null;
		}
		return firstName + " " + lastName;
	}

	@Override
	public Long getId() {
		return id;
	}

	public Set<String> getKeywords() {
		return keywords == null ? keywords = new HashSet<String>() : keywords;
	}

	public Date getLastActivity() {
		return lastActivity;
	}

	public String getLastName() {
		return lastName;
	}

	public String getLoginURL() {
		return loginURL;
	}

	public String getLogoutURL() {
		return logoutURL;
	}

	public Date getMemberFrom() {
		return memberFrom;
	}

	public String getNickname() {
		return nickname;
	}

	public String getPhotoId() {
		return photoId;
	}

	public int getRating() {
		return rating > 0 ? rating : (int) Math.random() * 1000 % 5;
	}

	public RoleEnum getRole() {
		return role;
	}

	public UserStatusEnum getUserStatus() {
		return status;
	}

	public String getWebsiteURL() {
		return websiteURL;
	}

	public Boolean isSignedIn() {
		return signedIn;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public void setEmailAddress(String email) {
		this.emailAddress = email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setKeywords(Set<String> keywords) {
		this.keywords = keywords;
	}

	public void setLastActivity(Date lastActivity) {
		this.lastActivity = lastActivity;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setLoginURL(String loginURL) {
		this.loginURL = loginURL;
	}

	public void setLogoutURL(String logoutURL) {
		this.logoutURL = logoutURL;
	}

	public void setMemberFrom(Date memberFrom) {
		this.memberFrom = memberFrom;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public void setRole(RoleEnum role) {
		this.role = role;
	}

	public void setSignedIn(boolean isSignedIn) {
		this.signedIn = isSignedIn;
	}

	public void setUserStatus(UserStatusEnum status) {
		this.status = status;
	}

	public void setWebsiteURL(String websiteURL) {
		this.websiteURL = websiteURL;
	}

}
