package com.tl.resource.business.dto;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ContractInforDto {
  private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

  public static final Integer CONTRACT_TYPE_GENERAL = 0;

  public static final Integer CONTRACT_TYPE_PROJECT = 1;

  public static final Integer CONTRACT_INVOICE_STATUS_NO_EIDT = 0;//未编制

  public static final Integer CONTRACT_INVOICE_STATUS_NO_OVER = 1;//未编制完

  public static final Integer CONTRACT_INVOICE_STATUS_OVER = 2;//已编制完

  private String id;

  private String contractCode;

  private String sellerName;

  private String currencyId;

  private String currencyName;

  private String signDate;

  private Integer urgentLevel;

  private String customerCode;

  private String customerName;

  private String customerCodeName;

  private Integer status;

  private String signAddress;

  private String reference;

  private String trafficMode;

  private Integer closingAccountModeId;

  private String closingAccountMode;

  private String deliveryAddressType;

  private String otherConvention;

  private String effectConditions;

  private BigDecimal productMoney;

  private BigDecimal taxRate;

  private BigDecimal taxMoney;

  private BigDecimal totalMoney;

  private BigDecimal overallRebate;

  private BigDecimal finalMoney;

  private String memo;

  private Date editDate;

  private String editDateString;

  private String ownContactPerson;

  private String userId;

  private String userName;

  private Integer contractType;

  private String customerFax;

  private String customerPhone;

  private String cusContactPerson;

  private Integer invoiceStatus;

  private List<ContractProductSortDto> contractProductSorts;

  private int fileCount;

  private Integer cusLockStatus;

  /**
   * 形式发票
   */
  private Integer exemplarInvoice;

  private BigDecimal invoiceMoney;

  private BigDecimal returnMoney;

  private BigDecimal deliveryMoney;

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

  public String getContractCode() {
    return contractCode;
  }

  public void setContractCode(String contractCode) {
    this.contractCode = contractCode;
  }

  public String getSellerName() {
    return sellerName;
  }

  public void setSellerName(String sellerName) {
    this.sellerName = sellerName;
  }

  public String getCurrencyName() {
    return currencyName;
  }

  public void setCurrencyName(String currencyName) {
    this.currencyName = currencyName;
  }

  public String getSignDate() {
    return signDate;
  }

  public void setSignDate(String signDate) {
    this.signDate = signDate;
  }

  public Integer getUrgentLevel() {
    return urgentLevel;
  }

  public void setUrgentLevel(Integer urgentLevel) {
    this.urgentLevel = urgentLevel;
  }

  public String getCustomerCode() {
    return customerCode;
  }

  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getSignAddress() {
    return signAddress;
  }

  public void setSignAddress(String signAddress) {
    this.signAddress = signAddress;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public String getTrafficMode() {
    return trafficMode;
  }

  public void setTrafficMode(String trafficMode) {
    this.trafficMode = trafficMode;
  }

  public Integer getClosingAccountModeId() {
    return closingAccountModeId;
  }

  public void setClosingAccountModeId(Integer closingAccountModeId) {
    this.closingAccountModeId = closingAccountModeId;
  }

  public String getClosingAccountMode() {
    return closingAccountMode;
  }

  public void setClosingAccountMode(String closingAccountMode) {
    this.closingAccountMode = closingAccountMode;
  }

  public String getDeliveryAddressType() {
    return deliveryAddressType;
  }

  public void setDeliveryAddressType(String deliveryAddressType) {
    this.deliveryAddressType = deliveryAddressType;
  }

  public String getOtherConvention() {
    return otherConvention;
  }

  public void setOtherConvention(String otherConvention) {
    this.otherConvention = otherConvention;
  }

  public String getEffectConditions() {
    return effectConditions;
  }

  public void setEffectConditions(String effectConditions) {
    this.effectConditions = effectConditions;
  }

  public BigDecimal getProductMoney() {
    return productMoney;
  }

  public void setProductMoney(BigDecimal productMoney) {
    this.productMoney = productMoney;
  }

  public BigDecimal getTaxRate() {
    return taxRate;
  }

  public void setTaxRate(BigDecimal taxRate) {
    this.taxRate = taxRate;
  }

  public BigDecimal getTaxMoney() {
    return taxMoney;
  }

  public void setTaxMoney(BigDecimal taxMoney) {
    this.taxMoney = taxMoney;
  }

  public BigDecimal getTotalMoney() {
    return totalMoney;
  }

  public void setTotalMoney(BigDecimal totalMoney) {
    this.totalMoney = totalMoney;
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

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public Date getEditDate() {
    return editDate;
  }

  public void setEditDate(Date editDate) {
    if (editDate != null) {
      editDateString = df.format(editDate);
    }
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

  public List<ContractProductSortDto> getContractProductSorts() {
    return contractProductSorts;
  }

  public void setContractProductSorts(List<ContractProductSortDto> contractProductSorts) {
    this.contractProductSorts = contractProductSorts;
  }

  public String getEditDateString() {
    return editDateString;
  }

  public void setEditDateString(String editDateString) {
    this.editDateString = editDateString;
  }

  public Integer getContractType() {
    return contractType;
  }

  public void setContractType(Integer contractType) {
    this.contractType = contractType;
  }

  public String getOwnContactPerson() {
    return ownContactPerson;
  }

  public void setOwnContactPerson(String ownContactPerson) {
    this.ownContactPerson = ownContactPerson;
  }

  public String getCustomerFax() {
    return customerFax;
  }

  public void setCustomerFax(String customerFax) {
    this.customerFax = customerFax;
  }

  public String getCustomerPhone() {
    return customerPhone;
  }

  public void setCustomerPhone(String customerPhone) {
    this.customerPhone = customerPhone;
  }

  public String getCusContactPerson() {
    return cusContactPerson;
  }

  public void setCusContactPerson(String cusContactPerson) {
    this.cusContactPerson = cusContactPerson;
  }

  public int getFileCount() {
    return fileCount;
  }

  public void setFileCount(int fileCount) {
    this.fileCount = fileCount;
  }

  public String getCustomerCodeName() {
    String name = this.customerName == null ? "" : this.customerName;
    String code = this.customerCode == null ? "" : this.customerCode;
    return code + "-" + name;
  }

  public void setCustomerCodeName(String customerCodeName) {
    this.customerCodeName = customerCodeName;
  }

  public Integer getCusLockStatus() {
    return cusLockStatus;
  }

  public void setCusLockStatus(Integer cusLockStatus) {
    this.cusLockStatus = cusLockStatus;
  }

  public String getCurrencyId() {
    return currencyId;
  }

  public void setCurrencyId(String currencyId) {
    this.currencyId = currencyId;
  }

  public Integer getInvoiceStatus() {
    return invoiceStatus;
  }

  public void setInvoiceStatus(Integer invoiceStatus) {
    this.invoiceStatus = invoiceStatus;
  }

  public Integer getExemplarInvoice() {
    return exemplarInvoice;
  }

  public void setExemplarInvoice(Integer exemplarInvoice) {
    this.exemplarInvoice = exemplarInvoice;
  }

  public BigDecimal getInvoiceMoney() {
    return invoiceMoney;
  }

  public void setInvoiceMoney(BigDecimal invoiceMoney) {
    this.invoiceMoney = invoiceMoney;
  }

  public BigDecimal getReturnMoney() {
    return returnMoney;
  }

  public void setReturnMoney(BigDecimal returnMoney) {
    this.returnMoney = returnMoney;
  }

  public BigDecimal getDeliveryMoney() {
    return deliveryMoney;
  }

  public void setDeliveryMoney(BigDecimal deliveryMoney) {
    this.deliveryMoney = deliveryMoney;
  }

}
