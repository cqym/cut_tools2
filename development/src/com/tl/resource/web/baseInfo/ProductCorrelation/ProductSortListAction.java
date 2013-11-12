package com.tl.resource.web.baseInfo.ProductCorrelation;

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

import com.tl.common.util.RegexUtils;
import com.tl.resource.business.baseInfo.ProductCorrelationService;
import com.tl.resource.dao.pojo.TProductSort;

/**
 * 
 * @author xtaia
 *  产品组别信息列表
 */
public class ProductSortListAction extends Action {

  private ProductCorrelationService productCorrelationService;

  public ProductCorrelationService getProductCorrelationService() {
    return productCorrelationService;
  }

  public void setProductCorrelationService(ProductCorrelationService productCorrelationService) {
    this.productCorrelationService = productCorrelationService;
  }

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    response.setContentType("text/html;charset=utf-8");

    String start = request.getParameter("start");
    start = (start == null ? "0" : start);
    String limit = request.getParameter("limit");
    limit = (limit == null ? "20" : limit);

    Map<String, Object> parmMap = new HashMap<String, Object>();

    String sort = request.getParameter("sort");
    if (sort != null) {
      parmMap.put("sort", RegexUtils.toDataBaseColName(sort));
      parmMap.put("dir", request.getParameter("dir"));
    }

    parmMap.put("start", Integer.parseInt(start));
    parmMap.put("limit", Integer.parseInt(limit));
    parmMap.put("productBrand", request.getParameter("productBrand"));
    parmMap.put("sortCode", request.getParameter("sortCode"));
    parmMap.put("sortName", request.getParameter("sortName"));
    parmMap.put("memo", request.getParameter("memo"));

    int total = productCorrelationService.getProductSortTotal(parmMap);

    List<TProductSort> list = productCorrelationService.getProductSortByPage(parmMap);

    String jsonStr = JSONArray.fromObject(list).toString();
    String resultStr = "{totalProperty : " + total + ", productSortList : " + jsonStr + "}";

    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();

    return null;
  }

}
