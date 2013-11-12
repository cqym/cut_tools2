package com.tl.resource.web.manage.forum;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.common.util.LoginInforUtil;
import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.ChatMsgDto;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.RolesDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.manage.ForumService;
import com.tl.resource.business.manage.UsersService;
import com.tl.resource.business.workDoing.UsersMessageBusinessService;
import com.tl.resource.dao.pojo.TForum;
import com.tl.resource.dao.pojo.TUserInfor;

public class ForumAction extends DispatchAction {
  private ForumService forumService;

  private UsersService userService;

  private UsersMessageBusinessService uersMessageBusinessService;

  public ActionForward addTheme(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    PrintWriter out = response.getWriter();
    TForum forum = (TForum) JSONObject.toBean(JSONObject.fromObject(request.getParameter("forum")), TForum.class);
    forum.setForumType(TForum.THEME_TYPE);
    forum.setEditDate(new Date());
    forum.setUserId(user.getId());
    forum.setStatus(TForum.STATUS_WAIT_OP);
    forum.setUserName(user.getUserName());
    UserDto accUser = userService.getUserById(forum.getAcceptUserId());
    if (accUser != null) {
      forum.setAcceptUserName(accUser.getTrueName());
    }
    UserDto copyUser = userService.getUserById(forum.getCopyUserId());
    if (copyUser != null) {
      forum.setCopyUserName(copyUser.getTrueName());
    }
    forumService.add(forum);
    //this.sendMsg(forum);
    out.print("true");
    out.flush();
    out.close();
    return null;
  }

  public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    PrintWriter out = response.getWriter();
    TForum forum = (TForum) JSONObject.toBean(JSONObject.fromObject(request.getParameter("forum")), TForum.class);
    forumService.update(forum);
    out.print("true");
    out.flush();
    out.close();
    return null;
  }

  public ActionForward endById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    PrintWriter out = response.getWriter();

    forumService.endById(request.getParameter("id"));
    out.print("true");
    out.flush();
    out.close();
    return null;
  }

  public ActionForward deleteById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    forumService.deleteById(request.getParameter("id"));
    out.print("true");
    out.flush();
    out.close();
    return null;
  }

  public ActionForward getFroumById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    TForum f = forumService.getTForumById(request.getParameter("id"));
    out.print(JSONObject.fromObject(f));
    out.flush();
    out.close();
    return null;
  }

  public ActionForward forumThemeList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String startIndex = request.getParameter("start");
    String pageSize = request.getParameter("limit");
    if (startIndex == null) {
      startIndex = "0";
    }
    if (pageSize == null) {
      pageSize = "10";
    }
    Map<String, String> mparams = new HashMap<String, String>();
    mparams.put("title", request.getParameter("title"));
    mparams.put("status", request.getParameter("status"));
    mparams.put("forumType", request.getParameter("forumType"));
    mparams.put("parentId", request.getParameter("parentId"));
    mparams.put("receiverUserId", request.getParameter("receiverUserId"));

    PaginationSupport pageInfor = forumService.findForum4Page(mparams, Integer.valueOf(startIndex), Integer.valueOf(pageSize));
    out.println(JSONObject.fromObject(pageInfor).toString());
    out.flush();
    out.close();
    return null;
  }

  private void sendMsg(TForum forum) {
    ChatMsgDto dto = new ChatMsgDto();
    dto.setFromUserId(forum.getUserId());
    dto.setFromUserName(forum.getUserName());
    //dto.setFromUserDepart(forum.get);
    dto.setMessage(getMsgFromForum(forum));
    dto.setSendTime(forum.getEditDate());
    //dto.setToUserDepart();
    dto.setToUserId(forum.getAcceptUserId());
    uersMessageBusinessService.insertMessage(dto);

    dto.setToUserId(forum.getCopyUserId());
    uersMessageBusinessService.insertMessage(dto);
  }

  private String getMsgFromForum(TForum forum) {
    StringBuffer bu = new StringBuffer(60);
    bu.append("待处理数据单号/产品：");
    bu.append(forum.getTitle());
    bu.append('\n');
    bu.append("内容：");
    bu.append(forum.getContent());
    bu.append('\n');
    bu.append("要求时间：");
    bu.append(DateFormatUtils.format(forum.getProcessDate(), "yyyy-MM-dd hh:mm:ss"));
    bu.append('\n');
    bu.append("备注：");
    bu.append(forum.getMemo());
    return bu.toString();
  }

  public ForumService getForumService() {
    return forumService;
  }

  public void setForumService(ForumService forumService) {
    this.forumService = forumService;
  }

  public void addUser(UserDto dto) {
    userService.addUser(dto);
  }

  public void deleteUser(String id) {
    userService.deleteUser(id);
  }

  public List<RolesDto> findUserRoles(String userId) {
    return userService.findUserRoles(userId);
  }

  public PaginationSupport findUsers(Map params, int startIndex, int pageSize) {
    return userService.findUsers(params, startIndex, pageSize);
  }

  public List<UserDto> findUsersAll() {
    return userService.findUsersAll();
  }

  public List<TUserInfor> getSelectedUser(Map<String, Object> params) {
    return userService.getSelectedUser(params);
  }

  public UserDto getUserById(String id) {
    return userService.getUserById(id);
  }

  public PaginationSupport getWillSelectUser(Map<String, Object> params, int startIndex, int pageSize) {
    return userService.getWillSelectUser(params, startIndex, pageSize);
  }

  public LoginInforDto login(String userName, String passsword) {
    return userService.login(userName, passsword);
  }

  public void saveResourcePurview(String userId, int businessType, List<String> ids) {
    userService.saveResourcePurview(userId, businessType, ids);
  }

  public void setUserValid(String userId, String valid) {
    userService.setUserValid(userId, valid);
  }

  public void updateUserInfor(UserDto dto) {
    userService.updateUserInfor(dto);
  }

  public void updateUserRoles(String userId, String[] roleIds) {
    userService.updateUserRoles(userId, roleIds);
  }

  public UsersService getUserService() {
    return userService;
  }

  public void setUserService(UsersService userService) {
    this.userService = userService;
  }

  public UsersMessageBusinessService getUersMessageBusinessService() {
    return uersMessageBusinessService;
  }

  public void setUersMessageBusinessService(UsersMessageBusinessService uersMessageBusinessService) {
    this.uersMessageBusinessService = uersMessageBusinessService;
  }

}
