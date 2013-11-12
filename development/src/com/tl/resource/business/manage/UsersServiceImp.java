package com.tl.resource.business.manage;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.tl.common.util.GenerateSerial;
import com.tl.common.util.MD5;
import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.ModulesDto;
import com.tl.resource.business.dto.RolesDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.dao.TModulesDefDAO;
import com.tl.resource.dao.TResourcePurviewDAO;
import com.tl.resource.dao.TUserInforDAO;
import com.tl.resource.dao.TUsersRolesDAO;
import com.tl.resource.dao.pojo.TModulesDef;
import com.tl.resource.dao.pojo.TResourcePurview;
import com.tl.resource.dao.pojo.TResourcePurviewExample;
import com.tl.resource.dao.pojo.TUserInfor;
import com.tl.resource.dao.pojo.TUserInforExample;
import com.tl.resource.dao.pojo.TUsersRolesExample;
import com.tl.resource.dao.pojo.TUsersRolesKey;
import com.tl.resource.dao.pojo.TUsersRolesExample.Criteria;

public class UsersServiceImp implements UsersService {
  private TUserInforDAO userInforDAO;

  private TUsersRolesDAO usersRolesDAO;

  private TModulesDefDAO modulesDefDAO;

  private TResourcePurviewDAO resourcePurviewDAO;

