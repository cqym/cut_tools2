package com.tl.resource.audit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.tl.resource.audit.dto.TAuditContentDefDto;
import com.tl.resource.dao.TAuditBatchRecordDAO;
import com.tl.resource.dao.TAuditContentAuditHistoryGxDAO;
import com.tl.resource.dao.TAuditContentAuditInstGxDAO;
import com.tl.resource.dao.TAuditContentDefDAO;
import com.tl.resource.dao.TAuditContentFlowDetailGxDAO;
import com.tl.resource.dao.TAuditHistoryDAO;
import com.tl.resource.dao.TAuditInstanceDAO;
import com.tl.resource.dao.pojo.TAuditBatchRecord;
import com.tl.resource.dao.pojo.TAuditContentAuditHistoryGx;
import com.tl.resource.dao.pojo.TAuditContentAuditHistoryGxExample;
import com.tl.resource.dao.pojo.TAuditContentAuditInstGx;
import com.tl.resource.dao.pojo.TAuditContentAuditInstGxExample;
import com.tl.resource.dao.pojo.TAuditContentDef;
import com.tl.resource.dao.pojo.TAuditContentDefExample;
import com.tl.resource.dao.pojo.TAuditContentFlowDetailGx;
import com.tl.resource.dao.pojo.TAuditContentFlowDetailGxExample;
import com.tl.resource.dao.pojo.TAuditHistory;
import com.tl.resource.dao.pojo.TAuditHistoryExample;
import com.tl.resource.dao.pojo.TAuditInstance;
import com.tl.resource.dao.pojo.TAuditInstanceExample;

public class AuditContentServiceImp implements IAuditContentService {
  private TAuditContentDefDAO auditContentDefDAO;

  private TAuditContentFlowDetailGxDAO auditContentFlowDetailGxDAO;

  private TAuditHistoryDAO auditHistoryDAO;

  private TAuditContentAuditHistoryGxDAO auditContentAuditHistoryGxDAO;

  private TAuditInstanceDAO auditInstanceDAO;

  private TAuditContentAuditInstGxDAO auditContentAuditInstGxDAO;

  private TAuditBatchRecordDAO auditBatchRecordDAO;

  @Override
  public List<TAuditContentDefDto> findAuditDetailAuditContentList(String auditDetailId) {
    List<TAuditContentDef> list = auditContentDefDAO.selectByAuditDetailId(auditDetailId);
    List<TAuditContentDefDto> acList = new ArrayList<TAuditContentDefDto>();
    try {
      for (Iterator iterator = list.iterator(); iterator.hasNext();) {
        TAuditContentDef auditContentDef = (TAuditContentDef) iterator.next();
        TAuditContentDefDto dto = new TAuditContentDefDto();
        BeanUtils.copyProperties(dto, auditContentDef);
        acList.add(dto);
      }
    } catch (Exception e) {
      // TODO: handle exception
    }
    return acList;
  }

  @Override
  public List<TAuditContentDefDto> findAllAuditContentList() {
    TAuditContentDefExample example = new TAuditContentDefExample();
    List<TAuditContentDef> list = auditContentDefDAO.selectByExample(example);
    List<TAuditContentDefDto> acList = new ArrayList<TAuditContentDefDto>();
    try {
      for (Iterator iterator = list.iterator(); iterator.hasNext();) {
        TAuditContentDef auditContentDef = (TAuditContentDef) iterator.next();
        TAuditContentDefDto dto = new TAuditContentDefDto();
        BeanUtils.copyProperties(dto, auditContentDef);
        acList.add(dto);
      }
    } catch (Exception e) {
      // TODO: handle exception
    }
    return acList;
  }

  public TAuditContentDefDAO getAuditContentDefDAO() {
    return auditContentDefDAO;
  }

  public void setAuditContentDefDAO(TAuditContentDefDAO auditContentDefDAO) {
    this.auditContentDefDAO = auditContentDefDAO;
  }

