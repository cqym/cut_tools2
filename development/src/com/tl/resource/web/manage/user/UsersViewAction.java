package com.tl.resource.web.manage.user;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.RolesDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.manage.UsersService;
import com.tl.resource.dao.pojo.TUserInfor;

public class UsersViewAction extends DispatchAction {
  private UsersService usersService;

  public ActionForward getUsersByPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setCharacterEncoding("utf-8");
    request.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    Map<String, String> params = new HashMap<String, String>();
    params.put("userName", request.getParameter("userName"));
    params.put("trueName", request.getParameter("trueName"));
    params.put("onlyValid", request.getParameter("onlyValid"));
    int startIndex = request.getParameter("start") == null ? 0 : Integer.valueOf(request.getParameter("start"));
    int pageSize = request.getParameter("limit") == null ? PaginationSupport.PAGESIZE : Integer.valueOf(request.getParameter("limit"));
    PaginationSupport pageObj = usersService.findUsers(params, startIndex, pageSize);
    String rt = JSONObject.fromObject(pageObj).toString();
    out.println(rt);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward getUsersRolesDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setCharacterEncoding("utf-8");
    request.setCharacterEncoding("utf-8");
    String userId = request.getParameter("userId");
    PrintWriter out = response.getWriter();
    List<RolesDto> list = usersService.findUserRoles(userId);
    out.println(JSONArray.fromObject(list).toString());
    out.flush();
    out.close();
    return null;
  }

  public ActionForward getSelectedUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setCharacterEncoding("utf-8");
    request.setCharacterEncoding("utf-8");
    String userId = request.getParameter("userId");
    String businessType = request.getParameter("businessType");
    PrintWriter out = response.getWriter();
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("userId", userId);
    params.put("businessType", businessType);
    List<TUserInfor> list = usersService.getSelectedUser(params);
    out.println(JSONArray.fromObject(list).toString());
    out.flush();
    out.close();
    return null;
  }

  public ActionForward getWillSelectUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setCharacterEncoding("utf-8");
    request.setCharacterEncoding("utf-8");
    String userId = request.getParameter("userId");
    String businessType = request.getParameter("businessType");
    PrintWriter out = response.getWriter();
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("userId", userId);
    params.put("businessType", businessType);
    int startIndex = request.getParameter("start") == null ? 0 : Integer.valueOf(request.getParameter("start"));
    int pageSize = request.getParameter("limit") == null ? PaginationSupport.PAGESIZE : Integer.valueOf(request.getParameter("limit"));
    PaginationSupport pageInfor = usersService.getWillSelectUser(params, startIndex, pageSize);
    String rt = JSONObject.fromObject(pageInfor).toString();
    out.println(rt);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward getUserInforById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setCharacterEncoding("utf-8");
    request.setCharacterEncoding("utf-8");
    String userId = request.getParameter("userId");
    PrintWriter out = response.getWriter();
    UserDto userInfor = usersService.getUserById(userId);
    out.println(JSONArray.fromObject(userInfor).toString());
    out.flush();
    out.close();
    return null;
  }

  public UsersService getUsersService() {
    return usersService;
  }

  public void setUsersService(UsersService usersService) {
    this.usersService = usersService;
  }

}
