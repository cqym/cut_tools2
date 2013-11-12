package com.tl.resource.business.dto;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class QuotationDto {
  private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

  public static final String QUO_TYPE_GENERAL = "0";

  public static final String QUO_TYPE_PROJECT = "2";

  public static final String QUO_TYPE_SCHEDULE = "3";

  public static final String QUO_TYPE_TRY = "4";

  public static final Integer TRANSFER_CONTRACT_ALL = 2;

  public static final Integer TRANSFER_CONTRACT_PART = 1;

  public static final Integer TRANSFER_CONTRACT_NEVER = 0;

  private String id;

  private String quotationCode;

  private Integer status;

  private String customerName;

  private String editorName;

  private String quotationDate;

  private String deliveryType;

  private String currencyName;

  private BigDecimal productMoney = BigDecimal.ZERO;

  private BigDecimal taxRate = BigDecimal.ZERO;

  private BigDecimal taxMoney = BigDecimal.ZERO;

  private BigDecimal totalMoney = BigDecimal.ZERO;

  private String paymentCondition;

  private String quotationType;

  private String memo;

  private String sellerName;

  private String cusContactPerson;

  private String customerCode;

  private String phoneFirst;

  private String deliveryDate;

  private String overallRebate;

  private String finalMoney;

  private String customerPhone;

  private String customerFax;

  private Integer urgentLevel;

  private String validStartDate;

  private String validEndDate;

  private Date editDate;

  private String editTimeStr;

  private String userName;

  private String currency;

  private Integer willOrderExpected;

  private String willFormalDate;

  private String slaveFile;

  /**
   * 合同编号
   */
  private String contractCode;

  private String impToQuoCode;

  /**
   * 交货单数量
   */
  private Integer deliveryNum;

  /**
   * 试刀申请
   */
  private String testRequest;

  /**
   * 试刀报告
   */
  private String testReport;

  private Integer exemplarInvoice;

  private Integer transferContract;

  private List detail;

  public Integer getUrgentLevel() {
    return urgentLevel;
  }

  public void setUrgentLevel(Integer urgentLevel) {
    this.urgentLevel = urgentLevel;
  }

  public String getValidStartDate() {
    return validStartDate;
  }

  public void setValidStartDate(String validStartDate) {
    this.validStartDate = validStartDate;
  }

  public String getValidEndDate() {
    return validEndDate;
  }

  public void setValidEndDate(String validEndDate) {
    this.validEndDate = validEndDate;
  }

  public Date getEditDate() {
    return editDate;
  }

  public void setEditDate(Date editDate) {
    this.editDate = editDate;
    if (editDate != null) {
      editTimeStr = df.format(editDate);
    }
  }

  public String getCustomerPhone() {
    return customerPhone;
  }

  public void setCustomerPhone(String customerPhone) {
    this.customerPhone = customerPhone;
  }

  public String getCustomerFax() {
    return customerFax;
  }

  public void setCustomerFax(String customerFax) {
    this.customerFax = customerFax;
  }

  public String getEditTimeStr() {
    return editTimeStr;
  }

  public void setEditTimeStr(String editTimeStr) {
    this.editTimeStr = editTimeStr;
  }

  public String getPhoneFirst() {
    return phoneFirst;
  }

  public void setPhoneFirst(String phoneFirst) {
    this.phoneFirst = phoneFirst;
  }

  public String getDeliveryDate() {
    return deliveryDate;
  }

  public void setDeliveryDate(String deliveryDate) {
    this.deliveryDate = deliveryDate;
  }

  public String getOverallRebate() {
    return overallRebate;
  }

  public void setOverallRebate(String overallRebate) {
    this.overallRebate = overallRebate;
  }

  public String getFinalMoney() {
    return finalMoney;
  }

  public void setFinalMoney(String finalMoney) {
    this.finalMoney = finalMoney;
  }

  public String getQuotationCode() {
    return quotationCode;
  }

  public void setQuotationCode(String quotationCode) {
    this.quotationCode = quotationCode;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getQuotationDate() {
    return quotationDate;
  }

  public void setQuotationDate(String quotationDate) {
    this.quotationDate = quotationDate;
  }

  public String getDeliveryType() {
    return deliveryType;
  }

  public void setDeliveryType(String deliveryType) {
    this.deliveryType = deliveryType;
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

  public String getPaymentCondition() {
    return paymentCondition;
  }

  public void setPaymentCondition(String paymentCondition) {
    this.paymentCondition = paymentCondition;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEditorName() {
    return editorName;
  }

  public void setEditorName(String editorName) {
    this.editorName = editorName;
  }

  public String getQuotationType() {
    return quotationType;
  }

  public void setQuotationType(String quotationType) {
    this.quotationType = quotationType;
  }

  public String getCusContactPerson() {
    return cusContactPerson;
  }

  public void setCusContactPerson(String cusContactPerson) {
    this.cusContactPerson = cusContactPerson;
  }

  public String getCustomerCode() {
    return customerCode;
  }

  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  public String getSellerName() {
    return sellerName;
  }

  public void setSellerName(String sellerName) {
    this.sellerName = sellerName;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public Integer getWillOrderExpected() {
    return willOrderExpected;
  }

  public void setWillOrderExpected(Integer willOrderExpected) {
    this.willOrderExpected = willOrderExpected;
  }

  public String getWillFormalDate() {
    return willFormalDate;
  }

  public void setWillFormalDate(String willFormalDate) {
    this.willFormalDate = willFormalDate;
  }

  public String getSlaveFile() {
    return slaveFile;
  }

  public void setSlaveFile(String slaveFile) {
    this.slaveFile = slaveFile;
  }

  public String getContractCode() {
    return contractCode;
  }

  public void setContractCode(String contractCode) {
    this.contractCode = contractCode;
  }

  public String getImpToQuoCode() {
    return impToQuoCode;
  }

  public void setImpToQuoCode(String impToQuoCode) {
    this.impToQuoCode = impToQuoCode;
  }

  public Integer getDeliveryNum() {
    return deliveryNum;
  }

  public void setDeliveryNum(Integer deliveryNum) {
    this.deliveryNum = deliveryNum;
  }

  public String getTestRequest() {
    return testRequest;
  }

  public void setTestRequest(String testRequest) {
    this.testRequest = testRequest;
  }

  public String getTestReport() {
    return testReport;
  }

  public void setTestReport(String testReport) {
    this.testReport = testReport;
  }

  public List getDetail() {
    return detail;
  }

  public void setDetail(List detail) {
    this.detail = detail;
  }

  public Integer getExemplarInvoice() {
    return exemplarInvoice;
  }

  public void setExemplarInvoice(Integer exemplarInvoice) {
    this.exemplarInvoice = exemplarInvoice;
  }

  public Integer getTransferContract() {
    return transferContract;
  }

  public void setTransferContract(Integer transferContract) {
    this.transferContract = transferContract;
  }

}
