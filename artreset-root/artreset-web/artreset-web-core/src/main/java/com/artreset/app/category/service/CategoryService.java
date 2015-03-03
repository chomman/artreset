package com.artreset.app.category.service;

import java.util.List;

import com.artreset.app.category.dto.CategoryDTO;
import com.artreset.app.category.exception.CategoryNotFoundException;
import com.artreset.model.Category;

/**
 * @author Taehyun Jung
 */
public interface CategoryService {

	public Category add(CategoryDTO added) throws CategoryNotFoundException;

	public Category update(CategoryDTO updated) throws CategoryNotFoundException;
	
	public Category deleteById(Long id) throws CategoryNotFoundException;

	public List<Category> findAll();

	public Category findById(Long id) throws CategoryNotFoundException;

}
