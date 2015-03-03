/**
 * 
 */
package com.artreset.app.art.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.artreset.common.model.BaseEntity;

/**
 * @author Taehyun Jung
 *
 */
@Entity
@Table(name = "product")
public class Product extends BaseEntity<Long> {
	@Id
	private Long id;
	
	@OneToOne
	private Art art;
	
	@Column(name= "price", nullable = false)
	private Double price;
	
	@Column(name= "product_status", nullable = false, columnDefinition = "int default 0")
	private ProductStatus productStatus;

	@Override
	public Long getId() {
		return id;
	}

	public Art getArt() {
		return art;
	}

	public Double getPrice() {
		return price;
	}

	public ProductStatus getProductStatus() {
		return productStatus;
	}
	
	public static class Builder {
		
		private Product p;
		
		public Builder(Art art){
			p = new Product();
			p.id = art.getId();
			p.art = art;
		}
		
		public Builder price(Double price) {
			p.price = price;
			return this;
		}
		
		public Builder productStatus(ProductStatus productStatus) {
			p.productStatus = productStatus;
			return this;
		}
		
		public Product build() {
			return p;
		}
		
	}
	
	public static Builder getBuilder(Art art) {
		return new Builder(art);
	}
}
