package com.tl.resource.web.audit;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.common.util.LoginInforUtil;
import com.tl.common.util.PaginationSupport;
import com.tl.resource.audit.IAuditService;
import com.tl.resource.audit.dto.AuditTypeFlowInforDto;
import com.tl.resource.audit.dto.TAuditBatchRecordDto;
import com.tl.resource.audit.dto.TAuditHistoryDto;
import com.tl.resource.business.dto.LoginInforDto;

public class AuditInforMangeAction extends DispatchAction {
  private IAuditService auditService;

  public ActionForward findWaitAuditTypeInfor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    //System.out.println("............................findWaitAuditTypeInfor.......=====================================");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    if (loginInfor != null) {
      List<AuditTypeFlowInforDto> list = auditService.findWaitAuditTypeInfor(loginInfor.getUser());
      out.println(JSONArray.fromObject(list).toString());
      out.flush();
      out.close();
    } else {

    }
    return null;
  }

  public ActionForward findAlreadyAuditTypeInfor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    if (loginInfor != null) {
      List<AuditTypeFlowInforDto> list = auditService.findAlreadyAuditTypeInfor(loginInfor.getUser());
      out.println(JSONArray.fromObject(list).toString());
      out.flush();
      out.close();
    } else {

    }
    return null;
  }

  public ActionForward findWaitAuditBusinessInfor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setCharacterEncoding("utf-8");
    String auditType = request.getParameter("auditType");
    String flowInforId = request.getParameter("flowInforId");
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

      PaginationSupport pageInfor = auditService.findWaitAuditBusinessInfor(new HashMap(), loginInfor.getUser(), auditType, flowInforId, Integer
        .valueOf(startIndex), Integer.valueOf(pageSize));
      out.println(JSONObject.fromObject(pageInfor).toString());
      out.flush();
      out.close();
    } else {

    }
    return null;
  }

  public ActionForward findAlreadyAuditBusinessInfor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setCharacterEncoding("utf-8");
    String auditType = request.getParameter("auditType");
    String flowInforId = request.getParameter("flowInforId");
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
      PaginationSupport pageInfor = auditService.findAlreadyAuditBusinessInfor(new HashMap(), loginInfor.getUser(), auditType, Integer
        .valueOf(startIndex), Integer.valueOf(pageSize));
      out.println(JSONObject.fromObject(pageInfor).toString());
      out.flush();
      out.close();
    } else {

    }
    return null;
  }

  public ActionForward executeAudit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setCharacterEncoding("utf-8");
    String[] bussinessIds = request.getParameterValues("bussinessIds");
    String auditInforId = request.getParameter("auditInforId");
    String comment = request.getParameter("comment");
    String opType = request.getParameter("opType");
    String auditType = request.getParameter("auditType");
    String[] auditContentArray = request.getParameterValues("auditContentArray");
    PrintWriter out = response.getWriter();
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    if (loginInfor != null) {
      List<String> sList = new ArrayList<String>();
      for (int i = 0; i < bussinessIds.length; i++) {
        sList.add(bussinessIds[i]);
      }
      auditService.executeAudit(sList, auditType, auditInforId, loginInfor.getUser(), comment, Integer.valueOf(opType), auditContentArray);
      out.print(true);
      out.flush();
      out.close();
    } else {

    }
    return null;
  }

  public ActionForward findAuditInfor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setCharacterEncoding("utf-8");
    String businessId = request.getParameter("businessId");
    String batchNumber = request.getParameter("batchNumber");
    PrintWriter out = response.getWriter();
    List<TAuditHistoryDto> list = auditService.findAuditHistoryInfor(businessId, batchNumber);
    String rt = JSONArray.fromObject(list).toString();
    //System.out.println(rt);
    out.println(rt);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward findAuditBatchRecord(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setCharacterEncoding("utf-8");
    String businessId = request.getParameter("businessId");
    PrintWriter out = response.getWriter();
    List<TAuditBatchRecordDto> list = auditService.findAuditBatchRecordByBusinessId(businessId);
    out.println(JSONArray.fromObject(list));
    out.flush();
    out.close();
    return null;
  }

  public IAuditService getAuditService() {
    return auditService;
  }

  public void setAuditService(IAuditService auditService) {
    this.auditService = auditService;
  }

}
