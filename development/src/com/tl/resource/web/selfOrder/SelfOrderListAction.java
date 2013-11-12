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

import com.tl.common.util.LoginInforUtil;
import com.tl.common.util.RegexUtils;
import com.tl.resource.business.dto.ReserveOrderBean;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.selfOrder.SelfOrderService;
import com.tl.resource.dao.pojo.TOrderInfor;

public class SelfOrderListAction extends Action {

  private SelfOrderService selfOrderService;

  /**
   * 自制品订单
   */
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
    String orderCode = "";
    String contractCode = "";
    String supplierName = "";
    String startTime = "";
    String endTime = "";
    String status = "";
    String customerName = "";
    String ownContactPerson = "";
    String needEditPlan = "";
    String searchStr = request.getParameter("searchStr");
    String memo = "";
    String year = null;
    if (searchStr != null && !"".equals(searchStr)) {
      JSONObject search = JSONObject.fromObject(searchStr);
      orderCode = search.has("orderCode") ? search.getString("orderCode") : "";
      supplierName = search.has("supplierName") ? search.getString("supplierName") : "";
      startTime = search.has("startTime") ? search.getString("startTime") : "";
      endTime = search.has("endTime") ? search.getString("endTime") : "";
      status = search.has("status") ? search.getString("status") : "";
      if (status != null && status.equals("全部")) {
        status = null;
      }
      contractCode = search.has("contractCode") ? search.getString("contractCode") : "";
      customerName = search.has("customerName") ? search.getString("customerName") : "";
      ownContactPerson = search.has("ownContactPerson") ? search.getString("ownContactPerson") : "";
      needEditPlan = search.has("needEditPlan") ? search.getString("needEditPlan") : "";
      memo = search.has("memo") ? search.getString("memo") : "";
      year = search.has("year") ? search.getString("year") : "";
    }
    String orderType = "3"; //自制品订单
    Map<String, Object> parmMap = new HashMap<String, Object>();

    String sort = request.getParameter("sort");
    if (sort != null) {
      parmMap.put("sort", RegexUtils.toDataBaseColName(sort));
      parmMap.put("dir", request.getParameter("dir"));
    }
    if (request.getParameter("orderType") != null) {
      orderType = request.getParameter("orderType");
    }

    parmMap.put("orderCode", orderCode);
    parmMap.put("contractCode", contractCode);
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
    parmMap.put("needEditPlan", needEditPlan);
    parmMap.put("memo", memo);
    parmMap.put("year", year);
    int total = selfOrderService.getContractOrderListCount(parmMap);
    List<TOrderInfor> orderList = selfOrderService.getContractOrderList(parmMap);
    PrintWriter out = response.getWriter();
    ReserveOrderBean bean = new ReserveOrderBean();
    bean.setRoot(orderList);
    bean.setTotalProperty(total);
    out.println(JSONObject.fromObject(bean).toString());
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
