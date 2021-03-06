package com.tl.resource.audit.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TAuditHistoryDto {
	private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	private String id;
	
	private String batchId;
	
	private String businessId;
	
	private String auditPerson;
	
	private String auditPersonId;
	
	private Integer auditOrder;
	
	private Integer auditDegree;
	
	private String roleId;
	
	private Integer auditOpType;
	
	private String auditComment;
	
	private String auditTypeId;
	
	private Date auditTime;
	private String auditTimeString;
	
	private String auditContent;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getAuditPerson() {
		return auditPerson;
	}

	public void setAuditPerson(String auditPerson) {
		this.auditPerson = auditPerson;
	}

	public String getAuditPersonId() {
		return auditPersonId;
	}

	public void setAuditPersonId(String auditPersonId) {
		this.auditPersonId = auditPersonId;
	}

	public Integer getAuditOrder() {
		return auditOrder;
	}

	public void setAuditOrder(Integer auditOrder) {
		this.auditOrder = auditOrder;
	}

	public Integer getAuditDegree() {
		return auditDegree;
	}

	public void setAuditDegree(Integer auditDegree) {
		this.auditDegree = auditDegree;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public Integer getAuditOpType() {
		return auditOpType;
	}

	public void setAuditOpType(Integer auditOpType) {
		this.auditOpType = auditOpType;
	}

	public String getAuditComment() {
		return auditComment;
	}

	public void setAuditComment(String auditComment) {
		this.auditComment = auditComment;
	}

	public String getAuditTypeId() {
		return auditTypeId;
	}

	public void setAuditTypeId(String auditTypeId) {
		this.auditTypeId = auditTypeId;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
		if(auditTime != null){
		    auditTimeString = df.format(auditTime);
		}
	}

	public String getAuditTimeString() {
		return auditTimeString;
	}

	public void setAuditTimeString(String auditTimeString) {
		this.auditTimeString = auditTimeString;
	}

	public String getAuditContent() {
		return auditContent;
	}

	public void setAuditContent(String auditContent) {
		this.auditContent = auditContent;
	}
	
}
