package com.tl.resource.web.manage.user;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.manage.UsersService;

public class UsersManageAction extends DispatchAction {
  private UsersService usersService;

  public ActionForward addUserInfor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setCharacterEncoding("utf-8");
    request.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String content = request.getParameter("content");
    JSONObject json = JSONObject.fromObject(content);
    UserDto dto = (UserDto) JSONObject.toBean(json, UserDto.class);
    usersService.addUser(dto);
    out.println(JSONObject.fromObject(dto).toString());
    out.flush();
    out.close();
    return null;
  }

  public ActionForward updateUserInfor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setCharacterEncoding("utf-8");
    request.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String content = request.getParameter("content");
    JSONObject json = JSONObject.fromObject(content);
    UserDto dto = (UserDto) JSONObject.toBean(json, UserDto.class);
    usersService.updateUserInfor(dto);
    out.println(JSONObject.fromObject(dto).toString());
    out.flush();
    out.close();
    return null;
  }

  public ActionForward updateUserDetailInfor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setCharacterEncoding("utf-8");
    request.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String userId = request.getParameter("userId");
    String rolesIdsJson = request.getParameter("rolesIds");
    //System.out.println(rolesIdsJson);
    JSONArray json = JSONArray.fromObject(rolesIdsJson);
    Object[] arr = json.toArray();
    String[] rolesIds = new String[arr.length];
    for (int i = 0; i < arr.length; i++) {
      rolesIds[i] = (String) arr[i];
    }
    usersService.updateUserRoles(userId, rolesIds);
    out.println(true);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward deleteUserInfor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setCharacterEncoding("utf-8");
    request.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String userId = request.getParameter("userId");
    usersService.deleteUser(userId);
    out.println(true);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward saveResourcePurview(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setCharacterEncoding("utf-8");
    request.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String userId = request.getParameter("userId");
    String[] ids = request.getParameterValues("userIds");
    int businessType = Integer.valueOf(request.getParameter("businessType"));
    List<String> idlist = new ArrayList<String>();
    for (int i = 0; i < ids.length; i++) {
      idlist.add(ids[i]);
    }
    usersService.saveResourcePurview(userId, businessType, idlist);
    out.println(true);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward setUserValid(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setCharacterEncoding("utf-8");
    request.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String userId = request.getParameter("userId");
    String valid = request.getParameter("valid");
    usersService.setUserValid(userId, valid);
    out.println(true);
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
