package com.tl.resource.audit;

import java.util.List;

import com.tl.resource.audit.dto.TAuditContentDefDto;

public interface IAuditContentBusinessObject {
  public static final String TECHNOLOGY = "1";//技术

  public static final String DELIVERY_DATE = "2";//交货期

  public static final String PRICE = "3";//价格

  public static final String CLOSING_ACCOUNT_MODE = "4";//结算方式

  public static final String DELIVERY_TYPE = "5";//交货方式

  List<TAuditContentDefDto> getBusinessAuditContents(String id);
}
