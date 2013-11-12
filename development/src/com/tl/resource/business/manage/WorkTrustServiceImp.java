package com.tl.resource.business.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tl.common.util.GenerateSerial;
import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.dao.TAuditFlowDetailDAO;
import com.tl.resource.dao.TAuditInstanceDAO;
import com.tl.resource.dao.TUserInforDAO;
import com.tl.resource.dao.TWorkTrustDetailDAO;
import com.tl.resource.dao.TWorkTrustRecordDAO;
import com.tl.resource.dao.pojo.TAuditFlowDetail;
import com.tl.resource.dao.pojo.TAuditFlowDetailExample;
import com.tl.resource.dao.pojo.TAuditInstance;
import com.tl.resource.dao.pojo.TAuditInstanceExample;
import com.tl.resource.dao.pojo.TUserInfor;
import com.tl.resource.dao.pojo.TWorkTrustDetail;
import com.tl.resource.dao.pojo.TWorkTrustDetailExample;
import com.tl.resource.dao.pojo.TWorkTrustRecord;
import com.tl.resource.dao.pojo.TWorkTrustRecordExample;

public class WorkTrustServiceImp implements WorkTrustService {
  private TWorkTrustDetailDAO workTrustDetailDAO;

  private TWorkTrustRecordDAO workTrustRecordDAO;

  private TAuditFlowDetailDAO auditFlowDetailDAO;

  private TAuditInstanceDAO auditInstanceDAO;

  private TUserInforDAO userInforDAO;

