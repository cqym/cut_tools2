package com.tl.resource.business.dto;

import java.math.BigDecimal;
import java.util.List;

public class OrderDetialsDto {
  private String uiProvider = "col";

  private String iconCls = "task";

  private String id;

  private String contractProductDetailId = null;

  private String stockOrderInforId;

  private String brandCode;

  private String toolsId;

  private String parentToolsId;

  private Integer leaf;

  private String productName;

  private String productCode;

  private BigDecimal contractAmount = BigDecimal.ZERO;

  private BigDecimal orderAmount = BigDecimal.ZERO;

  private String productUnit;

  private BigDecimal price = BigDecimal.ZERO;

  private BigDecimal productMoney = BigDecimal.ZERO;

  private boolean checked;

  private BigDecimal arrivalAmount = BigDecimal.ZERO;// 到货数量

  private BigDecimal reserveAmount = BigDecimal.ZERO;

  private BigDecimal planAmount = BigDecimal.ZERO;

  private String contractProjectSortId = null;

  private String proSortName;

  private String projectCode;

  private BigDecimal serialNumber = BigDecimal.ZERO;

  private String productBrand;

  private BigDecimal remainAmount = BigDecimal.ZERO;

  private String deliveryDate;

  private int fileCount;

  private Integer priceChange;

  private String memo;

  private String memo2;

  private String contractDeliveryDate;

  private String planDetailId;//计划明细id

  private String toolsTypeName;

  private List<OrderDetialsDto> children;

  public String getProductUnit() {
    return productUnit;
  }

  public void setProductUnit(String productUnit) {
    this.productUnit = productUnit;
  }

  public BigDecimal getProductMoney() {
    return productMoney;
  }

  public void setProductMoney(BigDecimal productMoney) {
    this.productMoney = productMoney;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public List<OrderDetialsDto> getChildren() {
    return children;
  }

  public void setChildren(List<OrderDetialsDto> children) {
    this.children = children;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getContractProductDetailId() {
    return contractProductDetailId;
  }

  public void setContractProductDetailId(String contractProductDetailId) {
    this.contractProductDetailId = contractProductDetailId;
  }

  public String getStockOrderInforId() {
    return stockOrderInforId;
  }

  public void setStockOrderInforId(String stockOrderInforId) {
    this.stockOrderInforId = stockOrderInforId;
  }

  public String getBrandCode() {
    return brandCode;
  }

  public void setBrandCode(String brandCode) {
    this.brandCode = brandCode;
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

  public Integer getLeaf() {
    return leaf;
  }

  public void setLeaf(Integer leaf) {
    this.leaf = leaf;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public BigDecimal getContractAmount() {
    return contractAmount;
  }

  public void setContractAmount(BigDecimal contractAmount) {
    this.contractAmount = contractAmount;
  }

  public BigDecimal getOrderAmount() {
    return orderAmount;
  }

  public void setOrderAmount(BigDecimal orderAmount) {
    this.orderAmount = orderAmount;
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

  public boolean isChecked() {
    return checked;
  }

  public void setChecked(boolean checked) {
    this.checked = checked;
  }

  public BigDecimal getArrivalAmount() {
    return arrivalAmount;
  }

  public void setArrivalAmount(BigDecimal arrivalAmount) {
    this.arrivalAmount = arrivalAmount;
  }

  public BigDecimal getReserveAmount() {
    return reserveAmount;
  }

  public void setReserveAmount(BigDecimal reserveAmount) {
    this.reserveAmount = reserveAmount;
  }

  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }

  public BigDecimal getPlanAmount() {
    return planAmount;
  }

  public void setPlanAmount(BigDecimal planAmount) {
    this.planAmount = planAmount;
  }

  public String getContractProjectSortId() {
    return contractProjectSortId;
  }

  public void setContractProjectSortId(String contractProjectSortId) {
    this.contractProjectSortId = contractProjectSortId;
  }

  public String getProSortName() {
    return proSortName;
  }

  public void setProSortName(String proSortName) {
    this.proSortName = proSortName;
  }

  public String getProjectCode() {
    return projectCode;
  }

  public void setProjectCode(String projectCode) {
    this.projectCode = projectCode;
  }

  public BigDecimal getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(BigDecimal serialNumber) {
    this.serialNumber = serialNumber;
  }

  public String getProductBrand() {
    return productBrand;
  }

  public void setProductBrand(String productBrand) {
    this.productBrand = productBrand;
  }

  public BigDecimal getRemainAmount() {
    return remainAmount;
  }

  public void setRemainAmount(BigDecimal remainAmount) {
    this.remainAmount = remainAmount;
  }

  public String getDeliveryDate() {
    return deliveryDate;
  }

  public void setDeliveryDate(String deliveryDate) {
    this.deliveryDate = deliveryDate;
  }

  public int getFileCount() {
    return fileCount;
  }

  public void setFileCount(int fileCount) {
    this.fileCount = fileCount;
  }

  public Integer getPriceChange() {
    return priceChange;
  }

  public void setPriceChange(Integer priceChange) {
    this.priceChange = priceChange;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public String getMemo2() {
    return memo2;
  }

  public void setMemo2(String memo2) {
    this.memo2 = memo2;
  }

  public String getContractDeliveryDate() {
    return contractDeliveryDate;
  }

  public void setContractDeliveryDate(String contractDeliveryDate) {
    this.contractDeliveryDate = contractDeliveryDate;
  }

  public String getPlanDetailId() {
    return planDetailId;
  }

  public void setPlanDetailId(String planDetailId) {
    this.planDetailId = planDetailId;
  }

  public String getToolsTypeName() {
    return toolsTypeName;
  }

  public void setToolsTypeName(String toolsTypeName) {
    this.toolsTypeName = toolsTypeName;
  }

}
