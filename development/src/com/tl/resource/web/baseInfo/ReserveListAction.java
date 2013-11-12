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
import com.tl.resource.dao.pojo.TReserveInfor;

/**
 * @author xtaia
 * 库存信息
 */
public class ReserveListAction extends Action {

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

    Map<String, Object> parmMap = new HashMap<String, Object>();

    String searchStr = request.getParameter("searchStr");
    if (searchStr != null) {

      JSONObject searchJson = JSONObject.fromObject(searchStr);
      parmMap.put("productName", searchJson.getString("productName"));
      parmMap.put("productCode", searchJson.getString("productCode"));
      parmMap.put("productSort", searchJson.getString("sortCode"));
      parmMap.put("brandCode", searchJson.getString("brandCode"));
      parmMap.put("productBrand", searchJson.getString("productBrand"));
      parmMap.put("productSource", searchJson.getString("productSource"));
    }

    //System.out.println("searchStr" + searchStr);

    String start = request.getParameter("start");
    String limit = request.getParameter("limit");

    parmMap.put("start", Integer.parseInt(start));
    parmMap.put("limit", Integer.parseInt(limit));

    int total = baseInfoService.getReserveTotal(parmMap);

    String sort = request.getParameter("sort");
    if (sort != null) {
      parmMap.put("sort", RegexUtils.toDataBaseColName(sort));
      parmMap.put("dir", request.getParameter("dir"));
    }

    List<TReserveInfor> list = baseInfoService.getReserveByPage(parmMap);

    String jsonStr = JSONArray.fromObject(list).toString();
    String resultStr = "{totalProperty : " + total + ", reserveList : " + jsonStr + "}";

    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();

    return null;
  }

}
