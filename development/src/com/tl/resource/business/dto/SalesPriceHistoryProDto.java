package com.tl.resource.business.dto;

import java.math.BigDecimal;
import java.util.Date;

public class SalesPriceHistoryProDto {
	
	/**
	 * 名称
	 */
	private String productName ;
	/**
	 * 货品编号
	 */
	private String productCode ;
	/**
	 * 牌号
	 */
	private String brandCode ;
	/**
	 * 组别编号
	 */
	private String productSortCode;
	/**
	 * 品牌
	 */
	private String productBrand;
	/**
	 * 来源名称
	 */
	private String productSource;
	/**
	 * 销售价格变动时间
	 */
	private String salePriceDate;
	
	/**
	 * 编制人
	 */
	private String userName;
		
	/**
	 * 销售面价历史
	 */
	private BigDecimal historyPrice;
	/**
	 * 编制时间
	 */
	private String  editDate;
	
	/**
	 * 面价历史ID
	 */
	private String id;
	
	/**
	 * 编制人Id
	 */
	private String userId;
	
	/**
	 * 工具信息ID
	 */
	private String productToolInforId;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getProductSortCode() {
		return productSortCode;
	}

	public void setProductSortCode(String productSortCode) {
		this.productSortCode = productSortCode;
	}

	public String getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}

	public String getProductSource() {
		return productSource;
	}

	public void setProductSource(String productSource) {
		this.productSource = productSource;
	}

	public String getSalePriceDate() {
		return salePriceDate;
	}

	public void setSalePriceDate(String salePriceDate) {
		this.salePriceDate = salePriceDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getHistoryPrice() {
		return historyPrice;
	}

	public void setHistoryPrice(BigDecimal historyPrice) {
		this.historyPrice = historyPrice;
	}

	public String getEditDate() {
		return editDate;
	}

	public void setEditDate(String editDate) {
		this.editDate = editDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProductToolInforId() {
		return productToolInforId;
	}

	public void setProductToolInforId(String productToolInforId) {
		this.productToolInforId = productToolInforId;
	}
	
	
	
	
	

}
