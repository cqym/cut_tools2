package com.tl.resource.business.dto;

import java.math.BigDecimal;

public class ProductsStatisticsDto {
  private String toolsId;

  private String productName;

  private String brandCode;

  private String productSortId;

  private String productSortCode;

  private String productBrand;

  private String productSource;

  private BigDecimal contractAmount;

  private BigDecimal scheduleAmount;

  private BigDecimal tryAmount;

  private BigDecimal money;

  public String getToolsId() {
    return toolsId;
  }

  public void setToolsId(String toolsId) {
    this.toolsId = toolsId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getBrandCode() {
    return brandCode;
  }

  public void setBrandCode(String brandCode) {
    this.brandCode = brandCode;
  }

  public String getProductSortId() {
    return productSortId;
  }

  public void setProductSortId(String productSortId) {
    this.productSortId = productSortId;
  }

  public String getProductSortCode() {
    return productSortCode;
  }

  public void setProductSortCode(String productSortCode) {
    this.productSortCode = productSortCode;
  }

  public String getProductBrand() {
    return productBrand;
  }

  public void setProductBrand(String productBrand) {
    this.productBrand = productBrand;
  }

  public String getProductSource() {
    return productSource;
  }

  public void setProductSource(String productSource) {
    this.productSource = productSource;
  }

  public BigDecimal getContractAmount() {
    return contractAmount;
  }

  public void setContractAmount(BigDecimal contractAmount) {
    this.contractAmount = contractAmount;
  }

  public BigDecimal getScheduleAmount() {
    return scheduleAmount;
  }

  public void setScheduleAmount(BigDecimal scheduleAmount) {
    this.scheduleAmount = scheduleAmount;
  }

  public BigDecimal getTryAmount() {
    return tryAmount;
  }

  public void setTryAmount(BigDecimal tryAmount) {
    this.tryAmount = tryAmount;
  }

  public BigDecimal getMoney() {
    return money;
  }

  public void setMoney(BigDecimal money) {
    this.money = money;
  }

}
