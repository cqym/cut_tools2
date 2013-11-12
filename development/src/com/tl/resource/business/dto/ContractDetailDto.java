package com.tl.resource.business.dto;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;

public class ContractDetailDto {
  private static final BigDecimal BIGDECIMAL_ZERO = new BigDecimal("0");
  static {
    BigDecimalConverter bd = new BigDecimalConverter(BIGDECIMAL_ZERO);
    ConvertUtils.register(bd, java.math.BigDecimal.class);
  }

  private String contractProductDetailId;

  private BigDecimal contractAmount = BigDecimal.ZERO;

  private BigDecimal remainAmount = BigDecimal.ZERO;

  private String brandCode;

  private String productName;

  private String productUnit;

  private BigDecimal price = BigDecimal.ZERO;

  private String toolsId;

  private String contractInforId;

  private String contractCode;

  private Integer leaf;

  private BigDecimal productMoney = BigDecimal.ZERO;

  private BigDecimal orderAmount = BigDecimal.ZERO;

  private String projectCode;

  private String productBrand;

  private String contractProjectSortId;

  private String productCode;

  private Integer serialNumber;

  private String proSortName;

  private String parentToolsId;

  private BigDecimal singleSetAssemblyAmount = BigDecimal.ZERO;

  private BigDecimal singleSetStockAmount = BigDecimal.ZERO;

  private BigDecimal arrivalAmount = BigDecimal.ZERO;

  private BigDecimal deliveryAmount = BigDecimal.ZERO;

  private BigDecimal reserveAmount = BigDecimal.ZERO;

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

  private Integer status;

  private String toolDescription;

  private String memo;

  private Object loader;//马的，ext给加了新属性，没办法的办法

  private String uiProvider = "col";

  private String iconCls = "task-folder";

  private List<ContractDetailDto> children;

  private BigDecimal sumOrderAmount;

  private BigDecimal sumArrivalAmount;

  private BigDecimal sumOutAmount;

  private BigDecimal sumDeliveryAmount;

  private BigDecimal amount;

  public String getContractProductDetailId() {
    return contractProductDetailId;
  }

  public void setContractProductDetailId(String contractProductDetailId) {
    this.contractProductDetailId = contractProductDetailId;
  }

  public BigDecimal getContractAmount() {
    return contractAmount;
  }

  public void setContractAmount(BigDecimal contractAmount) {
    this.contractAmount = contractAmount;
  }

  public BigDecimal getRemainAmount() {
    return remainAmount;
  }

  public void setRemainAmount(BigDecimal remainAmount) {
    this.remainAmount = remainAmount;
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

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
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

  public BigDecimal getProductMoney() {
    return productMoney;
  }

  public void setProductMoney(BigDecimal productMoney) {
    this.productMoney = productMoney;
  }

  public BigDecimal getOrderAmount() {
    return orderAmount;
  }

  public void setOrderAmount(BigDecimal orderAmount) {
    this.orderAmount = orderAmount;
  }

  public String getProjectCode() {
    return projectCode;
  }

  public void setProjectCode(String projectCode) {
    this.projectCode = projectCode;
  }

  public String getProductBrand() {
    return productBrand;
  }

  public void setProductBrand(String productBrand) {
    this.productBrand = productBrand;
  }

  public String getContractProjectSortId() {
    return contractProjectSortId;
  }

  public void setContractProjectSortId(String contractProjectSortId) {
    this.contractProjectSortId = contractProjectSortId;
  }

  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }

  public Integer getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(Integer serialNumber) {
    this.serialNumber = serialNumber;
  }

  public String getProSortName() {
    return proSortName;
  }

  public void setProSortName(String proSortName) {
    this.proSortName = proSortName;
  }

  public String getParentToolsId() {
    return parentToolsId;
  }

  public void setParentToolsId(String parentToolsId) {
    this.parentToolsId = parentToolsId;
  }

  public BigDecimal getSingleSetAssemblyAmount() {
    return singleSetAssemblyAmount;
  }

  public void setSingleSetAssemblyAmount(BigDecimal singleSetAssemblyAmount) {
    this.singleSetAssemblyAmount = singleSetAssemblyAmount;
  }

  public BigDecimal getSingleSetStockAmount() {
    return singleSetStockAmount;
  }

  public void setSingleSetStockAmount(BigDecimal singleSetStockAmount) {
    this.singleSetStockAmount = singleSetStockAmount;
  }

  public BigDecimal getArrivalAmount() {
    return arrivalAmount;
  }

  public void setArrivalAmount(BigDecimal arrivalAmount) {
    this.arrivalAmount = arrivalAmount;
  }

  public BigDecimal getDeliveryAmount() {
    return deliveryAmount;
  }

  public void setDeliveryAmount(BigDecimal deliveryAmount) {
    this.deliveryAmount = deliveryAmount;
  }

  public BigDecimal getReserveAmount() {
    return reserveAmount;
  }

  public void setReserveAmount(BigDecimal reserveAmount) {
    this.reserveAmount = reserveAmount;
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

  public BigDecimal getMoney() {
    return money;
  }

  public void setMoney(BigDecimal money) {
    this.money = money;
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

  public String getReserveInforId() {
    return reserveInforId;
  }

  public void setReserveInforId(String reserveInforId) {
    this.reserveInforId = reserveInforId;
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

  public List<ContractDetailDto> getChildren() {
    return children;
  }

  public void setChildren(List<ContractDetailDto> children) {
    this.children = children;
  }

  public BigDecimal getSumOrderAmount() {
    return sumOrderAmount;
  }

  public void setSumOrderAmount(BigDecimal sumOrderAmount) {
    this.sumOrderAmount = sumOrderAmount;
  }

  public BigDecimal getSumArrivalAmount() {
    return sumArrivalAmount;
  }

  public void setSumArrivalAmount(BigDecimal sumArrivalAmount) {
    this.sumArrivalAmount = sumArrivalAmount;
  }

  public BigDecimal getSumOutAmount() {
    return sumOutAmount;
  }

  public void setSumOutAmount(BigDecimal sumOutAmount) {
    this.sumOutAmount = sumOutAmount;
  }

  public BigDecimal getSumDeliveryAmount() {
    return sumDeliveryAmount;
  }

  public void setSumDeliveryAmount(BigDecimal sumDeliveryAmount) {
    this.sumDeliveryAmount = sumDeliveryAmount;
  }

  public String getContractCode() {
    return contractCode;
  }

  public void setContractCode(String contractCode) {
    this.contractCode = contractCode;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

}
