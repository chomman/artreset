/**
 * 
 */
package com.artreset.app.art.model;

import java.util.Set;

import org.springframework.test.util.ReflectionTestUtils;

import com.artreset.model.Artist;
import com.artreset.model.Category;
import com.artreset.model.Image;

/**
 * @author Taehyun Jung
 *
 */
public class ArtBuilder {
	
	private Art model;
	
	public ArtBuilder() {
		model = new Art();
	}
	
	public ArtBuilder id(Long id) {
		ReflectionTestUtils.setField(model, "id", id);
		return this;
	}
	
	public ArtBuilder title(String title) {
		model.update(title, model.getDescription(), model.getWidth(), model.getLength(), model.getHeight(), model.getArtist(), model.getCategory(), model.getAssentialImage(), model.getDetailImages());
		return this;
	}
	
	public ArtBuilder description(String description) {
		model.update(model.getTitle(), description, model.getWidth(), model.getLength(), model.getHeight(), model.getArtist(), model.getCategory(), model.getAssentialImage(), model.getDetailImages());
		return this;
	}
	
	public ArtBuilder width(Float width) {
		model.update(model.getTitle(), model.getDescription(), width, model.getLength(), model.getHeight(), model.getArtist(), model.getCategory(), model.getAssentialImage(), model.getDetailImages());
		return this;
	}
	
	public ArtBuilder length(Float length) {
		model.update(model.getTitle(), model.getDescription(), model.getWidth(), length, model.getHeight(), model.getArtist(), model.getCategory(), model.getAssentialImage(), model.getDetailImages());
		return this;
	}
	
	public ArtBuilder height(Float height) {
		model.update(model.getTitle(), model.getDescription(), model.getWidth(), model.getLength(), height, model.getArtist(), model.getCategory(), model.getAssentialImage(), model.getDetailImages());
		return this;
	}
	
	public ArtBuilder artist(Artist artist) {
		model.update(model.getTitle(), model.getDescription(), model.getWidth(), model.getLength(), model.getHeight(), artist, model.getCategory(), model.getAssentialImage(), model.getDetailImages());
		return this;
	}
	
	public ArtBuilder category(Category category) {
		model.update(model.getTitle(), model.getDescription(), model.getWidth(), model.getLength(), model.getHeight(), model.getArtist(), category, model.getAssentialImage(), model.getDetailImages());
		return this;
	}
	
	public ArtBuilder assentialImage(Image assentialImage) {
		model.update(model.getTitle(), model.getDescription(), model.getWidth(), model.getLength(), model.getHeight(), model.getArtist(), model.getCategory(), assentialImage, model.getDetailImages());
		return this;
	}
	
	public ArtBuilder details(Set<Image> details) {
		model.update(model.getTitle(), model.getDescription(), model.getWidth(), model.getLength(), model.getHeight(), model.getArtist(), model.getCategory(), model.getAssentialImage(), details);
		return this;
	}

	public Art build() {
		return model;
	}

}
