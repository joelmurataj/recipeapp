package com.recipeapp.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.stereotype.Repository;

import com.recipeapp.dao.UserDao;
import com.recipeapp.entity.User;

@Repository(value = "userDao")
public class UserDaoImpl implements UserDao {
	
	private static final Logger logger = LogManager.getLogger(UserDaoImpl.class.getName());

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public User exist(String username, String password) {
		try {
			logger.debug("finding if user exist");
			User user = entityManager
					.createQuery("Select user From User user Where user.username=:username and user.active=1",
							User.class)
					.setParameter("username", username).getSingleResult();
			BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
			if (encryptor.checkPassword(password, user.getPassword())) {
				logger.debug("user exist");
				return user;
			} else {
				logger.debug("user dont exist");
				return null;
			}
		} catch (Exception e) {
			logger.error("error finding user:" + e.getMessage());
			return null;

		}
	}

	@Override
	public User existUser(String username, String email) {
		try {
			logger.debug("finding user by username");
			return entityManager.createQuery(
					"Select user From User user Where user.username=:username or (user.email=:email and user.active=1)",
					User.class).setParameter("username", username).setParameter("email", email).getSingleResult();
		} catch (Exception e) {
			logger.error("error finding user " + e.getMessage());
			return null;

		}
	}

	@Override
	public boolean create(User user) {
		try {
			logger.debug("creating user.");
			entityManager.persist(user);
			logger.debug("user created succesfuly");
			return true;
		} catch (Exception e) {
			logger.error("Error creating user:" + e.getMessage());
			return false;
		}
	}
	
	@Override
	public boolean update(User user) {
		try {
			logger.debug("updating user {}.",user.getUsername());
			entityManager.merge(user);
			logger.debug("user updated succesfuly");
			return true;
		} catch (Exception e) {
			logger.error("Error updating user:" + e.getMessage());
			return false;
		}
	}

	@Override
	public boolean update(String password, int userId) {
		try {
			logger.debug("updating menager for user with id {}.", userId);
			entityManager.createQuery("update User user set user.password=:password where user.id=:id")
			.setParameter("password", password).setParameter("id", userId).executeUpdate();
			logger.debug("user updated succesfuly");
			return true;
		} catch (Exception e) {
			logger.error("Error updating user:" + e.getMessage());
			return false;
		}
	}
}
