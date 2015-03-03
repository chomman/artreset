package com.artreset.app.category.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.artreset.common.dto.BaseDTO;
import com.artreset.model.Category;

/**
 * @author Taehyun Jung
 */
public class CategoryDTO extends BaseDTO {

	private Long id;
	
	@NotEmpty
	@Length(max = Category.MAX_LENGTH_NAME)
	private String name;
	
	@Length(max = Category.MAX_LENGTH_DESCRIPTION)
	private String description;
	
	private Long parentId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
}
