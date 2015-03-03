/**
 * 
 */
package com.artreset.app.artist.model;

import org.springframework.test.util.ReflectionTestUtils;

import com.artreset.model.Artist;
import com.artreset.model.Image;
import com.artreset.model.User;

/**
 * @author Taehyun Jung
 *
 */
public class ArtistBuilder {
	
	private Artist model;
	
	public ArtistBuilder() {
		model = new Artist();
	}
	
	public ArtistBuilder id(Long id) {
		ReflectionTestUtils.setField(model, "id", id);
		return this;
	}
	
	public Artist build() {
		return model;
	}
	
	public ArtistBuilder nickName(String nickName){
		model.update(nickName, model.getDescription(), model.getShortDescription()
				, model.getArtistUserInfo(), model.getAvatar(), model.getPageCover());
		return this;
	}
	
	public ArtistBuilder description(String description){
		model.update(model.getNickName(), description, model.getShortDescription()
				, model.getArtistUserInfo(), model.getAvatar(), model.getPageCover());
		return this;
	}
	
	public ArtistBuilder shortDescription(String shortDescription){
		model.update(model.getNickName(), model.getDescription(), shortDescription
				, model.getArtistUserInfo(), model.getAvatar(), model.getPageCover());
		return this;
	}
	
	public ArtistBuilder artistUserInfo(User artistUserInfo){
		model.update(model.getNickName(), model.getDescription(), model.getShortDescription()
				, artistUserInfo, model.getAvatar(), model.getPageCover());
		return this;
	}
	
	public ArtistBuilder avatar(Image avatar){
		model.update(model.getNickName(), model.getDescription(), model.getShortDescription()
				, model.getArtistUserInfo(), avatar, model.getPageCover());
		return this;
	}
	
	public ArtistBuilder pageCover(Image pageCover){
		model.update(model.getNickName(), model.getDescription(), model.getShortDescription()
				, model.getArtistUserInfo(), model.getAvatar(), pageCover);
		return this;
	}

}
