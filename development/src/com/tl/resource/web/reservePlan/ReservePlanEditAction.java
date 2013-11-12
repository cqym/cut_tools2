package com.tl.resource.web.reservePlan;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import com.tl.resource.business.dto.ReservePlanDetailDto;
import com.tl.resource.business.dto.ReservePlanMainInforDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.reservePlan.ReservePlanService;

public class ReservePlanEditAction extends DispatchAction {
  private ReservePlanService reservePlanService;

  public ActionForward consultReserveInfors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String orderId = request.getParameter("orderId");
    ReservePlanMainInforDto dto = reservePlanService.consultReserveInfors(orderId);
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    dto.setUserId(user.getId());
    dto.setUserName(user.getUserName());
    out.println(JSONObject.fromObject(dto));
    out.flush();
    out.close();
    return null;
  }

  public ActionForward addReservePlan(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String reservePlanJsonStr = request.getParameter("reservePlanInfor");
    // String memo = request.getParameter("memo");
    // String urgentLevel = request.getParameter("urgentLevel") == null ?
    // "0" : request.getParameter("urgentLevel");
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();

    // System.out.println(reservePlanJsonStr);

    PrintWriter out = response.getWriter();
    JSONObject reservePlanJson = JSONObject.fromObject(reservePlanJsonStr);
    ReservePlanMainInforDto dto = (ReservePlanMainInforDto) JSONObject.toBean(reservePlanJson, ReservePlanMainInforDto.class);
    dto.setEditTime(new Date());
    dto.setUserName(user.getUserName());
    dto.setUserId(user.getId());
    // dto.setMemo(memo);
    // dto.setUrgentLevel(Integer.valueOf(urgentLevel));
    JSONArray productsArray = reservePlanJson.getJSONArray("reservePlanDetail");
    List<ReservePlanDetailDto> productsList = new ArrayList<ReservePlanDetailDto>();
    dto.setReservePlanDetail(productsList);
    for (Iterator iterator = productsArray.iterator(); iterator.hasNext();) {
      JSONObject sjson = (JSONObject) iterator.next();
      ReservePlanDetailDto t = (ReservePlanDetailDto) JSONObject.toBean(sjson, ReservePlanDetailDto.class);
      productsList.add(t);
    }
    try {
      reservePlanService.addReservePlan(dto);
      out.println(1);
    } catch (RuntimeException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      // System.out.println("ssss:" + e.getMessage());
      out.println(e.getMessage());
    }

    out.flush();
    out.close();
    return null;
  }

  public ActionForward updateReservePlan(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String reservePlanJsonStr = request.getParameter("reservePlanInfor");
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();

    PrintWriter out = response.getWriter();
    JSONObject reservePlanJson = JSONObject.fromObject(reservePlanJsonStr);
    ReservePlanMainInforDto dto = (ReservePlanMainInforDto) JSONObject.toBean(reservePlanJson, ReservePlanMainInforDto.class);
    dto.setEditTime(new Date());
    dto.setUserName(user.getUserName());
    dto.setUserId(user.getId());

    JSONArray productsArray = reservePlanJson.getJSONArray("reservePlanDetail");
    List<ReservePlanDetailDto> productsList = new ArrayList<ReservePlanDetailDto>();
    dto.setReservePlanDetail(productsList);
    for (Iterator iterator = productsArray.iterator(); iterator.hasNext();) {
      JSONObject sjson = (JSONObject) iterator.next();
      ReservePlanDetailDto t = (ReservePlanDetailDto) JSONObject.toBean(sjson, ReservePlanDetailDto.class);
      productsList.add(t);
    }
    try {
      reservePlanService.updateReservePlan(dto);
      out.println(1);
    } catch (RuntimeException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      // System.out.println("ssss:" + e.getMessage());
      out.println(e.getMessage());
    }

    out.flush();
    out.close();
    return null;
  }

  public ActionForward deleteReservePlan(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String[] ids = request.getParameterValues("ids");
    PrintWriter out = response.getWriter();
    try {
      if (ids != null) {
        for (int i = 0; i < ids.length; i++) {
          reservePlanService.deleteReservePlanById(ids[i]);
        }
      }
      out.println(JSONArray.fromObject(ids));
    } catch (RuntimeException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      // System.out.println("ssss:" + e.getMessage());
      out.println(e.getMessage());
    }

    out.flush();
    out.close();
    return null;
  }

  public ActionForward confirmReservePlan(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String[] ids = request.getParameterValues("ids");
    PrintWriter out = response.getWriter();
    try {
      if (ids != null) {
        for (int i = 0; i < ids.length; i++) {
          reservePlanService.confirmReservePlanById(ids[i]);
        }
      }
      out.print("");
    } catch (RuntimeException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      // System.out.println("ssss:" + e.getMessage());
      out.println(e.getMessage());
    }

    out.flush();
    out.close();
    return null;
  }

  public ReservePlanService getReservePlanService() {
    return reservePlanService;
  }

  public void setReservePlanService(ReservePlanService reservePlanService) {
    this.reservePlanService = reservePlanService;
  }

}
