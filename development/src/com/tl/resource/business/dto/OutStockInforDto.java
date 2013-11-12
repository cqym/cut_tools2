package com.tl.resource.business.dto;

import java.util.Date;
import java.util.List;

public class OutStockInforDto {
  public static final String OUT_STOCK_TYPE_DIRECT = "0";

  public static final String OUT_STOCK_TYPE_CONTRACT_TQ = "1";

  public static final String OUT_STOCK_TYPE_SCHEDULE_ORDER = "2";

  public static final String OUT_STOCK_TYPE_MAT = "3";

  public static final String OUT_STOCK_TYPE_CONTRACT_ORDER = "4";

  public static final String OUT_STOCK_TYPE_SCHEDULE_TQ = "5";

  public static final String OUT_STOCK_TYPE_TRY_TQ = "6";

  public static final String OUT_STOCK_TYPE_TRY_ORDER = "7";

  private String id;

  private String contractCode;

  private String contractId;

  private String memo;

  private Integer outStockType;

  private Integer status;

  private Date editDate;

  private String editDateString;

  private String userId;

  private String userName;

  private String outStockCode;

  private String quotationCode;

  private String quotationId;

  private String customerCode;

  private String customerName;

  private String customerCodeName;

  private String outStockDate;

  private String supplierName;

  private Date confirmDate;

  private String confirmUserName;

  private List<OutStockDetailDto> outStockDetails;

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

  public String getContractId() {
    return contractId;
  }

  public void setContractId(String contractId) {
    this.contractId = contractId;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public Integer getOutStockType() {
    return outStockType;
  }

  public void setOutStockType(Integer outStockType) {
    this.outStockType = outStockType;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
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

  public List<OutStockDetailDto> getOutStockDetails() {
    return outStockDetails;
  }

  public void setOutStockDetails(List<OutStockDetailDto> outStockDetails) {
    this.outStockDetails = outStockDetails;
  }

  public String getOutStockCode() {
    return outStockCode;
  }

  public void setOutStockCode(String outStockCode) {
    this.outStockCode = outStockCode;
  }

  public String getEditDateString() {
    return editDateString;
  }

  public void setEditDateString(String editDateString) {
    this.editDateString = editDateString;
  }

  public String getQuotationCode() {
    return quotationCode;
  }

  public void setQuotationCode(String quotationCode) {
    this.quotationCode = quotationCode;
  }

  public String getQuotationId() {
    return quotationId;
  }

  public void setQuotationId(String quotationId) {
    this.quotationId = quotationId;
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

  public String getOutStockDate() {
    return outStockDate;
  }

  public void setOutStockDate(String outStockDate) {
    this.outStockDate = outStockDate;
  }

  public String getSupplierName() {
    return supplierName;
  }

  public void setSupplierName(String supplierName) {
    this.supplierName = supplierName;
  }

  public String getCustomerCodeName() {
    String name = this.customerName == null ? "" : this.customerName;
    String code = this.customerCode == null ? "" : this.customerCode;
    return code + "-" + name;
  }

  public void setCustomerCodeName(String customerCodeName) {
    this.customerCodeName = customerCodeName;
  }

  public Date getConfirmDate() {
    return confirmDate;
  }

  public void setConfirmDate(Date confirmDate) {
    this.confirmDate = confirmDate;
  }

  public String getConfirmUserName() {
    return confirmUserName;
  }

  public void setConfirmUserName(String confirmUserName) {
    this.confirmUserName = confirmUserName;
  }

}
