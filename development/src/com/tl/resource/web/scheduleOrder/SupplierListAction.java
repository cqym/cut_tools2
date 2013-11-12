package com.tl.resource.web.scheduleOrder;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.resource.business.scheduleOrder.ScheduleOrderService;
import com.tl.resource.dao.pojo.TSuppliersInfor;

public class SupplierListAction extends Action {

  private ScheduleOrderService scheduleOrderService;

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    // TODO Auto-generated method stub
    response.setContentType("text/html;charset=utf-8");
    String size = request.getParameter("limit") == null ? "20" : request.getParameter("limit");
    String start = request.getParameter("start") == null ? "0" : request.getParameter("start");
    int pageSize = Integer.parseInt(size);
    int startIndex = Integer.parseInt(start);
    String qId = request.getParameter("qId");
    String supplierCode = request.getParameter("supplierCode");
    String supplierName = request.getParameter("supplierName");
    String contactPerson = request.getParameter("contactPerson");
    String brand = request.getParameter("brand");
    String leaf = request.getParameter("leaf");
    Map<String, Object> parmMap = new HashMap<String, Object>();
    parmMap.put("qId", qId);
    parmMap.put("pageSize", pageSize);
    parmMap.put("startIndex", startIndex);
    parmMap.put("supplierCode", supplierCode);
    parmMap.put("supplierName", supplierName);
    parmMap.put("contactPerson", contactPerson);
    parmMap.put("brand", brand);
    parmMap.put("leaf", leaf);
    parmMap.put("needOrder", "true");
    int total = scheduleOrderService.getSupplierListCount(parmMap);
    List<TSuppliersInfor> supplierList = scheduleOrderService.getSupplierList(parmMap);
    PrintWriter out = response.getWriter();
    out.println("{root:" + JSONArray.fromObject(supplierList).toString() + ",totalProperty:" + total + "}");
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
