package com.tl.resource.business.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class LogInfoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String logId;

	private String logUser;

	private String content;

	private String ip;

	private Timestamp logDate;

	private Integer logType;

	private String note;

	public LogInfoDto() {
	}

	public LogInfoDto(String logId, String logUser, String content, String ip,
			Timestamp logDate, String note) {
		this.logId = logId;
		this.logUser = logUser;
		this.content = content;
		this.ip = ip;
		this.logDate = logDate;
		this.note = note;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getLogUser() {
		return logUser;
	}

	public void setLogUser(String logUser) {
		this.logUser = logUser;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Timestamp getLogDate() {
		return logDate;
	}

	public void setLogDate(Timestamp logDate) {
		this.logDate = logDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getLogType() {
		return logType;
	}

	public void setLogType(Integer logType) {
		this.logType = logType;
	}

	public String toString() {
		return new StringBuffer("系统日志：").append(this.getNote()).toString();
	}

}
