package com.tl.resource.business.workDoing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.audit.IAuditService;
import com.tl.resource.audit.dto.AuditTypeFlowInforDto;
import com.tl.resource.audit.dto.LinkBusinessObject;
import com.tl.resource.business.dto.BusinessInforDto;
import com.tl.resource.business.dto.LoginInforDto;

public class AuditBusinessServiceImp implements AuditBusinessService {
  public static final String PRE_ID_STR_PART_FLOW_INFOR_ID = "flowId-";

  private IAuditService auditService;

  @Override
  public List<BusinessInforDto> getBusinessDataInfor(String pid, LoginInforDto loginInfor) {
    if ("".equals(pid) || "auditroot".equals(pid)) {
      return findWaitAuditTypeInfor(loginInfor);
    } else {
      String[] idparts = pid.split("\\-");
      String typeId = idparts[2];
      String flowId = idparts[1];
      return findWaitAuditBusinessInfor(typeId, flowId, loginInfor);
    }
  }

  private List<BusinessInforDto> findWaitAuditBusinessInfor(String typeId, String flowId, LoginInforDto loginInfor) {
    List<BusinessInforDto> blist = new ArrayList<BusinessInforDto>();
    PaginationSupport pageInfor = auditService.findWaitAuditBusinessInfor(new HashMap(), loginInfor.getUser(), typeId, flowId, 0, 99999999);
    List itemsList = pageInfor.getItems();
    for (Iterator iterator = itemsList.iterator(); iterator.hasNext();) {
      LinkBusinessObject bo = (LinkBusinessObject) iterator.next();
      BusinessInforDto dto = new BusinessInforDto();
      dto.setText(bo.getName());
      dto.setLeaf(new Integer(1).shortValue());
      blist.add(dto);
    }
    return blist;
  }

  private List<BusinessInforDto> findWaitAuditTypeInfor(LoginInforDto loginInfor) {
    List<BusinessInforDto> blist = new ArrayList<BusinessInforDto>();
    List<AuditTypeFlowInforDto> list = auditService.findWaitAuditTypeInfor(loginInfor.getUser());
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
      AuditTypeFlowInforDto auditTypeFlowInforDto = (AuditTypeFlowInforDto) iterator.next();
      BusinessInforDto dto = new BusinessInforDto();
      dto.setText(auditTypeFlowInforDto.getAuditFlowName());
      dto.setId(PRE_ID_STR_PART_FLOW_INFOR_ID + auditTypeFlowInforDto.getAuditFlowId() + "-" + auditTypeFlowInforDto.getAuditTypeId());
      blist.add(dto);
    }
    return blist;
  }

  public IAuditService getAuditService() {
    return auditService;
  }

  public void setAuditService(IAuditService auditService) {
    this.auditService = auditService;
  }

}
