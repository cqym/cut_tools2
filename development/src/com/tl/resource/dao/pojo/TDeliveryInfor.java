package com.tl.resource.dao.pojo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TDeliveryInfor {
  private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");

  private String editDateString;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_delivery_infor.id
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  private String id;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_delivery_infor.contract_infor_id
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  private String contractInforId;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_delivery_infor.delivery_code
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  private String deliveryCode;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_delivery_infor.contract_code
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  private String contractCode;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_delivery_infor.customer_code
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  private String customerCode;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_delivery_infor.customer_name
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  private String customerName;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_delivery_infor.delivery_date
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  private String deliveryDate;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_delivery_infor.contact_person
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  private String contactPerson;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_delivery_infor.status
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  private Integer status;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_delivery_infor.accept_person
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  private String acceptPerson;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_delivery_infor.accept_date
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  private String acceptDate;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_delivery_infor.memo
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  private String memo;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_delivery_infor.edit_date
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  private Date editDate;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_delivery_infor.user_id
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  private String userId;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_delivery_infor.user_name
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  private String userName;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_delivery_infor.delivery_type
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  private Integer deliveryType;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_delivery_infor.quotation_code
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  private String quotationCode;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_delivery_infor.quotation_id
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  private String quotationId;

  private String cusContactPerson;

  private String customerPhone;

  private String customerFax;

  private Integer fileCount;

  private Date updateDate;

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_delivery_infor.id
   * @return  the value of t_delivery_infor.id
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public String getId() {
    return id;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_delivery_infor.id
   * @param id  the value for t_delivery_infor.id
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_delivery_infor.contract_infor_id
   * @return  the value of t_delivery_infor.contract_infor_id
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public String getContractInforId() {
    return contractInforId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_delivery_infor.contract_infor_id
   * @param contractInforId  the value for t_delivery_infor.contract_infor_id
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public void setContractInforId(String contractInforId) {
    this.contractInforId = contractInforId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_delivery_infor.delivery_code
   * @return  the value of t_delivery_infor.delivery_code
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public String getDeliveryCode() {
    return deliveryCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_delivery_infor.delivery_code
   * @param deliveryCode  the value for t_delivery_infor.delivery_code
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public void setDeliveryCode(String deliveryCode) {
    this.deliveryCode = deliveryCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_delivery_infor.contract_code
   * @return  the value of t_delivery_infor.contract_code
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public String getContractCode() {
    return contractCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_delivery_infor.contract_code
   * @param contractCode  the value for t_delivery_infor.contract_code
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public void setContractCode(String contractCode) {
    this.contractCode = contractCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_delivery_infor.customer_code
   * @return  the value of t_delivery_infor.customer_code
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public String getCustomerCode() {
    return customerCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_delivery_infor.customer_code
   * @param customerCode  the value for t_delivery_infor.customer_code
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_delivery_infor.customer_name
   * @return  the value of t_delivery_infor.customer_name
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public String getCustomerName() {
    return customerName;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_delivery_infor.customer_name
   * @param customerName  the value for t_delivery_infor.customer_name
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_delivery_infor.delivery_date
   * @return  the value of t_delivery_infor.delivery_date
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public String getDeliveryDate() {
    return deliveryDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_delivery_infor.delivery_date
   * @param deliveryDate  the value for t_delivery_infor.delivery_date
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public void setDeliveryDate(String deliveryDate) {
    this.deliveryDate = deliveryDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_delivery_infor.contact_person
   * @return  the value of t_delivery_infor.contact_person
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public String getContactPerson() {
    return contactPerson;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_delivery_infor.contact_person
   * @param contactPerson  the value for t_delivery_infor.contact_person
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public void setContactPerson(String contactPerson) {
    this.contactPerson = contactPerson;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_delivery_infor.status
   * @return  the value of t_delivery_infor.status
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public Integer getStatus() {
    return status;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_delivery_infor.status
   * @param status  the value for t_delivery_infor.status
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public void setStatus(Integer status) {
    this.status = status;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_delivery_infor.accept_person
   * @return  the value of t_delivery_infor.accept_person
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public String getAcceptPerson() {
    return acceptPerson;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_delivery_infor.accept_person
   * @param acceptPerson  the value for t_delivery_infor.accept_person
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public void setAcceptPerson(String acceptPerson) {
    this.acceptPerson = acceptPerson;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_delivery_infor.accept_date
   * @return  the value of t_delivery_infor.accept_date
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public String getAcceptDate() {
    return acceptDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_delivery_infor.accept_date
   * @param acceptDate  the value for t_delivery_infor.accept_date
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public void setAcceptDate(String acceptDate) {
    this.acceptDate = acceptDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_delivery_infor.memo
   * @return  the value of t_delivery_infor.memo
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public String getMemo() {
    return memo;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_delivery_infor.memo
   * @param memo  the value for t_delivery_infor.memo
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public void setMemo(String memo) {
    this.memo = memo;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_delivery_infor.edit_date
   * @return  the value of t_delivery_infor.edit_date
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public Date getEditDate() {
    return editDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_delivery_infor.edit_date
   * @param editDate  the value for t_delivery_infor.edit_date
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public void setEditDate(Date editDate) {
    this.editDate = editDate;
    if (editDate != null) {
      editDateString = df.format(editDate);
    }
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_delivery_infor.user_id
   * @return  the value of t_delivery_infor.user_id
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public String getUserId() {
    return userId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_delivery_infor.user_id
   * @param userId  the value for t_delivery_infor.user_id
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public void setUserId(String userId) {
    this.userId = userId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_delivery_infor.user_name
   * @return  the value of t_delivery_infor.user_name
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public String getUserName() {
    return userName;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_delivery_infor.user_name
   * @param userName  the value for t_delivery_infor.user_name
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_delivery_infor.delivery_type
   * @return  the value of t_delivery_infor.delivery_type
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public Integer getDeliveryType() {
    return deliveryType;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_delivery_infor.delivery_type
   * @param deliveryType  the value for t_delivery_infor.delivery_type
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public void setDeliveryType(Integer deliveryType) {
    this.deliveryType = deliveryType;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_delivery_infor.quotation_code
   * @return  the value of t_delivery_infor.quotation_code
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public String getQuotationCode() {
    return quotationCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_delivery_infor.quotation_code
   * @param quotationCode  the value for t_delivery_infor.quotation_code
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public void setQuotationCode(String quotationCode) {
    this.quotationCode = quotationCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_delivery_infor.quotation_id
   * @return  the value of t_delivery_infor.quotation_id
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public String getQuotationId() {
    return quotationId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_delivery_infor.quotation_id
   * @param quotationId  the value for t_delivery_infor.quotation_id
   * @ibatorgenerated  Thu Dec 10 14:55:15 CST 2009
   */
  public void setQuotationId(String quotationId) {
    this.quotationId = quotationId;
  }

  public String getEditDateString() {
    return editDateString;
  }

  public void setEditDateString(String editDateString) {
    this.editDateString = editDateString;
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

  public Integer getFileCount() {
    return fileCount;
  }

  public void setFileCount(Integer fileCount) {
    this.fileCount = fileCount;
  }

}