  @Override
  public void saveAuditDetailAuditContent(String auditDetailId, List<String> ids) {
    TAuditContentFlowDetailGxExample example = new TAuditContentFlowDetailGxExample();
    example.createCriteria().andFlowDetailIdEqualTo(auditDetailId);
    auditContentFlowDetailGxDAO.deleteByExample(example);
    if (ids != null) {
      for (Iterator iterator = ids.iterator(); iterator.hasNext();) {
        String id = (String) iterator.next();
        TAuditContentFlowDetailGx record = new TAuditContentFlowDetailGx();
        record.setContentId(id);
        record.setFlowDetailId(auditDetailId);
        auditContentFlowDetailGxDAO.insert(record);
      }
    }
  }

  public TAuditContentFlowDetailGxDAO getAuditContentFlowDetailGxDAO() {
    return auditContentFlowDetailGxDAO;
  }

  public void setAuditContentFlowDetailGxDAO(TAuditContentFlowDetailGxDAO auditContentFlowDetailGxDAO) {
    this.auditContentFlowDetailGxDAO = auditContentFlowDetailGxDAO;
  }

  @Override
  public List<TAuditContentDefDto> getAuditFlowContent(String auditFlowId) {
    // TODO Auto-generated method stub
    List<TAuditContentDef> list = auditContentDefDAO.selectByAuditFlowId(auditFlowId);
    List<TAuditContentDefDto> acList = new ArrayList<TAuditContentDefDto>();
    try {
      for (Iterator iterator = list.iterator(); iterator.hasNext();) {
        TAuditContentDef auditContentDef = (TAuditContentDef) iterator.next();
        TAuditContentDefDto dto = new TAuditContentDefDto();
        BeanUtils.copyProperties(dto, auditContentDef);
        acList.add(dto);
      }
    } catch (Exception e) {
      // TODO: handle exception
    }
    return acList;
  }

  @Override
  public List<TAuditContentDefDto> findHistoryContentList(String busId) {
    TAuditBatchRecord lastBatch = auditBatchRecordDAO.getLastBatchByBusinessId(busId);

    List<TAuditContentDefDto> list = new ArrayList<TAuditContentDefDto>();
    TAuditHistoryExample example = new TAuditHistoryExample();
    example.createCriteria().andBusinessIdEqualTo(busId).andBatchIdEqualTo(lastBatch.getId());
    List<TAuditHistory> his = auditHistoryDAO.selectByExample(example);
    for (Iterator iterator = his.iterator(); iterator.hasNext();) {
      TAuditHistory auditHistory = (TAuditHistory) iterator.next();
      TAuditContentAuditHistoryGxExample exa = new TAuditContentAuditHistoryGxExample();
      exa.createCriteria().andAuditHistoryIdEqualTo(auditHistory.getId());
      List<TAuditContentAuditHistoryGx> hgxlist = auditContentAuditHistoryGxDAO.selectByExample(exa);
      for (Iterator iterator2 = hgxlist.iterator(); iterator2.hasNext();) {
        TAuditContentAuditHistoryGx auditContentAuditHistoryGx = (TAuditContentAuditHistoryGx) iterator2.next();
        TAuditContentDefDto it = new TAuditContentDefDto();
        it.setId(auditContentAuditHistoryGx.getContentId());
        it.setAuditPerson(auditHistory.getAuditPerson());
        it.setAuditDate(auditHistory.getAuditTime());
        it.setAuditContentName(auditContentDefDAO.selectByPrimaryKey(auditContentAuditHistoryGx.getContentId()).getAuditContentName());
        list.add(it);
      }
    }
    return list;
  }

  public TAuditContentAuditHistoryGxDAO getAuditContentAuditHistoryGxDAO() {
    return auditContentAuditHistoryGxDAO;
  }

  public void setAuditContentAuditHistoryGxDAO(TAuditContentAuditHistoryGxDAO auditContentAuditHistoryGxDAO) {
    this.auditContentAuditHistoryGxDAO = auditContentAuditHistoryGxDAO;
  }

