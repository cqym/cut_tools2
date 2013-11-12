package com.tl.resource.audit.dto;

public class TAuditTypeDto {
    private String id;
    private String auditTypeName;
    private String className;

    private Integer auditRecordCount;
    private Integer serialNumber;
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getAuditTypeName() {
		return auditTypeName;
	}


	public void setAuditTypeName(String auditTypeName) {
		this.auditTypeName = auditTypeName;
	}


	public String getClassName() {
		return className;
	}


	public void setClassName(String className) {
		this.className = className;
	}


	public Integer getAuditRecordCount() {
		return auditRecordCount;
	}


	public void setAuditRecordCount(Integer auditRecordCount) {
		this.auditRecordCount = auditRecordCount;
	}


	public Integer getSerialNumber() {
		return serialNumber;
	}


	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}
    
}
