package com.tl.resource.business.dto;

import java.util.Iterator;
import java.util.List;

public class LoginInforDto {
  private UserDto user = null;

  private List<ModulesDto> modules = null;

  public UserDto getUser() {
    return user;
  }

  public void setUser(UserDto user) {
    this.user = user;
  }

  public List<ModulesDto> getModules() {
    return modules;
  }

  public void setModules(List<ModulesDto> modules) {
    this.modules = modules;
  }

  public boolean hasModule(String moduleCode) {
    if (moduleCode == null || modules == null) {
      return false;
    }
    if (moduleCode.length() >= 3) {
      String fCode = moduleCode.substring(0, 3);
      for (Iterator iterator = modules.iterator(); iterator.hasNext();) {
        ModulesDto dto = (ModulesDto) iterator.next();
        if (fCode.equals(dto.getId()) && moduleCode.length() >= 6) {
          return dto.hasChild(moduleCode);
        }
      }
    }
    return false;
  }
}
