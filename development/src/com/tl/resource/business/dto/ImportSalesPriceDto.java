/**
 * 
 */
package com.tl.resource.business.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xtaia
 * 导入产品面价数据Dto
 */
public class ImportSalesPriceDto {
	
	private String id;
	private String brandCode ;
	private String oldBrandCode;
	private String productName;
	private String productBrand;
	private BigDecimal historyPrice = BigDecimal.ZERO;
	private String productUnit ;
	private String salePriceDate ;
	private String userId;
	private String userName;
	private Date editDate;
	private int rowIndex;
	private String rowNumber;
	private int flag;//该记录是否有效
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public String getOldBrandCode() {
		return oldBrandCode;
	}
	public void setOldBrandCode(String oldBrandCode) {
		this.oldBrandCode = oldBrandCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductBrand() {
		return productBrand;
	}
	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}
	
	public BigDecimal getHistoryPrice() {
		return historyPrice;
	}
	public void setHistoryPrice(BigDecimal historyPrice) {
		this.historyPrice = historyPrice;
	}
	public String getProductUnit() {
		return productUnit;
	}
	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}
	public String getSalePriceDate() {
		return salePriceDate;
	}
	public void setSalePriceDate(String salePriceDate) {
		this.salePriceDate = salePriceDate;
	}
	public int getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getEditDate() {
		return editDate;
	}
	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}
	public String getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
}
