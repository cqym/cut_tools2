package com.tl.resource.business.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotepadDto {
	private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private String id;

    private String title;

    private String memo;

    private String userName;
    private String userId;
    private Date editTime;
    private String editTimeString;
    private String typeId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		if(editTime != null){
			editTimeString = df.format(editTime);
		}
		this.editTime = editTime;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEditTimeString() {
		return editTimeString;
	}

	public void setEditTimeString(String editTimeString) {
		this.editTimeString = editTimeString;
	}
    
    
}
