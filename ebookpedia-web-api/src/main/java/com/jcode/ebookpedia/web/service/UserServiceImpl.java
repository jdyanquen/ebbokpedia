package com.jcode.ebookpedia.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jcode.ebookpedia.web.dao.UserAccountDao;
import com.jcode.ebookpedia.web.model.UserAccount;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserAccountDao dao;

	public UserAccount findById(long id) {
		return dao.findById(id);
	}

	public UserAccount findBySSO(String sso) {
		UserAccount user = dao.findBySSO(sso);
		return user;
	}

	public void saveUser(UserAccount user) {
		dao.save(user);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate
	 * update explicitly. Just fetch the entity from db and update it with proper
	 * values within transaction. It will be updated in db once transaction ends.
	 */
	public void updateUser(UserAccount user) {
		UserAccount entity = dao.findById(user.getId());
		if (entity != null) {
			entity.setSsoId(user.getSsoId());
			entity.setPassword(user.getPassword());
			entity.setFirstName(user.getFirstName());
			entity.setLastName(user.getLastName());
			entity.setEmail(user.getEmail());
			entity.setUserProfiles(user.getUserProfiles());
		}
	}

	public void deleteUserBySSO(String sso) {
		dao.deleteBySSO(sso);
	}

	public List<UserAccount> findAllUsers() {
		return dao.findAllUsers();
	}

	public boolean isUserSSOUnique(Integer id, String sso) {
		UserAccount user = findBySSO(sso);
		return (user == null || ((id != null) && (user.getId() == id)));
	}

}