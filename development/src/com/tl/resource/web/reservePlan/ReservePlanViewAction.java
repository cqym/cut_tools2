package com.tl.resource.web.reservePlan;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.common.util.PaginationSupport;
import com.tl.common.util.RegexUtils;
import com.tl.resource.business.dto.ReservePlanDetailDto;
import com.tl.resource.business.dto.ReservePlanMainInforDto;
import com.tl.resource.business.reservePlan.ReservePlanService;

public class ReservePlanViewAction extends DispatchAction {
  private ReservePlanService reservePlanService;

  public ActionForward reservePlanList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
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
    mparams.put("productCode", request.getParameter("productCode"));
    mparams.put("planCode", request.getParameter("planCode"));
    mparams.put("orderCode", request.getParameter("orderCode"));
    if (!"-1".equals(request.getParameter("status"))) {
      mparams.put("status", request.getParameter("status"));
    }
    mparams.put("startDate", request.getParameter("startDate"));
    mparams.put("endDate", request.getParameter("endDate"));
    mparams.put("memo", request.getParameter("memo"));
    mparams.put("planType", request.getParameter("planType"));
    mparams.put("year", request.getParameter("year"));
    String sort = request.getParameter("sort");
    if (sort != null) {
      if ("editDateString".equals(sort)) {
        sort = "editDate";
      }
      mparams.put("sort", RegexUtils.toDataBaseColName(sort));
      mparams.put("dir", request.getParameter("dir"));
    }
    mparams.put("needToOrder", request.getParameter("needToOrder"));
    PaginationSupport ps = reservePlanService.findReservePlans(mparams, Integer.valueOf(startIndex), Integer.valueOf(pageSize));

    out.println(JSONObject.fromObject(ps));
    out.flush();
    out.close();
    return null;
  }

  public ActionForward reservePlanViewById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String planId = request.getParameter("planId");

    ReservePlanMainInforDto rePlanInfor = reservePlanService.getReservePlanMainInfor(planId);
    String rt = JSONObject.fromObject(rePlanInfor).toString();
    //System.out.println(rt);
    out.println(rt);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward findPlanDetailByMainId(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String mainId = request.getParameter("mainId");
    String status = request.getParameter("status");
    List<ReservePlanDetailDto> planDetailList = reservePlanService.findPlanDetailByMainId(mainId, status);
    String rt = JSONArray.fromObject(planDetailList).toString();
    //System.out.println(rt);
    out.println(rt);
    out.flush();
    out.close();
    return null;
  }

  public ReservePlanService getReservePlanService() {
    return reservePlanService;
  }

  public void setReservePlanService(ReservePlanService reservePlanService) {
    this.reservePlanService = reservePlanService;
  }

}