  public TAuditHistoryDAO getAuditHistoryDAO() {
    return auditHistoryDAO;
  }

  public void setAuditHistoryDAO(TAuditHistoryDAO auditHistoryDAO) {
    this.auditHistoryDAO = auditHistoryDAO;
  }

  @Override
  public List<TAuditContentDefDto> findAllAuditContentList(String busId) {
    List<TAuditContentDefDto> list = findHistoryContentList(busId);//已经审过的
    TAuditInstanceExample example = new TAuditInstanceExample();
    example.createCriteria().andBusinessIdEqualTo(busId);
    List<TAuditInstance> insList = auditInstanceDAO.selectByExample(example);
    if (insList.size() > 0) {
      List<TAuditContentDefDto> waitAuditContentList = new ArrayList<TAuditContentDefDto>();//存未审的
      List<TAuditContentDefDto> flowAuditContentList = getAuditFlowContent(insList.get(0).getFlowInforId());//当前流程全部要审的
      for (Iterator iterator = flowAuditContentList.iterator(); iterator.hasNext();) {
        TAuditContentDefDto auditContentDefDto = (TAuditContentDefDto) iterator.next();
        boolean notContai = true;
        for (Iterator iterator2 = list.iterator(); iterator2.hasNext();) {
          TAuditContentDefDto dto = (TAuditContentDefDto) iterator2.next();
          if (auditContentDefDto.getId().equals(dto.getId())) {//如果当前是已经审过的，则设置标记
            notContai = false;
          }
        }
        if (notContai) {//未审批的
          waitAuditContentList.add(auditContentDefDto);
        }
      }
      list.addAll(waitAuditContentList);
    }
    return list;
  }

  public TAuditInstanceDAO getAuditInstanceDAO() {
    return auditInstanceDAO;
  }

  public void setAuditInstanceDAO(TAuditInstanceDAO auditInstanceDAO) {
    this.auditInstanceDAO = auditInstanceDAO;
  }

  @Override
  public List<TAuditContentDefDto> findWaitAuditContent(String busId, String userId) {
    // TODO Auto-generated method stub
    List<TAuditContentDefDto> list = findAllAuditContentList(busId);
    TAuditInstanceExample example = new TAuditInstanceExample();
    example.createCriteria().andBusinessIdEqualTo(busId).andAuditPersonIdEqualTo(userId);
    List<TAuditInstance> insList = auditInstanceDAO.selectByExample(example);
    if (insList.size() > 0) {
      TAuditContentAuditInstGxExample exa = new TAuditContentAuditInstGxExample();
      exa.createCriteria().andAuditInsIdEqualTo(insList.get(0).getId());
      List<TAuditContentAuditInstGx> insGxList = auditContentAuditInstGxDAO.selectByExample(exa);
      for (Iterator iterator = insGxList.iterator(); iterator.hasNext();) {
        TAuditContentAuditInstGx auditContentAuditInstGx = (TAuditContentAuditInstGx) iterator.next();
        for (Iterator iterator2 = list.iterator(); iterator2.hasNext();) {
          TAuditContentDefDto content = (TAuditContentDefDto) iterator2.next();
          if (content.getId().equals(auditContentAuditInstGx.getContentId())) {
            content.setWaitAudit(true);
          }
        }
      }
    }

    return list;
  }

  public TAuditContentAuditInstGxDAO getAuditContentAuditInstGxDAO() {
    return auditContentAuditInstGxDAO;
  }

  public void setAuditContentAuditInstGxDAO(TAuditContentAuditInstGxDAO auditContentAuditInstGxDAO) {
    this.auditContentAuditInstGxDAO = auditContentAuditInstGxDAO;
  }

  public TAuditBatchRecordDAO getAuditBatchRecordDAO() {
    return auditBatchRecordDAO;
  }

  public void setAuditBatchRecordDAO(TAuditBatchRecordDAO auditBatchRecordDAO) {
    this.auditBatchRecordDAO = auditBatchRecordDAO;
  }

}
