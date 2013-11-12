package com.tl.resource.web.scheduleOrder;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.common.util.LoginInforUtil;
import com.tl.resource.business.dto.ReserveOrderBean;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.scheduleOrder.ScheduleOrderService;
import com.tl.resource.dao.pojo.TOrderInfor;

public class ScheduleOrderListAction extends Action {

  private ScheduleOrderService scheduleOrderService;

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    UserDto userDto = null;
    if (LoginInforUtil.getLoginInfor(request) != null) {
      userDto = LoginInforUtil.getLoginInfor(request).getUser();
    }
    response.setContentType("text/html;charset=utf-8");
    String psize = request.getParameter("limit") == null ? "20" : request.getParameter("limit");
    String sIndex = request.getParameter("start") == null ? "0" : request.getParameter("start");
    Integer pageSize = Integer.parseInt(psize);
    Integer startIndex = Integer.parseInt(sIndex);
    String orderCode = request.getParameter("orderCode");
    String contractCode = request.getParameter("contractCode");
    String quotationCode = request.getParameter("quotationCode");
    String supplierName = request.getParameter("supplierName");
    String startTime = request.getParameter("startTime");
    String endTime = request.getParameter("endTime");
    String status = request.getParameter("status");
    String memo = request.getParameter("memo");
    String year = request.getParameter("year");
    if (status != null && status.equals("全部")) {
      status = null;
    }
    String customerName = request.getParameter("customerName");
    String ownContactPerson = request.getParameter("ownContactPerson");
    //		String orderType = "5"; //预定订单
    String orderType = request.getParameter("orderType");
    Map<String, Object> parmMap = new HashMap<String, Object>();
    parmMap.put("contractCode", contractCode);
    parmMap.put("quotationCode", quotationCode);
    parmMap.put("orderCode", orderCode);
    parmMap.put("supplierName", supplierName);
    parmMap.put("startTime", startTime);
    parmMap.put("endTime", endTime);
    parmMap.put("orderType", orderType);
    parmMap.put("status", status);
    parmMap.put("customerName", customerName);
    parmMap.put("ownContactPerson", ownContactPerson);
    parmMap.put("startIndex", startIndex);
    parmMap.put("pageSize", pageSize);
    parmMap.put("userId", userDto.getId());
    parmMap.put("memo", memo);
    parmMap.put("year", year);
    int total = scheduleOrderService.getScheduleOrderListCount(parmMap);
    List<TOrderInfor> orderList = scheduleOrderService.getScheduleOrderList(parmMap);
    PrintWriter out = response.getWriter();
    ReserveOrderBean bean = new ReserveOrderBean();
    bean.setRoot(orderList);
    bean.setTotalProperty(total);
    out.println(JSONObject.fromObject(bean).toString());
    out.flush();
    out.close();
    return null;
  }

  public ScheduleOrderService getScheduleOrderService() {
    return scheduleOrderService;
  }

  public void setScheduleOrderService(ScheduleOrderService scheduleOrderService) {
    this.scheduleOrderService = scheduleOrderService;
  }

}
