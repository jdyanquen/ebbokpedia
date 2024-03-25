package com.jcode.ebookpedia.client.dto;

import java.util.Set;

public class UserDto {

    private String id;
    private String username;
    private Set<UserProfileDto> userProfiles;
        
	public UserDto() {
		super();
	}

	public UserDto(String id, String username, Set<UserProfileDto> userProfiles) {
		super();
		this.id = id;
		this.username = username;
		this.userProfiles = userProfiles;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Set<UserProfileDto> getUserProfiles() {
		return userProfiles;
	}
	
	public void setUserProfiles(Set<UserProfileDto> userProfiles) {
		this.userProfiles = userProfiles;
	}
    
}