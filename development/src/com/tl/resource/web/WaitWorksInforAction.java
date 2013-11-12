package com.tl.resource.web;

import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.common.util.LoginInforUtil;
import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.WaitWorksInforService;
import com.tl.resource.business.contractOrder.ContractOrderService;
import com.tl.resource.business.dto.LoginInforDto;

public class WaitWorksInforAction extends DispatchAction {
  private WaitWorksInforService waitWorksInforService;

  private ContractOrderService contractOrderService;

  public ActionForward requestInfos(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    try {

      request.setCharacterEncoding("utf-8");
      response.setCharacterEncoding("utf-8");
      PrintWriter out = response.getWriter();
      LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
      int waitAuditCount = waitWorksInforService.findWaitAuditInfors(loginInfor);//待审核
      int waitorderConCount = waitWorksInforService.findWaitContract2OrderCount(loginInfor, 1);//需要编制合同订单的合同数
      int waitSelfOrderConCount = waitWorksInforService.findWaitContract2OrderCount(loginInfor, 0);//需要编制加工订单的合同数
      int waitContractQuoCount = waitWorksInforService.findWaitQuotation2ContractCount(loginInfor);//需要编制合同的报价单数
      int waitExpectedQuoCount = waitWorksInforService.findWaitExpectedQuotation2OrderCount(loginInfor);//需要编制预订订单的预订报价单数
      int waitExpectedQuo4SelfOrderCount = waitWorksInforService.findWaitExpectedQuotation2SelfOrderCount(loginInfor);//需要编制预订加工订单的预订报价单数
      int waitTryToolsQuoCount = waitWorksInforService.findWaitTryToolsQuotation2OrderCount(loginInfor);//需要编制试刀订单的试刀申请数
      int waitTryToolsQuo4SelfOrderCount = waitWorksInforService.findWaitTryToolsQuotation2SelfOrderCount(loginInfor);//需要编制试刀加工订单的试刀申请数
      int contractCountCouldUploadFile = waitWorksInforService.getContractCountCouldUploadFile(loginInfor);//需要上传附件的合同数
      int expectedQuo2QuoCount = waitWorksInforService.getExpectedQuo2QuoCount(loginInfor);//需要转正式报价单的 预订报价单数
      int tryTools2DeliveryCount = waitWorksInforService.getTryTools2DeliveryCount(loginInfor);//需要交货的试刀申请
      int tryTools2UploadReportCount = waitWorksInforService.getTryTools2UploadReportCount(loginInfor);//需要上传试刀报告的试刀申请数
      int tryTools2UploadApplyCount = waitWorksInforService.getTryTools2UploadApplyCount(loginInfor);//需要上传试刀申请的的试刀申请数
      int wait2PlanSelfOrderCount = waitWorksInforService.findWaitSelfOrder2Plan(loginInfor);//需要编制计划的加工订单数
      int wait2PlanOrderPlanCount = waitWorksInforService.findWait2PlanOrderPlanCount(loginInfor);//需要编制材料采购订单的计划数
      HashMap<String, Integer> h = new HashMap<String, Integer>();
      h.put("waitAuditCount", waitAuditCount);
      h.put("waitorderConCount", waitorderConCount);
      h.put("waitContractQuoCount", waitContractQuoCount);
      h.put("waitExpectedQuoCount", waitExpectedQuoCount);
      h.put("waitExpectedQuo4SelfOrderCount", waitExpectedQuo4SelfOrderCount);
      h.put("waitTryToolsQuoCount", waitTryToolsQuoCount);
      h.put("waitTryToolsQuo4SelfOrderCount", waitTryToolsQuo4SelfOrderCount);
      h.put("contractCountCouldUploadFile", contractCountCouldUploadFile);
      h.put("expectedQuo2QuoCount", expectedQuo2QuoCount);
      h.put("tryTools2DeliveryCount", tryTools2DeliveryCount);
      h.put("tryTools2UploadReportCount", tryTools2UploadReportCount);
      h.put("waitSelfOrderConCount", waitSelfOrderConCount);
      h.put("tryTools2UploadApplyCount", tryTools2UploadApplyCount);
      h.put("wait2PlanSelfOrderCount", wait2PlanSelfOrderCount);
      h.put("wait2PlanOrderPlanCount", wait2PlanOrderPlanCount);
      out.println(JSONObject.fromObject(h));
      out.flush();
      out.close();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  public ActionForward findWaitContract2OrderInfors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setCharacterEncoding("utf-8");
    String startIndex = request.getParameter("startIndex");
    String pageSize = request.getParameter("pageSize");

    if (startIndex == null) {
      startIndex = "0";
    }
    if (pageSize == null) {
      pageSize = "10";
    }
    String orderTypep = request.getParameter("orderType");
    orderTypep = orderTypep == null ? "0" : orderTypep;//0 加工，1采购

    PrintWriter out = response.getWriter();
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    if (loginInfor != null) {

      PaginationSupport pageInfor = waitWorksInforService.findWaitContract2OrderInfors(loginInfor, Integer.valueOf(startIndex), Integer
        .valueOf(pageSize), Integer.valueOf(orderTypep));
      if (pageInfor == null)
        return null;
      out.println(JSONObject.fromObject(pageInfor).toString());
      out.flush();
      out.close();
    } else {

    }
    return null;
  }

  public ActionForward findWaitQuotation2ContractInfors(ActionMapping mapping, ActionForm form, HttpServletRequest request,
    HttpServletResponse response) throws Exception {
    response.setCharacterEncoding("utf-8");
    String startIndex = request.getParameter("startIndex");
    String pageSize = request.getParameter("pageSize");
    if (startIndex == null) {
      startIndex = "0";
    }
    if (pageSize == null) {
      pageSize = "10";
    }
    PrintWriter out = response.getWriter();
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);

    if (loginInfor != null) {
      PaginationSupport pageInfor = waitWorksInforService.findWaitQuotation2ContractInfors(loginInfor, Integer.valueOf(startIndex), Integer
        .valueOf(pageSize));
      if (pageInfor == null)
        return null;
      out.println(JSONObject.fromObject(pageInfor).toString());
      out.flush();
      out.close();
    } else {

    }
    return null;
  }

  public WaitWorksInforService getWaitWorksInforService() {
    return waitWorksInforService;
  }

  public void setWaitWorksInforService(WaitWorksInforService waitWorksInforService) {
    this.waitWorksInforService = waitWorksInforService;
  }

  public ContractOrderService getContractOrderService() {
    return contractOrderService;
  }

  public void setContractOrderService(ContractOrderService contractOrderService) {
    this.contractOrderService = contractOrderService;
  }

}
