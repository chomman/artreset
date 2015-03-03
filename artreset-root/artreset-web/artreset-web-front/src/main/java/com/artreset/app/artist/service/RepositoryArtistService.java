/**
 * 
 */
package com.artreset.app.artist.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artreset.app.artist.dto.ArtistDTO;
import com.artreset.app.artist.exception.ArtistNotFoundException;
import com.artreset.app.artist.repository.ArtistRepository;
import com.artreset.image.repository.ImageRepository;
import com.artreset.model.Artist;
import com.artreset.model.Image;
import com.artreset.model.User;
import com.artreset.user.repository.UserRepository;
import com.artreset.user.service.AuthenticationFacade;

/**
 * @author Taehyun Jung
 *
 */
@Service
public class RepositoryArtistService implements ArtistService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryArtistService.class);

    private final ArtistRepository repository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    
    @Autowired
	public RepositoryArtistService(ArtistRepository repository, UserRepository userRepository, ImageRepository imageRepository) {
		this.repository = repository;
		this.userRepository = userRepository;
		this.imageRepository = imageRepository;
	}

    @Transactional
	@Override
	public Artist add(ArtistDTO added) {
		Artist result = null;
		String currentUserEmail = AuthenticationFacade.getContext().getAuthentication().getName();
		User currentUser = userRepository.findByEmail(currentUserEmail);
		LOGGER.debug("current user information 1: {}", currentUser);
		
		Image avatar = null, pageCover = null;
		
		if(added.getAvatarId() != null && added.getAvatarId() != null){
			avatar = imageRepository.findOne(added.getAvatarId());
		}
		
		if(added.getPageCoverId() != null && added.getPageCoverId() != null){
			pageCover = imageRepository.findOne(added.getPageCoverId());
		}
		
		Artist artist = Artist.getBuilder(currentUser)
				.nickname(added.getNickName())
				.description(added.getDescription())
				.shortDescription(added.getShortDescription())
				.avatar(avatar)
				.pageCover(pageCover)
				.build();
		
		result = repository.save(artist);
		return result;
	}

    @Transactional(rollbackFor = {ArtistNotFoundException.class})
	@Override
	public Artist deleteById(Long id) throws ArtistNotFoundException {
		LOGGER.debug("Deleting a artist entry with id: {}", id);
		
		Artist deleted = findById(id);
		LOGGER.debug("Deleting artist entry: {}", deleted);
		
		repository.delete(deleted);
		
		return deleted;
	}

    @Transactional(rollbackFor = {ArtistNotFoundException.class})
	@Override
	public Artist update(ArtistDTO updated) throws ArtistNotFoundException {
    	LOGGER.debug("Updating artist with information: {}", updated);
    	
    	Artist model = findById(updated.getId());
    	LOGGER.debug("Found a artist entry: {}", model);
    	
    	Image avatar = null, pageCover = null;
    	
    	if(updated.getAvatarId() != null){
    		avatar = imageRepository.findOne(updated.getAvatarId());
    	}else{
    		avatar = model.getAvatar();
    	}
    	
    	if(updated.getPageCoverId() != null){
    		pageCover = imageRepository.findOne(updated.getPageCoverId());
    	}else{
    		pageCover = model.getPageCover();
    	}
    	
    	model.update(updated.getNickName(), updated.getDescription(), 
    			updated.getShortDescription(), model.getArtistUserInfo(), 
    			avatar, pageCover);
    	
		return model;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Artist> findAll() {
		LOGGER.debug("Finding all artist entries.");
		return repository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Artist findById(Long id) throws ArtistNotFoundException {
		LOGGER.debug("Finding a artist entry with id: {}", id);
		Artist found = repository.findOne(id);
		LOGGER.debug("Found artist entry : {}", found);
		
		if(found == null){
			LOGGER.debug("artist id {} was not found.", id);
			throw new ArtistNotFoundException("Not Found Artist with id " + id);
		}
		
		return found;
	}
	
	@Transactional(readOnly = true)
	@Override
	public Artist findByEmail(String email) throws ArtistNotFoundException {
		LOGGER.debug("Finding a artist entry with email: {}", email);
		User user = userRepository.findByEmail(email);
		LOGGER.debug("Found user id {} for seach artist.", user.getId());
		return findById(user.getId());
	}

}
