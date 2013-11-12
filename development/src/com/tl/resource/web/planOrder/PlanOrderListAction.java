package com.tl.resource.web.planOrder;

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
import com.tl.resource.business.planOrder.PlanOrderService;
import com.tl.resource.dao.pojo.TOrderInfor;

public class PlanOrderListAction extends Action {

  private PlanOrderService planOrderService;

  /**
   * 储备订单
   */
  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    UserDto userDto = null;
    if (LoginInforUtil.getLoginInfor(request) != null) {
      userDto = LoginInforUtil.getLoginInfor(request).getUser();
    }
    // TODO Auto-generated method stub
    response.setContentType("text/html;charset=utf-8");
    String size = request.getParameter("limit") == null ? "20" : request.getParameter("limit");
    String start = request.getParameter("start") == null ? "0" : request.getParameter("start");
    int pageSize = Integer.parseInt(size);
    int startIndex = Integer.parseInt(start);
    String orderCode = "";
    String supplierName = "";
    String startTime = "";
    String endTime = "";
    String status = "";
    String searchStr = request.getParameter("searchStr");
    String memo = "";
    String year = null;
    if (searchStr != null && !"".equals(searchStr)) {
      JSONObject search = JSONObject.fromObject(searchStr);
      try {
        orderCode = search.has("orderCode") ? search.getString("orderCode") : null;
        supplierName = search.has("supplierName") ? search.getString("supplierName") : null;
        startTime = search.has("startTime") ? search.getString("startTime") : null;
        endTime = search.has("endTime") ? search.getString("endTime") : null;
        status = search.has("status") ? search.getString("status") : null;
        if (status != null && status.equals("全部")) {
          status = null;
        }
        memo = search.has("memo") ? search.getString("memo") : null;
        year = search.has("year") ? search.getString("year") : null;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    int orderType = 4; //储备计划订单	
    Map<String, Object> parmMap = new HashMap<String, Object>();

    String sort = request.getParameter("sort");
    if (sort != null) {
      parmMap.put("sort", RegexUtils.toDataBaseColName(sort));
      parmMap.put("dir", request.getParameter("dir"));
    }

    parmMap.put("orderCode", orderCode);
    parmMap.put("supplierName", supplierName);
    parmMap.put("startTime", startTime);
    parmMap.put("endTime", endTime);
    parmMap.put("orderType", orderType);
    parmMap.put("status", status);
    parmMap.put("startIndex", startIndex);
    parmMap.put("pageSize", pageSize);
    parmMap.put("userId", userDto.getId());
    parmMap.put("memo", memo);
    parmMap.put("year", year);
    int total = planOrderService.getOrderListCount(parmMap);
    List<TOrderInfor> orderList = planOrderService.getOrderList(parmMap);
    PrintWriter out = response.getWriter();
    ReserveOrderBean bean = new ReserveOrderBean();
    bean.setRoot(orderList);
    bean.setTotalProperty(total);
    out.println(JSONObject.fromObject(bean).toString());
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
