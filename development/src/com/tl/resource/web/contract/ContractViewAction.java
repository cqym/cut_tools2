package com.tl.resource.web.contract;

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
import com.tl.resource.business.contract.ContractEditService;
import com.tl.resource.business.dto.ContractInforDto;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.UserDto;

public class ContractViewAction extends DispatchAction {
  private ContractEditService contractEditService = null;

  public ActionForward contractList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
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
    mparams.put("customerName", request.getParameter("customerName"));
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
    mparams.put("conType", request.getParameter("conType"));
    mparams.put("year", request.getParameter("year"));

    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    mparams.put("currUserId", user.getId());
    PaginationSupport pageInfor = contractEditService.findContractInfors(mparams, Integer.valueOf(startIndex), Integer.valueOf(pageSize));
    String rt = JSONObject.fromObject(pageInfor).toString();
    out.println(rt);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward contractViewById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String id = request.getParameter("id");

    ContractInforDto conInfor = contractEditService.getContractInforById(id);
    String rt = JSONObject.fromObject(conInfor).toString();
    out.println(rt);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward contractAllDetailView(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String id = request.getParameter("id");
    String startIndex = request.getParameter("start");
    String pageSize = request.getParameter("limit");

    if (startIndex == null) {
      startIndex = "0";
    }
    if (pageSize == null) {
      pageSize = String.valueOf(Integer.MAX_VALUE);
    }
    Map<String, String> params = new HashMap<String, String>();
    params.put("contractId", id);
    String sort = request.getParameter("sort");
    if (sort != null) {
      if ("editDateString".equals(sort)) {
        sort = "editDate";
      }
      params.put("sort", RegexUtils.toDataBaseColName(sort));
      params.put("dir", request.getParameter("dir"));
    }
    PaginationSupport conDetail = contractEditService.findContractDetail(params, Integer.valueOf(startIndex), Integer.valueOf(pageSize));
    String rt = JSONObject.fromObject(conDetail).toString();

    out.println(rt);
    out.flush();
    out.close();
    return null;
  }

  public ContractEditService getContractEditService() {
    return contractEditService;
  }

  public void setContractEditService(ContractEditService contractEditService) {
    this.contractEditService = contractEditService;
  }

}
