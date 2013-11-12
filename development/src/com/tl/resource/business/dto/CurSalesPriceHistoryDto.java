package com.tl.resource.business.dto;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 销售历史Dto
 * @author xtaia
 * 
 */

public class CurSalesPriceHistoryDto {

  private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

  private String editDateString;

  /**
   * 工具信息ID
   */
  private String id;

  /**
   * 货品组_主键
   */
  private String productSortId;

  /**
   * 工具信息父ID
   */
  private String parentId;

  /**
   * 牌号
   */
  private String brandCode;

  /**
   * 货品编号
   */
  private String productCode;

  /**
   * 名称
   */
  private String productName;

  /**
   * 计量单位
   */
  private String productUnit;

  /**
   * 组别编号
   */
  private String productSortCode;

  /**
   * 品牌
   */
  private String productBrand;

  /**
   * 来源名称
   */
  private String productSource;

  /**
   * 附件
   */
  private String slaveFile;

  /**
   * 币别
   */
  private String currencyName;

  /**
   * 备注
   */
  private String memo;

  /**
   * 销售价格
   */
  private BigDecimal salePrice;

  /**
   * 销售价格变动时间
   */
  private String salePriceDate;

  /**
   * 采购价格
   */
  private BigDecimal stockPrice;

  /**
   * 采购价格变动时间
   */
  private String stockPriceDate;

  private Integer leaf;

  /**
   * 工具信息ID
   */
  private String productToolInforId;

  /**
   * 客户主键
   */
  private String customerInforId;

  /**
   * 客户名称
   */
  private String customerName;

  /**
   * 销售面价
   */
  private BigDecimal historyPrice;

  /**
   * 折扣
   */
  private BigDecimal rebate;

  /**
   * 净价
   */
  private BigDecimal netPrice;

  /**
   * 编制时间
   */
  private String editDate;

  /**
   * 编制人
   */
  private String userName;

  /**
   * 状态
   */
  private Integer status;

  private String quotationCode;

  private String contractCode;

  public String getContractCode() {
    return contractCode;
  }

  public void setContractCode(String contractCode) {
    this.contractCode = contractCode;
  }

  public String getQuotationCode() {
    return quotationCode;
  }

  public void setQuotationCode(String quotationCode) {
    this.quotationCode = quotationCode;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getProductSortId() {
    return productSortId;
  }

  public void setProductSortId(String productSortId) {
    this.productSortId = productSortId;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getBrandCode() {
    return brandCode;
  }

  public void setBrandCode(String brandCode) {
    this.brandCode = brandCode;
  }

  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
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

  public String getSlaveFile() {
    return slaveFile;
  }

  public void setSlaveFile(String slaveFile) {
    this.slaveFile = slaveFile;
  }

  public String getCurrencyName() {
    return currencyName;
  }

  public void setCurrencyName(String currencyName) {
    this.currencyName = currencyName;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public BigDecimal getSalePrice() {
    return salePrice;
  }

  public void setSalePrice(BigDecimal salePrice) {
    this.salePrice = salePrice;
  }

  public BigDecimal getStockPrice() {
    return stockPrice;
  }

  public void setStockPrice(BigDecimal stockPrice) {
    this.stockPrice = stockPrice;
  }

  public Integer getLeaf() {
    return leaf;
  }

  public void setLeaf(Integer leaf) {
    this.leaf = leaf;
  }

  public String getProductToolInforId() {
    return productToolInforId;
  }

  public void setProductToolInforId(String productToolInforId) {
    this.productToolInforId = productToolInforId;
  }

  public String getCustomerInforId() {
    return customerInforId;
  }

  public void setCustomerInforId(String customerInforId) {
    this.customerInforId = customerInforId;
  }

  public BigDecimal getHistoryPrice() {
    return historyPrice;
  }

  public void setHistoryPrice(BigDecimal historyPrice) {
    this.historyPrice = historyPrice;
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

  public String getEditDate() {
    return editDate;
  }

  public void setEditDate(String editDate) {
    this.editDate = editDate;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getEditDateString() {
    return editDateString;
  }

  public void setEditDateString(String editDateString) {
    this.editDateString = editDateString;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getSalePriceDate() {
    return salePriceDate;
  }

  public void setSalePriceDate(String salePriceDate) {
    this.salePriceDate = salePriceDate;
  }

  public String getStockPriceDate() {
    return stockPriceDate;
  }

  public void setStockPriceDate(String stockPriceDate) {
    this.stockPriceDate = stockPriceDate;
  }

}
