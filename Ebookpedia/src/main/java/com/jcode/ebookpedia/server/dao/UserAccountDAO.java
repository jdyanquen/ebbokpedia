package com.jcode.ebookpedia.server.dao;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.jcode.ebookpedia.server.model.UserAccount;

@Repository
public class UserAccountDAO extends AbstractDAO<UserAccount, Long> {

	public UserAccount getFromEmail(String emailAddress) {
		try {
			Query query = getEntityManager()
					.createQuery("SELECT ua FROM UserAccount ua WHERE ua.email = :emailAddress ");
			query.setParameter("emailAddress", emailAddress);
			return (UserAccount) query.getSingleResult();

		} catch (Exception e) {
			return null;
		}
	}
}
