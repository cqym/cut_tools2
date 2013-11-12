package com.tl.resource.web.planOrder;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.resource.business.planOrder.PlanOrderService;
import com.tl.resource.dao.pojo.TSuppliersInfor;

public class SuppliersInforListAction extends Action {

  private PlanOrderService planOrderService;

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setCharacterEncoding("utf-8");
    String size = request.getParameter("limit") == null ? "15" : request.getParameter("limit");
    String start = request.getParameter("start") == null ? "0" : request.getParameter("start");
    int pageSize = Integer.parseInt(size);
    int startIndex = Integer.parseInt(start);
    String supplierCode = "";
    String supplierName = "";
    String contactPerson = "";
    String brand = "";
    String planId = request.getParameter("planId");
    String searchStr = request.getParameter("searchStr");
    if (searchStr != null && !"".equals(searchStr)) {

      JSONObject search = JSONObject.fromObject(searchStr);
      try {
        supplierCode = search.getString("supplierCode");
        supplierName = search.getString("supplierName");
        contactPerson = search.getString("contactPerson");
        brand = search.getString("brand");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    Map<String, Object> mapStr = new HashMap<String, Object>();
    mapStr.put("start", startIndex);
    mapStr.put("limit", pageSize);
    mapStr.put("supplierCode", supplierCode);
    mapStr.put("supplierName", supplierName);
    mapStr.put("contactPerson", contactPerson);
    mapStr.put("brand", brand);
    mapStr.put("planId", planId);
    int total = planOrderService.getSuppliersInforListCount(mapStr);
    List<TSuppliersInfor> suppliersInfor = planOrderService.getSuppliersInforList(mapStr);
    String jsonResult = JSONArray.fromObject(suppliersInfor).toString();
    PrintWriter out = response.getWriter();
    out.write("{totalProperty:" + total + ",root:" + jsonResult + "}");
    out.flush();
    out.close();
    return null;
  }

  public PlanOrderService getPlanOrderService() {
    return planOrderService;
  }

  public void setPlanOrderService(PlanOrderService planOrderService) {
    this.planOrderService = planOrderService;
  }

}
