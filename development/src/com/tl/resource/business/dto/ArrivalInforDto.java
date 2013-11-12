package com.tl.resource.business.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ArrivalInforDto {
  private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

  public static final Integer ARRIVAL_TYPE_CONTRACT = 1;//合同订单对应的入库

  public static final Integer ARRIVAL_TYPE_CONTRACT_SELF = 3;//合同加工订单对应的入库

  public static final Integer ARRIVAL_TYPE_SCHEDLE = 5;//预订入库，包括预订订单

  public static final Integer ARRIVAL_TYPE_SCHEDLE_SELF = 7;//预订入库，包括预订加工对应的入库

  public static final Integer ARRIVAL_TYPE_TRY = 6;//试刀入库，包括试刀订单对应的入库

  public static final Integer ARRIVAL_TYPE_TRY_SELF = 8;//试刀入库，包括试刀加工对应的入库

  public static final Integer ARRIVAL_TYPE_RESERVE = 2;//储备入库 储备订单对应的入库

  public static final Integer ARRIVAL_TYPE_MAT_RESERVE = 4;//材料储备入库 材料储备订单对应的入库

  private String editDateString;

  private String id;

  private String arrivalCode;

  private String orderCode;

  private Integer status;

  private String contractCode;

  private Date editDate;

  private String userId;

  private String userName;

  private String supplierName;

  private String supplierId;

  private String deliveryDate;

  private String memo;

  private String orderInforId;

  private Integer orderType;//订单类型

  private String orderTypeStr;//订单类型

  private String customerName;

  private String customerCode;

  private String quotationId;

  private String quotationCode;

  private Integer arrivalType;

  private String confirmUserName;

  private Date confirmDate;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getArrivalCode() {
    return arrivalCode;
  }

  public void setArrivalCode(String arrivalCode) {
    this.arrivalCode = arrivalCode;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getContractCode() {
    return contractCode;
  }

  public void setContractCode(String contractCode) {
    this.contractCode = contractCode;
  }

  public Date getEditDate() {
    return editDate;
  }

  public void setEditDate(Date editDate) {
    this.editDate = editDate;

    if (editDate != null) {
      this.editDateString = df.format(editDate);
    }
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

  public String getSupplierName() {
    return supplierName;
  }

  public void setSupplierName(String supplierName) {
    this.supplierName = supplierName;
  }

  public String getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(String supplierId) {
    this.supplierId = supplierId;
  }

  public String getDeliveryDate() {
    return deliveryDate;
  }

  public void setDeliveryDate(String deliveryDate) {
    this.deliveryDate = deliveryDate;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public String getOrderInforId() {
    return orderInforId;
  }

  public void setOrderInforId(String orderInforId) {
    this.orderInforId = orderInforId;
  }

  public String getEditDateString() {
    return editDateString;
  }

  public void setEditDateString(String editDateString) {
    this.editDateString = editDateString;
  }

  public Integer getOrderType() {
    return orderType;
  }

  private String changeOrderType(int value) {
    switch (value) {
    case 1:
      return "合同入库";
    case 3:
      return "合同入库";
    case 2:
      return "储备入库";
    case 4:
      return "储备入库";
    case 5:
      return "预订入库";
    case 6:
      return "试刀入库";
    case 0:
      return "直接入库";
    }
    return "";
  }

  public void setOrderType(Integer orderType) {
    this.orderType = orderType;

    this.setOrderTypeStr(this.changeOrderType(orderType));
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getCustomerCode() {
    return customerCode;
  }

  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  public String getOrderTypeStr() {
    return orderTypeStr;
  }

  public void setOrderTypeStr(String orderTypeStr) {
    this.orderTypeStr = orderTypeStr;
  }

  public String getQuotationId() {
    return quotationId;
  }

  public void setQuotationId(String quotationId) {
    this.quotationId = quotationId;
  }

  public String getQuotationCode() {
    return quotationCode;
  }

  public void setQuotationCode(String quotationCode) {
    this.quotationCode = quotationCode;
  }

  public Integer getArrivalType() {
    return arrivalType;
  }

  public void setArrivalType(Integer arrivalType) {
    this.arrivalType = arrivalType;
  }

  public String getConfirmUserName() {
    return confirmUserName;
  }

  public void setConfirmUserName(String confirmUserName) {
    this.confirmUserName = confirmUserName;
  }

  public Date getConfirmDate() {
    return confirmDate;
  }

  public void setConfirmDate(Date confirmDate) {
    this.confirmDate = confirmDate;
  }

}