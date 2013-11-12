package com.tl.resource.web.contract;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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

import com.tl.common.util.LoginInforUtil;
import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.InvoiceDetailDto;
import com.tl.resource.business.dto.InvoiceInforDto;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.invoice.InvoiceService;
import com.tl.resource.dao.pojo.TCustomersInfor;

public class InvoiceAction extends DispatchAction {
  private InvoiceService invoiceService;

  public ActionForward viewInvoiceDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
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
      pageSize = "20";
    }
    Map<String, String> mparams = new HashMap<String, String>();
    mparams.put("contractInforId", request.getParameter("contractInforId"));
    mparams.put("invoiceType", request.getParameter("invoiceType"));

    PaginationSupport pageInfor = invoiceService.viewInvoiceInfors(mparams, Integer.valueOf(startIndex), Integer.valueOf(pageSize));
    String rt = JSONObject.fromObject(pageInfor).toString();
    out.println(rt);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward getInvoiceInfor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    InvoiceInforDto dto = invoiceService.getInvoiceInfor(request.getParameter("contractInforId"));
    TCustomersInfor cusinfor = null;
    if ("1".equals(request.getParameter("invoiceType"))) {
      cusinfor = invoiceService.getSuppliersInforByCode(request.getParameter("customerCode"));
    } else {
      cusinfor = invoiceService.getCustomerInforByCode(request.getParameter("customerCode"));
    }

    String rt = JSONObject.fromObject(dto).toString();
    String rt2 = JSONObject.fromObject(cusinfor).toString();
    out.print(new StringBuffer(200).append("var invoiceInfor=").append(rt).append(";var customerInfor=").append(rt2));
    out.flush();
    out.close();
    return null;
  }

  public ActionForward getCustomerInforByCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String id = request.getParameter("id");
    PrintWriter out = response.getWriter();
    TCustomersInfor dto = invoiceService.getCustomerInforByCode(id);
    String rt = JSONObject.fromObject(dto).toString();
    out.println(rt);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward addInvoiceInfor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String invoiceInforJsonStr = request.getParameter("invoiceInfor");
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    JSONObject invoiceInforJson = JSONObject.fromObject(invoiceInforJsonStr);
    InvoiceInforDto dto = (InvoiceInforDto) JSONObject.toBean(invoiceInforJson, InvoiceInforDto.class);
    dto.setInvoiceDate(new Date());
    dto.setUserName(user.getTrueName());
    dto.setUserId(user.getId());

    JSONArray invoiceDetailArray = invoiceInforJson.getJSONArray("invoiceDetail");
    List<InvoiceDetailDto> conProSortsList = new ArrayList<InvoiceDetailDto>();
    dto.setInvoiceDetail(conProSortsList);

    for (Iterator iterator = invoiceDetailArray.iterator(); iterator.hasNext();) {
      JSONObject detailjson = (JSONObject) iterator.next();
      InvoiceDetailDto detaildto = (InvoiceDetailDto) JSONObject.toBean(detailjson, InvoiceDetailDto.class);
      conProSortsList.add(detaildto);
    }
    invoiceService.saveInvoiceInfor(dto);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward deleteInvoiceInfor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String id = request.getParameter("id");
    String conDetailId = request.getParameter("conDetailId");
    String rt = invoiceService.deleteInvoiceInfor(id, conDetailId);
    out.print(rt == null ? "" : rt);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward updateContractInvoiceStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String conId = request.getParameter("conId");
    String value = request.getParameter("value");
    String rt = invoiceService.updateContractInvoiceStatus(conId, Integer.valueOf(value));
    out.print(rt == null ? "" : rt);
    out.flush();
    out.close();
    return null;
  }

  public InvoiceService getInvoiceService() {
    return invoiceService;
  }

  public void setInvoiceService(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

}
