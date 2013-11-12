package com.tl.resource.business.dto;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InvoiceDetailDto {
	private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private String id;
	private String contractDetailId;
	private BigDecimal money;
	private Date editDate;
	private String editDateString;
	private String invoiceInfoId;
	
	private String contractProjectSortId;
	private String toolsId;
	private String contractInforId;
	private Integer leaf;
	private String projectCode;
	
	private Integer serialNumber;
	
	private String productBrand;
	
	private String productCode;
	
	private String brandCode;
	private String productUnit;
	private String productName;
	private BigDecimal amount = BigDecimal.ZERO;
	
	private BigDecimal price = BigDecimal.ZERO;
	
	private BigDecimal rebate = BigDecimal.ZERO;
	
	private BigDecimal netPrice = BigDecimal.ZERO;
	
	private BigDecimal contractMoney = BigDecimal.ZERO;
	
	private BigDecimal taxNetPrice = BigDecimal.ZERO;
	
	private BigDecimal taxMoney = BigDecimal.ZERO;
	
	private String proSortName;
	private String invoiceDate;
	
	private BigDecimal invoiceAmount = BigDecimal.ZERO;
	private String memo;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContractDetailId() {
		return contractDetailId;
	}

	public void setContractDetailId(String contractDetailId) {
		this.contractDetailId = contractDetailId;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Date getEditDate() {
		return editDate;
	}

	public void setEditDate(Date editDate) {
		if(editDate != null){
		    editDateString = df.format(editDate);
		}
		this.editDate = editDate;
	}

	public String getInvoiceInfoId() {
		return invoiceInfoId;
	}

	public void setInvoiceInfoId(String invoiceInfoId) {
		this.invoiceInfoId = invoiceInfoId;
	}

	public String getContractProjectSortId() {
		return contractProjectSortId;
	}

	public void setContractProjectSortId(String contractProjectSortId) {
		this.contractProjectSortId = contractProjectSortId;
	}

	public String getToolsId() {
		return toolsId;
	}

	public void setToolsId(String toolsId) {
		this.toolsId = toolsId;
	}

	public String getContractInforId() {
		return contractInforId;
	}

	public void setContractInforId(String contractInforId) {
		this.contractInforId = contractInforId;
	}

	public Integer getLeaf() {
		return leaf;
	}

	public void setLeaf(Integer leaf) {
		this.leaf = leaf;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getRebate() {
		return rebate;
	}

	public void setRebate(BigDecimal rebate) {
		this.rebate = rebate;
	}

	public BigDecimal getNetPrice() {
		return netPrice;
	}

	public void setNetPrice(BigDecimal netPrice) {
		this.netPrice = netPrice;
	}

	public BigDecimal getContractMoney() {
		return contractMoney;
	}

	public void setContractMoney(BigDecimal contractMoney) {
		this.contractMoney = contractMoney;
	}

	public BigDecimal getTaxNetPrice() {
		return taxNetPrice;
	}

	public void setTaxNetPrice(BigDecimal taxNetPrice) {
		this.taxNetPrice = taxNetPrice;
	}

	public BigDecimal getTaxMoney() {
		return taxMoney;
	}

	public void setTaxMoney(BigDecimal taxMoney) {
		this.taxMoney = taxMoney;
	}

	public String getProSortName() {
		return proSortName;
	}

	public void setProSortName(String proSortName) {
		this.proSortName = proSortName;
	}

	public String getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}

	public String getEditDateString() {
		return editDateString;
	}

	public void setEditDateString(String editDateString) {
		this.editDateString = editDateString;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
}
