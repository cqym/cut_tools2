package com.tl.resource.business.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 订单信息Dto
 * @author ftl
 *
 */
public class OrderInfoDto implements Serializable {
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  public static final Integer ORDER_TYPE_CONTRACT = 1;//合同订单

  public static final Integer ORDER_TYPE_SCHEDLE = 5;//预订订单

  public static final Integer ORDER_TYPE_TRY = 6;//试刀订单

  public static final Integer ORDER_TYPE_CONTRACT_SELF = 3;//合同加工

  public static final Integer ORDER_TYPE_SCHEDLE_SELF = 7;//预订加工

  public static final Integer ORDER_TYPE_TRY_SELF = 8;//试刀加工

  public static final Integer ORDER_TYPE_RESERVE = 2;//储备

  public static final Integer ORDER_TYPE_MAT_RESERVE = 4;//材料储备

  /**
   * 订单ID
   */
  private String id;

  /**
   * 订单编号
   */
  private String orderCode;

  /**
   * 合同单号
   */
  private String contractCode;

  /**
   * 采购日期
   */
  private String orderDate;

  /**
   * 订单类型
   */
  private Integer orderType;

  /**
   * 状态
   */
  private Integer status;

  /**
   * 供货商
   */
  private String supplierName;

  /**
   * 供货商ID
   */
  private String supplierId;

  /**
   * 紧急程度
   */
  private Integer urgentLevel;

  /**
   * 制单人
   */
  private String userName;

  /**
   * 备注
   */
  private String mome;

  private BigDecimal overallRebate = BigDecimal.ZERO;

  private BigDecimal finalMoney = BigDecimal.ZERO;

  private String ownContactPerson;

  private String supplierOwnContactPerson;

  /**
   * 客户名称
   */
  private String customerName;

  /**
   * 客户编号
   */
  private String customerCode;

  /**
   *电话
   */
  private String phone;

  /**
  *传真
  */
  private String fax;

  private String contactPerson;

  private String currencyName;

  private BigDecimal productMoney = BigDecimal.ZERO;

  private String currencyId;

  private BigDecimal totalMoney = BigDecimal.ZERO;

  private BigDecimal taxRate = BigDecimal.ZERO;

  private String deliveryDate;

  private Date editDate;

  private String userId;

  private String editDateCopy;

  private String companyName;

  private String companyId;

  private String companyPhone;

  private String companyFax;

  private BigDecimal taxMoney = BigDecimal.ZERO;

  private String supplierContactPerson;

  private String supplierPhone;

  private String supplierFax;

  private String qualityStandard;

  private String deliveryAddressType;

  private String trafficMode;

  private BigDecimal closingAccountModeId;

  private String closingAccountMode;

  private String otherConvention;

  private String defaultDuty;

  private String effectConditions;

  private Integer fileCount;

  private String quotationId;

  private String quotationCode;

  private String contractDeliveryAddress;//销售合同交货地点及方式

  private String contractTrafficMode;

  private Integer editedPlan;

  private List orderDetail;

  private Integer contractUrgentLevel;

  private Date updateDate;

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public String getContractCode() {
    return contractCode;
  }

  public void setContractCode(String contractCode) {
    this.contractCode = contractCode;
  }

  public String getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(String orderDate) {
    this.orderDate = orderDate;
  }

  public Integer getOrderType() {
    return orderType;
  }

