package com.tl.resource.business.dto;

import java.math.BigDecimal;
import java.util.List;

public class ContractProductDetailDto {
  private String id;

  private String contractProjectSortId;

  private String contractInforId;

  private String toolsId;

  private String parentToolsId;

  private Integer leaf;

  private String projectCode;

  private Integer serialNumber;

  private String productBrand;

  private String productCode;

  private String brandCode;

  private String productName;

  private BigDecimal singleSetAssemblyAmount = BigDecimal.ZERO;

  private BigDecimal singleSetStockAmount = BigDecimal.ZERO;

  private BigDecimal arrivalAmount = BigDecimal.ZERO;

  private BigDecimal orderAmount = BigDecimal.ZERO;

  private BigDecimal amount = BigDecimal.ZERO;

  private BigDecimal remainAmount;//剩余数量

  private BigDecimal deliveryAmount = BigDecimal.ZERO;

  private BigDecimal reserveAmount = BigDecimal.ZERO;

  private BigDecimal outAmount = BigDecimal.ZERO;

  private String productUnit;

  private BigDecimal price = BigDecimal.ZERO;

  private BigDecimal rebate = BigDecimal.ZERO;

  private BigDecimal netPrice = BigDecimal.ZERO;

  private BigDecimal money = BigDecimal.ZERO;

  private BigDecimal taxNetPrice = BigDecimal.ZERO;

  private BigDecimal taxMoney = BigDecimal.ZERO;

  private Integer priceChange;

  private String deliveryDate;

  private String workshop;

  private String processCode;

  private String reserveInforId;

  private String reportCode;

  private String toolCode;

  private String proSortName;

  private Integer status;

  private String toolDescription;

  private String memo;

  private Object loader;//马的，ext给加了新属性，没办法的办法

  private String uiProvider = "col";

  private String iconCls = "task-folder";

  private int fileCount;

  private List<ContractProductDetailDto> children;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getContractProjectSortId() {
    return contractProjectSortId;
  }

  public void setContractProjectSortId(String contractProjectSortId) {
    this.contractProjectSortId = contractProjectSortId;
  }

  public String getContractInforId() {
    return contractInforId;
  }

  public void setContractInforId(String contractInforId) {
    this.contractInforId = contractInforId;
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

  public String getDeliveryDate() {
    return deliveryDate;
  }

  public void setDeliveryDate(String deliveryDate) {
    this.deliveryDate = deliveryDate;
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

  public String getToolCode() {
    return toolCode;
  }

  public void setToolCode(String toolCode) {
    this.toolCode = toolCode;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getToolDescription() {
    return toolDescription;
  }

  public void setToolDescription(String toolDescription) {
    this.toolDescription = toolDescription;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
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

  public List<ContractProductDetailDto> getChildren() {
    return children;
  }

  public void setChildren(List<ContractProductDetailDto> children) {
    this.children = children;
  }

  public Object getLoader() {
    return loader;
  }

  public void setLoader(Object loader) {
    this.loader = loader;
  }

  public String getProSortName() {
    return proSortName;
  }

  public void setProSortName(String proSortName) {
    this.proSortName = proSortName;
  }

  public BigDecimal getSingleSetAssemblyAmount() {
    return singleSetAssemblyAmount;
  }

  public void setSingleSetAssemblyAmount(BigDecimal singleSetAssemblyAmount) {
    if (singleSetAssemblyAmount == null) {
      this.singleSetAssemblyAmount = BigDecimal.ZERO;
    } else {
      this.singleSetAssemblyAmount = singleSetAssemblyAmount;
    }
  }

  public BigDecimal getSingleSetStockAmount() {
    return singleSetStockAmount;
  }

  public void setSingleSetStockAmount(BigDecimal singleSetStockAmount) {
    if (singleSetStockAmount == null) {
      this.singleSetStockAmount = BigDecimal.ZERO;
    } else {
      this.singleSetStockAmount = singleSetStockAmount;
    }

  }

  public BigDecimal getArrivalAmount() {
    return arrivalAmount;
  }

  public void setArrivalAmount(BigDecimal arrivalAmount) {

    if (arrivalAmount == null) {
      this.arrivalAmount = BigDecimal.ZERO;
    } else {
      this.arrivalAmount = arrivalAmount;
    }
  }

  public BigDecimal getOrderAmount() {
    return orderAmount;
  }

  public void setOrderAmount(BigDecimal orderAmount) {
    if (orderAmount == null) {
      this.orderAmount = BigDecimal.ZERO;
    } else {
      this.orderAmount = orderAmount;
    }
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {

    if (amount == null) {
      this.amount = BigDecimal.ZERO;
    } else {
      this.amount = amount;
    }
  }

  public BigDecimal getDeliveryAmount() {
    return deliveryAmount;
  }

  public void setDeliveryAmount(BigDecimal deliveryAmount) {

    if (deliveryAmount == null) {
      this.deliveryAmount = BigDecimal.ZERO;
    } else {
      this.deliveryAmount = deliveryAmount;
    }
  }

  public BigDecimal getRemainAmount() {
    return remainAmount;
  }

  public void setRemainAmount(BigDecimal remainAmount) {
    this.remainAmount = remainAmount;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {

    if (price == null) {
      this.price = BigDecimal.ZERO;
    } else {
      this.price = price;
    }
  }

  public BigDecimal getRebate() {
    return rebate;
  }

  public void setRebate(BigDecimal rebate) {

    if (rebate == null) {
      this.rebate = BigDecimal.ZERO;
    } else {
      this.rebate = rebate;
    }
  }

  public BigDecimal getNetPrice() {
    return netPrice;
  }

  public void setNetPrice(BigDecimal netPrice) {

    if (netPrice == null) {
      this.netPrice = BigDecimal.ZERO;
    } else {
      this.netPrice = netPrice;
    }
  }

  public BigDecimal getMoney() {
    return money;
  }

  public void setMoney(BigDecimal money) {

    if (money == null) {
      this.money = BigDecimal.ZERO;
    } else {
      this.money = money;
    }
  }

  public BigDecimal getTaxNetPrice() {
    return taxNetPrice;
  }

  public void setTaxNetPrice(BigDecimal taxNetPrice) {

    if (taxNetPrice == null) {
      this.taxNetPrice = BigDecimal.ZERO;
    } else {
      this.taxNetPrice = taxNetPrice;
    }
  }

  public BigDecimal getTaxMoney() {
    return taxMoney;
  }

  public void setTaxMoney(BigDecimal taxMoney) {

    if (taxMoney == null) {
      this.taxMoney = BigDecimal.ZERO;
    } else {
      this.taxMoney = taxMoney;
    }
  }

  public BigDecimal getReserveAmount() {
    return reserveAmount;
  }

  public void setReserveAmount(BigDecimal reserveAmount) {
    this.reserveAmount = reserveAmount;
    if (reserveAmount == null)
      this.reserveAmount = BigDecimal.ZERO;
  }

  public String getReserveInforId() {
    return reserveInforId;
  }

  public void setReserveInforId(String reserveInforId) {
    this.reserveInforId = reserveInforId;
  }

  public int getFileCount() {
    return fileCount;
  }

  public void setFileCount(int fileCount) {
    this.fileCount = fileCount;
  }

  public BigDecimal getOutAmount() {
    return outAmount;
  }

  public void setOutAmount(BigDecimal outAmount) {
    this.outAmount = outAmount;
  }

}
