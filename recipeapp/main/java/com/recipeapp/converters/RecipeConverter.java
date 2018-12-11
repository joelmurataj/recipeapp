package com.recipeapp.converters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.recipeapp.dto.RecipeDto;
import com.recipeapp.entity.Category;
import com.recipeapp.entity.Recipe;
import com.recipeapp.entity.User;


public class RecipeConverter {

	public static Recipe toRecipe(RecipeDto recipeDto) {
		if (recipeDto != null) {
			Recipe recipe = new Recipe();
			recipe.setTitle(recipeDto.getTitle());
			User user = new User();
			user.setId(recipeDto.getUserId());
			recipe.setUser(user);
			Category category= new Category();
			category.setId(recipeDto.getCategoryId());
			recipe.setCategory(category);
			recipe.setDescription(recipeDto.getDescription());
			recipe.setImage(recipeDto.getImage());
			Date date = new Date();
			recipe.setDate(date);
			recipe.setActive(true);
			return recipe;
		} else {
			return null;
		}

	}

	public static Recipe toEditRecipe(RecipeDto recipeDto) {
		if (recipeDto != null) {
			Recipe recipe = new Recipe();
			recipe.setId(recipeDto.getId());
			recipe.setTitle(recipeDto.getTitle());
			User user = new User();
			user.setId(recipeDto.getUserId());
			recipe.setUser(user);
			Category category= new Category();
			category.setId(recipeDto.getCategoryId());
			recipe.setCategory(category);
			recipe.setDescription(recipeDto.getDescription());
			recipe.setImage(recipeDto.getImage());
			recipe.setActive(true);
			recipe.setDate(recipeDto.getDate());
			return recipe;
		} else {
			return null;
		}

	}

	public static RecipeDto toRecipeDto(Recipe recipe) {
		if (recipe != null) {
			RecipeDto recipeDto = new RecipeDto();
			recipeDto.setId(recipe.getId());
			recipeDto.setTitle(recipe.getTitle());
			recipeDto.setUserId(recipe.getUser().getId());
			recipeDto.setUsername(recipe.getUser().getUsername());
			recipeDto.setCategoryId(recipe.getCategory().getId());
			recipeDto.setCategoryName(recipe.getCategory().getName());
			recipeDto.setDescription(recipe.getDescription());
			recipeDto.setImage(recipe.getImage());
			recipeDto.setDate(recipe.getDate());
			recipeDto.setActive(recipe.isActive());
			return recipeDto;
		} else {
			return null;
		}

	}

	public static List<RecipeDto> toRecipeListDto(List<Recipe> list) {
		ArrayList<RecipeDto> recipeDto = new ArrayList<>();
		if (list != null) {
			for (Recipe recipe : list) {
				recipeDto.add(toRecipeDto(recipe));
			}
			return recipeDto;
		} else {
			return recipeDto;
		}
	}
}
