/**
 * 
 */
package com.artreset.app.art.model;

/**
 * @author Taehyun Jung
 *
 */
public enum ProductStatus {
	
	ON_SALE("on sale"), ON_BID("on bid"), BE_SOLD("be sold");
	
	private String description;
	
	private ProductStatus(String description) {
		this.description = description;
	}
	
	public String getDescription(){
		return description;
	}

}
