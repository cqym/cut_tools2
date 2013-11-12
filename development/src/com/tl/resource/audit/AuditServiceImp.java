package com.tl.resource.audit;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.tl.common.util.GenerateSerial;
import com.tl.common.util.PaginationSupport;
import com.tl.resource.audit.dto.AuditTypeFlowInforDto;
import com.tl.resource.audit.dto.LinkBusinessObject;
import com.tl.resource.audit.dto.TAuditBatchRecordDto;
import com.tl.resource.audit.dto.TAuditContentDefDto;
import com.tl.resource.audit.dto.TAuditHistoryDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.dao.AuditDao;
import com.tl.resource.dao.TAuditBatchRecordDAO;
import com.tl.resource.dao.TAuditContentAuditHistoryGxDAO;
import com.tl.resource.dao.TAuditContentAuditInstGxDAO;
import com.tl.resource.dao.TAuditContentFlowDetailGxDAO;
import com.tl.resource.dao.TAuditFlowDetailDAO;
import com.tl.resource.dao.TAuditFlowInforDAO;
import com.tl.resource.dao.TAuditHistoryDAO;
import com.tl.resource.dao.TAuditInstanceDAO;
import com.tl.resource.dao.TAuditInstanceHistoryDAO;
import com.tl.resource.dao.TAuditTypeDAO;
import com.tl.resource.dao.TWorkTrustDetailDAO;
import com.tl.resource.dao.TWorkTrustRecordDAO;
import com.tl.resource.dao.pojo.AuditTypeFlowInfor;
import com.tl.resource.dao.pojo.TAuditBatchRecord;
import com.tl.resource.dao.pojo.TAuditBatchRecordExample;
import com.tl.resource.dao.pojo.TAuditContentAuditHistoryGx;
import com.tl.resource.dao.pojo.TAuditContentAuditInstGx;
import com.tl.resource.dao.pojo.TAuditContentFlowDetailGx;
import com.tl.resource.dao.pojo.TAuditContentFlowDetailGxExample;
import com.tl.resource.dao.pojo.TAuditFlowDetail;
import com.tl.resource.dao.pojo.TAuditFlowDetailExample;
import com.tl.resource.dao.pojo.TAuditFlowInfor;
import com.tl.resource.dao.pojo.TAuditFlowInforExample;
import com.tl.resource.dao.pojo.TAuditHistory;
import com.tl.resource.dao.pojo.TAuditHistoryExample;
import com.tl.resource.dao.pojo.TAuditInstance;
import com.tl.resource.dao.pojo.TAuditInstanceExample;
import com.tl.resource.dao.pojo.TAuditInstanceHistory;
import com.tl.resource.dao.pojo.TAuditInstanceHistoryExample;
import com.tl.resource.dao.pojo.TAuditType;
import com.tl.resource.dao.pojo.TWorkTrustDetail;
import com.tl.resource.dao.pojo.TWorkTrustDetailExample;
import com.tl.resource.dao.pojo.TWorkTrustRecord;
import com.tl.resource.dao.pojo.TWorkTrustRecordExample;

public class AuditServiceImp implements IAuditService {
  private TAuditBatchRecordDAO auditBatchRecordDAO;

  private TAuditFlowDetailDAO auditFlowDetailDAO;

  private TAuditFlowInforDAO auditFlowInforDAO;

  private TAuditHistoryDAO auditHistoryDAO;

  private TAuditInstanceDAO auditInstanceDAO;

  private TAuditTypeDAO auditTypeDAO;

  private IAuditBusinessObject auditBusinessObject;

  private AuditDao auditDao;

  private TAuditInstanceHistoryDAO auditInstanceHistoryDAO;

  private TWorkTrustDetailDAO workTrustDetailDAO;

  private TWorkTrustRecordDAO workTrustRecordDAO;

  private TAuditContentFlowDetailGxDAO auditContentFlowDetailGxDAO;

