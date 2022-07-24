package com.jcode.ebookpedia.web.service;

import java.util.List;

import com.jcode.ebookpedia.web.model.UserAccount;

public interface UserService {

	UserAccount findById(long id);

	UserAccount findBySSO(String sso);

	void saveUser(UserAccount user);

	void updateUser(UserAccount user);

	void deleteUserBySSO(String sso);

	List<UserAccount> findAllUsers();

	boolean isUserSSOUnique(Integer id, String sso);

}