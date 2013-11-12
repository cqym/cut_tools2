package com.tl.resource.web.quotation.generalquo;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
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

import com.tl.common.util.RegexUtils;
import com.tl.resource.business.baseInfo.CustomerService;
import com.tl.resource.business.quotation.generalquo.GeneralQuoService;
import com.tl.resource.dao.pojo.TCustomersInfor;

public class GetCustomerAction extends Action {
  private GeneralQuoService generalQuoService;

  private CustomerService customerService;

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setContentType("text/html;charset=utf-8");

    String start = request.getParameter("start") == null ? "0" : request.getParameter("start");
    String limit = request.getParameter("limit") == null ? "100" : request.getParameter("limit");
    String searchStr = request.getParameter("searchStr");
    JSONObject jsonObj = JSONObject.fromObject(searchStr);
    String customerCode = "";
    String customerName = "";

    if (jsonObj != null && !jsonObj.isNullObject()) {
      if (jsonObj.has("customerCode")) {
        customerCode = jsonObj.getString("customerCode");
      }
      if (jsonObj.has("customerName")) {
        customerName = jsonObj.getString("customerName");
      }
    }

    Map<String, Object> parmMap = new HashMap<String, Object>();

    parmMap.put("start", Integer.parseInt(start));
    parmMap.put("limit", Integer.parseInt(limit));
    parmMap.put("customerCode", customerCode);
    parmMap.put("customerName", customerName);

    int total = generalQuoService.getCustomerInfoTotal(parmMap);

    String sort = request.getParameter("sort");
    if (sort != null) {
      if ("editTimeStr".equals(sort)) {
        sort = "editDate";
      }
      parmMap.put("sort", RegexUtils.toDataBaseColName(sort));
      parmMap.put("dir", request.getParameter("dir"));
    }

    List<TCustomersInfor> list = generalQuoService.getCustomerInfoByBage(parmMap);
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
      TCustomersInfor customersInfor = (TCustomersInfor) iterator.next();
      customersInfor.setSumAccount(customerService.getSumAccountByCustomerCode(customersInfor.getCustomerCode()));
      customersInfor.setSumContractMoney(customerService.getSumContractMoneyByCustomerCode(customersInfor.getCustomerCode()));
    }
    String jsonResult = JSONArray.fromObject(list).toString();
    //System.out.println(jsonResult);
    PrintWriter out = response.getWriter();

    String resultStr = "{totalProperty : " + total + ", customer : " + jsonResult + "}";

    out.write(resultStr);
    out.flush();
    out.close();

    return null;
  }

  public GeneralQuoService getGeneralQuoService() {
    return generalQuoService;
  }

  public void setGeneralQuoService(GeneralQuoService generalQuoService) {
    this.generalQuoService = generalQuoService;
  }

  public CustomerService getCustomerService() {
    return customerService;
  }

  public void setCustomerService(CustomerService customerService) {
    this.customerService = customerService;
  }

}
