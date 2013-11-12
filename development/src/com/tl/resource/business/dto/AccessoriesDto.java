package com.tl.resource.business.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 附件Dto
 * @author ftl
 *
 */
public class AccessoriesDto {
	private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private String id;
	/**
	 * 路径
	 */
	private String path;
	/**
	 * 名称
	 */
	private String fileName;
	/**
	 * 文件大小
	 */
	private Long fileSize;
	/**
	 * 上传时间
	 */
	private Date uploadTime;
	
	private String uploadTimeStr;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 产品ID
	 */
	private String businessId;
	/**
	 * 附件类型
	 */
	private Integer businessType;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
		if(uploadTime != null) {
			this.uploadTimeStr = df.format(uploadTime);
		}
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
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public Integer getBusinessType() {
		return businessType;
	}
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
	public String getUploadTimeStr() {
		return uploadTimeStr;
	}
	public void setUploadTimeStr(String uploadTimeStr) {
		this.uploadTimeStr = uploadTimeStr;
	}
}
