/**
 * 
 */
package com.tl.resource.web.baseInfo;

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

import com.tl.common.util.RegexUtils;
import com.tl.resource.business.baseInfo.BaseInfoService;
import com.tl.resource.dao.pojo.TCustomersInfor;

/**
 * @author xtaia
 * 客户信息列表
 */
public class CustomersListAction extends Action {

  private BaseInfoService baseInfoService;

  public BaseInfoService getBaseInfoService() {
    return baseInfoService;
  }

  public void setBaseInfoService(BaseInfoService baseInfoService) {
    this.baseInfoService = baseInfoService;
  }

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setContentType("text/html;charset=utf-8");

    String start = request.getParameter("start") == null ? "0" : request.getParameter("start");
    String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit");

    Map<String, Object> parmMap = new HashMap<String, Object>();

    String searchStr = request.getParameter("searchStr");
    if (searchStr != null) {

      JSONObject searchJson = JSONObject.fromObject(searchStr);
      if (searchJson.has("customerName")) {
        parmMap.put("customerName", searchJson.getString("customerName"));

      }
      if (searchJson.has("customerCode")) {
        parmMap.put("customerCode", searchJson.getString("customerCode"));

      }
      if (searchJson.has("degreeCode")) {
        parmMap.put("degreeCode", searchJson.getString("degreeCode"));

      }
      if (searchJson.has("ownContactPerson")) {
        parmMap.put("ownContactPerson", searchJson.getString("ownContactPerson"));

      }
      //System.out.println(searchJson.getString("degreeCode") + "@@@@@@@@@@@@@" + searchJson.getString("ownContactPerson"));

    }

    parmMap.put("start", Integer.parseInt(start));
    parmMap.put("limit", Integer.parseInt(limit));

    //得到客户的总数
    int total = baseInfoService.getCustomersCount(parmMap);

    String sort = request.getParameter("sort");
    if (sort != null) {
      parmMap.put("sort", RegexUtils.toDataBaseColName(sort));
      parmMap.put("dir", request.getParameter("dir"));
    }

    List<TCustomersInfor> list = baseInfoService.getCustomersList(parmMap);

    String jsonStr = JSONArray.fromObject(list).toString();
    String resultStr = "{totalProperty : " + total + ", customersList : " + jsonStr + "}";

    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();

    return null;
  }

}
