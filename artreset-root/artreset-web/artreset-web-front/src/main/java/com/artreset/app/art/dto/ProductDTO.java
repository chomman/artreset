/**
 * 
 */
package com.artreset.app.art.dto;

import com.artreset.app.art.model.Art;
import com.artreset.app.art.model.ProductStatus;
import com.artreset.common.dto.BaseDTO;

/**
 * @author Taehyun Jung
 *
 */
public class ProductDTO extends BaseDTO {
	
	private Long id;
	
	private Double price;
	
	private ProductStatus productStatus;
	
	private Art art;
	
	private ProductStatus[] productStatusList = ProductStatus.values();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public ProductStatus getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(ProductStatus productStatus) {
		this.productStatus = productStatus;
	}

	public Art getArt() {
		return art;
	}

	public void setArt(Art art) {
		this.art = art;
	}

	public ProductStatus[] getProductStatusList() {
		return productStatusList;
	}

	public void setProductStatusList(ProductStatus[] productStatusList) {
		this.productStatusList = productStatusList;
	}
}
