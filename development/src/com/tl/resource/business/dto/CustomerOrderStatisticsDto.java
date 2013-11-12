package com.tl.resource.business.dto;

import java.math.BigDecimal;

public class CustomerOrderStatisticsDto {
  private String customerName;

  private String ownContactPerson;

  private String contractCode;

  private Integer status;

  private BigDecimal contractAmount;

  private BigDecimal orderAmount;

  private BigDecimal arrivalAmount;

  private BigDecimal deliveryAmount;

  private BigDecimal contractMoney;

  private BigDecimal invoiceMoney;

  private BigDecimal accountsMoney;

  private String memo;

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getOwnContactPerson() {
    return ownContactPerson;
  }

  public void setOwnContactPerson(String ownContactPerson) {
    this.ownContactPerson = ownContactPerson;
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

  public BigDecimal getContractMoney() {
    return contractMoney;
  }

  public void setContractMoney(BigDecimal contractMoney) {
    this.contractMoney = contractMoney;
  }

  public BigDecimal getInvoiceMoney() {
    return invoiceMoney;
  }

  public void setInvoiceMoney(BigDecimal invoiceMoney) {
    this.invoiceMoney = invoiceMoney;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public BigDecimal getAccountsMoney() {
    return accountsMoney;
  }

  public void setAccountsMoney(BigDecimal accountsMoney) {
    this.accountsMoney = accountsMoney;
  }

}
