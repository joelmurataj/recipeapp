package com.recipeapp.service;

import com.recipeapp.dto.UserDto;

public interface UserService {
	public boolean create(UserDto user);
	//public boolean remove(int userId);
	public boolean update(UserDto userDto);
	public UserDto existRecipe( String username, String email);
	
	//public UserDto findById(int id);
	//public List<UserDto> getAll(int id);
	public UserDto exist(String username, String password);
	public boolean update(String password, int userId);
}
