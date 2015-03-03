package com.artreset.app.category.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artreset.app.category.dto.CategoryDTO;
import com.artreset.app.category.exception.CategoryNotFoundException;
import com.artreset.app.category.repository.CategoryRepository;
import com.artreset.model.Category;

/**
 * @author Taehyun Jung
 *
 */
@Service
public class RepositoryCategoryService implements CategoryService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryCategoryService.class);
	
	private final CategoryRepository repository;
	
	@Autowired
	public RepositoryCategoryService(CategoryRepository repository) {
		this.repository = repository;
	}
	
	@Transactional
	@Override
	public Category add(CategoryDTO added) throws CategoryNotFoundException {
		LOGGER.debug("Adding a new category entry : {}", added);
		
		Category parent = null;
		
		if(added.getParentId() != null && added.getParentId() > 0){
			parent = findById(added.getParentId());
		}
		
		Category model = Category.getBuilder(added.getName())
				.description(added.getDescription())
				.parentCategory(parent)
				.build();
		
		return repository.save(model);
	}

	@Transactional(rollbackFor = {CategoryNotFoundException.class})
	@Override
	public Category update(CategoryDTO updated) throws CategoryNotFoundException {
		LOGGER.debug("update category : {}", updated);
		
		Category model = findById(updated.getId());
		LOGGER.debug("Found a model will update : {}", model);
		
		Category parent = null;
		
		if(updated.getParentId() != null && updated.getParentId() > 0){
			parent = findById(updated.getParentId());
		}
		
		model.update(updated.getName(), updated.getDescription(), parent);
		
		return model;
	}

	@Transactional(rollbackFor = {CategoryNotFoundException.class})
	@Override
	public Category deleteById(Long id) throws CategoryNotFoundException {
		LOGGER.debug("Delete Category id: {}", id);
		
		Category deleted = findById(id);
		LOGGER.debug("Found a model will delete : {}", deleted);
		
		repository.delete(deleted);
		return deleted;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Category> findAll() {
		LOGGER.debug("Finding all category entries");
		return repository.findAll();
	}

	@Transactional(readOnly = true, rollbackFor = {CategoryNotFoundException.class})
	@Override
	public Category findById(Long id) throws CategoryNotFoundException {
		LOGGER.debug("Finding a category entry with id : {}", id);
		
		Category found = repository.findOne(id);
		LOGGER.debug("Found category entry : {}", found);
		
		if(found == null){
			LOGGER.debug("category id {} was not found.", id);
			throw new CategoryNotFoundException("Not Fount Category with id " + id);
		}
		LOGGER.debug("category id {} was not null. Go ahead.", id);
		
		return found;
	}

}
