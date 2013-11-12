package com.tl.resource.business.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ReservePlanDetailDto {

  private String id;

  private String stockOrderDetailId;

  private String brandCode;

  private String productCode;

  private String toolsId;

  private String parentToolsId;

  private Integer leaf;

  private String productName;

  private BigDecimal planAmount;

  private String projectCode;

  private BigDecimal serialNumber = BigDecimal.ZERO;

  private String productUnit;

  private BigDecimal price;

  private BigDecimal productMoney;

  private String orderInforId;

  private String orderCode;

  private String contractId;

  private String contractCode;

  private Integer status;

  private String planCode;

  private Date editDate;

  private String userId;

  private String userName;

  private BigDecimal reserveAmount;

  private BigDecimal orderAmount;

  private String productBrand;

  private String matStockOrderDetailId;

  private BigDecimal remainAmount = BigDecimal.ZERO;

  private List<ReservePlanDetailDto> children;

  private String uiProvider = "col";

  private String iconCls = "task";

  private String proSortName;

  private String memo;

  private String memo2;

  private String mainId;

  private String deliveryDate;

  private BigDecimal outAmount;

  private BigDecimal arrivalAmount;

  private String reserveInforId;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getStockOrderDetailId() {
    return stockOrderDetailId;
  }

  public void setStockOrderDetailId(String stockOrderDetailId) {
    this.stockOrderDetailId = stockOrderDetailId;
  }

  public String getBrandCode() {
    return brandCode;
  }

  public void setBrandCode(String brandCode) {
    this.brandCode = brandCode;
  }

  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
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

  public BigDecimal getPlanAmount() {
    return planAmount;
  }

  public void setPlanAmount(BigDecimal planAmount) {
    this.planAmount = planAmount;
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

  public String getOrderInforId() {
    return orderInforId;
  }

  public void setOrderInforId(String orderInforId) {
    this.orderInforId = orderInforId;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public String getContractId() {
    return contractId;
  }

  public void setContractId(String contractId) {
    this.contractId = contractId;
  }

  public String getContractCode() {
    return contractCode;
  }

  public void setContractCode(String contractCode) {
    this.contractCode = contractCode;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getPlanCode() {
    return planCode;
  }

  public void setPlanCode(String planCode) {
    this.planCode = planCode;
  }

  public Date getEditDate() {
    return editDate;
  }

  public void setEditDate(Date editDate) {
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

  public BigDecimal getReserveAmount() {
    return reserveAmount;
  }

  public void setReserveAmount(BigDecimal reserveAmount) {
    this.reserveAmount = reserveAmount;
  }

  public List<ReservePlanDetailDto> getChildren() {
    return children;
  }

  public void setChildren(List<ReservePlanDetailDto> children) {
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

  public BigDecimal getOrderAmount() {
    return orderAmount;
  }

  public void setOrderAmount(BigDecimal orderAmount) {
    this.orderAmount = orderAmount;
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

  public String getMatStockOrderDetailId() {
    return matStockOrderDetailId;
  }

  public void setMatStockOrderDetailId(String matStockOrderDetailId) {
    this.matStockOrderDetailId = matStockOrderDetailId;
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

  public String getMainId() {
    return mainId;
  }

  public void setMainId(String mainId) {
    this.mainId = mainId;
  }

  public String getDeliveryDate() {
    return deliveryDate;
  }

  public void setDeliveryDate(String deliveryDate) {
    this.deliveryDate = deliveryDate;
  }

  public BigDecimal getOutAmount() {
    return outAmount;
  }

  public void setOutAmount(BigDecimal outAmount) {
    this.outAmount = outAmount;
  }

  public BigDecimal getArrivalAmount() {
    return arrivalAmount;
  }

  public void setArrivalAmount(BigDecimal arrivalAmount) {
    this.arrivalAmount = arrivalAmount;
  }

  public String getReserveInforId() {
    return reserveInforId;
  }

  public void setReserveInforId(String reserveInforId) {
    this.reserveInforId = reserveInforId;
  }

}
