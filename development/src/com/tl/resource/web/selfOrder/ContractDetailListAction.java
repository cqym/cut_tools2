package com.tl.resource.web.selfOrder;

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

import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.OrderDetialsDto;
import com.tl.resource.business.selfOrder.SelfOrderService;

public class ContractDetailListAction extends Action {

  private SelfOrderService selfOrderService;

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    // TODO Auto-generated method stub
    response.setContentType("text/html;charset=utf-8");
    String size = request.getParameter("limit") == null ? "20" : request.getParameter("limit");
    String start = request.getParameter("start") == null ? "0" : request.getParameter("start");
    int pageSize = Integer.parseInt(size);
    int startIndex = Integer.parseInt(start);
    String contractCode = request.getParameter("contractCode");
    String supplierId = request.getParameter("supplierId");
    //搜索字符串
    String brandCode = request.getParameter("brandCode");
    String productName = request.getParameter("productName");
    String contractProjectSortId = request.getParameter("contractProjectSortId");
    Map<String, Object> parmMap = new HashMap<String, Object>();
    parmMap.put("contractCode", contractCode);
    parmMap.put("supplierId", supplierId);
    parmMap.put("contractProjectSortId", contractProjectSortId);
    parmMap.put("brandCode", brandCode);
    parmMap.put("productName", productName);
    parmMap.put("startIndex", startIndex);
    parmMap.put("pageSize", pageSize);
    int total = selfOrderService.getContractDetailListCount(parmMap);
    List<OrderDetialsDto> contractDetailList = selfOrderService.getContractDetailList(parmMap);
    PaginationSupport pageInfo = new PaginationSupport(contractDetailList, total, pageSize, startIndex);
    String pageInfoJson = JSONObject.fromObject(pageInfo).toString();
    PrintWriter out = response.getWriter();
    out.write(pageInfoJson);
    out.flush();
    out.close();
    return null;

  }

  public SelfOrderService getSelfOrderService() {
    return selfOrderService;
  }

  public void setSelfOrderService(SelfOrderService selfOrderService) {
    this.selfOrderService = selfOrderService;
  }

}