  private TAuditContentAuditHistoryGxDAO auditContentAuditHistoryGxDAO;

  private TAuditContentAuditInstGxDAO auditContentAuditInstGxDAO;

  @Override
  public void addPersonAuditComment(String personName, String comment) {
    // TODO Auto-generated method stub

  }

  @Override
  public String cancelAudit(String businessType, String sourceId, UserDto userDto) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void dispatchAudit(IAuditBusinessObject bo, TAuditInstance auditInstancePO) {
    // TODO Auto-generated method stub

  }

  @Override
  public void executeAudit(List<String> bussinessIds, String auditType, String auditInforId, UserDto auditor, String comment, Integer opType,
    String[] auditContentArray) {
    for (Iterator iterator = bussinessIds.iterator(); iterator.hasNext();) {
      String id = (String) iterator.next();
      executeAudit(id, auditType, auditInforId, auditor, comment, opType, auditContentArray);
    }
  }

  @Override
  public void executeAudit(String bussinessId, String auditType, String auditInforId, UserDto auditor, String comment, Integer opType,
    String[] auditContentArray) {

    //1.根据当前 审批模版inforId,businessId 查到所有本业务中最小的审批级数
    Integer orderPerCount = auditInstanceDAO.getMinOrder(auditInforId, bussinessId);
    //System.out.println("orderPerCount:"+orderPerCount);
    //2.根据当前 审批模版inforId,businessId,personId 得到本审批人在 审批序列中所处级数
    TAuditInstanceExample insExp = new TAuditInstanceExample();
    insExp.createCriteria().andFlowInforIdEqualTo(auditInforId).andBusinessIdEqualTo(bussinessId).andAuditPersonIdEqualTo(auditor.getId());
    insExp.setOrderByClause("audit_order desc");
    List<TAuditInstance> insList = auditInstanceDAO.selectByExample(insExp);
    if (insList == null || insList.size() == 0) {
      return;
    }
    TAuditInstance currIns = insList.get(0);

    //如果不相等，则不能审批
    if (orderPerCount == null || currIns == null || !orderPerCount.equals(currIns.getAuditOrder())) {
      return;
    }
    //如果相等，向history表中插入审批数据
    TAuditHistory auditHisPo = new TAuditHistory();
    auditHisPo.setId(GenerateSerial.getUUID());
    auditHisPo.setAuditComment(comment);
    auditHisPo.setAuditOpType(opType);
    auditHisPo.setAuditDegree(currIns.getAuditDegree());
    auditHisPo.setAuditOrder(currIns.getAuditOrder());
    auditHisPo.setAuditPerson(currIns.getAuditPerson());
    auditHisPo.setAuditPersonId(currIns.getAuditPersonId());
    auditHisPo.setAuditTime(new Date());
    auditHisPo.setBatchId(currIns.getBatchId());
    auditHisPo.setBusinessId(bussinessId);
    auditHisPo.setAuditTypeId(auditType);
    auditHistoryDAO.insert(auditHisPo);

    //    TAuditContentAuditInstGxExample insContentEx = new TAuditContentAuditInstGxExample();
    //    insContentEx.createCriteria().andAuditInsIdIn(insIds);//andAuditInsIdEqualTo(currIns.getId());
    //    List<TAuditContentAuditInstGx> conAuditList = auditContentAuditInstGxDAO.selectByExample(insContentEx);
    if (auditContentArray != null) {
      for (int i = 0; i < auditContentArray.length; i++) {
        TAuditContentAuditHistoryGx record = new TAuditContentAuditHistoryGx();
        record.setAuditHistoryId(auditHisPo.getId());
        record.setContentId(auditContentArray[i]);
        auditContentAuditHistoryGxDAO.insert(record);
      }
    }

    //删除当前审批人的Instance
    for (Iterator iterator = insList.iterator(); iterator.hasNext();) {
      TAuditInstance auditInstance = (TAuditInstance) iterator.next();
      //TAuditContentAuditInstGxExample insAuditContentDelExp = new TAuditContentAuditInstGxExample();
      //insAuditContentDelExp.createCriteria().andAuditInsIdEqualTo(auditInstance.getId());
      //      auditContentAuditInstGxDAO.deleteByExample(insAuditContentDelExp);//删除 实例之前，先删除实例的审批内容
      auditInstanceDAO.deleteByPrimaryKey(auditInstance.getId());
    }

    TAuditInstanceExample insExp2 = new TAuditInstanceExample();
    insExp2.createCriteria().andAuditDegreeEqualTo(0)//（0必须审批,1同级审批可跨过） 
      .andAuditOrderEqualTo(currIns.getAuditOrder()).andFlowInforIdEqualTo(auditInforId).andBusinessIdEqualTo(currIns.getBusinessId());
    int count = auditInstanceDAO.countByExample(insExp2);
    if (count == 0) {//当前审批人所处的级别中，如果所有审批度都为1的，则删除同级全部Instance
      TAuditInstanceExample insExp3 = new TAuditInstanceExample();
      insExp3.createCriteria().andAuditDegreeNotEqualTo(0).andAuditOrderEqualTo(currIns.getAuditOrder()).andFlowInforIdEqualTo(auditInforId)
        .andBusinessIdEqualTo(currIns.getBusinessId());
      auditInstanceDAO.deleteByExample(insExp3);
    }

    TAuditInstanceExample insExp4 = new TAuditInstanceExample();
    insExp4.createCriteria().andFlowInforIdEqualTo(auditInforId).andBusinessIdEqualTo(currIns.getBusinessId());
    count = auditInstanceDAO.countByExample(insExp4);
    IAuditBusinessObject obj = getAuditBusinessObject(String.valueOf(auditType));
    //IAuditBusinessObject obj = getAuditBusinessObject();
    if (count == 0 && opType == 1) {//如果审批流程执行完毕，则回调业务
      obj.setAbo(auditHisPo);
      //obj.loadBo(bussinessId);
      //if(opType == 1){//1审批通过，0审批退回
      obj.endAudit();
      //}
    }
    if (opType == 0) {
      obj.setAbo(auditHisPo);
      obj.cancelAudit();

      TAuditInstanceExample delInsEx = new TAuditInstanceExample();
      delInsEx.createCriteria().andFlowInforIdEqualTo(auditInforId).andBusinessIdEqualTo(currIns.getBusinessId());
      //如果是审批退回，把当前审批的状态，保存起来，下次提交，直接从退回人处提交
      List<TAuditInstance> inss = auditInstanceDAO.selectByExample(delInsEx);
      try {
        inss.add(currIns);
        for (Iterator iterator = inss.iterator(); iterator.hasNext();) {
          TAuditInstance auditInstance = (TAuditInstance) iterator.next();
          TAuditInstanceHistory record = new TAuditInstanceHistory();
          BeanUtils.copyProperties(record, auditInstance);
          auditInstanceHistoryDAO.insert(record);

          /*if (!auditInstance.getId().equals(currIns.getId())) {
            TAuditContentAuditInstGxExample insAuditContentDelExp = new TAuditContentAuditInstGxExample();
            insAuditContentDelExp.createCriteria().andAuditInsIdEqualTo(auditInstance.getId());
            auditContentAuditInstGxDAO.deleteByExample(insAuditContentDelExp);
          }*/
        }
      } catch (IllegalAccessException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      auditInstanceDAO.deleteByExample(delInsEx);

    }
  }

  @Override
  public String existsAuditFlow(String auditType, UserDto userDto) {
    TAuditFlowInforExample example = new TAuditFlowInforExample();
    example.createCriteria().andAuditTypeIdEqualTo(String.valueOf(auditType)).andDepartIdEqualTo(userDto.getDepartId()).andEnableEqualTo(1);
    List<TAuditFlowInfor> list = auditFlowInforDAO.selectByExample(example);
    if (list == null || list.size() != 1) {
      return null;
    }
    return list.get(0).getId();
  }

  @Override
  public String existsAuditFlow(String auditType, UserDto userDto, double sumLimit) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean existsAuditFlowDetail(String auditFlowId) {
    TAuditFlowDetailExample example = new TAuditFlowDetailExample();
    example.createCriteria().andFlowInforIdEqualTo(auditFlowId);
    return auditFlowDetailDAO.countByExample(example) > 0;
  }

  @Override
  public List getAuditInfo(String auditType, String sourceId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List getAuditTypeList() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List getUnAuditInfo(String auditType, String sourceId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List loadPersonAuditComment(String personName) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean sendAudit(IAuditBusinessObject po, Collection auditHistory, UserDto userDto, double sumLimit) {
    // TODO Auto-generated method stub
    String auditType = po.getBusinessType();
    List<TAuditContentDefDto> businessAuditContentlist = null;
    if (po instanceof IAuditContentBusinessObject) {
      businessAuditContentlist = ((IAuditContentBusinessObject) po).getBusinessAuditContents(po.getAbo().getBusinessId());
    }
    String flowId = existsAuditFlow(auditType, userDto);
    if (flowId == null)
      return false;

    TAuditFlowDetailExample fdEx = new TAuditFlowDetailExample();
    fdEx.createCriteria().andFlowInforIdEqualTo(flowId);
    List<TAuditFlowDetail> dList = auditFlowDetailDAO.selectByExample(fdEx);

    for (Iterator it = auditHistory.iterator(); it.hasNext();) {
      TAuditHistory auditHis = (TAuditHistory) it.next();
      for (Iterator iterator = dList.iterator(); iterator.hasNext();) {
        TAuditFlowDetail auditFlowDetail = (TAuditFlowDetail) iterator.next();
        if (businessAuditContentlist != null) {//如果此流程设置了审批内容，则判断当前 审批明细 时候 包含了 当前审批内容，如果没有包含，则不插入此审批实例
          if (!isInsContainAuditContent(businessAuditContentlist, auditFlowDetail))
            continue;
        }
        TAuditInstance ins = new TAuditInstance();
        ins.setId(GenerateSerial.getUUID());
        ins.setAuditDegree(auditFlowDetail.getAuditDegree());
        ins.setAuditOrder(auditFlowDetail.getAuditOrder());
        ins.setAuditPerson(auditFlowDetail.getAuditPersonName());
        ins.setAuditPersonId(auditFlowDetail.getAuditPersonId());
        ins.setBatchId(auditHis.getBatchId());
        ins.setBusinessId(auditHis.getBusinessId());
        ins.setFlowInforId(flowId);
        auditInstanceDAO.insert(ins);

        addInstanceContentGx(auditFlowDetail.getId(), ins.getId());

        TWorkTrustRecordExample ep = new TWorkTrustRecordExample();
        ep.createCriteria().andGetRightPersonIdEqualTo(auditFlowDetail.getAuditPersonId()).andOpTypeEqualTo(0);
        List<TWorkTrustRecord> wtlist = workTrustRecordDAO.selectByExample(ep);//查是否有被授权
        for (Iterator iterator2 = wtlist.iterator(); iterator2.hasNext();) {
          TWorkTrustRecord workTrustRecord = (TWorkTrustRecord) iterator2.next();
          TWorkTrustDetailExample detailExp = new TWorkTrustDetailExample();
          detailExp.createCriteria().andTabelNameEqualTo("t_audit_flow_detail").andTableIdEqualTo(auditFlowDetail.getId()).andTrustRecordIdEqualTo(
            workTrustRecord.getId());
          List<TWorkTrustDetail> wtdlist = workTrustDetailDAO.selectByExample(detailExp);
          for (Iterator iterator3 = wtdlist.iterator(); iterator3.hasNext();) {
            TWorkTrustDetail workTrustDetail = (TWorkTrustDetail) iterator3.next();
            TWorkTrustDetail record = new TWorkTrustDetail();
            record.setId(GenerateSerial.getUUID());
            record.setTabelName("t_audit_instance");
            record.setTableId(ins.getId());
            record.setTrustRecordId(workTrustRecord.getId());
            record.setUserId(workTrustDetail.getUserId());
            record.setUserName(workTrustDetail.getUserName());
            workTrustDetailDAO.insert(record);
          }
        }
      }
    }
    return true;
  }

  private void addInstanceContentGx(String flowDetailId, String insId) {
    // TODO Auto-generated method stub
    TAuditContentFlowDetailGxExample fexcontent = new TAuditContentFlowDetailGxExample();
    fexcontent.createCriteria().andFlowDetailIdEqualTo(flowDetailId);
    List<TAuditContentFlowDetailGx> acfd = auditContentFlowDetailGxDAO.selectByExample(fexcontent);
    for (Iterator iterator2 = acfd.iterator(); iterator2.hasNext();) {
      TAuditContentFlowDetailGx auditContentFlowDetailGx = (TAuditContentFlowDetailGx) iterator2.next();
      TAuditContentAuditInstGx re = new TAuditContentAuditInstGx();
      re.setAuditInsId(insId);
      re.setContentId(auditContentFlowDetailGx.getContentId());
      auditContentAuditInstGxDAO.insert(re);
    }
  }

  private boolean isInsContainAuditContent(List<TAuditContentDefDto> auditContentlist, TAuditFlowDetail auditFlowDetail) {
    boolean flowHasContent = false;
    TAuditContentFlowDetailGxExample fexcontent = new TAuditContentFlowDetailGxExample();
    fexcontent.createCriteria().andFlowDetailIdEqualTo(auditFlowDetail.getId());
    List<TAuditContentFlowDetailGx> acfd = auditContentFlowDetailGxDAO.selectByExample(fexcontent);
    if (acfd == null || acfd.size() == 0)
      return true;//如果当前审批流程没有设置 审批内容，则默认为期包含审批内容
    for (Iterator iterator2 = acfd.iterator(); iterator2.hasNext();) {
      TAuditContentFlowDetailGx auditContentFlowDetailGx = (TAuditContentFlowDetailGx) iterator2.next();
      for (Iterator iterator3 = auditContentlist.iterator(); iterator3.hasNext();) {
        TAuditContentDefDto auditContentDefDto = (TAuditContentDefDto) iterator3.next();
        if (auditContentFlowDetailGx.getContentId().equals(auditContentDefDto.getId())) {
          flowHasContent = true;
          break;
        }
      }
      if (flowHasContent)
        break;
    }
    return flowHasContent;
  }

  @Override
  public String submitBusiness(String businessType, String sourceId, UserDto userDto) {
    // TODO Auto-generated method stub
    IAuditBusinessObject obj = getAuditBusinessObject(businessType);
    //IAuditBusinessObject obj = getAuditBusinessObject();
    if (obj == null)
      return "审批类型[" + businessType + "]没有定义!";

    TAuditBatchRecordExample batchEx = new TAuditBatchRecordExample();
    batchEx.createCriteria().andAuditTypeEqualTo(businessType).andBusinessIdEqualTo(sourceId);
    Integer batchNumber = auditBatchRecordDAO.countByExample(batchEx);

    TAuditBatchRecord auditBatchPo = new TAuditBatchRecord();
    auditBatchPo.setId(GenerateSerial.getUUID());
    auditBatchPo.setAuditEnd(0);
    auditBatchPo.setBatchNumber(batchNumber + 1);
    auditBatchPo.setBusinessId(sourceId);
    auditBatchPo.setAuditType(businessType);
    auditBatchRecordDAO.insert(auditBatchPo);

    TAuditHistory bo = new TAuditHistory();
    bo.setAuditComment("提交审核");
    bo.setAuditOpType(3);
    bo.setAuditPerson(userDto.getTrueName());
    bo.setAuditPersonId(userDto.getId());
    bo.setAuditTime(new Date());
    bo.setAuditTypeId(businessType);
    bo.setBatchId(auditBatchPo.getId());
    bo.setId(GenerateSerial.getUUID());
    bo.setBusinessId(sourceId);
    obj.setAbo(bo);
    return submitBusiness(obj, userDto);
  }

  public IAuditBusinessObject getAuditBusinessObject(String businessType) {
    TAuditType auditTypePo = auditTypeDAO.selectByPrimaryKey(businessType);
    if (auditTypePo == null) {
      return null;
    }
    IAuditBusinessObject obj = null;
    try {
      obj = (IAuditBusinessObject) Class.forName(auditTypePo.getClassName()).newInstance();
    } catch (InstantiationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return obj;
  }

  @Override
  public String submitBusiness(IAuditBusinessObject po, UserDto userDto) {
    // TODO Auto-generated method stub
    String flowId = existsAuditFlow(po.getBusinessType(), userDto);
    if (flowId == null)
      return "没有定义审批模版!";
    if (!existsAuditFlowDetail(flowId)) {
      return "审批模版没有定义审批流程！";
    }
    ;
    List<TAuditHistory> list = new ArrayList<TAuditHistory>();
    list.add(po.getAbo());
    TAuditInstanceHistoryExample insHis = new TAuditInstanceHistoryExample();
    insHis.createCriteria().andBusinessIdEqualTo(po.getAbo().getBusinessId());
    List<TAuditInstanceHistory> insHisList = auditInstanceHistoryDAO.selectByExample(insHis);
    if (insHisList != null && insHisList.size() > 0) {
      try {
        for (Iterator iterator = insHisList.iterator(); iterator.hasNext();) {
          TAuditInstanceHistory auditInstanceHistory = (TAuditInstanceHistory) iterator.next();
          TAuditInstance ins = new TAuditInstance();
          BeanUtils.copyProperties(ins, auditInstanceHistory);
          ins.setBatchId(po.getAbo().getBatchId());
          auditInstanceDAO.insert(ins);

        }
        auditInstanceHistoryDAO.deleteByExample(insHis);
      } catch (IllegalAccessException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      auditHistoryDAO.insert(po.getAbo());
      po.submitAudit();
      return null;
    } else {
      boolean rt = sendAudit(po, list, userDto, 0);
      if (rt) {
        auditHistoryDAO.insert(po.getAbo());
        po.submitAudit();
      } else {
        return "送审失败！";
      }
    }
    return null;
  }

  @Override
  public String submitBusiness(String businessType, String sourceId, UserDto userDto, String ownerDeptId) {
    // TODO Auto-generated method stub
    return null;
  }

  public TAuditBatchRecordDAO getAuditBatchRecordDAO() {
    return auditBatchRecordDAO;
  }

  public void setAuditBatchRecordDAO(TAuditBatchRecordDAO auditBatchRecordDAO) {
    this.auditBatchRecordDAO = auditBatchRecordDAO;
  }

  public TAuditFlowDetailDAO getAuditFlowDetailDAO() {
    return auditFlowDetailDAO;
  }

  public void setAuditFlowDetailDAO(TAuditFlowDetailDAO auditFlowDetailDAO) {
    this.auditFlowDetailDAO = auditFlowDetailDAO;
  }

  public TAuditFlowInforDAO getAuditFlowInforDAO() {
    return auditFlowInforDAO;
  }

  public void setAuditFlowInforDAO(TAuditFlowInforDAO auditFlowInforDAO) {
    this.auditFlowInforDAO = auditFlowInforDAO;
  }

  public TAuditHistoryDAO getAuditHistoryDAO() {
    return auditHistoryDAO;
  }

  public void setAuditHistoryDAO(TAuditHistoryDAO auditHistoryDAO) {
    this.auditHistoryDAO = auditHistoryDAO;
  }

  public TAuditInstanceDAO getAuditInstanceDAO() {
    return auditInstanceDAO;
  }

  public void setAuditInstanceDAO(TAuditInstanceDAO auditInstanceDAO) {
    this.auditInstanceDAO = auditInstanceDAO;
  }

  public TAuditTypeDAO getAuditTypeDAO() {
    return auditTypeDAO;
  }

  public void setAuditTypeDAO(TAuditTypeDAO auditTypeDAO) {
    this.auditTypeDAO = auditTypeDAO;
  }

  public IAuditBusinessObject getAuditBusinessObject() {
    return auditBusinessObject;
  }

  @Override
  public void setAuditBusinessObject(IAuditBusinessObject auditBusinessObject) {
    // TODO Auto-generated method stub
    this.auditBusinessObject = auditBusinessObject;
  }

  @Override
  public PaginationSupport findAlreadyAuditBusinessInfor(Map params, UserDto auditor, String auditType, int startIndex, int pageSize) {
    // TODO Auto-generated method stub
    TAuditHistoryExample insexp = new TAuditHistoryExample();
    insexp.createCriteria().andAuditPersonIdEqualTo(auditor.getId()).andAuditTypeIdEqualTo(auditType);
    int count = auditHistoryDAO.countByExample(insexp);
    List list = auditHistoryDAO.getPersonsAuditHistroyRecord(auditor.getId(), auditType, startIndex, pageSize);
    IAuditBusinessObject abo = getAuditBusinessObject(String.valueOf(auditType));
    List businessList = new ArrayList();
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
      com.tl.resource.dao.pojo.TAuditHistory his = (com.tl.resource.dao.pojo.TAuditHistory) iterator.next();
      abo.loadBo(his.getBusinessId());
      businessList.add(abo.getBo());
    }
    PaginationSupport pageInfor = new PaginationSupport(businessList, count, pageSize, startIndex);
    pageInfor.setHeaders(abo.getShowColumns());
    return pageInfor;
  }

  @Override
  public List<AuditTypeFlowInforDto> findAlreadyAuditTypeInfor(UserDto user) {
    // TODO Auto-generated method stub
    List<AuditTypeFlowInforDto> nlist = new ArrayList<AuditTypeFlowInforDto>();
    List<AuditTypeFlowInfor> list = auditDao.findAlreadyAuditTypeInfor(user);
    if (list != null) {
      try {
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
          AuditTypeFlowInfor auditTypeFlowInfor = (AuditTypeFlowInfor) iterator.next();
          AuditTypeFlowInforDto dto = new AuditTypeFlowInforDto();
          BeanUtils.copyProperties(dto, auditTypeFlowInfor);
          nlist.add(dto);
        }
      } catch (IllegalAccessException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return nlist;
  }

  @Override
  public PaginationSupport findWaitAuditBusinessInfor(Map params, UserDto auditor, String auditType, String flowInforId, int startIndex, int pageSize) {
    int count = auditInstanceDAO.getAuditorsInsCount(auditor.getId(), flowInforId);
    List list = auditInstanceDAO.getAuditorsIns(auditor.getId(), flowInforId, startIndex, pageSize);
    IAuditBusinessObject abo = getAuditBusinessObject(String.valueOf(auditType));
    List businessList = new ArrayList();
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
      com.tl.resource.business.dto.AuditInstanceDto ins = (com.tl.resource.business.dto.AuditInstanceDto) iterator.next();
      try {
        abo.loadBo(ins.getBusinessId());
      } catch (NullPointerException e) {//如果装载业务数据失败，则继续装载其他的业务数据，不要因为一个失败导致所有都失败
        // TODO Auto-generated catch block
        e.printStackTrace();
        continue;
      }
      LinkBusinessObject bo = abo.getBo();
      bo.addProperty("trustPersonId", ins.getUserId());
      bo.addProperty("trustPersonName", ins.getUserName());
      businessList.add(bo);
    }
    PaginationSupport pageInfor = new PaginationSupport(businessList, count, pageSize, startIndex);
    pageInfor.setHeaders(abo.getShowColumns());
    return pageInfor;
  }

  @Override
  public List<AuditTypeFlowInforDto> findWaitAuditTypeInfor(UserDto user) {
    // TODO Auto-generated method stub
    List<AuditTypeFlowInforDto> nlist = new ArrayList<AuditTypeFlowInforDto>();
    List<AuditTypeFlowInfor> list = auditDao.findWaitAuditTypeInfor(user);
    if (list != null) {
      try {
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
          AuditTypeFlowInfor auditTypeFlowInfor = (AuditTypeFlowInfor) iterator.next();
          AuditTypeFlowInforDto dto = new AuditTypeFlowInforDto();
          BeanUtils.copyProperties(dto, auditTypeFlowInfor);
          nlist.add(dto);
        }
      } catch (IllegalAccessException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return nlist;
  }

  public AuditDao getAuditDao() {
    return auditDao;
  }

  public void setAuditDao(AuditDao auditDao) {
    this.auditDao = auditDao;
  }

  @Override
  public List<TAuditHistoryDto> findAuditHistoryInfor(String businessId, String batchNumber) {
    // TODO Auto-generated method stub

    return auditDao.findAuditHistoryInfor(businessId, batchNumber);
  }

  @Override
  public List<TAuditBatchRecordDto> findAuditBatchRecordByBusinessId(String businessId) {
    TAuditBatchRecordExample e = new TAuditBatchRecordExample();
    e.createCriteria().andBusinessIdEqualTo(businessId);
    e.setOrderByClause("batch_number");
    List<TAuditBatchRecordDto> nList = new ArrayList<TAuditBatchRecordDto>();
    List<TAuditBatchRecord> list = auditBatchRecordDAO.selectByExample(e);
    try {
      for (Iterator iterator = list.iterator(); iterator.hasNext();) {
        TAuditBatchRecord auditBatchRecord = (TAuditBatchRecord) iterator.next();
        TAuditBatchRecordDto tt = new TAuditBatchRecordDto();
        BeanUtils.copyProperties(tt, auditBatchRecord);
        nList.add(tt);
      }
    } catch (IllegalAccessException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    } catch (InvocationTargetException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    return nList;
  }

  public TAuditInstanceHistoryDAO getAuditInstanceHistoryDAO() {
    return auditInstanceHistoryDAO;
  }

  public void setAuditInstanceHistoryDAO(TAuditInstanceHistoryDAO auditInstanceHistoryDAO) {
    this.auditInstanceHistoryDAO = auditInstanceHistoryDAO;
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

  public TAuditContentFlowDetailGxDAO getAuditContentFlowDetailGxDAO() {
    return auditContentFlowDetailGxDAO;
  }

  public void setAuditContentFlowDetailGxDAO(TAuditContentFlowDetailGxDAO auditContentFlowDetailGxDAO) {
    this.auditContentFlowDetailGxDAO = auditContentFlowDetailGxDAO;
  }

  public TAuditContentAuditHistoryGxDAO getAuditContentAuditHistoryGxDAO() {
    return auditContentAuditHistoryGxDAO;
  }

  public void setAuditContentAuditHistoryGxDAO(TAuditContentAuditHistoryGxDAO auditContentAuditHistoryGxDAO) {
    this.auditContentAuditHistoryGxDAO = auditContentAuditHistoryGxDAO;
  }

  public TAuditContentAuditInstGxDAO getAuditContentAuditInstGxDAO() {
    return auditContentAuditInstGxDAO;
  }

  public void setAuditContentAuditInstGxDAO(TAuditContentAuditInstGxDAO auditContentAuditInstGxDAO) {
    this.auditContentAuditInstGxDAO = auditContentAuditInstGxDAO;
  }

}
