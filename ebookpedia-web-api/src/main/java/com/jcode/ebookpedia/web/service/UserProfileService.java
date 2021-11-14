package com.jcode.ebookpedia.web.service;

import java.util.List;

import com.jcode.ebookpedia.web.model.UserProfile;

public interface UserProfileService {

	UserProfile findById(int id);

	UserProfile findByType(String type);

	List<UserProfile> findAll();

}