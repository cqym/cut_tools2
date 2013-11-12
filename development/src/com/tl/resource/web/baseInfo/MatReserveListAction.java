/**
 * 
 */
package com.tl.resource.web.baseInfo;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.baseInfo.MatReserveInforService;

/**
 * @author xtaia
 * 材料库存信息
 */
public class MatReserveListAction extends Action {

  private MatReserveInforService matReserveInforService;

  public MatReserveInforService getMatReserveInforService() {
    return matReserveInforService;
  }

  public void setMatReserveInforService(MatReserveInforService matReserveInforService) {
    this.matReserveInforService = matReserveInforService;
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

    PaginationSupport page = matReserveInforService.findReserveInfor(parmMap, Integer.parseInt(start), Integer.parseInt(limit));

    PrintWriter out = response.getWriter();
    out.write(JSONObject.fromObject(page).toString());
    out.flush();
    out.close();

    return null;
  }

}
