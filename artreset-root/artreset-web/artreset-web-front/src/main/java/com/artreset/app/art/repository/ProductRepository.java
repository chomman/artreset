package com.artreset.app.art.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artreset.app.art.model.Art;
import com.artreset.app.art.model.Product;
import com.artreset.model.Artist;

/**
 * @author Taehyun Jung
 */
public interface ProductRepository extends JpaRepository<Product, Long> {	
}
