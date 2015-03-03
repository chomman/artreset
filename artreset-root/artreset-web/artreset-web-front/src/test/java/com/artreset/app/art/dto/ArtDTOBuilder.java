/**
 * 
 */
package com.artreset.app.art.dto;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Taehyun Jung
 *
 */
public class ArtDTOBuilder extends ArtDTO {
	
	private ArtDTO dto;

	public ArtDTOBuilder() {
		dto = new ArtDTO();
	}
	
	public ArtDTOBuilder id(Long id) {
		dto.setId(id);
		return this;
	}
	
	public ArtDTOBuilder title(String title) {
		dto.setTitle(title);
		return this;
	}
	
	public ArtDTOBuilder description(String description) {
		dto.setDescription(description);
		return this;
	}
	
	public ArtDTOBuilder width(Float width) {
		dto.setWidth(width);
		return this;
	}
	
	public ArtDTOBuilder length(Float length) {
		dto.setLength(length);
		return this;
	}
	
	public ArtDTOBuilder height(Float height) {
		dto.setHeight(height);
		return this;
	}
	
	public ArtDTOBuilder assential(MultipartFile assential) {
		dto.setAssential(assential);
		return this;
	}
	

	public ArtDTO build() {
		return dto;
	}

}
