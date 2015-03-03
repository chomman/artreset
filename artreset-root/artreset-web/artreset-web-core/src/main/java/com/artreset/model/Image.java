package com.artreset.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.artreset.common.model.BaseEntity;

/**
 *
 * @author Taehyun Jung
 */
@Entity
@Table(name = "images")
@NamedQueries({
    @NamedQuery(name = "images", query = "select i from Image i order by i.id")
})
@JsonIgnoreProperties({"id","thumbnailFilename","newFilename","contentType","dateCreated","lastUpdated"})
public class Image extends BaseEntity<Long> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private String thumbnailFilename;
    
    private String newFilename;
    
    private String contentType;
    
    @Column(name = "size_")
    private Long size;
    
    private Long thumbnailSize;
    
    /*@Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;*/
    
    @Transient
    private String url;
    
    @Transient
    private String thumbnailUrl;
    
    @Transient
    private String deleteUrl;
    
    @Transient
    private String deleteType;
    
    

	/**
	 * @return the url
	 */
	public String getUrl() {
		if(url == null || url.length() <= 0){
			url = "/image/picture/" + getId();
		}
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the thumbnailUrl
	 */
	public String getThumbnailUrl() {
		if(thumbnailUrl == null || thumbnailUrl.length() <= 0){
			thumbnailUrl = "/image/thumbnail/" + getId();
		}
		return thumbnailUrl;
	}

	/**
	 * @param thumbnailUrl the thumbnailUrl to set
	 */
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	/**
	 * @return the deleteUrl
	 */
	public String getDeleteUrl() {
		return deleteUrl;
	}

	/**
	 * @param deleteUrl the deleteUrl to set
	 */
	public void setDeleteUrl(String deleteUrl) {
		if(deleteUrl == null || deleteUrl.length() <= 0){
			deleteUrl = "/image/delete/" + getId();
		}
		this.deleteUrl = deleteUrl;
	}

	/**
	 * @return the deleteType
	 */
	public String getDeleteType() {
		return deleteType;
	}

	/**
	 * @param deleteType the deleteType to set
	 */
	public void setDeleteType(String deleteType) {
		if(deleteType == null || deleteType.length() <= 0){
			deleteType = "DELETE";
		}
		this.deleteType = deleteType;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the thumbnailFilename
	 */
	public String getThumbnailFilename() {
		return thumbnailFilename;
	}

	/**
	 * @return the newFilename
	 */
	public String getNewFilename() {
		return newFilename;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @return the size
	 */
	public Long getSize() {
		return size;
	}

	/**
	 * @return the thumbnailSize
	 */
	public Long getThumbnailSize() {
		return thumbnailSize;
	}

	public Image() {}

	@Override
	public Long getId() {
		return id;
	}
	
	public static Builder getBuilder(Image image) {
		return new Builder(image);
	}
    
    public static Builder getBuilder(String name) {
    	return new Builder(name);
    }
    
    public static class Builder {
    	
    	private Image built;
    	
    	public Builder(String name){
    		built = new Image();
    		built.name = name;
    	}
    	
    	public Builder(Image image){
    		if(image == null){
    			built = new Image();
    		}else{
    			built = image;
    		}
    	}
    	
    	public Image build(){
    		return built;
    	}
    	
    	public Builder thumbnailFilename(String thumbnailFilename) {
    		built.thumbnailFilename = thumbnailFilename;
    		return this;
    	}
        
        public Builder newFilename(String newFilename) {
    		built.newFilename = newFilename;
    		return this;
    	}
        
        public Builder contentType(String contentType) {
    		built.contentType = contentType;
    		return this;
    	}
        
        public Builder size(Long size) {
    		built.size = size;
    		return this;
    	}
        
        public Builder thumbnailSize(Long thumbnailSize) {
    		built.thumbnailSize = thumbnailSize;
    		return this;
    	}
        
        public Builder url(String url) {
    		built.url = url;
    		return this;
    	}
        
        public Builder thumbnailUrl(String thumbnailUrl) {
    		built.thumbnailUrl = thumbnailUrl;
    		return this;
    	}
        
        public Builder deleteUrl(String deleteUrl) {
    		built.deleteUrl = deleteUrl;
    		return this;
    	}
        
        public Builder deleteType(String deleteType) {
    		built.deleteType = deleteType;
    		return this;
    	}
    }

}
