package com.tl.resource.business.workDoing;

import java.util.List;

import com.tl.resource.business.dto.BusinessInforDto;
import com.tl.resource.business.dto.LoginInforDto;

public interface AuditBusinessService {
  public List<BusinessInforDto> getBusinessDataInfor(String pid, LoginInforDto loginInfor);
}