  @Override
  public void cancelRight(LoginInforDto opUser, String authorUserId) {
    TWorkTrustRecordExample example = new TWorkTrustRecordExample();
    example.createCriteria().andOpTypeEqualTo(0).andAuthorizePersonIdEqualTo(authorUserId);
    List<com.tl.resource.dao.pojo.TWorkTrustRecord> list = workTrustRecordDAO.selectByExample(example);
    if (list == null || list.size() == 0)
      return;
    TWorkTrustRecord wtr = list.get(0);

    TUserInfor authorUser = userInforDAO.selectByPrimaryKey(authorUserId);

    TWorkTrustRecord wr = new TWorkTrustRecord();
    wr.setId(GenerateSerial.getUUID());
    wr.setOpPerson(opUser.getUser().getTrueName());
    wr.setOpPersonId(opUser.getUser().getId());
    wr.setOpTime(new Date());
    wr.setAuthorizePerson(authorUser.getTrueName());
    wr.setAuthorizePersonId(authorUser.getId());
    wr.setOpType(1);//授权
    wr.setGetRightPerson(wtr.getGetRightPerson());
    wr.setGetRightPersonId(wtr.getGetRightPersonId());
    workTrustRecordDAO.insert(wr);

    wtr.setOpType(2);
    workTrustRecordDAO.updateByPrimaryKey(wtr);

    TWorkTrustDetailExample ttt = new TWorkTrustDetailExample();
    ttt.createCriteria().andTrustRecordIdEqualTo(wtr.getId());
    List<TWorkTrustDetail> dlist = workTrustDetailDAO.selectByExample(ttt);
    for (Iterator iterator = dlist.iterator(); iterator.hasNext();) {
      TWorkTrustDetail workTrustDetail = (TWorkTrustDetail) iterator.next();
      if ("t_audit_flow_detail".equals(workTrustDetail.getTabelName())) {
        TAuditFlowDetail record = new TAuditFlowDetail();
        record.setId(workTrustDetail.getTableId());
        record.setAuditPersonId(wtr.getAuthorizePersonId());
        record.setAuditPersonName(wtr.getAuthorizePerson());
        auditFlowDetailDAO.updateByPrimaryKeySelective(record);
      } else if ("t_audit_instance".equals(workTrustDetail.getTabelName())) {
        TAuditInstance record = new TAuditInstance();
        record.setId(workTrustDetail.getTableId());
        record.setAuditPerson(wtr.getAuthorizePerson());
        record.setAuditPersonId(wtr.getAuthorizePersonId());
        auditInstanceDAO.updateByPrimaryKeySelective(record);
      }
    }
    example.clear();
    example.createCriteria().andOpTypeEqualTo(0).andOpTimeGreaterThan(wtr.getOpTime());
    list = workTrustRecordDAO.selectByExample(example);
    if (list == null || list.size() == 0)
      return;

    List<String> recordIds = new ArrayList<String>();
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
      TWorkTrustRecord po = (TWorkTrustRecord) iterator.next();
      recordIds.add(po.getId());
    }
    ttt.clear();
    ttt.createCriteria().andTrustRecordIdIn(recordIds);
    List<TWorkTrustDetail> list2 = workTrustDetailDAO.selectByExample(ttt);
    for (Iterator iterator = list2.iterator(); iterator.hasNext();) {
      TWorkTrustDetail workTrustDetail = (TWorkTrustDetail) iterator.next();
      for (Iterator iterator2 = dlist.iterator(); iterator2.hasNext();) {
        TWorkTrustDetail workTrustDetail2 = (TWorkTrustDetail) iterator2.next();
        if (workTrustDetail.getTableId().equals(workTrustDetail2.getTableId())) {
          workTrustDetailDAO.deleteByPrimaryKey(workTrustDetail.getId());
          break;
        }
      }
    }
  }

  @Override
  public String getOrToRight(String userId) {
    TWorkTrustRecordExample example = new TWorkTrustRecordExample();
    example.createCriteria().andOpTypeEqualTo(0).andAuthorizePersonIdEqualTo(userId);
    List<com.tl.resource.dao.pojo.TWorkTrustRecord> list = workTrustRecordDAO.selectByExample(example);
    if (list != null && list.size() > 0)
      return list.get(0).getGetRightPerson();//返回 得到权力人的名称
    return "";
  }

  @Override
  public String grandRight(String getRightUserId, String authorUserId, LoginInforDto opUser) {
    TWorkTrustRecordExample example1 = new TWorkTrustRecordExample();
    example1.createCriteria().andGetRightPersonIdEqualTo(authorUserId).andOpTypeEqualTo(0);
    List<TWorkTrustRecord> wtr = workTrustRecordDAO.selectByExample(example1);
    if (wtr != null && wtr.size() > 0) {
      if (wtr.get(0).getAuthorizePersonId().equals(getRightUserId)) {
        return "出现环形委托，当前委托失败！";
      }
    }
    while (wtr != null && wtr.size() > 0) {
      example1.clear();
      TWorkTrustRecord t = wtr.get(0);
      example1.createCriteria().andGetRightPersonIdEqualTo(t.getAuthorizePersonId()).andOpTypeEqualTo(0);
      wtr = workTrustRecordDAO.selectByExample(example1);
      if (wtr == null || wtr.size() == 0) {
        break;
      }
      if (wtr.get(0).getAuthorizePersonId().equals(getRightUserId)) {
        return "出现环形委托，当前委托失败！";
      }
    }
    TUserInfor getRightUser = userInforDAO.selectByPrimaryKey(getRightUserId);
    TUserInfor authorUser = userInforDAO.selectByPrimaryKey(authorUserId);
    TWorkTrustRecord wr = new TWorkTrustRecord();
    wr.setId(GenerateSerial.getUUID());
    wr.setOpPerson(opUser.getUser().getTrueName());
    wr.setOpPersonId(opUser.getUser().getId());
    wr.setOpTime(new Date());
    wr.setAuthorizePerson(authorUser.getTrueName());
    wr.setAuthorizePersonId(authorUser.getId());
    wr.setOpType(0);//授权
    wr.setGetRightPerson(getRightUser.getTrueName());
    wr.setGetRightPersonId(getRightUser.getId());
    workTrustRecordDAO.insert(wr);

    TAuditFlowDetailExample example = new TAuditFlowDetailExample();
    example.createCriteria().andAuditPersonIdEqualTo(wr.getAuthorizePersonId());
    List<TAuditFlowDetail> afdList = auditFlowDetailDAO.selectByExample(example);
    for (Iterator iterator = afdList.iterator(); iterator.hasNext();) {
      TAuditFlowDetail auditFlowDetail = (TAuditFlowDetail) iterator.next();
      TWorkTrustDetail record = new TWorkTrustDetail();
      record.setId(GenerateSerial.getUUID());
      record.setTabelName("t_audit_flow_detail");
      record.setTableId(auditFlowDetail.getId());
      record.setTrustRecordId(wr.getId());
      record.setUserId(auditFlowDetail.getAuditPersonId());
      record.setUserName(auditFlowDetail.getAuditPersonName());
      workTrustDetailDAO.insert(record);//备份flow数据

      auditFlowDetail.setAuditPersonId(getRightUser.getId());
      auditFlowDetail.setAuditPersonName(getRightUser.getTrueName());
      auditFlowDetailDAO.updateByPrimaryKey(auditFlowDetail);
    }
    TAuditInstanceExample insExp = new TAuditInstanceExample();
    insExp.createCriteria().andAuditPersonIdEqualTo(wr.getAuthorizePersonId());
    List<TAuditInstance> insList = auditInstanceDAO.selectByExample(insExp);
    for (Iterator iterator = insList.iterator(); iterator.hasNext();) {
      TAuditInstance auditInstance = (TAuditInstance) iterator.next();
      TWorkTrustDetail record = new TWorkTrustDetail();
      record.setId(GenerateSerial.getUUID());
      record.setTabelName("t_audit_instance");
      record.setTableId(auditInstance.getId());
      record.setTrustRecordId(wr.getId());
      record.setUserId(auditInstance.getAuditPersonId());
      record.setUserName(auditInstance.getAuditPerson());
      workTrustDetailDAO.insert(record);//备份instance数据

      auditInstance.setAuditPersonId(getRightUser.getId());
      auditInstance.setAuditPerson(getRightUser.getTrueName());
      auditInstanceDAO.updateByPrimaryKey(auditInstance);
    }
    return null;
  }

  public TWorkTrustDetailDAO getWorkTrustDetailDAO() {
    return workTrustDetailDAO;
  }

  public void setWorkTrustDetailDAO(TWorkTrustDetailDAO workTrustDetailDAO) {
    this.workTrustDetailDAO = workTrustDetailDAO;
  }

  public TWorkTrustRecordDAO getWorkTrustRecordDAO() {
    return workTrustRecordDAO;
  }

  public void setWorkTrustRecordDAO(TWorkTrustRecordDAO workTrustRecordDAO) {
    this.workTrustRecordDAO = workTrustRecordDAO;
  }

  public TAuditFlowDetailDAO getAuditFlowDetailDAO() {
    return auditFlowDetailDAO;
  }

  public void setAuditFlowDetailDAO(TAuditFlowDetailDAO auditFlowDetailDAO) {
    this.auditFlowDetailDAO = auditFlowDetailDAO;
  }

  public TAuditInstanceDAO getAuditInstanceDAO() {
    return auditInstanceDAO;
  }

  public void setAuditInstanceDAO(TAuditInstanceDAO auditInstanceDAO) {
    this.auditInstanceDAO = auditInstanceDAO;
  }

  public TUserInforDAO getUserInforDAO() {
    return userInforDAO;
  }

  public void setUserInforDAO(TUserInforDAO userInforDAO) {
    this.userInforDAO = userInforDAO;
  }

  @Override
  public PaginationSupport getUserListWorkTrust(Map params, int startIndex, int pageSize) {
    return workTrustRecordDAO.getUserListWorkTrust(params, startIndex, pageSize);
  }

  @Override
  public PaginationSupport getUserWorkTrustHistory(Map params, int startIndex, int pageSize) {
    TWorkTrustRecordExample example = new TWorkTrustRecordExample();
    example.createCriteria().andAuthorizePersonIdEqualTo((String) params.get("userId"));
    example.setPageSize(pageSize);
    example.setStartIndex(startIndex);
    example.setOrderByClause("op_time desc");
    List list = workTrustRecordDAO.selectByExample(example);
    int count = workTrustRecordDAO.countByExample(example);
    PaginationSupport ps = new PaginationSupport(list, count, pageSize, startIndex);
    return ps;
  }

  @Override
  public List getGrantRightUserList(UserDto user) {
    TWorkTrustRecordExample example = new TWorkTrustRecordExample();
    example.createCriteria().andOpTypeEqualTo(0).andGetRightPersonIdEqualTo(user.getId());
    List<com.tl.resource.dao.pojo.TWorkTrustRecord> list = workTrustRecordDAO.selectByExample(example);
    return list;
  }

}
