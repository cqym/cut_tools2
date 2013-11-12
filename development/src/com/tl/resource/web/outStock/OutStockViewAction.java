package com.tl.resource.web.outStock;

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
import com.tl.resource.business.dto.OutStockInforDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.outStock.OutStockService;

public class OutStockViewAction extends DispatchAction {
  private OutStockService outStockService;

  public ActionForward outStockList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
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
    Map<String, String> mparams = new HashMap<String, String>();
    mparams.put("contractCode", request.getParameter("contractCode"));
    mparams.put("quotationCode", request.getParameter("quotationCode"));
    mparams.put("status", request.getParameter("status"));
    mparams.put("memo", request.getParameter("memo"));
    if ("-1".equals(mparams.get("status"))) {
      mparams.put("status", null);
    }
    mparams.put("outStockCode", request.getParameter("outStockCode"));
    mparams.put("startDate", request.getParameter("startDate"));
    mparams.put("endDate", request.getParameter("endDate"));
    mparams.put("outStockType", request.getParameter("outStockType"));//
    mparams.put("year", request.getParameter("year"));

    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    mparams.put("currentUserId", user.getId());
    String sort = request.getParameter("sort");
    if (sort != null) {
      if ("editDateString".equals(sort)) {
        sort = "editDate";
      }
      mparams.put("sort", RegexUtils.toDataBaseColName(sort));
      mparams.put("dir", request.getParameter("dir"));
    }
    PaginationSupport ps = outStockService.findOutStockInfors(mparams, Integer.valueOf(startIndex), Integer.valueOf(pageSize));

    out.println(JSONObject.fromObject(ps));
    out.flush();
    out.close();
    return null;
  }

  public ActionForward outStockViewById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String id = request.getParameter("id");

    OutStockInforDto osInfor = outStockService.getOutStockInforDtoById(id);
    String rt = JSONObject.fromObject(osInfor).toString();
    //System.out.println(rt);
    out.println(rt);
    out.flush();
    out.close();
    return null;
  }

  public OutStockService getOutStockService() {
    return outStockService;
  }

  public void setOutStockService(OutStockService outStockService) {
    this.outStockService = outStockService;
  }

}
