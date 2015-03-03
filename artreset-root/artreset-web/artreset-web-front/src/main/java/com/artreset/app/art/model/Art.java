/**
 * 
 */
package com.artreset.app.art.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.artreset.common.model.BaseEntity;
import com.artreset.common.model.UnitLength;
import com.artreset.model.Artist;
import com.artreset.model.Category;
import com.artreset.model.Image;

/**
 * @author Taehyun Jung
 *
 */
@Entity
@Table(name = "arts")
public class Art extends BaseEntity<Long> {
	
	public static final int MAX_LENGTH_DESCRIPTION = 500;
    public static final int MAX_LENGTH_TITLE = 100;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(name = "title", nullable = false, length = MAX_LENGTH_TITLE)
	private String title;

    @Column(name = "description", nullable = true, length = MAX_LENGTH_DESCRIPTION)
    private String description;
    
    /**
     * 작품크기 가로
     */
    @Column(name = "width", nullable = true)
    private Float width;
    
    /**
     * 작품크기 세로
     */
    @Column(name = "length", nullable = true)
    private Float length;
    
    /**
     * 작품크기 높이
     */
    @Column(name = "height", nullable = true)
    private Float height;
    
    @Column(name = "unit_length", nullable = true)
    private UnitLength unitLength;
    
    @ManyToOne
    private Artist artist;
    
    /**
     * 작품장르
     */
    @ManyToOne
    private Category category;
    
    @NotNull
    @ManyToOne
    private Image assentialImage;
    
    @ManyToMany
    private Set<Image> detailImages;
    
	@Override
    public Long getId() {
        return id;
    }
	
	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Float getWidth() {
		return width;
	}

	public Float getLength() {
		return length;
	}

	public Float getHeight() {
		return height;
	}

	public UnitLength getUnitLength() {
		return unitLength;
	}

	public Artist getArtist() {
		return artist;
	}

	public Category getCategory() {
		return category;
	}

	public Image getAssentialImage() {
		return assentialImage;
	}

	public Set<Image> getDetailImages() {
		return detailImages;
	}

	public static class Builder {
		private Art built;
		
		public Builder(String title) {
			built = new Art();
			built.title = title;
		}
		
		public Art build() {
			return built;
		}
		
		public Builder description(String description) {
			built.description = description;
			return this;
		}
		
		public Builder width(Float width) {
			built.width = width;
			return this;
		}
		
		public Builder length(Float length) {
			built.length = length;
			return this;
		}
		
		public Builder height(Float height) {
			built.height = height;
			return this;
		}
		
		public Builder unitLength(UnitLength unitLength) {
			built.unitLength = unitLength;
			return this;
		}
		
		public Builder artist(Artist artist) {
			built.artist = artist;
			return this;
		}
		
		public Builder category(Category category) {
			built.category = category;
			return this;
		}
		
		public Builder assentialImage(Image assentialImage) {
			built.assentialImage = assentialImage;
			return this;
		}
		
		public Builder detailImages(Set<Image> detailImages) {
			built.detailImages = detailImages;
			return this;
		}
	}
	
	public static Builder getBuilder(String title) {
		return new Builder(title);
	}
	
	/**
	 * @param title
	 * @param description
	 * @param width
	 * @param length
	 * @param height
	 * @param artist
	 * @param category
	 * @param assentialImage
	 * @param details
	 */
	public void update(String title, String description, Float width, Float length, Float height, 
			Artist artist, Category category, Image assentialImage, Set<Image> detailImages) {
		this.title = title;
		this.description = description;
		this.width = width;
		this.length = length;
		this.height = height;
		this.artist = artist;
		this.category = category;
		this.assentialImage = assentialImage;
		this.detailImages = detailImages;
	}
	
	

}
