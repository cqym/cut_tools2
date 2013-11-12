package com.tl.resource.web.dataExp;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.common.smartupload.Constant;
import com.tl.common.util.LoginInforUtil;
import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.exceltemplete.ExpTempleteService;
import com.tl.resource.dao.pojo.TExpTemplete;

public class ExpTempleteAction extends DispatchAction {
  private ExpTempleteService expTempleteService;

  public ActionForward addTemplete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String expTempleteJsonStr = request.getParameter("expTemplete");
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    PrintWriter out = response.getWriter();
    JSONObject expTempleteJson = JSONObject.fromObject(expTempleteJsonStr);
    TExpTemplete po = (TExpTemplete) JSONObject.toBean(expTempleteJson, TExpTemplete.class);
    po.setUserId(user.getId());
    po.setUserName(user.getTrueName());
    po.setCreateTime(new Date());
    try {
      expTempleteService.insertTemplete(po);
      out.print(true);
    } catch (RuntimeException e) {
      // TODO Auto-generated catch block
      out.print(false);
      e.printStackTrace();
    }
    out.flush();
    out.close();
    return null;
  }

  public ActionForward deleteTemplete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String[] ids = request.getParameterValues("ids");
    expTempleteService.deleteTempleteByIds(ids);
    PrintWriter out = response.getWriter();
    out.print(true);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward updateTemplete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {

    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String expTempleteJsonStr = request.getParameter("expTemplete");
    PrintWriter out = response.getWriter();
    JSONObject expTempleteJson = JSONObject.fromObject(expTempleteJsonStr);
    TExpTemplete po = (TExpTemplete) JSONObject.toBean(expTempleteJson, TExpTemplete.class);
    try {
      expTempleteService.updateTemplete(po);
      out.print(true);
    } catch (RuntimeException e) {
      // TODO Auto-generated catch block
      out.print(false);
      e.printStackTrace();
    }
    out.flush();
    out.close();
    return null;
  }

  public ActionForward selectTemplete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
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
    if (!"-1".equals(request.getParameter("templeteType"))) {
      mparams.put("templeteType", request.getParameter("templeteType"));
    }
    mparams.put("templeteName", request.getParameter("templeteName"));
    mparams.put("memo", request.getParameter("memo"));
    mparams.put("startDate", request.getParameter("startDate"));
    mparams.put("endDate", request.getParameter("endDate"));
    PaginationSupport pageInfor = expTempleteService.getTExpTempletes(mparams, Integer.valueOf(startIndex), Integer.valueOf(pageSize));

    try {
      String rt = JSONObject.fromObject(pageInfor).toString();
      out.println(rt);
      out.flush();
      out.close();
    } catch (RuntimeException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  public ActionForward expertExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String busId = request.getParameter("busId");
    String tempId = request.getParameter("tempId");
    response.setContentType("application/vnd.ms-excel");
    try {
      expTempleteService.expertExce(busId, tempId, response, Constant.UPLOAD_DIR);
    } catch (RuntimeException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  public ActionForward expertListExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {

    Map para = request.getParameterMap();
    String tempId = request.getParameter("tempId");
    response.setContentType("application/vnd.ms-excel");
    try {
      HashMap conditionMap = new HashMap();
      for (Iterator iterator = para.keySet().iterator(); iterator.hasNext();) {
        Object key = iterator.next();
        conditionMap.put(key, request.getParameter((String) key));
      }
      UserDto userDto = LoginInforUtil.getLoginInfor(request).getUser();
      conditionMap.put("start", 0);
      conditionMap.put("limit", Integer.MAX_VALUE);
      conditionMap.put("userId", userDto.getId());
      if ("全部".equals(conditionMap.get("status"))) {
        conditionMap.remove("status");
      }

      expTempleteService.expertListExce(conditionMap, tempId, response, Constant.UPLOAD_DIR);
    } catch (RuntimeException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  public ExpTempleteService getExpTempleteService() {
    return expTempleteService;
  }

  public void setExpTempleteService(ExpTempleteService expTempleteService) {
    this.expTempleteService = expTempleteService;
  }

}
