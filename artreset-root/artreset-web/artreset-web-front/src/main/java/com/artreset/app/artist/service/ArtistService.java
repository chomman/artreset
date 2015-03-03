/**
 * 
 */
package com.artreset.app.artist.service;

import java.util.List;

import com.artreset.app.artist.dto.ArtistDTO;
import com.artreset.app.artist.exception.ArtistNotFoundException;
import com.artreset.model.Artist;

/**
 * @author Taehyun Jung
 *
 */
public interface ArtistService {
	
    public Artist add(ArtistDTO added);

    public Artist deleteById(Long id) throws ArtistNotFoundException;

    public List<Artist> findAll();

    public Artist findById(Long id) throws ArtistNotFoundException;
    
    public Artist findByEmail(String email) throws ArtistNotFoundException;

    public Artist update(ArtistDTO updated) throws ArtistNotFoundException;

}