  public void setOrderType(Integer orderType) {
    this.orderType = orderType;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getSupplierName() {
    return supplierName;
  }

  public void setSupplierName(String supplierName) {
    this.supplierName = supplierName;
  }

  public String getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(String supplierId) {
    this.supplierId = supplierId;
  }

  public Integer getUrgentLevel() {
    return urgentLevel;
  }

  public void setUrgentLevel(Integer urgentLevel) {
    this.urgentLevel = urgentLevel;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getMome() {
    return mome;
  }

  public void setMome(String mome) {
    this.mome = mome;
  }

  public BigDecimal getOverallRebate() {
    return overallRebate;
  }

  public void setOverallRebate(BigDecimal overallRebate) {
    this.overallRebate = overallRebate;
  }

  public BigDecimal getFinalMoney() {
    return finalMoney;
  }

  public void setFinalMoney(BigDecimal finalMoney) {
    this.finalMoney = finalMoney;
  }

  public String getOwnContactPerson() {
    return ownContactPerson;
  }

  public void setOwnContactPerson(String ownContactPerson) {
    this.ownContactPerson = ownContactPerson;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getCustomerCode() {
    return customerCode;
  }

  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  public String getSupplierOwnContactPerson() {
    return supplierOwnContactPerson;
  }

  public void setSupplierOwnContactPerson(String supplierOwnContactPerson) {
    this.supplierOwnContactPerson = supplierOwnContactPerson;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getFax() {
    return fax;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }

  public String getContactPerson() {
    return contactPerson;
  }

  public void setContactPerson(String contactPerson) {
    this.contactPerson = contactPerson;
  }

  public String getCurrencyName() {
    return currencyName;
  }

  public void setCurrencyName(String currencyName) {
    this.currencyName = currencyName;
  }

  public BigDecimal getProductMoney() {
    return productMoney;
  }

  public void setProductMoney(BigDecimal productMoney) {
    this.productMoney = productMoney;
  }

  public String getCurrencyId() {
    return currencyId;
  }

  public void setCurrencyId(String currencyId) {
    this.currencyId = currencyId;
  }

  public BigDecimal getTotalMoney() {
    return totalMoney;
  }

  public void setTotalMoney(BigDecimal totalMoney) {
    this.totalMoney = totalMoney;
  }

  public BigDecimal getTaxRate() {
    return taxRate;
  }

  public void setTaxRate(BigDecimal taxRate) {
    this.taxRate = taxRate;
  }

  public String getDeliveryDate() {
    return deliveryDate;
  }

  public void setDeliveryDate(String deliveryDate) {
    this.deliveryDate = deliveryDate;
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

  public String getEditDateCopy() {
    return editDateCopy;
  }

  public void setEditDateCopy(String editDateCopy) {
    this.editDate = editDate;
    if (editDate != null)
      this.editDateCopy = dateFormat.format(editDate);
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public String getCompanyPhone() {
    return companyPhone;
  }

  public void setCompanyPhone(String companyPhone) {
    this.companyPhone = companyPhone;
  }

  public String getCompanyFax() {
    return companyFax;
  }

  public void setCompanyFax(String companyFax) {
    this.companyFax = companyFax;
  }

  public String getSupplierContactPerson() {
    return supplierContactPerson;
  }

  public void setSupplierContactPerson(String supplierContactPerson) {
    this.supplierContactPerson = supplierContactPerson;
  }

  public String getSupplierPhone() {
    return supplierPhone;
  }

  public void setSupplierPhone(String supplierPhone) {
    this.supplierPhone = supplierPhone;
  }

  public String getSupplierFax() {
    return supplierFax;
  }

  public void setSupplierFax(String supplierFax) {
    this.supplierFax = supplierFax;
  }

  public String getQualityStandard() {
    return qualityStandard;
  }

  public void setQualityStandard(String qualityStandard) {
    this.qualityStandard = qualityStandard;
  }

  public String getDeliveryAddressType() {
    return deliveryAddressType;
  }

  public void setDeliveryAddressType(String deliveryAddressType) {
    this.deliveryAddressType = deliveryAddressType;
  }

  public String getTrafficMode() {
    return trafficMode;
  }

  public void setTrafficMode(String trafficMode) {
    this.trafficMode = trafficMode;
  }

  public BigDecimal getClosingAccountModeId() {
    return closingAccountModeId;
  }

  public void setClosingAccountModeId(BigDecimal closingAccountModeId) {
    this.closingAccountModeId = closingAccountModeId;
  }

  public String getClosingAccountMode() {
    return closingAccountMode;
  }

  public void setClosingAccountMode(String closingAccountMode) {
    this.closingAccountMode = closingAccountMode;
  }

  public String getOtherConvention() {
    return otherConvention;
  }

  public void setOtherConvention(String otherConvention) {
    this.otherConvention = otherConvention;
  }

  public String getDefaultDuty() {
    return defaultDuty;
  }

  public void setDefaultDuty(String defaultDuty) {
    this.defaultDuty = defaultDuty;
  }

  public String getEffectConditions() {
    return effectConditions;
  }

  public void setEffectConditions(String effectConditions) {
    this.effectConditions = effectConditions;
  }

  public BigDecimal getTaxMoney() {
    return taxMoney;
  }

  public void setTaxMoney(BigDecimal taxMoney) {
    this.taxMoney = taxMoney;
  }

  public Integer getFileCount() {
    return fileCount;
  }

  public void setFileCount(Integer fileCount) {
    this.fileCount = fileCount;
  }

  public String getQuotationId() {
    return quotationId;
  }

  public void setQuotationId(String quotationId) {
    this.quotationId = quotationId;
  }

  public String getQuotationCode() {
    return quotationCode;
  }

  public void setQuotationCode(String quotationCode) {
    this.quotationCode = quotationCode;
  }

  public Integer getEditedPlan() {
    return editedPlan;
  }

  public void setEditedPlan(Integer editedPlan) {
    this.editedPlan = editedPlan;
  }

  public String getContractDeliveryAddress() {
    return contractDeliveryAddress;
  }

  public void setContractDeliveryAddress(String contractDeliveryAddress) {
    this.contractDeliveryAddress = contractDeliveryAddress;
  }

  public String getContractTrafficMode() {
    return contractTrafficMode;
  }

  public void setContractTrafficMode(String contractTrafficMode) {
    this.contractTrafficMode = contractTrafficMode;
  }

  public List getOrderDetail() {
    return orderDetail;
  }

  public void setOrderDetail(List orderDetail) {
    this.orderDetail = orderDetail;
  }

  public Integer getContractUrgentLevel() {
    return contractUrgentLevel;
  }

  public void setContractUrgentLevel(Integer contractUrgentLevel) {
    this.contractUrgentLevel = contractUrgentLevel;
  }

}
