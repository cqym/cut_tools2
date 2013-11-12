package com.tl.resource.business.dto;

import java.math.BigDecimal;
import java.util.List;

public class DeliveryProductDetailDto {

  private String id;

  private String contractProductDetailId;

  private String deliveryInforId;

  private String projectCode;

  private Integer serialNumber;

  private String brandCode;

  private String toolsId;

  private String parentToolsId;

  private Integer leaf;

  private String productName;

  private String productCode;

  private String productBrand;

  private BigDecimal contractAmount = BigDecimal.ZERO;

  private BigDecimal amount = BigDecimal.ZERO;

  private BigDecimal netPrice = BigDecimal.ZERO;

  private String productUnit;

  private BigDecimal productMoney = BigDecimal.ZERO;

  private BigDecimal allDeliveryAmount = BigDecimal.ZERO;

  private BigDecimal allArrivalAmount = BigDecimal.ZERO;

  private String memo;

  private String memo2;

  private Integer sourceType;

  private String sourceId;

  private String contractProjectSortId;

  private String proSortName;

  private Object loader;//马的，ext给加了新属性，没办法的办法

  private String uiProvider = "col";

  private String iconCls = "task-folder";

  private List<DeliveryProductDetailDto> children;

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

  public String getDeliveryInforId() {
    return deliveryInforId;
  }

  public void setDeliveryInforId(String deliveryInforId) {
    this.deliveryInforId = deliveryInforId;
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

  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }

  public BigDecimal getContractAmount() {
    return contractAmount;
  }

  public void setContractAmount(BigDecimal contractAmount) {

    this.contractAmount = contractAmount;
    if (contractAmount == null)
      this.contractAmount = BigDecimal.ZERO;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
    if (amount == null)
      this.amount = BigDecimal.ZERO;
  }

  public BigDecimal getNetPrice() {
    return netPrice;
  }

  public void setNetPrice(BigDecimal netPrice) {
    this.netPrice = netPrice;
    if (netPrice == null)
      this.netPrice = BigDecimal.ZERO;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public Integer getSourceType() {
    return sourceType;
  }

  public void setSourceType(Integer sourceType) {
    this.sourceType = sourceType;
  }

  public String getSourceId() {
    return sourceId;
  }

  public void setSourceId(String sourceId) {
    this.sourceId = sourceId;
  }

  public String getContractProjectSortId() {
    return contractProjectSortId;
  }

  public void setContractProjectSortId(String contractProjectSortId) {
    this.contractProjectSortId = contractProjectSortId;
  }

  public Object getLoader() {
    return loader;
  }

  public void setLoader(Object loader) {
    this.loader = loader;
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

  public List<DeliveryProductDetailDto> getChildren() {
    return children;
  }

  public void setChildren(List<DeliveryProductDetailDto> children) {
    this.children = children;
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
    if (productMoney == null)
      this.productMoney = BigDecimal.ZERO;
  }

  public String getProSortName() {
    return proSortName;
  }

  public void setProSortName(String proSortName) {
    this.proSortName = proSortName;
  }

  public BigDecimal getAllDeliveryAmount() {
    return allDeliveryAmount;
  }

  public void setAllDeliveryAmount(BigDecimal allDeliveryAmount) {
    this.allDeliveryAmount = allDeliveryAmount;
    if (allDeliveryAmount == null)
      this.allDeliveryAmount = BigDecimal.ZERO;
  }

  public BigDecimal getAllArrivalAmount() {
    return allArrivalAmount;
  }

  public void setAllArrivalAmount(BigDecimal allArrivalAmount) {
    this.allArrivalAmount = allArrivalAmount;
    if (allArrivalAmount == null)
      this.allArrivalAmount = BigDecimal.ZERO;
  }

  public String getProductBrand() {
    return productBrand;
  }

  public void setProductBrand(String productBrand) {
    this.productBrand = productBrand;
  }

  public String getMemo2() {
    return memo2;
  }

  public void setMemo2(String memo2) {
    this.memo2 = memo2;
  }

}
