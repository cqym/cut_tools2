package com.tl.resource.business.dto;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ContractAccountsInforDto {
	private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private String id;

    private String contractInforId;

    private String contractCode;

    private String customerCode;

    private String customerName;

    private String memo;

    private Date editDate;
    
    private String editDateStr;

    private String userId;

    private String userName;
    private Integer contractType;
    private BigDecimal money = BigDecimal.ZERO;
    
    private BigDecimal contractMoney = BigDecimal.ZERO;

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
		this.editDate = editDate;
		if(editDate != null){
			this.editDateStr = df.format(editDate);
		}
	}
	
	public String getEditDateStr() {
		return editDateStr;
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

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
		if(this.money==null){this.money = BigDecimal.ZERO; }
			
	}

	public BigDecimal getContractMoney() {
		return contractMoney;
	}

	public void setContractMoney(BigDecimal contractMoney) {
		this.contractMoney = contractMoney;
		if(this.money==null){this.money = BigDecimal.ZERO; }
	}

	public Integer getContractType() {
		return contractType;
	}

	public void setContractType(Integer contractType) {
		this.contractType = contractType;
	}

	public void setEditDateStr(String editDateStr) {
		this.editDateStr = editDateStr;
	}
	
	

}
