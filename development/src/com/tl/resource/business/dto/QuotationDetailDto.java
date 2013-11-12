package com.tl.resource.business.dto;

import java.math.BigDecimal;
import java.util.List;

public class QuotationDetailDto {
	private String id;
	private String quotationInforId;
	private String quotationProjectSortId;
	private String toolsId;
	private String parentToolsId;
	private boolean leaf = true;
	private Integer serialNumber;
	private String productBrand;
	private String productCode;
	private String brandCode;
	private String productName;
	private BigDecimal amount= BigDecimal.ZERO;
    private BigDecimal price= BigDecimal.ZERO;
	private String productUnit;
	private BigDecimal rebate= BigDecimal.ZERO;
	private BigDecimal netPrice= BigDecimal.ZERO;
	private BigDecimal money= BigDecimal.ZERO;
	private BigDecimal taxNetPrice= BigDecimal.ZERO;
	private BigDecimal taxMoney= BigDecimal.ZERO;
	private Integer priceChange;
	private String workshop;
	private String processCode;
	private String reportCode;
	private BigDecimal orderAmount = BigDecimal.ZERO;
	private BigDecimal remainAmount = BigDecimal.ZERO;
	private String proSortName;
	private int intLeaf = 1;
	
	private String uiProvider="col";
	private String iconCls="task-folder";
	
	private BigDecimal singleSetAssemblyAmount= BigDecimal.ZERO;
	private BigDecimal singleSetStockAmount= BigDecimal.ZERO;
	private String projectCode;

	private String deliveryDate; //添加日期 2009-12-03(ftl)
	
	private String slaveFile;
	
	/**
	 * 20100119 xtaia 项目报价单数据导出
	 */
	private String toolDescription;
	private String toolCode;
	
	private String memo;
	
	private List<QuotationDetailDto> children;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQuotationInforId() {
		return quotationInforId;
	}
	public void setQuotationInforId(String quotationInforId) {
		this.quotationInforId = quotationInforId;
	}
	public String getToolsId() {
		return toolsId;
	}
	public void setToolsId(String toolsId) {
		this.toolsId = toolsId;
	}
	public String getParentToolsId() {
		return parentToolsId;
	}
	public void setParentToolsId(String parentToolsId) {
		this.parentToolsId = parentToolsId;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
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
	
	public String getProductUnit() {
		return productUnit;
	}
	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}
	
	public Integer getPriceChange() {
		return priceChange;
	}
	public void setPriceChange(Integer priceChange) {
		this.priceChange = priceChange;
	}
	public String getWorkshop() {
		return workshop;
	}
	public void setWorkshop(String workshop) {
		this.workshop = workshop;
	}
	public String getProcessCode() {
		return processCode;
	}
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	public String getReportCode() {
		return reportCode;
	}
	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}
	public List<QuotationDetailDto> getChildren() {
		return children;
	}
	public void setChildren(List<QuotationDetailDto> children) {
		this.children = children;
	}
	public String getUiProvider() {
		return uiProvider;
	}
	public void setUiProvider(String uiProvider) {
		this.uiProvider = uiProvider;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		if(amount == null) this.amount = BigDecimal.ZERO;
		else this.amount = amount;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		if(price == null) this.price = BigDecimal.ZERO;
		else this.price = price;
	}
	public BigDecimal getRebate() {
		return rebate;
	}
	public void setRebate(BigDecimal rebate) {
		if(rebate == null) this.rebate = BigDecimal.ZERO;
		else this.rebate = rebate;
	}
	public BigDecimal getNetPrice() {
		return netPrice;
	}
	public void setNetPrice(BigDecimal netPrice) {
		if(netPrice == null)  this.netPrice = BigDecimal.ZERO;
		else this.netPrice = netPrice;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		if(money == null) this.money = BigDecimal.ZERO;
		else this.money = money;
	}
	public BigDecimal getTaxNetPrice() {
		return taxNetPrice;
	}
	public void setTaxNetPrice(BigDecimal taxNetPrice) {
		if(taxNetPrice == null) this.taxNetPrice = BigDecimal.ZERO;
		else this.taxNetPrice = taxNetPrice;
	}
	public BigDecimal getTaxMoney() {
		return taxMoney;
	}
	public void setTaxMoney(BigDecimal taxMoney) {
		if(taxMoney == null) this.taxMoney = BigDecimal.ZERO;
		else this.taxMoney = taxMoney;
	}
	public BigDecimal getSingleSetAssemblyAmount() {
		return singleSetAssemblyAmount;
	}
	public void setSingleSetAssemblyAmount(BigDecimal singleSetAssemblyAmount) {
		if(singleSetAssemblyAmount == null) this.singleSetAssemblyAmount = BigDecimal.ZERO;
		else this.singleSetAssemblyAmount = singleSetAssemblyAmount;
	}
	public BigDecimal getSingleSetStockAmount() {
		return singleSetStockAmount;
	}
	public void setSingleSetStockAmount(BigDecimal singleSetStockAmount) {
		if(singleSetStockAmount == null) this.singleSetStockAmount = BigDecimal.ZERO;
		else this.singleSetStockAmount = singleSetStockAmount;
	}
	public String getQuotationProjectSortId() {
		return quotationProjectSortId;
	}
	public void setQuotationProjectSortId(String quotationProjectSortId) {
		this.quotationProjectSortId = quotationProjectSortId;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getToolDescription() {
		return toolDescription;
	}
	public void setToolDescription(String toolDescription) {
		this.toolDescription = toolDescription;
	}
	public String getToolCode() {
		return toolCode;
	}
	public void setToolCode(String toolCode) {
		this.toolCode = toolCode;
	}
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	public BigDecimal getRemainAmount() {
		return remainAmount;
	}
	public void setRemainAmount(BigDecimal remainAmount) {
		this.remainAmount = remainAmount;
	}
	public String getProSortName() {
		return proSortName;
	}
	public void setProSortName(String proSortName) {
		this.proSortName = proSortName;
	}
	public int getIntLeaf() {
		return intLeaf;
	}
	public void setIntLeaf(int intLeaf) {
		this.intLeaf = intLeaf;
	}
	public String getSlaveFile() {
		return slaveFile;
	}
	public void setSlaveFile(String slaveFile) {
		this.slaveFile = slaveFile;
	}

}
