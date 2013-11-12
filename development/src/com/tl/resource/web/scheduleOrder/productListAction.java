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

import com.tl.resource.business.dto.OrderDetialsDto;
import com.tl.resource.business.scheduleOrder.ScheduleOrderService;

public class productListAction extends Action {

  private ScheduleOrderService scheduleOrderService;

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setContentType("text/html;charset=utf-8");
    String psize = request.getParameter("limit") == null ? "20" : request.getParameter("limit");
    String sIndex = request.getParameter("start") == null ? "0" : request.getParameter("start");
    String quotationCode = request.getParameter("quotationCode");
    String supplierId = request.getParameter("supplierId");
    //		String productBrand = request.getParameter("productBrand");
    //		String contractCode = request.getParameter("contractCode");
    String contractProjectSortId = request.getParameter("id");
    String brandCode = request.getParameter("brandCode");
    String productName = request.getParameter("productName");
    String outStockType = request.getParameter("outStockType");
    Integer pageSize = Integer.parseInt(psize);
    Integer startIndex = Integer.parseInt(sIndex);
    Map<String, Object> parmMap = new HashMap<String, Object>();
    parmMap.put("startIndex", startIndex);
    parmMap.put("pageSize", pageSize);
    parmMap.put("quotationCode", quotationCode);
    parmMap.put("supplierId", supplierId);
    parmMap.put("contractProjectSortId", contractProjectSortId);
    parmMap.put("brandCode", brandCode);
    parmMap.put("productName", productName);
    parmMap.put("outStockType", outStockType);
    int total = scheduleOrderService.getQuotationDetailCountBySupplier(parmMap);
    List<OrderDetialsDto> quotationInforList = scheduleOrderService.getQuotationDetailBySupplier(parmMap);
    PrintWriter out = response.getWriter();
    out.write("{root:" + JSONArray.fromObject(quotationInforList).toString() + ",totalProperty:" + total + "}");
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
