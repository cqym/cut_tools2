package com.tl.resource.dao.pojo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TOutStockInfor {
  private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_out_stock_infor.id
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  private String id;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_out_stock_infor.contract_code
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  private String contractCode;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_out_stock_infor.contract_id
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  private String contractId;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_out_stock_infor.memo
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  private String memo;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_out_stock_infor.out_stock_type
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  private Integer outStockType;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_out_stock_infor.status
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  private Integer status;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_out_stock_infor.edit_date
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  private Date editDate;

  private String editDateString;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_out_stock_infor.user_id
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  private String userId;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_out_stock_infor.user_name
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  private String userName;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_out_stock_infor.out_stock_code
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  private String outStockCode;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_out_stock_infor.quotation_code
   * @ibatorgenerated  Thu Dec 10 11:19:03 CST 2009
   */
  private String quotationCode;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column t_out_stock_infor.quotation_id
   * @ibatorgenerated  Thu Dec 10 11:19:03 CST 2009
   */
  private String quotationId;

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_out_stock_infor.id
   * @return  the value of t_out_stock_infor.id
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  private String customerCode;

  private String customerName;

  private String outStockDate;

  private Date confirmDate;

  private String confirmUserName;

  public String getId() {
    return id;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_out_stock_infor.id
   * @param id  the value for t_out_stock_infor.id
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_out_stock_infor.contract_code
   * @return  the value of t_out_stock_infor.contract_code
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  public String getContractCode() {
    return contractCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_out_stock_infor.contract_code
   * @param contractCode  the value for t_out_stock_infor.contract_code
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  public void setContractCode(String contractCode) {
    this.contractCode = contractCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_out_stock_infor.contract_id
   * @return  the value of t_out_stock_infor.contract_id
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  public String getContractId() {
    return contractId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_out_stock_infor.contract_id
   * @param contractId  the value for t_out_stock_infor.contract_id
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  public void setContractId(String contractId) {
    this.contractId = contractId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_out_stock_infor.memo
   * @return  the value of t_out_stock_infor.memo
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  public String getMemo() {
    return memo;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_out_stock_infor.memo
   * @param memo  the value for t_out_stock_infor.memo
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  public void setMemo(String memo) {
    this.memo = memo;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_out_stock_infor.out_stock_type
   * @return  the value of t_out_stock_infor.out_stock_type
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  public Integer getOutStockType() {
    return outStockType;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_out_stock_infor.out_stock_type
   * @param outStockType  the value for t_out_stock_infor.out_stock_type
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  public void setOutStockType(Integer outStockType) {
    this.outStockType = outStockType;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_out_stock_infor.status
   * @return  the value of t_out_stock_infor.status
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  public Integer getStatus() {
    return status;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_out_stock_infor.status
   * @param status  the value for t_out_stock_infor.status
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  public void setStatus(Integer status) {
    this.status = status;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_out_stock_infor.edit_date
   * @return  the value of t_out_stock_infor.edit_date
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  public Date getEditDate() {
    if (editDate != null) {
      editDateString = df.format(editDate);
    }
    return editDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_out_stock_infor.edit_date
   * @param editDate  the value for t_out_stock_infor.edit_date
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  public void setEditDate(Date editDate) {
    this.editDate = editDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_out_stock_infor.user_id
   * @return  the value of t_out_stock_infor.user_id
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  public String getUserId() {
    return userId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_out_stock_infor.user_id
   * @param userId  the value for t_out_stock_infor.user_id
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  public void setUserId(String userId) {
    this.userId = userId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_out_stock_infor.user_name
   * @return  the value of t_out_stock_infor.user_name
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  public String getUserName() {
    return userName;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_out_stock_infor.user_name
   * @param userName  the value for t_out_stock_infor.user_name
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the database column t_out_stock_infor.out_stock_code
   * @return  the value of t_out_stock_infor.out_stock_code
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  public String getOutStockCode() {
    return outStockCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database column t_out_stock_infor.out_stock_code
   * @param outStockCode  the value for t_out_stock_infor.out_stock_code
   * @ibatorgenerated  Tue Nov 17 17:33:14 CST 2009
   */
  public void setOutStockCode(String outStockCode) {
    this.outStockCode = outStockCode;
  }

  public String getEditDateString() {
    return editDateString;
  }

  public void setEditDateString(String editDateString) {
    this.editDateString = editDateString;
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

  public String getOutStockDate() {
    return outStockDate;
  }

  public void setOutStockDate(String outStockDate) {
    this.outStockDate = outStockDate;
  }

  public Date getConfirmDate() {
    return confirmDate;
  }

  public void setConfirmDate(Date confirmDate) {
    this.confirmDate = confirmDate;
  }

  public String getConfirmUserName() {
    return confirmUserName;
  }

  public void setConfirmUserName(String confirmUserName) {
    this.confirmUserName = confirmUserName;
  }

}