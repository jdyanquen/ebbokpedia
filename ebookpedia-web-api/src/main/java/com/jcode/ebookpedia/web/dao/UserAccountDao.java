package com.jcode.ebookpedia.web.dao;

import java.util.List;

import com.jcode.ebookpedia.web.model.UserAccount;

public interface UserAccountDao {

	UserAccount findById(long id);

	UserAccount findBySSO(String sso);

	void save(UserAccount user);

	void deleteBySSO(String sso);

	List<UserAccount> findAllUsers();

}