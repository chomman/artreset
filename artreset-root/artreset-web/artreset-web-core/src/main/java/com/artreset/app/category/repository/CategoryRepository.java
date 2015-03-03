package com.artreset.app.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artreset.model.Category;

/**
 * @author Taehyun Jung
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
