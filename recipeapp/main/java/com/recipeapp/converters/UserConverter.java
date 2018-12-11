package com.recipeapp.converters;

import org.jasypt.util.password.BasicPasswordEncryptor;

import com.recipeapp.dto.UserDto;
import com.recipeapp.entity.User;


public class UserConverter {

	public static User toUser(UserDto userDto) {
		if (userDto != null) {
			User user = new User();
			user.setEmail(userDto.getEmail());
			user.setUsername(userDto.getUsername());
			BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
			user.setPassword(encryptor.encryptPassword(userDto.getPassword()));
			user.setActive(true);
			return user;
		} else {
			return null;
		}

	}

	public static User toEditUser(UserDto userDto) {
		if (userDto != null) {
			User user = new User();
			user.setId(userDto.getId());
			user.setEmail(userDto.getEmail());
			user.setUsername(userDto.getUsername());
			BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
			user.setPassword(encryptor.encryptPassword(userDto.getPassword()));
			user.setActive(true);
			return user;
		} else {
			return null;
		}

	}

	public static UserDto toUserDto(User user) {
		if (user != null) {
			UserDto userDto = new UserDto();
			userDto.setId(user.getId());
			userDto.setEmail(user.getEmail());
			userDto.setUsername(user.getUsername());
			userDto.setPassword(user.getPassword());
			userDto.setActive(user.isActive());
			return userDto;
		} else {
			return null;
		}

	}
}
