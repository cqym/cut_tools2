package com.tl.resource.business.dto;

import java.math.BigDecimal;
import java.util.List;

public class ArrivalOrderDetialsDto {
  private String uiProvider = "col";

  private String iconCls = "task";

  private String id;

  private String contractProductDetailId;

  private String stockOrderInforId;

  private String productBrand;

  private String productCode;

  private String brandCode;

  private String toolsId;

  private String parentToolsId;

  private Integer leaf;

  private String productName;

  private BigDecimal contractAmount;

  private BigDecimal orderAmount;

  private String productUnit;

  private BigDecimal price;

  private BigDecimal productMoney;

  private boolean checked;

  private BigDecimal arrivalAmount;// 到货数量

  private List<ArrivalOrderDetialsDto> children;

  private BigDecimal hasArrivalAmount;// 已经到货数量

  private String contractProjectSortId;

  private String proSortName;

  private String projectCode;

  private BigDecimal serialNumber = BigDecimal.ZERO;

  private String deliveryDate;

  private Integer priceChange;

  private String memo;

  private String memo2;

  private String contractDeliveryDate;

  private String toolsTypeName;

  public String getToolsTypeName() {
    return toolsTypeName;
  }

  public void setToolsTypeName(String toolsTypeName) {
    this.toolsTypeName = toolsTypeName;
  }

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

  public List<ArrivalOrderDetialsDto> getChildren() {
    return children;
  }

  public void setChildren(List<ArrivalOrderDetialsDto> children) {
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

  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
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
    if (serialNumber == null)
      this.serialNumber = BigDecimal.ZERO;
  }

  public String getDeliveryDate() {
    return deliveryDate;
  }

  public void setDeliveryDate(String deliveryDate) {
    this.deliveryDate = deliveryDate;
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

}
