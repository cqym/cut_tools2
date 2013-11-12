package com.tl.resource.business.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReservePlanMainInforDto {
  private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");

  private String planCode;

  private String id;

  private String contractCode;

  private String contractId;

  private String orderCode;

  private String orderId;

  private Date editTime;

  private String editDateString;

  private String userName;

  private String userId;

  private String memo;

  private Integer urgentLevel;

  private Integer status;

  private Integer planType;

  private List<OrderDetialsDto> orderDetailList;

  private List<ReservePlanDetailDto> reservePlanDetail;

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

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public Date getEditTime() {
    return editTime;
  }

  public void setEditTime(Date editTime) {
    this.editTime = editTime;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public List<ReservePlanDetailDto> getReservePlanDetail() {
    return reservePlanDetail;
  }

  public void setReservePlanDetail(List<ReservePlanDetailDto> reservePlanDetail) {
    this.reservePlanDetail = reservePlanDetail;
  }

  public String getEditDateString() {
    if (this.editTime != null) {
      return df.format(this.editTime);
    }
    return editDateString;
  }

  public void setEditDateString(String editDateString) {
    this.editDateString = editDateString;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public List<OrderDetialsDto> getOrderDetailList() {
    return orderDetailList;
  }

  public void setOrderDetailList(List<OrderDetialsDto> orderDetailList) {
    this.orderDetailList = orderDetailList;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public Integer getUrgentLevel() {
    return urgentLevel;
  }

  public void setUrgentLevel(Integer urgentLevel) {
    this.urgentLevel = urgentLevel;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPlanCode() {
    return planCode;
  }

  public void setPlanCode(String planCode) {
    this.planCode = planCode;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getPlanType() {
    return planType;
  }

  public void setPlanType(Integer planType) {
    this.planType = planType;
  }

}
