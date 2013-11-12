package com.tl.resource.audit.dto;

import java.util.Date;

public class TAuditContentDefDto {
  private String id;

  private String auditContentName;

  private String memo;

  private String auditPerson;

  private Date auditDate;

  private boolean waitAudit;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAuditContentName() {
    return auditContentName;
  }

  public void setAuditContentName(String auditContentName) {
    this.auditContentName = auditContentName;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public String getAuditPerson() {
    return auditPerson;
  }

  public void setAuditPerson(String auditPerson) {
    this.auditPerson = auditPerson;
  }

  public Date getAuditDate() {
    return auditDate;
  }

  public void setAuditDate(Date auditDate) {
    this.auditDate = auditDate;
  }

  public boolean isWaitAudit() {
    return waitAudit;
  }

  public void setWaitAudit(boolean waitAudit) {
    this.waitAudit = waitAudit;
  }

}