  @Override
  public void addUser(UserDto dto) {
    try {
      dto.setRegTime(new Date());
      dto.setPassword(MD5.getMD5("111111"));
      TUserInfor record = new TUserInfor();
      BeanUtils.copyProperties(record, dto);
      record.setId(GenerateSerial.getUUID());
      dto.setId(record.getId());
      record.setValid(UserDto.VALID);
      userInforDAO.insert(record);

      for (int i = 1; i < 7; i++) {
        TResourcePurview po = new TResourcePurview();
        po.setId(GenerateSerial.getUUID());
        po.setResourceType(i);
        po.setTargetUserId(record.getId());
        po.setUserId(record.getId());
        resourcePurviewDAO.insert(po);
      }
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public void deleteUser(String id) {
    TUsersRolesExample example = new TUsersRolesExample();
    Criteria c = example.createCriteria();
    c.andIdEqualTo(id);
    usersRolesDAO.deleteByExample(example);

    TUserInforExample e = new TUserInforExample();
    e.createCriteria().andIdEqualTo(id);
    userInforDAO.deleteByExample(e);
  }

  @Override
  public PaginationSupport findUsers(Map params, int startIndex, int pageSize) {
    // TODO Auto-generated method stub
    TUserInforExample e = new TUserInforExample();
    e.setByPage(true);
    e.setStartIndex(startIndex);
    e.setPageSize(pageSize);
    com.tl.resource.dao.pojo.TUserInforExample.Criteria c = e.createCriteria();
    if (null != params.get("userName") && !"".equals(params.get("userName"))) {
      c.andUserNameLike("%" + (String) params.get("userName") + "%");
    }
    if (null != params.get("trueName") && !"".equals(params.get("trueName"))) {
      c.andTrueNameLike("%" + (String) params.get("trueName") + "%");
    }
    if (null != params.get("onlyValid") && !"".equals(params.get("onlyValid"))) {
      c.andValidEqualTo(UserDto.VALID);
    }
    // e.setOrderByClause(" reg_time desc");
    PaginationSupport ps = null;
    try {
      List list = userInforDAO.selectByExample(e);
      List<UserDto> usersDtoList = new ArrayList<UserDto>();
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
      for (Iterator iterator = list.iterator(); iterator.hasNext();) {
        TUserInfor user = (TUserInfor) iterator.next();
        UserDto dest = new UserDto();
        BeanUtils.copyProperties(dest, user);
        dest.setRegTimeString(df.format(user.getRegTime()));
        usersDtoList.add(dest);
      }
      //e.setOrderByClause(null);
      ps = new PaginationSupport(usersDtoList, userInforDAO.countByExample(e), startIndex);
    } catch (Exception e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    return ps;
  }

  private List<UserDto> convertToDto(List<TUserInfor> userInfors) throws Exception {
    UserDto usersDto;
    if (null == userInfors)
      return null;

    List<UserDto> target = new ArrayList<UserDto>();
    for (int i = 0; i < userInfors.size(); i++) {
      TUserInfor user = userInfors.get(i);
      usersDto = new UserDto();
      BeanUtils.copyProperties(usersDto, user);
      target.add(usersDto);
    }

    return target;
  }

  @Override
  public List<UserDto> findUsersAll() {
    // TODO Auto-generated method stub
    TUserInforExample e = new TUserInforExample();
    return userInforDAO.selectByExample(e);
  }

  @Override
  public void updateUserInfor(UserDto dto) {
    // TODO Auto-generated method stub
    try {
      TUserInfor record = new TUserInfor();
      if (dto.getPassword() != null) {
        dto.setPassword(MD5.getMD5(dto.getPassword()));
      }
      BeanUtils.copyProperties(record, dto);
      userInforDAO.updateByPrimaryKeySelective(record);
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  public TUserInforDAO getUserInforDAO() {
    return userInforDAO;
  }

  public void setUserInforDAO(TUserInforDAO userInforDAO) {
    this.userInforDAO = userInforDAO;
  }

  public TUsersRolesDAO getUsersRolesDAO() {
    return usersRolesDAO;
  }

  public void setUsersRolesDAO(TUsersRolesDAO usersRolesDAO) {
    this.usersRolesDAO = usersRolesDAO;
  }

  @Override
  public void updateUserRoles(String userId, String[] roleIds) {
    TUsersRolesExample example = new TUsersRolesExample();
    example.createCriteria().andIdEqualTo(userId);
    usersRolesDAO.deleteByExample(example);

    for (int i = 0; i < roleIds.length; i++) {
      TUsersRolesKey record = new TUsersRolesKey();
      record.setRoleId(roleIds[i]);
      record.setId(userId);
      usersRolesDAO.insert(record);
    }
  }

  @Override
  public List<RolesDto> findUserRoles(String userId) {
    // TODO Auto-generated method stub
    return userInforDAO.getUserRoles(userId);
  }

  @Override
  public LoginInforDto login(String userName, String passsword) {
    TUserInforExample example = new TUserInforExample();
    example.createCriteria().andUserNameEqualTo(userName);
    List<TUserInfor> list = userInforDAO.selectByExample(example);
    if (list == null || list.size() != 1) {
      return null;
    }

    TUserInfor user = list.get(0);
    if (user.getPassword() != null && user.getPassword().equals(MD5.getMD5(passsword))) {
      LoginInforDto lid = new LoginInforDto();
      UserDto utd = new UserDto();
      try {
        BeanUtils.copyProperties(utd, user);
      } catch (IllegalAccessException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      lid.setUser(utd);

      List<ModulesDto> mdtoList = new ArrayList<ModulesDto>();
      List<TModulesDef> mlist = modulesDefDAO.getUsersModules(user.getId(), "root");
      try {
        for (Iterator iterator = mlist.iterator(); iterator.hasNext();) {
          TModulesDef modulesDef = (TModulesDef) iterator.next();
          ModulesDto mdto = new ModulesDto();
          BeanUtils.copyProperties(mdto, modulesDef);
          findAllChildren(mdto, user.getId());
          mdtoList.add(mdto);
        }
      } catch (IllegalAccessException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      lid.setModules(mdtoList);
      return lid;
    }
    return null;
  }

  private List<ModulesDto> findAllChildren(ModulesDto vo, String userId) {
    List<ModulesDto> list = new ArrayList<ModulesDto>();
    try {

      List<TModulesDef> children = modulesDefDAO.getUsersModules(userId, vo.getId());

      for (Iterator iterator = children.iterator(); iterator.hasNext();) {
        TModulesDef modulesDef = (TModulesDef) iterator.next();
        ModulesDto mdto = new ModulesDto();
        BeanUtils.copyProperties(mdto, modulesDef);
        list.add(mdto);
      }

      vo.setChildren(list);
      for (Iterator iterator = list.iterator(); iterator.hasNext();) {
        ModulesDto o = (ModulesDto) iterator.next();
        if (o.getLeaf() != 1) {
          o.setChildren(findAllChildren(o, userId));
        }
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return list;
  }

  public TModulesDefDAO getModulesDefDAO() {
    return modulesDefDAO;
  }

  public void setModulesDefDAO(TModulesDefDAO modulesDefDAO) {
    this.modulesDefDAO = modulesDefDAO;
  }

  @Override
  public List<TUserInfor> getSelectedUser(Map<String, Object> params) {
    return userInforDAO.getSelectedUser(params);
  }

  @Override
  public PaginationSupport getWillSelectUser(Map<String, Object> params, int startIndex, int pageSize) {
    PaginationSupport ps = userInforDAO.getWillSelectUser(params, startIndex, pageSize);
    List list = ps.getItems();
    List<UserDto> usersDtoList = new ArrayList<UserDto>();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    try {
      for (Iterator iterator = list.iterator(); iterator.hasNext();) {
        TUserInfor user = (TUserInfor) iterator.next();
        UserDto dest = new UserDto();
        BeanUtils.copyProperties(dest, user);
        dest.setRegTimeString(df.format(user.getRegTime()));
        usersDtoList.add(dest);
      }
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    ps.setItems(usersDtoList);
    return ps;
  }

  public TResourcePurviewDAO getResourcePurviewDAO() {
    return resourcePurviewDAO;
  }

  public void setResourcePurviewDAO(TResourcePurviewDAO resourcePurviewDAO) {
    this.resourcePurviewDAO = resourcePurviewDAO;
  }

  @Override
  public void saveResourcePurview(String userId, int businessType, List<String> ids) {

    TResourcePurviewExample example = new TResourcePurviewExample();
    example.createCriteria().andUserIdEqualTo(userId).andResourceTypeEqualTo(businessType);
    resourcePurviewDAO.deleteByExample(example);
    if (ids == null || ids.size() == 0)
      return;
    for (Iterator iterator = ids.iterator(); iterator.hasNext();) {
      String id = (String) iterator.next();
      TResourcePurview record = new TResourcePurview();
      record.setId(GenerateSerial.getUUID());
      record.setResourceType(businessType);
      record.setUserId(userId);
      record.setTargetUserId(id);
      resourcePurviewDAO.insert(record);
    }

  }

  @Override
  public UserDto getUserById(String id) {
    TUserInfor userPo = userInforDAO.selectByPrimaryKey(id);
    UserDto dest = new UserDto();
    try {
      BeanUtils.copyProperties(dest, userPo);
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return dest;
  }

  @Override
  public void setUserValid(String userId, String valid) {
    if (valid == null) {
      valid = "0";
    }
    TUserInfor record = new TUserInfor();
    record.setId(userId);
    record.setValid(Integer.valueOf(valid));
    userInforDAO.updateByPrimaryKeySelective(record);
  }

}
