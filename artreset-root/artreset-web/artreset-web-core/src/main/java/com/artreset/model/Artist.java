/**
 * 
 */
package com.artreset.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import com.artreset.common.model.BaseEntity;

/**
 * @author Taehyun Jung
 *
 */
@Entity
@Table(name = "artists")
public class Artist extends BaseEntity<Long> {
	
	public static final int MAX_LENGTH_NICKNAME = 100;
	public static final int MAX_LENGTH_DESCRIPTION = 500;
	public static final int MAX_LENGTH_SHORT_DESCRIPTION = 100;
	
	@Id
	private Long id;
	
	@Column(name = "nick_name", length = MAX_LENGTH_NICKNAME, nullable = true)
	private String nickName;
	
	@Column(name = "description", length = MAX_LENGTH_DESCRIPTION, nullable = true)
	private String description;
	
	@Column(name = "short_description", length = MAX_LENGTH_SHORT_DESCRIPTION, nullable = true)
	private String shortDescription;
	
	@OneToOne
	private User artistUserInfo;
	
	@ManyToOne
	private  Image avatar;
	
	@ManyToOne
	private  Image pageCover;

    @Override
    public Long getId() {
        return id;
    }

	public String getNickName() {
		return nickName;
	}

	public String getDescription() {
		return description;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public User getArtistUserInfo() {
		return artistUserInfo;
	}

	public Image getAvatar() {
		return avatar;
	}

	public Image getPageCover() {
		return pageCover;
	}
	
	public static class Builder {
		private Artist artist;
		
		public Builder(User user){
			artist = new Artist();
			artist.artistUserInfo = user;
			artist.id=user.getId();
			//artist.nickName = user.getFirstName();
		}
		
		public Builder nickname(String nickName){
			if(StringUtils.isNotEmpty(nickName)){
				artist.nickName = nickName;
			}
			return this;
		}
		
		public Builder description(String description){
			artist.description = description;
			return this;
		}
		
		public Builder shortDescription(String shortDescription){
			artist.shortDescription = shortDescription;
			return this;
		}
		
		public Builder avatar(Image avatar){
			artist.avatar = avatar;
			return this;
		}
		
		public Builder pageCover(Image pageCover){
			artist.pageCover = pageCover;
			return this;
		}
		
		public Artist build(){
			return artist;
		}
	}

	public static Builder getBuilder(User user) {
		return new Builder(user);
	}

	/**
	 * @param nickName
	 * @param description
	 * @param shortDescription
	 * @param artistUserInfo
	 * @param avatar
	 * @param pageCover
	 */
	public void update(String nickName, String description, String shortDescription,
			User artistUserInfo, Image avatar, Image pageCover) {
		this.nickName = nickName;
		this.description = description;
		this.shortDescription = shortDescription;
		this.artistUserInfo = artistUserInfo;
		this.avatar = avatar;
		this.pageCover = pageCover;
	}

	
}
