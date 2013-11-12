package com.tl.resource.business.dto;

import java.math.BigDecimal;

public class UserWorkStatisticsDto {
  private String userId;

  private String userName;

  private BigDecimal contractProductMoney;

  private BigDecimal contractFinalMoney;

  private BigDecimal deliveryMoney;

  private BigDecimal invoiceMoney;

  private BigDecimal accountsMoney;

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

  public BigDecimal getContractProductMoney() {
    return contractProductMoney;
  }

  public void setContractProductMoney(BigDecimal contractProductMoney) {
    this.contractProductMoney = contractProductMoney;
  }

  public BigDecimal getContractFinalMoney() {
    return contractFinalMoney;
  }

  public void setContractFinalMoney(BigDecimal contractFinalMoney) {
    this.contractFinalMoney = contractFinalMoney;
  }

  public BigDecimal getDeliveryMoney() {
    return deliveryMoney;
  }

  public void setDeliveryMoney(BigDecimal deliveryMoney) {
    this.deliveryMoney = deliveryMoney;
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

}
