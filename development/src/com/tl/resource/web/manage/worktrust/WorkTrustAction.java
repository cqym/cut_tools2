package com.tl.resource.web.manage.worktrust;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.common.util.LoginInforUtil;
import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.manage.WorkTrustService;

public class WorkTrustAction extends DispatchAction {
  private WorkTrustService workTrustService;

  public ActionForward getOrToRight(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    String name = workTrustService.getOrToRight(loginInfor.getUser().getId());
    List list = workTrustService.getGrantRightUserList(loginInfor.getUser());
    HashMap rt = new HashMap();
    rt.put("getRightUserName", name);
    rt.put("grantRightUserList", list);
    String rtpara = JSONObject.fromObject(rt).toString();
    out.print(rtpara);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward cancelRight(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    String authorUserId = request.getParameter("authorUserId");
    workTrustService.cancelRight(loginInfor, authorUserId);
    return null;
  }

  public ActionForward grandRight(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    String getRightUserId = request.getParameter("getRightUserId");
    String authorUserId = request.getParameter("authorUserId");
    String rt = workTrustService.grandRight(getRightUserId, authorUserId, loginInfor);
    PrintWriter out = response.getWriter();
    out.print("{msg:'" + rt + "'}");
    out.flush();
    out.close();
    return null;
  }

  public ActionForward getUserListWorkTrust(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String startIndex = request.getParameter("start");
    String pageSize = request.getParameter("limit");
    if (startIndex == null) {
      startIndex = "0";
    }
    if (pageSize == null) {
      pageSize = "10";
    }
    PrintWriter out = response.getWriter();
    Map<String, Object> p = new HashMap<String, Object>();
    PaginationSupport pageList = workTrustService.getUserListWorkTrust(p, Integer.valueOf(startIndex), Integer.valueOf(pageSize));
    String rt = JSONObject.fromObject(pageList).toString();
    out.print(rt);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward getUserWorkTrustHistory(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String startIndex = request.getParameter("start");
    String pageSize = request.getParameter("limit");
    if (startIndex == null) {
      startIndex = "0";
    }
    if (pageSize == null) {
      pageSize = "10";
    }
    PrintWriter out = response.getWriter();
    Map<String, Object> p = new HashMap<String, Object>();
    p.put("userId", request.getParameter("userId"));
    PaginationSupport pageList = workTrustService.getUserWorkTrustHistory(p, Integer.valueOf(startIndex), Integer.valueOf(pageSize));
    String rt = JSONObject.fromObject(pageList).toString();
    out.print(rt);
    out.flush();
    out.close();
    return null;
  }

  /**
   * 得到当前用户的委托人
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward getGrantRightUserList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");

    PrintWriter out = response.getWriter();
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    List list = workTrustService.getGrantRightUserList(loginInfor.getUser());
    String rt = JSONObject.fromObject(list).toString();
    out.print(rt);
    out.flush();
    out.close();
    return null;
  }

  public WorkTrustService getWorkTrustService() {
    return workTrustService;
  }

  public void setWorkTrustService(WorkTrustService workTrustService) {
    this.workTrustService = workTrustService;
  }

}
