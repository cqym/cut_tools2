package com.tl.resource.dao.pojo;

import java.math.BigDecimal;
import java.util.List;

public class TProductArrivalDetail {

  private String uiProvider = "col";

  private String iconCls = "task";

  private String productBrand;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_product_arrival_detail.id
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  private String id;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_product_arrival_detail.order_amount
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  private BigDecimal orderAmount = BigDecimal.ZERO;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_product_arrival_detail.brand_code
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  private String brandCode;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_product_arrival_detail.order_detail_id
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  private String orderDetailId;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_product_arrival_detail.product_arrival_infor_id
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  private String productArrivalInforId;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_product_arrival_detail.tools_id
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  private String toolsId;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_product_arrival_detail.parent_tools_id
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  private String parentToolsId;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_product_arrival_detail.leaf
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  private Integer leaf;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_product_arrival_detail.product_name
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  private String productName;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_product_arrival_detail.actual_amount
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  private BigDecimal actualAmount = BigDecimal.ZERO;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_product_arrival_detail.arrival_amount
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  private BigDecimal arrivalAmount = BigDecimal.ZERO;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_product_arrival_detail.product_unit
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  private String productUnit;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_product_arrival_detail.price
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  private BigDecimal price = BigDecimal.ZERO;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_product_arrival_detail.product_money
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  private BigDecimal productMoney = BigDecimal.ZERO;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_product_arrival_detail.memo
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  private String memo;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_product_arrival_detail.product_code
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  private String productCode;

  private List<TProductArrivalDetail> children;

  /**
   * 合同分项ID
   */
  private String contractProjectSortId;

  /**
   * 合同分项名称
   */
  private String proSortName;

  private String productPosition;

  private String reserveCode;

