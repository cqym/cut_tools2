/**
 * 
 */
package com.tl.resource.business.dto;

import java.math.BigDecimal;

/**
 * @author xtaia
 * 采购历史报价
 */
public class OrderPriceHistoryDto {
	
	
	private String id;
	/**
	 * 供应商ID
	 */
	private String suppliersInforId;
	
	/**
	 * 供应商名称
	 */
	private String supplierName;
	
	/**
	 * 产品ID 
	 */
	private String productToolsInforId;
	/**
	 * 产品名称
	 */
	private String productName;
	
	/**
	 * 计量单位
	 */
	private String productUnit;

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
	 * 
	 */
	private Integer leaf;
	/**
	 * 
	 */
	private String parentId;
	/**
	 * 货品编号
	 */
	private String productCode;
	/**
	 * 市场面价
	 */
	private BigDecimal salePrice;
	
	/**
	 * 历史价格
	 */
	private BigDecimal historyPrice;
	/**
	 * 牌号
	 */
	private String brandCode;
	/**
	 * 编制时间
	 */
	private String editDate;
	/**
	 * 编制人ID
	 */
	private String userId;
	/**
	 * 编制人名称
	 */
	private String userName;
	/**
	 * 价格变动时间
	 */
	private String stockPriceDate;
	private BigDecimal historyMarketPrice = BigDecimal.ZERO;
	private BigDecimal historyRebate = BigDecimal.ZERO;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSuppliersInforId() {
		return suppliersInforId;
	}
	public void setSuppliersInforId(String suppliersInforId) {
		this.suppliersInforId = suppliersInforId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getProductToolsInforId() {
		return productToolsInforId;
	}
	public void setProductToolsInforId(String productToolsInforId) {
		this.productToolsInforId = productToolsInforId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getLeaf() {
		return leaf;
	}
	public void setLeaf(Integer leaf) {
		this.leaf = leaf;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public BigDecimal getHistoryPrice() {
		return historyPrice;
	}
	public void setHistoryPrice(BigDecimal historyPrice) {
		this.historyPrice = historyPrice;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public String getEditDate() {
		return editDate;
	}
	public void setEditDate(String editDate) {
		this.editDate = editDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getStockPriceDate() {
		return stockPriceDate;
	}
	public void setStockPriceDate(String stockPriceDate) {
		this.stockPriceDate = stockPriceDate;
	}
	public String getProductUnit() {
		return productUnit;
	}
	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
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
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
	public BigDecimal getHistoryRebate() {
		return historyRebate;
	}
	public void setHistoryRebate(BigDecimal historyRebate) {
		this.historyRebate = historyRebate;
	}
	public BigDecimal getHistoryMarketPrice() {
		return historyMarketPrice;
	}
	public void setHistoryMarketPrice(BigDecimal historyMarketPrice) {
		this.historyMarketPrice = historyMarketPrice;
	}

}
