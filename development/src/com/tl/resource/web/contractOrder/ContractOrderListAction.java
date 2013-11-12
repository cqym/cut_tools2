package com.tl.resource.web.contractOrder;

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
import com.tl.resource.business.contractOrder.ContractOrderService;
import com.tl.resource.business.dto.ReserveOrderBean;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.dao.pojo.TOrderInfor;

/**
 * 
 * @author ls
 *	合同订单列表页面列表
 */
public class ContractOrderListAction extends Action {

  private ContractOrderService contractOrderService;

  /**
   * 合同订单
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
    String supplierOwnContactPerson = "";
    String userName = "";
    String searchStr = request.getParameter("searchStr");
    String memo = "";
    String orderType = "1"; //合同订单
    String year = null;
    if (searchStr != null && !"".equals(searchStr)) {
      JSONObject search = JSONObject.fromObject(searchStr);
      orderCode = search.has("orderCode") ? search.getString("orderCode") : null;
      supplierName = search.has("supplierName") ? search.getString("supplierName") : null;
      startTime = search.has("startTime") ? search.getString("startTime") : null;
      endTime = search.has("endTime") ? search.getString("endTime") : null;
      status = search.has("status") ? search.getString("status") : null;
      supplierOwnContactPerson = search.has("supplierOwnContactPerson") ? search.getString("supplierOwnContactPerson") : null;
      if (status != null && status.equals("全部")) {
        status = null;
      }
      customerName = search.has("customerName") ? search.getString("customerName") : "";
      ownContactPerson = search.has("ownContactPerson") ? search.getString("ownContactPerson") : "";
      contractCode = search.has("contractCode") ? search.getString("contractCode") : null;
      memo = search.has("memo") ? search.getString("memo") : null;
      if (search.has("orderType") && search.getString("orderType") != null && !"".equals(search.getString("orderType").trim())) {
        orderType = search.getString("orderType");
      }
      year = search.has("year") ? search.getString("year") : null;
    }

    Map<String, Object> parmMap = new HashMap<String, Object>();

    String sort = request.getParameter("sort");
    if (sort != null) {
      parmMap.put("sort", RegexUtils.toDataBaseColName(sort));
      parmMap.put("dir", request.getParameter("dir"));
    }
    parmMap.put("memo", memo);
    parmMap.put("contractCode", contractCode);
    parmMap.put("orderCode", orderCode);
    parmMap.put("supplierName", supplierName);
    parmMap.put("startTime", startTime);
    parmMap.put("endTime", endTime);
    parmMap.put("orderType", orderType);
    parmMap.put("status", status);
    parmMap.put("customerName", customerName);
    parmMap.put("ownContactPerson", ownContactPerson);
    parmMap.put("supplierOwnContactPerson", supplierOwnContactPerson);
    parmMap.put("userName", userName);
    parmMap.put("startIndex", startIndex);
    parmMap.put("pageSize", pageSize);
    parmMap.put("userId", userDto.getId());
    parmMap.put("year", year);

    int total = contractOrderService.getOrderTotal(parmMap);
    List<TOrderInfor> orderList = contractOrderService.getContractOrderList(parmMap);
    PrintWriter out = response.getWriter();
    ReserveOrderBean bean = new ReserveOrderBean();
    bean.setRoot(orderList);
    bean.setTotalProperty(total);
    out.println(JSONObject.fromObject(bean).toString());
    out.flush();
    out.close();
    return null;
  }

  public ContractOrderService getContractOrderService() {
    return contractOrderService;
  }

  public void setContractOrderService(ContractOrderService contractOrderService) {
    this.contractOrderService = contractOrderService;
  }

}
