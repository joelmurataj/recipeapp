package com.recipeapp.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

import com.recipeapp.dto.RecipeDto;
import com.recipeapp.service.RecipeService;
import com.recipeapp.utility.Message;

@ManagedBean(name = "recipeManagementBean")
@ViewScoped
public class RecipeManagementBean {

	private RecipeDto recipeDto;
	private List<RecipeDto> recipeDtoList;
	private List<RecipeDto> recipeDtoListForUser;
	private RecipeDto selectedRecipe;
	private String recipeId;

	@ManagedProperty(value = "#{recipeService}")
	private RecipeService recipeService;
	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;

	@PostConstruct
	public void init() {
		recipeDto = new RecipeDto();
		refresh();
	}

	public void refresh() {
		this.recipeDtoList = recipeService.getAllRecipes();
		this.recipeDtoListForUser = recipeService.getAllRecipes(userBean.getUserDto().getId());
	}

	public void createRecipe() {
		recipeDto.setUserId(userBean.getUserDto().getId());
		if (recipeService.create(recipeDto)) {
			refresh();
			recipeDto = new RecipeDto();
			Message.addMessage(Message.bundle.getString("RECIPE_ADDED"), "info");

		} else {
			Message.addMessage(Message.bundle.getString("RECIPE_NOTADDED"), "warn");
		}

	}

	public void deleteRecipe(int recipeId) {

		if (recipeService.delete(recipeId)) {
			refresh();
			Message.addMessage(Message.bundle.getString("RECIPE_DELETED"), "info");
		} else {
			Message.addMessage(Message.bundle.getString("RECIPE_NOTDELETED"), "warn");

		}
	}

	public String editRecipe() {
		RecipeDto recipe;
		if (recipeId != null) {
			recipe = recipeService.findById(Integer.parseInt(recipeId));
			if (!recipe.equals(recipeDto)) {
				recipe.setCategoryId(recipeDto.getCategoryId());
				recipe.setDescription(recipeDto.getDescription());
				recipe.setTitle(recipeDto.getTitle());
				if (recipeService.update(recipe)) {
					refresh();
					Message.addFlushMessage(recipe.getTitle() + " :" + Message.bundle.getString("RECIPE_EDITED"),
							"info");
					return "myRecipeManagement?faces-redirect=true";

				} else {
					Message.addMessage(recipe.getTitle() + " :" + Message.bundle.getString("RECIPE_NOTEDITED"), "warn");
				}

			} else {
				Message.addMessage(Message.bundle.getString("NO_CHANGES"), "warn");

			}
		}
		return "";
	}

	public void loadUser() {
		if (recipeId != null && recipeDto != null && recipeDto.isActive()) {
			recipeDto = recipeService.findById(Integer.parseInt(recipeId));
		}
	}

	public void filterByTitul() {
		this.recipeDtoList = recipeService.filterByTitle(recipeDto.getTitle());
	}

	public RecipeDto getRecipeDto() {
		return recipeDto;
	}

	public void setRecipeDto(RecipeDto recipeDto) {
		this.recipeDto = recipeDto;
	}

	public List<RecipeDto> getRecipeDtoList() {
		return recipeDtoList;
	}

	public void setRecipeDtoList(List<RecipeDto> recipeDtoList) {
		this.recipeDtoList = recipeDtoList;
	}

	public RecipeService getRecipeService() {
		return recipeService;
	}

	public void setRecipeService(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public RecipeDto getSelectedRecipe() {
		return selectedRecipe;
	}

	public void setSelectedRecipe(RecipeDto selectedRecipe) {
		this.selectedRecipe = selectedRecipe;
	}

	public List<RecipeDto> getRecipeDtoListForUser() {
		return recipeDtoListForUser;
	}

	public void setRecipeDtoListForUser(List<RecipeDto> recipeDtoListForUser) {
		this.recipeDtoListForUser = recipeDtoListForUser;
	}

	public String getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(String recipeId) {
		this.recipeId = recipeId;
	}
}
