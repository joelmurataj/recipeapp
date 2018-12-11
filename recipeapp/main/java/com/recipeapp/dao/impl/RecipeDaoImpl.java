package com.recipeapp.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.recipeapp.dao.RecipeDao;
import com.recipeapp.entity.Recipe;

@Repository(value = "recipeDao")
public class RecipeDaoImpl implements RecipeDao {
	
	private static final Logger logger = LogManager.getLogger(RecipeDaoImpl.class.getName());

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public boolean create(Recipe recipe) {
		try {
			logger.debug("creating recipe {}.",recipe.getTitle());
			entityManager.persist(recipe);
			logger.debug("the recipe was added.");
			return true;
		} catch (Exception e) {
			logger.error("Error adding recipe:" + e.getMessage());
			return false;
		}
	}

	@Override
	public boolean delete(int recipeId) {
		try {
			logger.debug("deleting recipe with id {}.",recipeId);
			entityManager.createQuery("update Recipe recipe set recipe.active=0 where recipe.id=:id")
					.setParameter("id", recipeId).executeUpdate();
			logger.debug("recipe was delted.");
			return true;
		} catch (Exception e) {
			logger.error("Error deleting project:" + e.getMessage());
			return false;
		}
	}

	@Override
	public boolean update(Recipe recipe) {
		try {
			logger.debug("updating recipe {}.",recipe.getTitle());
			entityManager.merge(recipe);
			logger.debug("the recipe was updated.");
			return true;
		} catch (Exception e) {
			logger.error("Error updating recipe:" + e.getMessage());
			return false;
		}
	}

	@Override
	public Recipe findById(int id) {
		try {
			logger.debug("finding recipe with id {}.",id);
			return entityManager.find(Recipe.class, id);
		} catch (Exception e) {
			logger.error("Error finding recipe:" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<Recipe> getAllRecipes(int userId) {
		try {
			logger.debug("finding all recipe for user whit id{}",userId);
			return (List<Recipe>) entityManager
					.createQuery("select recipe from Recipe recipe where recipe.user.id=:userId and recipe.active=1  ORDER BY recipe.date DESC",
							Recipe.class)
					.setParameter("userId", userId).getResultList();
			
		} catch (RuntimeException e) {
			logger.error("error finding recipes" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<Recipe> getAllRecipes() {
		try {
			logger.debug("finding all recipes");
			return (List<Recipe>) entityManager
					.createQuery("select recipe from Recipe recipe where recipe.active=1  ORDER BY recipe.date DESC",
							Recipe.class).getResultList();
		} catch (RuntimeException e) {
			logger.error("error finding recipes: " + e.getMessage());
			return null;
		}
	}
	
	@Override
	public List<Recipe> filterByTitle(String title) {
		try {
			logger.debug("filter list of recipes");
			ArrayList<Recipe> resultList = (ArrayList<Recipe>) entityManager
					.createQuery("select recipe from Recipe recipe "
								+ "where recipe.title LIKE :title "
								+ "and recipe.active=1")
					.setParameter("title", "%" + title + "%").getResultList();
			if (resultList.isEmpty()) {
				logger.debug("not any recipe found");
				return null;
			} else {
				logger.debug("recipes retrieved:" + resultList);
				return resultList;
			}
		} catch (Exception e) {
			logger.error("error finding recipes:" + e.getMessage());
			return null;
		}
	}

}
