/**
 * 
 */
package com.artreset.image.model;

import org.springframework.test.util.ReflectionTestUtils;

import com.artreset.model.Image;

/**
 * @author Taehyun Jung
 *
 */
public class ImageBuilder {
	
	private Image model;
	
	public ImageBuilder() {
		model = new Image();
	}
	
	public ImageBuilder id(Long id){
		ReflectionTestUtils.setField(model, "id", id);
		return this;
	}
	
	public Image build(){
		return model;
	}	

}
