package com.artreset.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artreset.model.Image;
import com.artreset.model.Todo;

/**
 * Image 저장 Repository
 * 
 * @author Taehyun Jung
 */
public interface ImageRepository extends JpaRepository<Image, Long> {
}
