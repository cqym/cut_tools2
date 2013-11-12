package com.tl.resource.web.notepad;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
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
import com.tl.common.util.RegexUtils;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.NotepadDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.notepad.NotepadService;

public class NotepadAction extends DispatchAction {
  private NotepadService notepadService = null;

  public NotepadService getNotepadService() {
    return notepadService;
  }

  public void setNotepadService(NotepadService notepadService) {
    this.notepadService = notepadService;
  }

  public ActionForward addNotepad(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String notepadJsonStr = request.getParameter("notepad");
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    JSONObject notepadJson = JSONObject.fromObject(notepadJsonStr);
    NotepadDto dto = (NotepadDto) JSONObject.toBean(notepadJson, NotepadDto.class);
    dto.setUserName(user.getTrueName());
    dto.setUserId(user.getId());
    notepadService.addNodepad(dto);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward updateNotepad(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String notepadJsonStr = request.getParameter("notepad");
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    JSONObject notepadJson = JSONObject.fromObject(notepadJsonStr);
    NotepadDto dto = (NotepadDto) JSONObject.toBean(notepadJson, NotepadDto.class);
    notepadService.updateNodepad(dto);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward deleteNotepad(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String[] ids = request.getParameterValues("ids");
    for (int i = 0; i < ids.length; i++) {
      notepadService.deleteNodepad(ids[i]);
    }
    out.flush();
    out.close();
    return null;
  }

  public ActionForward notepadList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
    Map<String, Object> mparams = new HashMap<String, Object>();
    mparams.put("title", request.getParameter("title"));
    mparams.put("memo", request.getParameter("memo"));

    String sort = request.getParameter("sort");
    if (sort != null) {
      if ("editTimeString".equals(sort)) {
        sort = "editTime";
      }
      mparams.put("sort", RegexUtils.toDataBaseColName(sort));
      mparams.put("dir", request.getParameter("dir"));
    }
    mparams.put("startDate", request.getParameter("startDate"));
    mparams.put("endDate", request.getParameter("endDate"));
    mparams.put("typeId", request.getParameter("typeId"));
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    mparams.put("userId", user.getId());
    PaginationSupport pageInfor = notepadService.getNotepads(mparams, Integer.valueOf(startIndex), Integer.valueOf(pageSize));
    String rt = JSONObject.fromObject(pageInfor).toString();
    out.println(rt);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward addNoticeNotepad(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String notepadJsonStr = request.getParameter("notepad");
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    JSONObject notepadJson = JSONObject.fromObject(notepadJsonStr);
    NotepadDto dto = (NotepadDto) JSONObject.toBean(notepadJson, NotepadDto.class);
    dto.setUserName(user.getTrueName());
    dto.setUserId(user.getId());
    if (notepadJson.has("editTime")) {
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      String editstring = notepadJson.getString("editTime");
      dto.setEditTime(df.parse(editstring));
    }
    notepadService.addNoticeNotepad(dto);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward updateNoticeNotepad(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String notepadJsonStr = request.getParameter("notepad");
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    JSONObject notepadJson = JSONObject.fromObject(notepadJsonStr);
    NotepadDto dto = (NotepadDto) JSONObject.toBean(notepadJson, NotepadDto.class);
    dto.setUserName(user.getTrueName());
    dto.setUserId(user.getId());
    if (notepadJson.has("editTime")) {
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      String editstring = notepadJson.getString("editTime");
      dto.setEditTime(df.parse(editstring));
    }
    notepadService.updateNodepad(dto);
    out.flush();
    out.close();
    return null;
  }
}
