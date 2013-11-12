package com.tl.resource.business.dto;

import java.math.BigDecimal;

public class ProductArrivalDetailDto {

  private String id;

  private BigDecimal orderAmount;

  private String brandCode;

  private String orderDetailId;

  private String productArrivalInforId;

  private String toolsId;

  private String parentToolsId;

  private Integer leaf;

  private String productName;

  private BigDecimal actualAmount;

  private BigDecimal arrivalAmount;

  private String productUnit;

  private BigDecimal price;

  private BigDecimal orderPrice;

  private BigDecimal salePrice;

  private BigDecimal productMoney;

  private String memo;

  private String productCode;

  private String productBrand;

  private BigDecimal hasArrivalAmount;//已经到货数量 

  private String projectCode;

  private BigDecimal serialNumber = BigDecimal.ZERO;

  private String productPosition;

  private String reserveCode;

  /**
   * 合同分项ID
   */
  private String contractProjectSortId;

  /**
   * 合同分项名称
   */
  private String proSortName;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public BigDecimal getOrderAmount() {
    return orderAmount;
  }

  public void setOrderAmount(BigDecimal orderAmount) {
    this.orderAmount = orderAmount;
  }

  public String getBrandCode() {
    return brandCode;
  }

  public void setBrandCode(String brandCode) {
    this.brandCode = brandCode;
  }

  public String getOrderDetailId() {
    return orderDetailId;
  }

  public void setOrderDetailId(String orderDetailId) {
    this.orderDetailId = orderDetailId;
  }

  public String getProductArrivalInforId() {
    return productArrivalInforId;
  }

  public void setProductArrivalInforId(String productArrivalInforId) {
    this.productArrivalInforId = productArrivalInforId;
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

  public BigDecimal getActualAmount() {
    return actualAmount;
  }

  public void setActualAmount(BigDecimal actualAmount) {
    this.actualAmount = actualAmount;
  }

  public BigDecimal getArrivalAmount() {
    return arrivalAmount;
  }

  public void setArrivalAmount(BigDecimal arrivalAmount) {
    this.arrivalAmount = arrivalAmount;
  }

  public String getProductUnit() {
    return productUnit;
  }

  public void setProductUnit(String productUnit) {
    this.productUnit = productUnit;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getProductMoney() {
    return productMoney;
  }

  public void setProductMoney(BigDecimal productMoney) {
    this.productMoney = productMoney;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }

  public BigDecimal getHasArrivalAmount() {
    return hasArrivalAmount;
  }

  public void setHasArrivalAmount(BigDecimal hasArrivalAmount) {
    this.hasArrivalAmount = hasArrivalAmount;
  }

  public String getProductBrand() {
    return productBrand;
  }

  public void setProductBrand(String productBrand) {
    this.productBrand = productBrand;
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

  public String getProSortName() {
    return proSortName;
  }

  public void setProSortName(String proSortName) {
    this.proSortName = proSortName;
  }

  public String getContractProjectSortId() {
    return contractProjectSortId;
  }

  public void setContractProjectSortId(String contractProjectSortId) {
    this.contractProjectSortId = contractProjectSortId;
  }

  public String getProductPosition() {
    return productPosition;
  }

  public void setProductPosition(String productPosition) {
    this.productPosition = productPosition;
  }

  public String getReserveCode() {
    return reserveCode;
  }

  public void setReserveCode(String reserveCode) {
    this.reserveCode = reserveCode;
  }

  public BigDecimal getOrderPrice() {
    return orderPrice;
  }

  public void setOrderPrice(BigDecimal orderPrice) {
    this.orderPrice = orderPrice;
  }

  public BigDecimal getSalePrice() {
    return salePrice;
  }

  public void setSalePrice(BigDecimal salePrice) {
    this.salePrice = salePrice;
  }

}