package com.tl.resource.business.manage;

import java.util.List;
import java.util.Map;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.UserDto;

public interface WorkTrustService {
  /**
   * 获得当前userId，是可以授权，还是 收权(0授，1收)
   * @param userId
   * @return
   */
  public String getOrToRight(String userId);

  /**
   * 收权
   * @param opUser 本次操作人
   * @param authorUserId 原始权限拥有人
   */
  public void cancelRight(LoginInforDto opUser, String authorUserId);

  /**
   * 授权
   * @param getRightUserId 获得权限的用户的id
   * @param authorUserId   原始权限拥有人
   * @param opUser 本次操作人
   */
  public String grandRight(String getRightUserId, String authorUserId, LoginInforDto opUser);

  PaginationSupport getUserListWorkTrust(Map params, int startIndex, int pageSize);

  PaginationSupport getUserWorkTrustHistory(Map params, int startIndex, int pageSize);

  public List getGrantRightUserList(UserDto user);
}
