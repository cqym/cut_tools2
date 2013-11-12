package com.tl.resource.web.reserveOrder;

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

import com.tl.resource.business.dto.TreeDto;
import com.tl.resource.business.reserveOrder.ReserveOrderService;

public class ProductListAction extends Action {

  private ReserveOrderService reserveOrderServiceImpl;

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setContentType("text/html;charset=utf-8");
    String size = request.getParameter("limit") == null ? "20" : request.getParameter("limit");
    String start = request.getParameter("start") == null ? "0" : request.getParameter("start");
    int pageSize = Integer.parseInt(size);
    int startIndex = Integer.parseInt(start);

    Map<String, Object> parmMap = new HashMap<String, Object>();
    parmMap.put("productCode", request.getParameter("productCode"));
    parmMap.put("brandCode", request.getParameter("brandCode"));
    parmMap.put("productName", request.getParameter("productName"));
    parmMap.put("supplierId", request.getParameter("supplierId"));
    parmMap.put("startIndex", startIndex);
    parmMap.put("pageSize", pageSize);

    parmMap.put("brand", request.getParameter("brand"));
    parmMap.put("salePriceDate", request.getParameter("salePriceDate"));

    int total = reserveOrderServiceImpl.getProToolsTotal(parmMap);
    List<TreeDto> proList = reserveOrderServiceImpl.getOrderProToolsList(parmMap);
    PrintWriter out = response.getWriter();
    out.write("{orderDetail:" + JSONArray.fromObject(proList).toString() + ",totalProperty:" + total + "}");
    out.flush();
    out.close();
    return null;
  }

  public ReserveOrderService getReserveOrderServiceImpl() {
    return reserveOrderServiceImpl;
  }

  public void setReserveOrderServiceImpl(ReserveOrderService reserveOrderServiceImpl) {
    this.reserveOrderServiceImpl = reserveOrderServiceImpl;
  }

}
