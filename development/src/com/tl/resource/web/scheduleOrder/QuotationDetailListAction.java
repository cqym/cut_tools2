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
import com.tl.resource.dao.pojo.TQuotationProductDetail;

public class QuotationDetailListAction extends Action {

  private ScheduleOrderService scheduleOrderService;

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setContentType("text/html;charset=utf-8");
    String qId = request.getParameter("qId");
    Map<String, Object> parmMap = new HashMap<String, Object>();
    parmMap.put("qId", qId);
    parmMap.put("outStockType", 5);
    parmMap.put("leaf", request.getParameter("leaf"));
    parmMap.put("toolsTypeName", request.getParameter("toolsTypeName"));
    List<TQuotationProductDetail> quotationInforList = scheduleOrderService.getQuotationDetailByInforId(parmMap);
    PrintWriter out = response.getWriter();
    out.write("{root:" + JSONArray.fromObject(quotationInforList).toString() + "}");
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
