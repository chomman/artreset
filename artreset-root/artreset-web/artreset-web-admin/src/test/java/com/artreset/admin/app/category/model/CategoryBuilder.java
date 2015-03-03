/**
 * 
 */
package com.artreset.admin.app.category.model;

import org.springframework.test.util.ReflectionTestUtils;

import com.artreset.model.Category;

/**
 * @author Taehyun Jung
 *
 */
public class CategoryBuilder {
	private Category model;
	
	public CategoryBuilder(){
		model = new Category();
	}
	
	public CategoryBuilder id(Long id){
		ReflectionTestUtils.setField(model, "id", id);
		return this;
	}
	
	public CategoryBuilder name(String name){
		model.update(name, model.getDescription(), model.getParentCategory());
		return this;
	}
	
	public CategoryBuilder description(String description){
		model.update(model.getName(), description, model.getParentCategory());
		return this;
	}
	
	public CategoryBuilder parent(Category parent){
		model.update(model.getName(), model.getDescription(), parent);
		return this;
	}
	
	public Category build(){
		return model;
	}
}
