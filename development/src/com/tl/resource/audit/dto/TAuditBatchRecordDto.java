package com.tl.resource.audit.dto;

public class TAuditBatchRecordDto {
	private String id;

   
    private Integer batchNumber;

    
    private String businessId;

    
    private String auditType;

    
    private Integer auditEnd;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Integer getBatchNumber() {
		return batchNumber;
	}


	public void setBatchNumber(Integer batchNumber) {
		this.batchNumber = batchNumber;
	}


	public String getBusinessId() {
		return businessId;
	}


	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}


	public String getAuditType() {
		return auditType;
	}


	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}


	public Integer getAuditEnd() {
		return auditEnd;
	}


	public void setAuditEnd(Integer auditEnd) {
		this.auditEnd = auditEnd;
	}
    
}
