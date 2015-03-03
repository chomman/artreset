package com.artreset.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.artreset.common.model.BaseEntity;

/**
 * @author Taehyun Jung
 */
@Entity
@Table(name="category")
public class Category extends BaseEntity<Long> {
	
	public static final int MAX_LENGTH_NAME = 100;
	public static final int MAX_LENGTH_DESCRIPTION = 500;
	
	@Id
	@Column(name="CATEGORY_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name", nullable = false, length = MAX_LENGTH_NAME)
	private String name;
	
	@Column(name = "description", nullable = true, length = MAX_LENGTH_NAME)
	private String description;
	
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="parent_id")
	private Category parentCategory;
	
	@OneToMany(mappedBy="parentCategory")
	private Set<Category> childrenCategories;

	@Override
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
	public Category getParentCategory() {
		return parentCategory;
	}

	public Set<Category> getChildrenCategories() {
		return childrenCategories;
	}

	public static Builder getBuilder(String name){
		return new Builder(name);
	}
	
	public static class Builder {
		
		private Category built;
		
		public Builder(String name){
			built = new Category();
			built.name = name;
		}
		
		public Category build(){
			return built;
		}
		
		public Builder description(String description){
			built.description = description;
			return this;
		}
		
		public Builder parentCategory(Category parentCategory){
			built.parentCategory = parentCategory;
			return this;
		}
		
		public Builder childrenCategories(Set<Category> childrenCategories) {
			built.childrenCategories = childrenCategories;
			return this;
		}
	}	
	
	public void update(String name, String description, Category parent){
		this.name = name;
		this.description = description;
		this.parentCategory = parent;
	}

}
