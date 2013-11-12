package com.tl.resource.business.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ContractViewPanelDto {
	private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static final BigDecimal hundred = new BigDecimal("100");
	private String id;
	private String contractCode;
	private String sellerName;
	private String currencyName;
	private String customerCode;
	private String customerName;
	
	private String urgentLevel;
	private String signDate;
	
	private Integer status;
	private BigDecimal finalMoney;
	private BigDecimal money;
	private Date editDate;
	private String editDateString;
	private String userId;
	private String userName;
	private String ownContactPerson;
	private BigDecimal contractAmount;
	private BigDecimal deliveryAmount;
	private BigDecimal orderAmount;
	private BigDecimal outAmount;
	private BigDecimal tqOutAmount;
	private BigDecimal invoiceMoney;
	
	private BigDecimal orderPercent;
	private BigDecimal orderArrivalPercent;
	private BigDecimal allArrivalPercent;
	private BigDecimal deliveryPercent;
	private BigDecimal contractAccountPercent;
	private BigDecimal invoiceMoneyPercent;
	private Integer fileCount;
	private String memo;
	private BigDecimal taxRate;
	private String invoiceProgressStyle = "panel_progress_plugin";
	private String invoiceStyle = "panel_boder_plugin";
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public BigDecimal getFinalMoney() {
		return finalMoney;
	}
	public void setFinalMoney(BigDecimal finalMoney) {
		this.finalMoney = finalMoney;
	}
	public Date getEditDate() {
		return editDate;
	}
	public void setEditDate(Date editDate) {
		if(editDate != null){
		    editDateString = df.format(editDate);
		}
		this.editDate = editDate;
	}
	public String getEditDateString() {
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public BigDecimal getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}
	public BigDecimal getDeliveryAmount() {
		return deliveryAmount;
	}
	public void setDeliveryAmount(BigDecimal deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	public BigDecimal getOutAmount() {
		return outAmount;
	}
	public void setOutAmount(BigDecimal outAmount) {
		this.outAmount = outAmount;
	}
	public BigDecimal getTqOutAmount() {
		return tqOutAmount;
	}
	public void setTqOutAmount(BigDecimal tqOutAmount) {
		this.tqOutAmount = tqOutAmount;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public String getOwnContactPerson() {
		return ownContactPerson;
	}
	public void setOwnContactPerson(String ownContactPerson) {
		this.ownContactPerson = ownContactPerson;
	}
	public BigDecimal getOrderPercent() {
		BigDecimal d = contractAmount.subtract(tqOutAmount);
		d = (d == null || d.compareTo(BigDecimal.ZERO) == 0) ? BigDecimal.ONE : d;
		orderPercent = orderAmount.divide(d,RoundingMode.CEILING).multiply(hundred);
		return orderPercent;
	}
	public void setOrderPercent(BigDecimal orderPercent) {
		this.orderPercent = orderPercent;
	}
	public BigDecimal getOrderArrivalPercent() {
		BigDecimal d = orderAmount;
		d = (d == null || d.compareTo(BigDecimal.ZERO) == 0) ? BigDecimal.ONE : d;
		orderArrivalPercent = outAmount.subtract(tqOutAmount).divide(d,RoundingMode.CEILING).multiply(hundred);
		return orderArrivalPercent;
	}
	public void setOrderArrivalPercent(BigDecimal orderArrivalPercent) {
		this.orderArrivalPercent = orderArrivalPercent;
	}
	public BigDecimal getAllArrivalPercent() {
		BigDecimal d = contractAmount;
		d = (d == null || d.compareTo(BigDecimal.ZERO) == 0) ? BigDecimal.ONE : d;
		allArrivalPercent = outAmount.divide(d,RoundingMode.CEILING).multiply(hundred);
		return allArrivalPercent;
	}
	public void setAllArrivalPercent(BigDecimal allArrivalPercent) {
		this.allArrivalPercent = allArrivalPercent;
	}
	public BigDecimal getDeliveryPercent() {
		BigDecimal d = contractAmount;
		d = (d == null || d.compareTo(BigDecimal.ZERO) == 0) ? BigDecimal.ONE : d;
		deliveryPercent = deliveryAmount.divide(d,RoundingMode.CEILING).multiply(hundred);
		return deliveryPercent;
	}
	public void setDeliveryPercent(BigDecimal deliveryPercent) {
		this.deliveryPercent = deliveryPercent;
	}
	public BigDecimal getContractAccountPercent() {
		BigDecimal d = finalMoney;
		d = (d == null || d.compareTo(BigDecimal.ZERO) == 0) ? BigDecimal.ONE : d;
		contractAccountPercent = money.divide(d,RoundingMode.CEILING).multiply(hundred);
		return contractAccountPercent;
	}
	public void setContractAccountPercent(BigDecimal contractAccountPercent) {
		this.contractAccountPercent = contractAccountPercent;
	}
	public BigDecimal getInvoiceMoney() {
		return invoiceMoney;
	}
	public void setInvoiceMoney(BigDecimal invoiceMoney) {
		this.invoiceMoney = invoiceMoney;
	}
	public BigDecimal getInvoiceMoneyPercent() {
		if(taxRate == null || taxRate.doubleValue() == 0){
			return new BigDecimal("100");
		}
		BigDecimal d = finalMoney;
		d = (d == null || d.compareTo(BigDecimal.ZERO) == 0) ? BigDecimal.ONE : d;
		invoiceMoneyPercent = invoiceMoney.divide(d,RoundingMode.CEILING).multiply(hundred);
		return invoiceMoneyPercent;
	}
	public void setInvoiceMoneyPercent(BigDecimal invoiceMoneyPercent) {
		this.invoiceMoneyPercent = invoiceMoneyPercent;
	}
	public Integer getFileCount() {
		return fileCount;
	}
	public void setFileCount(Integer fileCount) {
		this.fileCount = fileCount;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public BigDecimal getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(BigDecimal taxRate) {
		invoiceProgressStyle = (taxRate == null || taxRate.doubleValue() == 0 ? "" : invoiceProgressStyle);
		invoiceStyle = (taxRate == null || taxRate.doubleValue() == 0 ? "panel_boder_plugin_noborder" : invoiceStyle);
		this.taxRate = taxRate;
	}
	public String getInvoiceProgressStyle() {
		return invoiceProgressStyle;
	}
	public void setInvoiceProgressStyle(String invoiceProgressStyle) {
		this.invoiceProgressStyle = invoiceProgressStyle;
	}
	public String getInvoiceStyle() {
		return invoiceStyle;
	}
	public void setInvoiceStyle(String invoiceStyle) {
		this.invoiceStyle = invoiceStyle;
	}
	public String getUrgentLevel() {
		return urgentLevel;
	}
	public void setUrgentLevel(String urgentLevel) {
		this.urgentLevel = urgentLevel;
	}
	public String getSignDate() {
		return signDate;
	}
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	
	
}
