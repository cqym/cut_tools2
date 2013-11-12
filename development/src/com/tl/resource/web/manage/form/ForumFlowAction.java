package com.tl.resource.web.manage.form;

import java.io.PrintWriter;
import java.util.Date;
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
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.manage.ForumService;
import com.tl.resource.business.manage.UsersService;
import com.tl.resource.dao.pojo.TForum;

public class ForumFlowAction extends DispatchAction {
  private ForumService forumService;

  private UsersService userService;

  public ActionForward getWaitReturnForumMsg(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    List<TForum> fList = forumService.getWaitReturnForumMsg(user.getId());
    PrintWriter out = response.getWriter();
    out.print(JSONArray.fromObject(fList));
    out.flush();
    out.close();
    return null;
  }

  public ActionForward followTheme(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    PrintWriter out = response.getWriter();
    TForum forum = (TForum) JSONObject.toBean(JSONObject.fromObject(request.getParameter("forum")), TForum.class);
    forum.setForumType(TForum.FOLLOW_TYPE);
    forum.setEditDate(new Date());
    forum.setUserId(user.getId());
    forum.setUserName(user.getUserName());
    forumService.followTheme(forum);
    out.print("true");
    out.flush();
    out.close();
    return null;
  }

  public ActionForward getTheThemeFlows(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String id = request.getParameter("id");
    List<TForum> fList = forumService.getThemeFlowsByForumId(id);
    PrintWriter out = response.getWriter();
    out.print(JSONArray.fromObject(fList));
    out.flush();
    out.close();
    return null;
  }

  public ForumService getForumService() {
    return forumService;
  }

  public void setForumService(ForumService forumService) {
    this.forumService = forumService;
  }

  public UsersService getUserService() {
    return userService;
  }

  public void setUserService(UsersService userService) {
    this.userService = userService;
  }

}
