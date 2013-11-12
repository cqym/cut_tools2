package com.tl.resource.business.dto;

import java.math.BigDecimal;
import java.util.List;

public class OutStockDetailDto {
  private String uiProvider = "col";

  private String iconCls = "task";

  private String id;

  private String reserveInforId;

  private String contractProjectSortId;

  private String proSortName;

  private String outStockInforId;

  private String productCode;

  private String projectCode;

  private Integer serialNumber;

  private String brandCode;

  private String toolsId;

  private String parentToolsId;

  private Integer leaf;

  private String productName;

  private String productUnit;

  private String reserveCode;

  private String productBrand;

  private BigDecimal actualAmount = BigDecimal.ZERO;

  private BigDecimal reserveAmount = BigDecimal.ZERO;

  private BigDecimal price = BigDecimal.ZERO;

  private BigDecimal allOutAmount = BigDecimal.ZERO;

  private BigDecimal amount = BigDecimal.ZERO;

  private BigDecimal contractAmount = BigDecimal.ZERO;

  private BigDecimal money = BigDecimal.ZERO;

  private String contractProductDetailId;

  private String orderDetailId;

  private Integer newRecord;

  private BigDecimal oldAmount = BigDecimal.ZERO;

  private BigDecimal matOldAmount = BigDecimal.ZERO;

  private BigDecimal needAmount = BigDecimal.ZERO;

  private BigDecimal orderAmount = BigDecimal.ZERO;

  private BigDecimal matReserveAmount = BigDecimal.ZERO;

  private BigDecimal matOutAmount = BigDecimal.ZERO;

  private BigDecimal theAllOutAmount = BigDecimal.ZERO;

  private List<OutStockDetailDto> children;

  private String memo;

  private String memo2;

  private BigDecimal salePrice = BigDecimal.ZERO;

  public String getContractProjectSortId() {
    return contractProjectSortId;
  }

  public void setContractProjectSortId(String contractProjectSortId) {
    this.contractProjectSortId = contractProjectSortId;
  }

  public String getProSortName() {
    return proSortName;
  }

  public void setProSortName(String proSortName) {
    this.proSortName = proSortName;
  }

  public String getProjectCode() {
    return projectCode;
  }

  public void setProjectCode(String projectCode) {
    this.projectCode = projectCode;
  }

  public Integer getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(Integer serialNumber) {
    this.serialNumber = serialNumber;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getReserveInforId() {
    return reserveInforId;
  }

  public void setReserveInforId(String reserveInforId) {
    this.reserveInforId = reserveInforId;
  }

  public String getOutStockInforId() {
    return outStockInforId;
  }

  public void setOutStockInforId(String outStockInforId) {
    this.outStockInforId = outStockInforId;
  }

  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }

  public String getBrandCode() {
    return brandCode;
  }

  public void setBrandCode(String brandCode) {
    this.brandCode = brandCode;
  }

  public String getToolsId() {
    return toolsId;
  }

  public void setToolsId(String toolsId) {
    this.toolsId = toolsId;
  }

  public String getParentToolsId() {
    return parentToolsId;
  }

  public void setParentToolsId(String parentToolsId) {
    this.parentToolsId = parentToolsId;
  }

  public Integer getLeaf() {
    return leaf;
  }

