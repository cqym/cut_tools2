package com.tl.resource.business.dto;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TreeDto {
  private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

  private boolean leaf = true;

  private String uiProvider = "col";

  private String iconCls = "task";

  private List<TreeDto> children;

  private String id;

  private String productSortId;

  private String parentId;

  private String brandCode;

  private String productCode;

  private String productName;

  private String productUnit;

  private String productSortCode;

  private String productBrand;

  private String productSource;

  private String slaveFile;

  private String currencyName;

  private String memo;

  private BigDecimal stockPrice;

  private BigDecimal salePrice;

  private Date stockPriceDate;

  private Date salePriceDate;

  private String toolsId;

  private String createDateStr;

  /**
   * 折扣
   */
  private BigDecimal rebate;

  private BigDecimal amount;

  private String brandCodeHistory;

  private String toolsTypeId;

  private String toolsTypeName;

  public String getProductSortId() {
    return productSortId;
  }

  public void setProductSortId(String productSortId) {
    this.productSortId = productSortId;
  }

  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }

  public String getCurrencyName() {
    return currencyName;
  }

  public void setCurrencyName(String currencyName) {
    this.currencyName = currencyName;
  }

  public BigDecimal getStockPrice() {
    return stockPrice;
  }

  public void setStockPrice(BigDecimal stockPrice) {
    this.stockPrice = stockPrice;
  }

  public Date getStockPriceDate() {
    return stockPriceDate;
  }

  public void setStockPriceDate(Date stockPriceDate) {
    this.stockPriceDate = stockPriceDate;

    if (stockPriceDate != null) {
      this.createDateStr = df.format(stockPriceDate);
    }
  }

  public Date getSalePriceDate() {
    return salePriceDate;
  }

  public void setSalePriceDate(Date salePriceDate) {
    this.salePriceDate = salePriceDate;
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

  public String getProductBrand() {
    return productBrand;
  }

  public void setProductBrand(String productBrand) {
    this.productBrand = productBrand;
  }

  public String getProductSortCode() {
    return productSortCode;
  }

  public void setProductSortCode(String productSortCode) {
    this.productSortCode = productSortCode;
  }

  public String getProductSource() {
    return productSource;
  }

  public void setProductSource(String productSource) {
    this.productSource = productSource;
  }

  public String getProductUnit() {
    return productUnit;
  }

  public void setProductUnit(String productUnit) {
    this.productUnit = productUnit;
  }

  public String getSlaveFile() {
    return slaveFile;
  }

  public void setSlaveFile(String slaveFile) {
    this.slaveFile = slaveFile;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public List<TreeDto> getChildren() {
    return children;
  }

  public void setChildren(List<TreeDto> children) {
    this.children = children;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
    this.toolsId = id;
  }

  public boolean isLeaf() {
    return leaf;
  }

  public void setLeaf(boolean leaf) {
    this.leaf = leaf;
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

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public BigDecimal getSalePrice() {
    return salePrice;
  }

  public void setSalePrice(BigDecimal salePrice) {
    this.salePrice = salePrice;
  }

  public BigDecimal getRebate() {
    return rebate;
  }

  public void setRebate(BigDecimal rebate) {
    this.rebate = rebate;
  }

  public String getToolsId() {
    return toolsId;
  }

  public void setToolsId(String toolsId) {
    this.toolsId = toolsId;
  }

  public String getCreateDateStr() {
    return createDateStr;
  }

  public void setCreateDateStr(String createDateStr) {
    this.createDateStr = createDateStr;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getBrandCodeHistory() {
    return brandCodeHistory;
  }

  public void setBrandCodeHistory(String brandCodeHistory) {
    this.brandCodeHistory = brandCodeHistory;
  }

  public String getToolsTypeId() {
    return toolsTypeId;
  }

  public void setToolsTypeId(String toolsTypeId) {
    this.toolsTypeId = toolsTypeId;
  }

  public String getToolsTypeName() {
    return toolsTypeName;
  }

  public void setToolsTypeName(String toolsTypeName) {
    this.toolsTypeName = toolsTypeName;
  }

}
