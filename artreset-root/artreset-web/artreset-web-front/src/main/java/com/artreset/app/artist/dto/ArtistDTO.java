/**
 * 
 */
package com.artreset.app.artist.dto;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.artreset.common.dto.BaseDTO;
import com.artreset.model.Artist;

/**
 * @author Taehyun Jung
 *
 */
public class ArtistDTO extends BaseDTO {
	
	private Long id;
	
	@Length(max = Artist.MAX_LENGTH_NICKNAME)
	private String nickName;
	
	@Length(max = Artist.MAX_LENGTH_DESCRIPTION)
	private String description;
	
	@Length(max = Artist.MAX_LENGTH_SHORT_DESCRIPTION)
	private String shortDescription;
	
	private MultipartFile avatar;
	
	private MultipartFile pageCover;
	
	private Long avatarId;
	
	private Long pageCoverId;
	
	private String avatarThumbnailUrl;
	
	private String pageCoverThumbnailurl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public MultipartFile getAvatar() {
		return avatar;
	}

	public void setAvatar(MultipartFile avatar) {
		this.avatar = avatar;
	}

	public MultipartFile getPageCover() {
		return pageCover;
	}

	public void setPageCover(MultipartFile pageCover) {
		this.pageCover = pageCover;
	}

	public Long getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(Long avatarId) {
		this.avatarId = avatarId;
	}

	public Long getPageCoverId() {
		return pageCoverId;
	}

	public void setPageCoverId(Long pageCoverId) {
		this.pageCoverId = pageCoverId;
	}

	public String getAvatarThumbnailUrl() {
		return avatarThumbnailUrl;
	}

	public void setAvatarThumbnailUrl(String avatarThumbnailUrl) {
		this.avatarThumbnailUrl = avatarThumbnailUrl;
	}

	public String getPageCoverThumbnailurl() {
		return pageCoverThumbnailurl;
	}

	public void setPageCoverThumbnailurl(String pageCoverThumbnailurl) {
		this.pageCoverThumbnailurl = pageCoverThumbnailurl;
	}

}
