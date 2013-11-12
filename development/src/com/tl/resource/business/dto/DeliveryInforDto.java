package com.tl.resource.business.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DeliveryInforDto {
  private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

  public static final String DELIVERY_TYPE_CONTRACT = "0";

  public static final String DELIVERY_TYPE_SCHEDULE = "1";

  public static final String DELIVERY_TYPE_TRY = "2";

  private String id;

  private String contractInforId;

  private String deliveryCode;

  private String contractCode;

  private String customerCode;

  private String customerName;

  private String customerCodeName;

  private String deliveryDate;

  private String contactPerson;

  private Integer status;

  private String acceptPerson;

  private String acceptDate;

  private String memo;

  private Date editDate;

  private String editDateString;

  private String userId;

  private String userName;

  private Integer deliveryType;

  private String quotationCode;

  private String quotationId;

  private String cusContactPerson;

  private String customerPhone;

  private String customerFax;

  private String deliveryAddressType;

  private String trafficMode;

  private List<DeliveryProductDetailDto> deliveryProductDetailDto;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getContractInforId() {
    return contractInforId;
  }

  public void setContractInforId(String contractInforId) {
    this.contractInforId = contractInforId;
  }

  public String getDeliveryCode() {
    return deliveryCode;
  }

  public void setDeliveryCode(String deliveryCode) {
    this.deliveryCode = deliveryCode;
  }

  public String getContractCode() {
    return contractCode;
  }

  public void setContractCode(String contractCode) {
    this.contractCode = contractCode;
  }

  public String getCustomerCode() {
    return customerCode;
  }

  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getDeliveryDate() {
    return deliveryDate;
  }

  public void setDeliveryDate(String deliveryDate) {
    this.deliveryDate = deliveryDate;
  }

  public String getContactPerson() {
    return contactPerson;
  }

  public void setContactPerson(String contactPerson) {
    this.contactPerson = contactPerson;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getAcceptPerson() {
    return acceptPerson;
  }

  public void setAcceptPerson(String acceptPerson) {
    this.acceptPerson = acceptPerson;
  }

  public String getAcceptDate() {
    return acceptDate;
  }

  public void setAcceptDate(String acceptDate) {
    this.acceptDate = acceptDate;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public Date getEditDate() {
    return editDate;
  }

  public void setEditDate(Date editDate) {
    if (editDate != null) {
      editDateString = df.format(editDate);
    }
    this.editDate = editDate;
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

  public List<DeliveryProductDetailDto> getDeliveryProductDetailDto() {
    return deliveryProductDetailDto;
  }

  public void setDeliveryProductDetailDto(List<DeliveryProductDetailDto> deliveryProductDetailDto) {
    this.deliveryProductDetailDto = deliveryProductDetailDto;
  }

  public String getEditDateString() {
    return editDateString;
  }

  public void setEditDateString(String editDateString) {
    this.editDateString = editDateString;
  }

  public Integer getDeliveryType() {
    return deliveryType;
  }

  public void setDeliveryType(Integer deliveryType) {
    this.deliveryType = deliveryType;
  }

  public String getQuotationCode() {
    return quotationCode;
  }

  public void setQuotationCode(String quotationCode) {
    this.quotationCode = quotationCode;
  }

  public String getQuotationId() {
    return quotationId;
  }

  public void setQuotationId(String quotationId) {
    this.quotationId = quotationId;
  }

  public String getCusContactPerson() {
    return cusContactPerson;
  }

  public void setCusContactPerson(String cusContactPerson) {
    this.cusContactPerson = cusContactPerson;
  }

  public String getCustomerPhone() {
    return customerPhone;
  }

  public void setCustomerPhone(String customerPhone) {
    this.customerPhone = customerPhone;
  }

  public String getCustomerFax() {
    return customerFax;
  }

  public void setCustomerFax(String customerFax) {
    this.customerFax = customerFax;
  }

  public String getCustomerCodeName() {
    String name = this.customerName == null ? "" : this.customerName;
    String code = this.customerCode == null ? "" : this.customerCode;
    return code + "-" + name;
  }

  public void setCustomerCodeName(String customerCodeName) {
    this.customerCodeName = customerCodeName;
  }

  public String getDeliveryAddressType() {
    return deliveryAddressType;
  }

  public void setDeliveryAddressType(String deliveryAddressType) {
    this.deliveryAddressType = deliveryAddressType;
  }

  public String getTrafficMode() {
    return trafficMode;
  }

  public void setTrafficMode(String trafficMode) {
    this.trafficMode = trafficMode;
  }

}
