package com.tl.resource.web.manage.mainPage;

import java.io.PrintWriter;
import java.util.Date;
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
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.manage.CompanyReportService;
import com.tl.resource.dao.pojo.CompanyReports;

public class CompanyReportAction extends DispatchAction {
  private CompanyReportService companyReportService;

  public ActionForward addReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    JSONObject companyReportJson = JSONObject.fromObject(request.getParameter("companyReport"));
    CompanyReports cr = (CompanyReports) JSONObject.toBean(companyReportJson, CompanyReports.class);
    cr.setEditDate(new Date());
    cr.setEditUserId(user.getId());
    cr.setEditUserName(user.getUserName());
    try {
      companyReportService.addCompanyReport(cr);
      out.print("success");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      out.print(e.getMessage());
    }
    out.flush();
    out.close();
    return null;
  }

  public ActionForward updateReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    JSONObject companyReportJson = JSONObject.fromObject(request.getParameter("companyReport"));
    CompanyReports cr = (CompanyReports) JSONObject.toBean(companyReportJson, CompanyReports.class);
    cr.setEditDate(new Date());
    cr.setEditUserId(user.getId());
    cr.setEditUserName(user.getUserName());
    try {
      companyReportService.updateCompanyReport(cr);
      out.print("success");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      out.print(e.getMessage());
    }
    out.flush();
    out.close();
    return null;
  }

  public ActionForward deleteById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    try {
      companyReportService.deleteById(request.getParameter("id"));
      out.print("success");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      out.print(e.getMessage());
    }
    out.flush();
    out.close();
    return null;
  }

  public ActionForward findReports(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    try {
      PaginationSupport pageInfor = companyReportService.findReports4Page(new HashMap<String, String>(), 0, Integer.MAX_VALUE);
      out.print(JSONObject.fromObject(pageInfor).toString());
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      out.print(e.getMessage());
    }
    out.flush();
    out.close();
    return null;
  }

  public ActionForward getReportLast(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    try {
      out.print(JSONObject.fromObject(companyReportService.getReportLast()).toString());
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      out.print(e.getMessage());
    }
    out.flush();
    out.close();
    return null;
  }

  public CompanyReportService getCompanyReportService() {
    return companyReportService;
  }

  public void setCompanyReportService(CompanyReportService companyReportService) {
    this.companyReportService = companyReportService;
  }

}
