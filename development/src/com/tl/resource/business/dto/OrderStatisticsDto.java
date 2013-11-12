package com.tl.resource.business.dto;

import java.math.BigDecimal;

public class OrderStatisticsDto {
  private Integer orderType;

  private String supplierName;

  private String orderCode;

  private String sourceCode;

  private Integer status;

  private BigDecimal orderMoney;

  private BigDecimal invoiceMoney;

  private BigDecimal accountsMoney;

  private String memo;

  public Integer getOrderType() {
    return orderType;
  }

  public void setOrderType(Integer orderType) {
    this.orderType = orderType;
  }

  public String getSupplierName() {
    return supplierName;
  }

  public void setSupplierName(String supplierName) {
    this.supplierName = supplierName;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public String getSourceCode() {
    return sourceCode;
  }

  public void setSourceCode(String sourceCode) {
    this.sourceCode = sourceCode;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public BigDecimal getOrderMoney() {
    return orderMoney;
  }

  public void setOrderMoney(BigDecimal orderMoney) {
    this.orderMoney = orderMoney;
  }

  public BigDecimal getInvoiceMoney() {
    return invoiceMoney;
  }

  public void setInvoiceMoney(BigDecimal invoiceMoney) {
    this.invoiceMoney = invoiceMoney;
  }

  public BigDecimal getAccountsMoney() {
    return accountsMoney;
  }

  public void setAccountsMoney(BigDecimal accountsMoney) {
    this.accountsMoney = accountsMoney;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

}
