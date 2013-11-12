package com.tl.resource.audit;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.audit.dto.AuditTypeFlowInforDto;
import com.tl.resource.audit.dto.TAuditBatchRecordDto;
import com.tl.resource.audit.dto.TAuditHistoryDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.dao.pojo.TAuditInstance;

public interface IAuditService {
  /**
   * 提交审批，流程开始创建
   * @param businessType 业务类型
   * @param sourceId 业务id
   * @param loginInfo 登陆信息
   * @return 长度为0成功
   */

  public String submitBusiness(String businessType, String sourceId, UserDto userDto);

  /**
   * 提交审批，流程开始创建
   * @param po
   * @param loginInfo
   * @return
   */

  public String submitBusiness(IAuditBusinessObject po, UserDto userDto);

  /**
   * 完成提交送审功能
   * 
   * @param auditType
   * @param sourceIds
   *            业务数据ID集合
   * @param loginInfo
   * @return true:送审成功，false:
   */
  boolean sendAudit(IAuditBusinessObject po, Collection auditHistory, UserDto userDto, double sumLimit);

  /**
   * 执行审批功能
   * @param auditContentArray 
   * 
   * @param 
   * @param 
   *            
   * @param 
   *            
   */
  public void executeAudit(List<String> bussinessIds, String auditType, String auditInforId, UserDto auditor, String comment, Integer opType,
    String[] auditContentArray);

  /**
   * 执行审批功能
   * @param 
   * @param 
   */
  public void executeAudit(String bussinessId, String auditType, String auditInforId, UserDto auditor, String comment, Integer opType,
    String[] auditContentArray);

  /**
   * 根据审批类别和用户信息判断是否存在审批模板
   * 
   * @param auditType
   * @param loginInfo
   * @return 如果存在一个审批模板返回审批模板ID，有多个或没有返回-1
   */
  public String existsAuditFlow(String auditType, UserDto userDto);

  /**
   * 根据审批类别、用户信息和限制金额判断是否存在审批模板，适用于按金额审批合同
   * 
   * @param auditType
   * @param loginInfo
   * @return 如果存在一个审批模板返回审批模板ID，有多个或没有返回-1
   */
  public String existsAuditFlow(String auditType, UserDto userDto, double sumLimit);

  /**
   * 根据审批流程模板ID判断是否存在审批人
   * 
   * @param auditFlowId
   * @return false:不存在审批人,true:至少有一个审批人
   */
  public boolean existsAuditFlowDetail(String auditFlowId);

  /**
   * 根据审批类型及业务数据ID获取审批信息
   * 
   * @param auditType
   * @param sourceId
   * @return 审批信息列表，包装对象为HashMap
   */
  public List getAuditInfo(String auditType, String sourceId);

  public List getUnAuditInfo(String auditType, String sourceId);

  /**
   * 获取审批类型列表
   * 
   * @return
   */
  public List getAuditTypeList();

  /**
   * 发起人取消提交审批
   * @param businessType 业务类型
   * @param sourceId 业务id
   * @param loginInfo 登陆信息
   * @return 长度为0成功
   */
  public String cancelAudit(String businessType, String sourceId, UserDto userDto);

  public String submitBusiness(String businessType, String sourceId, UserDto userDto, String ownerDeptId);

  /**
   * 根据用户名称获得使用次数最多的审批意见集合
   * @param personName
   * @return
   */
  public List loadPersonAuditComment(String personName);

  /**
   * 增加用户的审批意见记录
   * @param personName用户名
   * @param comment审批意见
   */
  public void addPersonAuditComment(String personName, String comment);

  public void dispatchAudit(IAuditBusinessObject bo, TAuditInstance tAuditInstancePO);

  public void setAuditBusinessObject(IAuditBusinessObject auditBusinessObject);

  /**
   * 得到待审批类型 信息
   * @param user
   * @return
   */
  public List<AuditTypeFlowInforDto> findWaitAuditTypeInfor(UserDto user);

  /**
   * 得到已经审批类型 信息
   * @param user
   * @return
   */
  public List<AuditTypeFlowInforDto> findAlreadyAuditTypeInfor(UserDto user);

  public PaginationSupport findWaitAuditBusinessInfor(Map params, UserDto auditor, String auditType, String flowInforId, int startIndex, int pageSize);

  public PaginationSupport findAlreadyAuditBusinessInfor(Map params, UserDto auditor, String auditType, int startIndex, int pageSize);

  public List<TAuditHistoryDto> findAuditHistoryInfor(String businessId, String batchNumber);

  public List<TAuditBatchRecordDto> findAuditBatchRecordByBusinessId(String businessId);

  public IAuditBusinessObject getAuditBusinessObject(String businessType);

}