  public void setLeaf(Integer leaf) {
    this.leaf = leaf;
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

  public String getReserveCode() {
    return reserveCode;
  }

  public void setReserveCode(String reserveCode) {
    this.reserveCode = reserveCode;
  }

  public BigDecimal getActualAmount() {
    return actualAmount;
  }

  public void setActualAmount(BigDecimal actualAmount) {
    this.actualAmount = actualAmount;
    if (actualAmount == null)
      this.actualAmount = BigDecimal.ZERO;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
    if (price == null)
      this.price = BigDecimal.ZERO;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
    if (amount == null)
      this.amount = BigDecimal.ZERO;
  }

  public BigDecimal getContractAmount() {
    return contractAmount;
  }

  public void setContractAmount(BigDecimal contractAmount) {
    this.contractAmount = contractAmount;
    if (contractAmount == null)
      this.contractAmount = BigDecimal.ZERO;
  }

  public BigDecimal getMoney() {
    return money;
  }

  public void setMoney(BigDecimal money) {
    this.money = money;
    if (money == null)
      this.money = BigDecimal.ZERO;
  }

  public String getContractProductDetailId() {
    return contractProductDetailId;
  }

  public void setContractProductDetailId(String contractProductDetailId) {
    this.contractProductDetailId = contractProductDetailId;
  }

  public BigDecimal getReserveAmount() {
    return reserveAmount;
  }

  public void setReserveAmount(BigDecimal reserveAmount) {
    this.reserveAmount = reserveAmount;
    if (reserveAmount == null)
      this.reserveAmount = BigDecimal.ZERO;
  }

  public Integer getNewRecord() {
    return newRecord;
  }

  public void setNewRecord(Integer newRecord) {
    this.newRecord = newRecord;
  }

  public BigDecimal getOldAmount() {
    return oldAmount;
  }

  public void setOldAmount(BigDecimal oldAmount) {
    this.oldAmount = oldAmount;
    if (oldAmount == null)
      this.oldAmount = BigDecimal.ZERO;
  }

  public BigDecimal getAllOutAmount() {
    return allOutAmount;
  }

  public void setAllOutAmount(BigDecimal allOutAmount) {
    this.allOutAmount = allOutAmount;
    if (allOutAmount == null)
      this.allOutAmount = BigDecimal.ZERO;
  }

  public List<OutStockDetailDto> getChildren() {
    return children;
  }

  public void setChildren(List<OutStockDetailDto> children) {
    this.children = children;
  }

  public BigDecimal getNeedAmount() {
    return needAmount;
  }

  public void setNeedAmount(BigDecimal needAmount) {
    this.needAmount = needAmount;
    if (needAmount == null)
      this.needAmount = BigDecimal.ZERO;
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

  public String getOrderDetailId() {
    return orderDetailId;
  }

  public void setOrderDetailId(String orderDetailId) {
    this.orderDetailId = orderDetailId;
  }

  public BigDecimal getOrderAmount() {
    return orderAmount;
  }

  public void setOrderAmount(BigDecimal orderAmount) {
    this.orderAmount = orderAmount;
    if (orderAmount == null)
      this.orderAmount = BigDecimal.ZERO;
  }

  public String getProductBrand() {
    return productBrand;
  }

  public void setProductBrand(String productBrand) {
    this.productBrand = productBrand;
  }

  public BigDecimal getMatReserveAmount() {
    return matReserveAmount;
  }

  public void setMatReserveAmount(BigDecimal matReserveAmount) {
    this.matReserveAmount = matReserveAmount;
  }

  public BigDecimal getMatOutAmount() {
    return matOutAmount;
  }

  public void setMatOutAmount(BigDecimal matOutAmount) {
    this.matOutAmount = matOutAmount;
  }

  public BigDecimal getTheAllOutAmount() {
    return theAllOutAmount;
  }

  public void setTheAllOutAmount(BigDecimal theAllOutAmount) {
    this.theAllOutAmount = theAllOutAmount;
  }

  public BigDecimal getMatOldAmount() {
    return matOldAmount;
  }

  public void setMatOldAmount(BigDecimal matOldAmount) {
    this.matOldAmount = matOldAmount;
    if (matOldAmount == null)
      this.matOldAmount = BigDecimal.ZERO;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public String getMemo2() {
    return memo2;
  }

  public void setMemo2(String memo2) {
    this.memo2 = memo2;
  }

  public BigDecimal getSalePrice() {
    return salePrice;
  }

  public void setSalePrice(BigDecimal salePrice) {
    this.salePrice = salePrice;
  }

}
