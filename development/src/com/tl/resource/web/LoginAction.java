package com.tl.resource.web;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.common.util.LoginInforUtil;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.manage.UsersService;

public class LoginAction extends DispatchAction {
  private UsersService usersService;

  public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String userName = request.getParameter("userName");
    String passsword = request.getParameter("password");
    LoginInforDto loginInfor = usersService.login(userName, passsword);
    if (loginInfor != null && loginInfor.getUser() != null && !new Integer(1).equals(loginInfor.getUser().getValid())) {
      LoginInforUtil.setLoginInfor(request, loginInfor);
      request.setAttribute("LoginInfor", JSONObject.fromObject(loginInfor));
      if ("admin".equals(loginInfor.getUser().getUserName())) {
        return mapping.findForward("loginManageSuccess");
      } else {
        return mapping.findForward("loginSuccess");
      }
    }
    return mapping.findForward("loginFail");
  }

  public ActionForward getUserModules(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    LoginInforDto loginInfor = (LoginInforDto) LoginInforUtil.getLoginInfor(request);
    if (loginInfor != null) {
      out.println(JSONArray.fromObject(loginInfor.getModules()).toString());
    } else {
      out.println(false);
    }
    out.flush();
    out.close();
    return null;
  }

  public ActionForward autoFlush(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    LoginInforUtil.getLoginInfor(request);
    return null;
  }

  public UsersService getUsersService() {
    return usersService;
  }

  public void setUsersService(UsersService usersService) {
    this.usersService = usersService;
  }

}
