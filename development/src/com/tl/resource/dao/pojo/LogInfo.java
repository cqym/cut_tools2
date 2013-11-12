package com.tl.resource.dao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class LogInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String logId;

	private String logUser;

	private String content;

	private String ip;

	private Timestamp logDate;

	private Integer logType;

	private String note;

	/**
	 * 
	 * @return
	 */
	public String getLogId() {
		return logId;
	}

	/**
	 * 
	 * @param logId
	 * @column LOG_ID
	 */
	public void setLogId(String logId) {
		this.logId = logId;
	}

	/**
	 * 
	 * @return
	 */

	public String getLogUser() {
		return logUser;
	}

	/**
	 * 
	 * @param logUser
	 * @column LOG_USER
	 */
	public void setLogUser(String logUser) {
		this.logUser = logUser;
	}

	/**
	 * 
	 * @return
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 
	 * @param content
	 * @column CONTENT
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 
	 * @return
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * 
	 * @param ip
	 * @column IP
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 
	 * @return
	 */
	public Timestamp getLogDate() {
		return logDate;
	}

	/**
	 * 
	 * @param logDate
	 * @column LOG_DATE
	 */
	public void setLogDate(Timestamp logDate) {
		this.logDate = logDate;
	}

	/**
	 * 
	 * @return
	 */
	public String getNote() {
		return note;
	}

	/**
	 * 
	 * @param note
	 * @column NOTE
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getLogType() {
		return logType;
	}

	/**
	 * 
	 * @param logType
	 * @column LOG_TYP
	 */
	public void setLogType(Integer logType) {
		this.logType = logType;
	}

}
