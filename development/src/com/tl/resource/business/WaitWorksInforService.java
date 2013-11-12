package com.tl.resource.business;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.LoginInforDto;

public interface WaitWorksInforService {
  public int findWaitAuditInfors(LoginInforDto loginInfor);

  public PaginationSupport findWaitQuotation2ContractInfors(LoginInforDto loginInfor, int startIndex, int pageSize);

  public int findWaitQuotation2ContractCount(LoginInforDto loginInfor);

  public PaginationSupport findWaitContract2OrderInfors(LoginInforDto loginInfor, int startIndex, int pageSize, int orderType);

  public int findWaitContract2OrderCount(LoginInforDto loginInfor, int orderType);

  /**
   *待做预定订单的报价单
   * @param loginInfor
   * @return
   */
  public int findWaitExpectedQuotation2OrderCount(LoginInforDto loginInfor);

  /**
   * 待做预定加工订单的报价单
   * @param loginInfor
   * @return
   */
  public int findWaitExpectedQuotation2SelfOrderCount(LoginInforDto loginInfor);

  /**
   * 待做试刀订单的报价单
   * @param loginInfor
   * @return
   */
  public int findWaitTryToolsQuotation2OrderCount(LoginInforDto loginInfor);

  /**
   * 待做试刀加工订单的报价单
   * @param loginInfor
   * @return
   */
  public int findWaitTryToolsQuotation2SelfOrderCount(LoginInforDto loginInfor);

  /**
   * 未上传附件合同个数
   * @param loginInfor
   * @return
   */
  public int getContractCountCouldUploadFile(LoginInforDto loginInfor);

  /**
   * 预定报价 到期 未转正式报价 个数
   * @param loginInfor
   * @return
   */
  public int getExpectedQuo2QuoCount(LoginInforDto loginInfor);

  /**
   * 试刀申请已到试刀开始时间，还为交货，给物流提示
   * @param loginInfor
   * @return
   */
  public int getTryTools2DeliveryCount(LoginInforDto loginInfor);

  /**
   * 试刀申请 已到结束时间 还未传 试刀报告
   * @param loginInfor
   * @return
   */
  public int getTryTools2UploadReportCount(LoginInforDto loginInfor);

  /**
   * 试刀申请 已开始 还未传 试刀申请
   * @param loginInfor
   * @return
   */
  public int getTryTools2UploadApplyCount(LoginInforDto loginInfor);

  public int findWaitSelfOrder2Plan(LoginInforDto loginInfor);

  public int findWait2PlanOrderPlanCount(LoginInforDto loginInfor);
}
