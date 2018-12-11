package com.recipeapp.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipeapp.converters.RecipeConverter;
import com.recipeapp.dao.RecipeDao;
import com.recipeapp.dto.RecipeDto;
import com.recipeapp.service.RecipeService;

@Service("recipeService")
public class RecipeServiceImpl implements RecipeService{

	@Autowired
	RecipeDao recipeDao;
	
	@Override
	@Transactional
	public boolean create(RecipeDto recipeDto) {
		return recipeDao.create(RecipeConverter.toRecipe(recipeDto));
	}

	@Override
	@Transactional
	public boolean delete(int recipeId) {
		return recipeDao.delete(recipeId);
	}

	@Override
	@Transactional
	public boolean update(RecipeDto recipeDto) {
		return recipeDao.update(RecipeConverter.toEditRecipe(recipeDto));
	}

	@Override
	public RecipeDto findById(int id) {
		return RecipeConverter.toRecipeDto(recipeDao.findById(id));
	}

	@Override
	public List<RecipeDto> getAllRecipes(int userId) {
		return RecipeConverter.toRecipeListDto(recipeDao.getAllRecipes(userId));
	}

	@Override
	public List<RecipeDto> getAllRecipes() {
		return RecipeConverter.toRecipeListDto(recipeDao.getAllRecipes());
	}
	
	@Override
	public List<RecipeDto> filterByTitle(String title){
		return RecipeConverter.toRecipeListDto(recipeDao.filterByTitle(title));
	}
}
