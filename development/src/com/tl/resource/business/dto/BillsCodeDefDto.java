package com.tl.resource.business.dto;

public class BillsCodeDefDto {
	private String id;

    
    private String billType;

    
    private String billTypeName;

    
    private String billDefNormal;

   
    private Integer serialNumber;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getBillType() {
		return billType;
	}


	public void setBillType(String billType) {
		this.billType = billType;
	}


	public String getBillTypeName() {
		return billTypeName;
	}


	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}


	public String getBillDefNormal() {
		return billDefNormal;
	}


	public void setBillDefNormal(String billDefNormal) {
		this.billDefNormal = billDefNormal;
	}


	public Integer getSerialNumber() {
		return serialNumber;
	}


	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}
    
    
}
