package com.tl.resource.web.workDoing;

import java.io.PrintWriter;
import java.util.Date;
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

import com.tl.common.util.LoginInforUtil;
import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.BusinessInforDto;
import com.tl.resource.business.dto.ChatMsgDto;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.workDoing.UsersMessageBusinessService;

public class UsersMessageBusinessAction extends DispatchAction {
  private static Map<String, ChatMsgDto> msgCache = new HashMap<String, ChatMsgDto>();

  private UsersMessageBusinessService uersMessageBusinessService;

  public ActionForward getUsersNodes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String pid = request.getParameter("node");
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    List<BusinessInforDto> list = uersMessageBusinessService.getBusinessDataInfor(pid, loginInfor);
    String rt = JSONArray.fromObject(list).toString();
    out.println(rt);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward requestMsg(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String toUserId = request.getParameter("userId");
    ChatMsgDto dto = msgCache.get(toUserId);
    if (dto != null) {
      String rt = JSONObject.fromObject(dto).toString();
      out.print(rt);
      msgCache.remove(toUserId);
    } else {
      String rt = "null";
      out.print(rt);
    }
    out.flush();
    out.close();
    return null;
  }

  public ActionForward sendMsg(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String toUserId = request.getParameter("toUserId");
    String toDepart = request.getParameter("toDepart");
    String msg = request.getParameter("message");
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    ChatMsgDto dto = new ChatMsgDto();
    dto.setFromUserId(loginInfor.getUser().getId());
    dto.setFromUserName(loginInfor.getUser().getUserName());
    dto.setFromUserDepart(loginInfor.getUser().getDepartId());
    dto.setMessage(msg);
    dto.setSendTime(new Date());
    dto.setToUserDepart(toDepart);
    dto.setToUserId(toUserId);
    msgCache.put(toUserId, dto);
    uersMessageBusinessService.insertMessage(dto);

    dto = new ChatMsgDto();
    dto.setFromUserId(toUserId);
    dto.setFromUserName(loginInfor.getUser().getUserName());
    dto.setMessage(msg);
    dto.setSendTime(new Date());
    dto.setToUserId(loginInfor.getUser().getId());
    msgCache.put(loginInfor.getUser().getId(), dto);

    return null;
  }

  public ActionForward getMessageHistory(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    String startIndex = request.getParameter("start");
    startIndex = startIndex == null ? "0" : startIndex;
    String pageSize = request.getParameter("limit");
    pageSize = pageSize == null ? "10" : pageSize;
    String userId = request.getParameter("userId");
    String myId = loginInfor.getUser().getId();
    PaginationSupport pageInfor = uersMessageBusinessService
      .getMessageHistory(userId, myId, Integer.parseInt(startIndex), Integer.parseInt(pageSize));
    String rt = JSONObject.fromObject(pageInfor).toString();
    out.println(rt);
    out.flush();
    out.close();
    return null;
  }

  public UsersMessageBusinessService getUersMessageBusinessService() {
    return uersMessageBusinessService;
  }

  public void setUersMessageBusinessService(UsersMessageBusinessService uersMessageBusinessService) {
    this.uersMessageBusinessService = uersMessageBusinessService;
  }

}
