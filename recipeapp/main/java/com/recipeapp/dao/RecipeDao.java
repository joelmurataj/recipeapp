package com.recipeapp.dao;

import java.util.List;

import com.recipeapp.entity.Recipe;

public interface RecipeDao {

	public boolean create(Recipe recipe);
	public boolean delete(int recipeId);
	public boolean update(Recipe recipe);
	public Recipe findById(int id);
	public List<Recipe> getAllRecipes(int userId);
	public List<Recipe> getAllRecipes();
	public List<Recipe> filterByTitle(String title);
}
