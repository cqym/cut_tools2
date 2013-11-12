package com.tl.resource.audit.dto;

import java.util.Date;

public class TAuditFlowInforDto {
	private String id;

    
    private String auditTypeId;

    
    private String departId;
    
    private String departName;
    private String defineTimeStr;
    
    private String flowCode;

    
    private Integer enable;

   
    private String flowName;

    
    private Date defineTime;

    
    private String definePerson;

    
    private String definePersonId;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getAuditTypeId() {
		return auditTypeId;
	}


	public void setAuditTypeId(String auditTypeId) {
		this.auditTypeId = auditTypeId;
	}


	public String getDepartId() {
		return departId;
	}


	public void setDepartId(String departId) {
		this.departId = departId;
	}


	public String getFlowCode() {
		return flowCode;
	}


	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}


	public Integer getEnable() {
		return enable;
	}


	public void setEnable(Integer enable) {
		this.enable = enable;
	}


	public String getFlowName() {
		return flowName;
	}


	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}


	public Date getDefineTime() {
		return defineTime;
	}


	public void setDefineTime(Date defineTime) {
		this.defineTime = defineTime;
	}


	public String getDefinePerson() {
		return definePerson;
	}


	public void setDefinePerson(String definePerson) {
		this.definePerson = definePerson;
	}


	public String getDefinePersonId() {
		return definePersonId;
	}


	public void setDefinePersonId(String definePersonId) {
		this.definePersonId = definePersonId;
	}


	public String getDepartName() {
		return departName;
	}


	public void setDepartName(String departName) {
		this.departName = departName;
	}


	public String getDefineTimeStr() {
		return defineTimeStr;
	}


	public void setDefineTimeStr(String defineTimeStr) {
		this.defineTimeStr = defineTimeStr;
	}
    
}
