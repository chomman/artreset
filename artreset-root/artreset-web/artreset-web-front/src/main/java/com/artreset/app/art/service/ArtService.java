/**
 * 
 */
package com.artreset.app.art.service;

import java.util.List;

import com.artreset.app.art.dto.ArtDTO;
import com.artreset.app.art.dto.ProductDTO;
import com.artreset.app.art.exception.ArtNotFoundException;
import com.artreset.app.art.exception.ProductNotFoundException;
import com.artreset.app.art.model.Art;
import com.artreset.app.art.model.Product;
import com.artreset.model.Artist;

/**
 * @author Taehyun Jung
 *
 */
public interface ArtService {
	
    public Art add(ArtDTO added);

    public Art deleteById(Long id) throws ArtNotFoundException;

    public List<Art> findAll();

    public Art findById(Long id) throws ArtNotFoundException;

    public Art update(ArtDTO updated) throws ArtNotFoundException;
    
    public List<Art> findByArtist(Artist artist) throws ArtNotFoundException;
    
    public List<Art> findByCategory(Long categorId) throws ArtNotFoundException;

	public Product produce(ProductDTO product);
	
	public Product getProductById(Long id) throws ProductNotFoundException;

}
