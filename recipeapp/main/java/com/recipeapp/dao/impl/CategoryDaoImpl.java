package com.recipeapp.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.recipeapp.dao.CategoryDao;
import com.recipeapp.entity.Category;

@Repository(value = "categoryDao")
public class CategoryDaoImpl implements CategoryDao{
	
	private static final Logger logger = LogManager.getLogger(CategoryDaoImpl.class.getName());


	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Category> getAllCategory() {
		try {
			logger.debug("finding all categorys");
			return entityManager.createQuery("Select category from Category category").getResultList();
		}catch(Exception e) {
			logger.error("error finding categorys: " + e.getMessage());
			return null;
		}
	}
}
