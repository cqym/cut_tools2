package com.tl.resource.audit.dto;

public class TAuditFlowDetailDto {
	private String id;

    
    private String flowInforId;

    
    private Integer auditOrder;

    private Integer auditDegree;

    
    private String auditPersonId;

   
    private String auditPersonName;

    
    private String roleId;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getFlowInforId() {
		return flowInforId;
	}


	public void setFlowInforId(String flowInforId) {
		this.flowInforId = flowInforId;
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


	public String getAuditPersonId() {
		return auditPersonId;
	}


	public void setAuditPersonId(String auditPersonId) {
		this.auditPersonId = auditPersonId;
	}


	public String getAuditPersonName() {
		return auditPersonName;
	}


	public void setAuditPersonName(String auditPersonName) {
		this.auditPersonName = auditPersonName;
	}


	public String getRoleId() {
		return roleId;
	}


	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
    
}
