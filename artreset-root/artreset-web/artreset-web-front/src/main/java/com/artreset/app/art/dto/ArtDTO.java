/**
 * 
 */
package com.artreset.app.art.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.artreset.app.art.model.Art;
import com.artreset.common.dto.BaseDTO;
import com.artreset.common.model.UnitLength;
import com.artreset.model.Artist;
import com.artreset.model.Category;
import com.artreset.model.Image;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Taehyun Jung
 * 
 */
public class ArtDTO extends BaseDTO implements Serializable {
	private static final Logger LOGGER = LoggerFactory.getLogger(ArtDTO.class);
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	@NotEmpty
	@Length(max = Art.MAX_LENGTH_TITLE)
	private String title;
	
	@Length(max = Art.MAX_LENGTH_DESCRIPTION)
	private String description;
	
	private Float width;
	
	private Float length;
	
	private Float height;
	
	
	private UnitLength unitLength;
	
	private Artist artist;
	
	private Image assentialImage;
	
	private Set<Image> detailImages;
	
	@NotNull
	@JsonIgnore
	private MultipartFile assential;
	
	private Set<MultipartFile> details;
	
	private Long categoryId;
	
	//=== assist field ===
	
	private List<Category> categories;
	
	private Category category;
	
	private UnitLength[] unitLengths;
	
	public ArtDTO() {
		super();
		this.unitLengths = UnitLength.values();
		LOGGER.debug("unitLength size : {} - {}", unitLengths.length, unitLength);
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getWidth() {
		return width;
	}

	public void setWidth(Float width) {
		this.width = width;
	}

	public Float getLength() {
		return length;
	}

	public void setLength(Float length) {
		this.length = length;
	}

	public Float getHeight() {
		return height;
	}

	public void setHeight(Float height) {
		this.height = height;
	}

	public UnitLength getUnitLength() {
		return unitLength;
	}

	public void setUnitLength(UnitLength unitLength) {
		this.unitLength = unitLength;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public Image getAssentialImage() {
		return assentialImage;
	}

	public void setAssentialImage(Image assentialImage) {
		this.assentialImage = assentialImage;
	}

	public Set<Image> getDetailImages() {
		return detailImages;
	}

	public void setDetailImages(Set<Image> detailImages) {
		this.detailImages = detailImages;
	}

	public MultipartFile getAssential() {
		return assential;
	}

	public void setAssential(MultipartFile assential) {
		this.assential = assential;
	}

	public Set<MultipartFile> getDetails() {
		return details;
	}

	public void setDetails(Set<MultipartFile> details) {
		this.details = details;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public UnitLength[] getUnitLengths() {
		return unitLengths;
	}

	public void setUnitLengths(UnitLength[] unitLengths) {
		this.unitLengths = unitLengths;
	}
}