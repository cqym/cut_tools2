package com.tl.resource.business.dto;

import java.util.Date;

public class UserDto {
  public static final Integer VALID = 0;

  public static final Integer NO_VALID = 1;

  private String id;

  private String userName;

  private String trueName;

  private String password;

  private Date regTime;

  private String regTimeString;

  private String departId;

  private String departName;

  private Integer valid;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getTrueName() {
    return trueName;
  }

  public void setTrueName(String trueName) {
    this.trueName = trueName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Date getRegTime() {
    return regTime;
  }

  public void setRegTime(Date regTime) {
    this.regTime = regTime;
  }

  public String getRegTimeString() {
    return regTimeString;
  }

  public void setRegTimeString(String regTimeString) {
    this.regTimeString = regTimeString;
  }

  public String getDepartId() {
    return departId;
  }

  public void setDepartId(String departId) {
    this.departId = departId;
  }

  public String getDepartName() {
    return departName;
  }

  public void setDepartName(String departName) {
    this.departName = departName;
  }

  public Integer getValid() {
    return valid;
  }

  public void setValid(Integer valid) {
    this.valid = valid;
  }

}
