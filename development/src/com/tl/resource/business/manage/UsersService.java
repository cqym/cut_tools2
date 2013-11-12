package com.tl.resource.business.manage;

import java.util.List;
import java.util.Map;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.RolesDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.dao.pojo.TUserInfor;

public interface UsersService {
  public void addUser(UserDto dto);

  public void updateUserInfor(UserDto dto);

  public void updateUserRoles(String userId, String[] roleIds);

  public void deleteUser(String id);

  public List<UserDto> findUsersAll();

  public PaginationSupport findUsers(Map params, int startIndex, int pageSize);

  public List<RolesDto> findUserRoles(String userId);

  public LoginInforDto login(String userName, String passsword);

  public PaginationSupport getWillSelectUser(Map<String, Object> params, int startIndex, int pageSize);

  public List<TUserInfor> getSelectedUser(Map<String, Object> params);

  public void saveResourcePurview(String userId, int businessType, List<String> ids);

  public UserDto getUserById(String id);

  public void setUserValid(String userId, String valid);
}
