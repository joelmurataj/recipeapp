package com.recipeapp.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipeapp.converters.UserConverter;
import com.recipeapp.dao.UserDao;
import com.recipeapp.dto.UserDto;
import com.recipeapp.entity.User;
import com.recipeapp.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;

	@Override
	public UserDto exist(String username, String password) {
		User user = userDao.exist(username, password);
		if (user != null) {

			return UserConverter.toUserDto(user);
		} else {
			return null;
		}
	}

	@Override
	public UserDto existRecipe(String username, String email) {
		return UserConverter.toUserDto(userDao.existUser(username,email));
	}

	@Override
	@Transactional
	public boolean create(UserDto user) {
		return userDao.create(UserConverter.toUser(user));
	}

	@Override
	@Transactional
	public boolean update(UserDto userDto) {
		return userDao.update(UserConverter.toEditUser(userDto));
	}

	@Override
	@Transactional
	public boolean update(String password, int userId) {
		return userDao.update(password, userId);
	}

}
