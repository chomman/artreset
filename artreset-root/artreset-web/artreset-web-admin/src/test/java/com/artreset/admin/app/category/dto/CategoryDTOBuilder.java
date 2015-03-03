/**
 * 
 */
package com.artreset.admin.app.category.dto;

import com.artreset.app.category.dto.CategoryDTO;


/**
 * @author Taehyun Jung
 *
 */
public class CategoryDTOBuilder {
	
	private CategoryDTO dto;
	
	public CategoryDTOBuilder() {
		dto = new CategoryDTO();
	}
	
	public CategoryDTOBuilder id(Long id){
		dto.setId(id);
		return this;
	}
	
	public CategoryDTOBuilder name(String name){
		dto.setName(name);
		return this;
	}
	
	public CategoryDTOBuilder description(String description){
		dto.setDescription(description);
		return this;
	}
	
	public CategoryDTOBuilder parentId(Long parentId){
		dto.setParentId(parentId);
		return this;
	}
	
	public CategoryDTO build(){
		return dto;
	}

}
