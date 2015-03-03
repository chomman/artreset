/**
 * 
 */
package com.artreset.app.art.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artreset.app.art.dto.ArtDTO;
import com.artreset.app.art.dto.ProductDTO;
import com.artreset.app.art.exception.ArtNotFoundException;
import com.artreset.app.art.exception.ProductNotFoundException;
import com.artreset.app.art.model.Art;
import com.artreset.app.art.model.Product;
import com.artreset.app.art.repository.ArtRepository;
import com.artreset.app.art.repository.ProductRepository;
import com.artreset.app.artist.repository.ArtistRepository;
import com.artreset.app.artist.service.RepositoryArtistService;
import com.artreset.model.Artist;

/**
 * @author Taehyun Jung
 *
 */
@Service
public class RepositoryArtService implements ArtService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryArtService.class);
	
	private final ArtRepository artRepository;
	private final ArtistRepository artistRepository;
	private final ProductRepository productRepository;
	
	@Autowired
	public RepositoryArtService(ArtRepository artRepository, ArtistRepository artistRepository, ProductRepository productRepository) {
		this.artRepository = artRepository;
		this.artistRepository = artistRepository;
		this.productRepository = productRepository;
	}

	@Transactional
	@Override
	public Art add(ArtDTO added) {
		LOGGER.debug("Adding a art entry with information: {}", added);
		
		Art art = Art.getBuilder(added.getTitle())
				.description(added.getDescription())
				.artist(added.getArtist())
				.category(added.getCategory())
				.width(added.getWidth())
				.length(added.getLength())
				.height(added.getHeight())
				.unitLength(added.getUnitLength())
				.assentialImage(added.getAssentialImage())
				.detailImages(added.getDetailImages())
				.build();
		
		Art result = artRepository.save(art);
		return result;
	}

	@Transactional(rollbackFor = {ArtNotFoundException.class})
	@Override
	public Art deleteById(Long id) throws ArtNotFoundException {
		LOGGER.debug("Deleting a art entry with id: {}", id);
		
		Art deleted = findById(id);
		LOGGER.debug("Deleting to-do entry: {}", deleted);
		
		artRepository.delete(deleted);
		return deleted;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Art> findAll() {
		LOGGER.debug("Finding all art entries");
        return artRepository.findAll();
	}

	@Transactional(readOnly = true, rollbackFor = {ArtNotFoundException.class})
	@Override
	public Art findById(Long id) throws ArtNotFoundException {
		LOGGER.debug("Finding a art with id: {}", id);
		
		Art found = artRepository.findOne(id);
		LOGGER.debug("Found a art entry: {}", id);
		
		if(found == null){
			throw new ArtNotFoundException("No art entry found with id: " + id);
		}
		
		return found;
	}

	@Transactional(rollbackFor = {ArtNotFoundException.class})
	@Override
	public Art update(ArtDTO updated) throws ArtNotFoundException {
		LOGGER.debug("Updating a art with information: {}", updated);
		
		Art model = findById(updated.getId());
		LOGGER.debug("Found a art with information: {}", model);
		
		model.update(updated.getTitle(), updated.getDescription(), updated.getWidth(), updated.getLength(), updated.getHeight(), 
				updated.getArtist(), updated.getCategory(), updated.getAssentialImage(), updated.getDetailImages());
		
		return model;
	}

	@Override
	public List<Art> findByArtist(Artist artist) throws ArtNotFoundException {
		LOGGER.debug("Finding arts with artist information: {}", artist);
		
		List<Art> models = artRepository.findByArtist(artist);
		LOGGER.debug("Finding {} arts with information: {}", models.size(), models);
		
		return models;
	}

	@Override
	public List<Art> findByCategory(Long categorId) throws ArtNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product produce(ProductDTO dto) {
		LOGGER.debug("Producing a product with information: {}", dto);
		
		Product added = Product.getBuilder(dto.getArt())
					.price(dto.getPrice())
					.productStatus(dto.getProductStatus())
					.build();
		
		Product product = productRepository.save(added);
		
		return product;
	}
	
	@Override
	public Product getProductById(Long id) throws ProductNotFoundException {
		LOGGER.debug("Finding a proudct with id: {}", id);
		Product found = productRepository.findOne(id);

		LOGGER.debug("Found a art entry: {}", id);
		
		if(found == null){
			throw new ProductNotFoundException("No product entry found with id: " + id);
		}
		return found;
	}

}
