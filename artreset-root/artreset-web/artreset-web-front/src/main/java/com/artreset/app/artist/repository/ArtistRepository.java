/**
 * 
 */
package com.artreset.app.artist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artreset.model.Artist;

/**
 * @author Taehyun Jung
 *
 */
public interface ArtistRepository extends JpaRepository<Artist, Long> {

}
