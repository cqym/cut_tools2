package com.tl.resource.business.workDoing;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.tl.common.util.GenerateSerial;
import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.BusinessInforDto;
import com.tl.resource.business.dto.ChatMsgDto;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.dao.TDepartmentsInforDAO;
import com.tl.resource.dao.TUserInforDAO;
import com.tl.resource.dao.TUserMessageRecordDAO;
import com.tl.resource.dao.pojo.TDepartmentsInfor;
import com.tl.resource.dao.pojo.TDepartmentsInforExample;
import com.tl.resource.dao.pojo.TUserInfor;
import com.tl.resource.dao.pojo.TUserInforExample;
import com.tl.resource.dao.pojo.TUserMessageRecord;
import com.tl.resource.dao.pojo.TUserMessageRecordExample;

public class UsersMessageBusinessServiceImp implements UsersMessageBusinessService {
  private TUserInforDAO userInforDAO;

  private TDepartmentsInforDAO departmentsInforDAO;

  private TUserMessageRecordDAO userMessageRecordDAO;

  @Override
  public List<BusinessInforDto> getBusinessDataInfor(String pid, LoginInforDto loginInfor) {
    if ("".equals(pid) || "odatebroot".equals(pid)) {
      return getDepartsNodes(pid);
    } else {
      return getUsersNodes(pid);
    }
  }

  private List<BusinessInforDto> getDepartsNodes(String pid) {
    List<BusinessInforDto> rtList = new ArrayList<BusinessInforDto>();
    TDepartmentsInforExample example = new TDepartmentsInforExample();
    example.createCriteria().andIdNotEqualTo("007").andIdNotEqualTo("001001");
    List<TDepartmentsInfor> list = departmentsInforDAO.selectByExample(example);
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
      TDepartmentsInfor departmentsInfor = (TDepartmentsInfor) iterator.next();
      BusinessInforDto dto = new BusinessInforDto();
      dto.setText(departmentsInfor.getDepartName());
      dto.setLeaf(new Integer(0).shortValue());
      dto.setId(departmentsInfor.getId());
      rtList.add(dto);
    }
    return rtList;
  }

  private List<BusinessInforDto> getUsersNodes(String pid) {
    TUserInforExample example = new TUserInforExample();
    List<BusinessInforDto> rtList = new ArrayList<BusinessInforDto>();
    example.createCriteria().andUserNameNotEqualTo("admin").andDepartIdEqualTo(pid);
    List<TUserInfor> list = userInforDAO.selectByExample(example);
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
      TUserInfor userInfor = (TUserInfor) iterator.next();
      BusinessInforDto dto = new BusinessInforDto();
      dto.setText(userInfor.getUserName());
      dto.setLeaf(new Integer(1).shortValue());
      dto.setId(userInfor.getId());
      rtList.add(dto);
    }
    return rtList;
  }

  public TUserInforDAO getUserInforDAO() {
    return userInforDAO;
  }

  public void setUserInforDAO(TUserInforDAO userInforDAO) {
    this.userInforDAO = userInforDAO;
  }

  public TDepartmentsInforDAO getDepartmentsInforDAO() {
    return departmentsInforDAO;
  }

  public void setDepartmentsInforDAO(TDepartmentsInforDAO departmentsInforDAO) {
    this.departmentsInforDAO = departmentsInforDAO;
  }

  public TUserMessageRecordDAO getUserMessageRecordDAO() {
    return userMessageRecordDAO;
  }

  public void setUserMessageRecordDAO(TUserMessageRecordDAO userMessageRecordDAO) {
    this.userMessageRecordDAO = userMessageRecordDAO;
  }

  @Override
  public void insertMessage(ChatMsgDto dto) {
    try {
      TUserMessageRecord record = new TUserMessageRecord();
      BeanUtils.copyProperties(record, dto);
      record.setId(GenerateSerial.getUUID());
      userMessageRecordDAO.insert(record);
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public PaginationSupport getMessageHistory(String userId, String myId, int startIndex, int pageSize) {
    TUserMessageRecordExample example = new TUserMessageRecordExample();
    example.createCriteria().andToUserIdEqualTo(myId).andFromUserIdEqualTo(userId);
    example.or(example.createCriteria().andFromUserIdEqualTo(myId).andToUserIdEqualTo(userId));
    example.setOrderByClause("send_time desc");
    example.setPageSize(pageSize);
    example.setStartIndex(startIndex);
    List list = userMessageRecordDAO.selectByExample(example);
    int count = userMessageRecordDAO.countByExample(example);
    List dtoList = new ArrayList();
    try {
      for (Iterator iterator = list.iterator(); iterator.hasNext();) {
        TUserMessageRecord umr = (TUserMessageRecord) iterator.next();
        ChatMsgDto dto = new ChatMsgDto();
        BeanUtils.copyProperties(dto, umr);
        dtoList.add(dto);
      }
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    PaginationSupport pa = new PaginationSupport(dtoList, count, startIndex);
    return pa;
  }
}
