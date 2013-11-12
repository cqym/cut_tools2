package com.tl.resource.business.workDoing;

import java.util.List;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.BusinessInforDto;
import com.tl.resource.business.dto.ChatMsgDto;
import com.tl.resource.business.dto.LoginInforDto;

public interface UsersMessageBusinessService {
  public List<BusinessInforDto> getBusinessDataInfor(String pid, LoginInforDto loginInfor);

  public void insertMessage(ChatMsgDto dto);

  public PaginationSupport getMessageHistory(String userId, String myId, int startIndex, int pageSize);
}
