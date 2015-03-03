package com.artreset.app.art.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artreset.app.art.model.Art;
import com.artreset.model.Artist;

/**
 * @author Taehyun Jung
 */
public interface ArtRepository extends JpaRepository<Art, Long> {
	
	List<Art> findByArtist(Artist artist);
}
