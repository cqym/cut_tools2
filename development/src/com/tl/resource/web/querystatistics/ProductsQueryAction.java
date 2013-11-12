package com.tl.resource.web.querystatistics;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.common.util.LoginInforUtil;
import com.tl.common.util.PaginationSupport;
import com.tl.common.util.RegexUtils;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.querystatistics.ProductsQueryService;

public class ProductsQueryAction extends DispatchAction {
  private ProductsQueryService productsQueryService;

  public ActionForward productsQueryList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String startIndex = request.getParameter("start");
    String pageSize = request.getParameter("limit");
    if (startIndex == null) {
      startIndex = "0";
    }
    if (pageSize == null) {
      pageSize = "10";
    }
    Map<String, Object> mparams = new HashMap<String, Object>();
    mparams.put("productName", request.getParameter("productName"));
    mparams.put("brandCode", request.getParameter("brandCode"));
    if (!"-2".equals(request.getParameter("status"))) {
      mparams.put("status", request.getParameter("status"));
    }
    String sort = request.getParameter("sort");
    if (sort != null) {
      if ("editDateString".equals(sort)) {
        sort = "editDate";
      }
      mparams.put("sort", RegexUtils.toDataBaseColName(sort));
      mparams.put("dir", request.getParameter("dir"));
    }
    mparams.put("startTime", request.getParameter("startTime"));
    mparams.put("endTime", request.getParameter("endTime"));
    mparams.put("productBrand", request.getParameter("productBrand"));
    mparams.put("productSortCode", request.getParameter("productSortCode") == null ? null : request.getParameter("productSortCode").toUpperCase());
    mparams.put("productSource", request.getParameter("productSource"));
    mparams.put("year", request.getParameter("year"));

    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    mparams.put("currUserId", user.getId());
    PaginationSupport pageInfor = productsQueryService.getList(mparams, Integer.valueOf(startIndex), Integer.valueOf(pageSize));
    String rt = JSONObject.fromObject(pageInfor).toString();
    out.println(rt);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward productsSaleBillsQueryList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String startIndex = request.getParameter("start");
    String pageSize = request.getParameter("limit");
    if (startIndex == null) {
      startIndex = "0";
    }
    if (pageSize == null) {
      pageSize = "10";
    }
    Map<String, Object> mparams = new HashMap<String, Object>();
    mparams.put("customerName", request.getParameter("customerName"));
    mparams.put("startTime", request.getParameter("startTime"));
    mparams.put("endTime", request.getParameter("endTime"));
    mparams.put("year", request.getParameter("year"));
    mparams.put("toolsId", request.getParameter("toolsId"));
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    mparams.put("currUserId", user.getId());
    PaginationSupport pageInfor = productsQueryService.getSaleBillList(mparams, Integer.valueOf(startIndex), Integer.valueOf(pageSize));
    String rt = JSONObject.fromObject(pageInfor).toString();
    out.println(rt);
    out.flush();
    out.close();
    return null;
  }

  public ProductsQueryService getProductsQueryService() {
    return productsQueryService;
  }

  public void setProductsQueryService(ProductsQueryService productsQueryService) {
    this.productsQueryService = productsQueryService;
  }

}
