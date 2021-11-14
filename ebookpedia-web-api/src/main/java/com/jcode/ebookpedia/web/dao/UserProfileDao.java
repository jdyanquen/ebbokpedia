package com.jcode.ebookpedia.web.dao;

import java.util.List;

import com.jcode.ebookpedia.web.model.UserProfile;

public interface UserProfileDao {

	List<UserProfile> findAll();

	UserProfile findByType(String type);

	UserProfile findById(int id);
}