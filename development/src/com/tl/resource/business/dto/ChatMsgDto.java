package com.tl.resource.business.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatMsgDto {

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

  private String fromUserId;

  private String fromUserName;

  private String fromUserDepart;

  private String toUserId;

  private String toUserName;

  private Date sendTime;

  private String sendDateString;

  private String message;

  private String toUserDepart;

  public String getFromUserId() {
    return fromUserId;
  }

  public void setFromUserId(String fromUserId) {
    this.fromUserId = fromUserId;
  }

  public String getFromUserName() {
    return fromUserName;
  }

  public void setFromUserName(String fromUserName) {
    this.fromUserName = fromUserName;
  }

  public String getToUserId() {
    return toUserId;
  }

  public void setToUserId(String toUserId) {
    this.toUserId = toUserId;
  }

  public String getToUserName() {
    return toUserName;
  }

  public void setToUserName(String toUserName) {
    this.toUserName = toUserName;
  }

  public Date getSendTime() {
    return sendTime;
  }

  public void setSendTime(Date sendTime) {
    this.sendTime = sendTime;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getSendDateString() {
    if (sendTime == null) {
      return null;
    }
    return dateFormat.format(sendTime);
  }

  public void setSendDateString(String sendDateString) {
    this.sendDateString = sendDateString;
  }

  public String getFromUserDepart() {
    return fromUserDepart;
  }

  public void setFromUserDepart(String fromUserDepart) {
    this.fromUserDepart = fromUserDepart;
  }

  public String getToUserDepart() {
    return toUserDepart;
  }

  public void setToUserDepart(String toUserDepart) {
    this.toUserDepart = toUserDepart;
  }

}
