package com.jcode.ebookpedia.web.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.jcode.ebookpedia.web.model.UserAccount;

@Repository("userAccountDao")
public class UserAccountDaoImpl extends AbstractDao<Long, UserAccount> implements UserAccountDao {

	public UserAccount findById(long id) {
		UserAccount user = getByKey(id);
		if (user != null) {
			initializeCollection(user.getUserProfiles());
		}
		return user;
	}

	public UserAccount findBySSO(String sso) {
		System.out.println("SSO : " + sso);
		try {
			UserAccount user = (UserAccount) getEntityManager().createQuery("SELECT u FROM UserAccount u WHERE u.ssoId LIKE :ssoId")
					.setParameter("ssoId", sso).getSingleResult();

			if (user != null) {
				initializeCollection(user.getUserProfiles());
			}
			return user;
		} catch (NoResultException ex) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<UserAccount> findAllUsers() {
		List<UserAccount> users = getEntityManager().createQuery("SELECT DISTINCT u FROM UserAccount u JOIN FETCH u.userProfiles up JOIN FETCH up.permissions p ORDER BY u.firstName ASC")
				.getResultList();
		return users;
	}

	public void save(UserAccount user) {
		persist(user);
	}

	public void deleteBySSO(String sso) {
		UserAccount user = (UserAccount) getEntityManager().createQuery("SELECT u FROM UserAccount u WHERE u.ssoId LIKE :ssoId")
				.setParameter("ssoId", sso).getSingleResult();
		delete(user);
	}

	// An alternative to Hibernate.initialize()
	protected void initializeCollection(Collection<?> collection) {
		if (collection == null) {
			return;
		}
		collection.iterator().hasNext();
	}

}