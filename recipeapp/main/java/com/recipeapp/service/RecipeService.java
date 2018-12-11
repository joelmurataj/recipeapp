package com.recipeapp.service;

import java.util.List;

import com.recipeapp.dto.RecipeDto;

public interface RecipeService {

	public boolean create(RecipeDto recipeDto);
	public boolean delete(int recipeId);
	public boolean update(RecipeDto recipeDto);
	public RecipeDto findById(int id);
	public List<RecipeDto> getAllRecipes(int userId);
	public List<RecipeDto> getAllRecipes();
	public List<RecipeDto> filterByTitle(String title);
}
