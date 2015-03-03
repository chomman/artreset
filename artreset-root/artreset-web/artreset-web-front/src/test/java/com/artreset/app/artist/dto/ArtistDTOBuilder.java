/**
 * 
 */
package com.artreset.app.artist.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Taehyun Jung
 *
 */
public class ArtistDTOBuilder extends ArtistDTO {
	
	private ArtistDTO dto;

	public ArtistDTOBuilder() {
		dto = new ArtistDTO();
	}
	
	public ArtistDTOBuilder id(Long id) {
		dto.setId(id);
		return this;
	}
	
	public ArtistDTOBuilder nickName(String nickName) {
		dto.setNickName(nickName);
		return this;
	}
	
	public ArtistDTOBuilder description(String description) {
		dto.setDescription(description);
		return this;
	}
	
	public ArtistDTOBuilder shortDescription(String shortDescription) {
		dto.setShortDescription(shortDescription);
		return this;
	}
	
	public ArtistDTOBuilder avatar(MultipartFile avatar) {
		dto.setAvatar(avatar);
		return this;
	}
	
	public ArtistDTOBuilder pageCover(MultipartFile pageCover) {
		dto.setPageCover(pageCover);
		return this;
	}
	
	public ArtistDTOBuilder avatarId(Long avatarId) {
		dto.setAvatarId(avatarId);
		return this;
	}
	
	public ArtistDTOBuilder pageCoverId(Long pageCoverId) {
		dto.setPageCoverId(pageCoverId);
		return this;
	}
	
	public ArtistDTO build() {
		return dto;
	}
	
}