  private String toolsTypeName;

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_product_arrival_detail.id
   * @return  the value of t_product_arrival_detail.id
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public String getId() {
    return id;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_product_arrival_detail.id
   * @param id  the value for t_product_arrival_detail.id
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_product_arrival_detail.order_amount
   * @return  the value of t_product_arrival_detail.order_amount
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public BigDecimal getOrderAmount() {
    return orderAmount;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_product_arrival_detail.order_amount
   * @param orderAmount  the value for t_product_arrival_detail.order_amount
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public void setOrderAmount(BigDecimal orderAmount) {
    this.orderAmount = orderAmount;
    if (orderAmount == null) {
      this.orderAmount = BigDecimal.ZERO;
    }
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_product_arrival_detail.brand_code
   * @return  the value of t_product_arrival_detail.brand_code
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public String getBrandCode() {
    return brandCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_product_arrival_detail.brand_code
   * @param brandCode  the value for t_product_arrival_detail.brand_code
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public void setBrandCode(String brandCode) {
    this.brandCode = brandCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_product_arrival_detail.order_detail_id
   * @return  the value of t_product_arrival_detail.order_detail_id
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public String getOrderDetailId() {
    return orderDetailId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_product_arrival_detail.order_detail_id
   * @param orderDetailId  the value for t_product_arrival_detail.order_detail_id
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public void setOrderDetailId(String orderDetailId) {
    this.orderDetailId = orderDetailId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_product_arrival_detail.product_arrival_infor_id
   * @return  the value of t_product_arrival_detail.product_arrival_infor_id
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public String getProductArrivalInforId() {
    return productArrivalInforId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_product_arrival_detail.product_arrival_infor_id
   * @param productArrivalInforId  the value for t_product_arrival_detail.product_arrival_infor_id
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public void setProductArrivalInforId(String productArrivalInforId) {
    this.productArrivalInforId = productArrivalInforId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_product_arrival_detail.tools_id
   * @return  the value of t_product_arrival_detail.tools_id
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public String getToolsId() {
    return toolsId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_product_arrival_detail.tools_id
   * @param toolsId  the value for t_product_arrival_detail.tools_id
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public void setToolsId(String toolsId) {
    this.toolsId = toolsId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_product_arrival_detail.parent_tools_id
   * @return  the value of t_product_arrival_detail.parent_tools_id
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public String getParentToolsId() {
    return parentToolsId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_product_arrival_detail.parent_tools_id
   * @param parentToolsId  the value for t_product_arrival_detail.parent_tools_id
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public void setParentToolsId(String parentToolsId) {
    this.parentToolsId = parentToolsId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_product_arrival_detail.leaf
   * @return  the value of t_product_arrival_detail.leaf
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public Integer getLeaf() {
    return leaf;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_product_arrival_detail.leaf
   * @param leaf  the value for t_product_arrival_detail.leaf
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public void setLeaf(Integer leaf) {
    this.leaf = leaf;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_product_arrival_detail.product_name
   * @return  the value of t_product_arrival_detail.product_name
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public String getProductName() {
    return productName;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_product_arrival_detail.product_name
   * @param productName  the value for t_product_arrival_detail.product_name
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public void setProductName(String productName) {
    this.productName = productName;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_product_arrival_detail.actual_amount
   * @return  the value of t_product_arrival_detail.actual_amount
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public BigDecimal getActualAmount() {
    return actualAmount;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_product_arrival_detail.actual_amount
   * @param actualAmount  the value for t_product_arrival_detail.actual_amount
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public void setActualAmount(BigDecimal actualAmount) {
    this.actualAmount = actualAmount;
    if (actualAmount == null) {
      this.actualAmount = BigDecimal.ZERO;
    }
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_product_arrival_detail.arrival_amount
   * @return  the value of t_product_arrival_detail.arrival_amount
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public BigDecimal getArrivalAmount() {
    return arrivalAmount;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_product_arrival_detail.arrival_amount
   * @param arrivalAmount  the value for t_product_arrival_detail.arrival_amount
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public void setArrivalAmount(BigDecimal arrivalAmount) {
    this.arrivalAmount = arrivalAmount;
    if (arrivalAmount == null) {
      this.arrivalAmount = BigDecimal.ZERO;
    }
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_product_arrival_detail.product_unit
   * @return  the value of t_product_arrival_detail.product_unit
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public String getProductUnit() {
    return productUnit;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_product_arrival_detail.product_unit
   * @param productUnit  the value for t_product_arrival_detail.product_unit
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public void setProductUnit(String productUnit) {
    this.productUnit = productUnit;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_product_arrival_detail.price
   * @return  the value of t_product_arrival_detail.price
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public BigDecimal getPrice() {
    return price;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_product_arrival_detail.price
   * @param price  the value for t_product_arrival_detail.price
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public void setPrice(BigDecimal price) {
    this.price = price;
    if (price == null) {
      this.price = BigDecimal.ZERO;
    }
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_product_arrival_detail.product_money
   * @return  the value of t_product_arrival_detail.product_money
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public BigDecimal getProductMoney() {
    return productMoney;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_product_arrival_detail.product_money
   * @param productMoney  the value for t_product_arrival_detail.product_money
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public void setProductMoney(BigDecimal productMoney) {
    this.productMoney = productMoney;
    if (productMoney == null) {
      this.productMoney = BigDecimal.ZERO;
    }
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_product_arrival_detail.memo
   * @return  the value of t_product_arrival_detail.memo
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public String getMemo() {
    return memo;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_product_arrival_detail.memo
   * @param memo  the value for t_product_arrival_detail.memo
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public void setMemo(String memo) {
    this.memo = memo;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_product_arrival_detail.product_code
   * @return  the value of t_product_arrival_detail.product_code
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public String getProductCode() {
    return productCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_product_arrival_detail.product_code
   * @param productCode  the value for t_product_arrival_detail.product_code
   * @ibatorgenerated  Tue Nov 10 17:49:41 CST 2009
   */
  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }

  public List<TProductArrivalDetail> getChildren() {
    return children;
  }

  public void setChildren(List<TProductArrivalDetail> children) {
    this.children = children;
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

  public String getProductBrand() {
    return productBrand;
  }

  public void setProductBrand(String productBrand) {
    this.productBrand = productBrand;
  }

  public String getProSortName() {
    return proSortName;
  }

  public void setProSortName(String proSortName) {
    this.proSortName = proSortName;
  }

  public String getContractProjectSortId() {
    return contractProjectSortId;
  }

  public void setContractProjectSortId(String contractProjectSortId) {
    this.contractProjectSortId = contractProjectSortId;
  }

  public String getProductPosition() {
    return productPosition;
  }

  public void setProductPosition(String productPosition) {
    this.productPosition = productPosition;
  }

  public String getReserveCode() {
    return reserveCode;
  }

  public void setReserveCode(String reserveCode) {
    this.reserveCode = reserveCode;
  }

  public String getToolsTypeName() {
    return toolsTypeName;
  }

  public void setToolsTypeName(String toolsTypeName) {
    this.toolsTypeName = toolsTypeName;
  }

}