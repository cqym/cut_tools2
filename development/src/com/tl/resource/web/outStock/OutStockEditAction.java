package com.tl.resource.web.outStock;

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
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.OutStockDetailDto;
import com.tl.resource.business.dto.OutStockInforDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.manage.BillsCodeDefService;
import com.tl.resource.business.outStock.OutStockService;

public class OutStockEditAction extends DispatchAction {
  private OutStockService outStockService;

  private BillsCodeDefService billsCodeDefService;

  public ActionForward consultReserveInfors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
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
    mparams.put("brandCode", request.getParameter("brandCode"));
    mparams.put("productCode", request.getParameter("productCode"));
    mparams.put("productName", request.getParameter("productName"));
    mparams.put("productBrand", request.getParameter("productBrand"));

    PaginationSupport ps = outStockService.consultReserveInfors(mparams, Integer.valueOf(startIndex), Integer.valueOf(pageSize));

    out.println(JSONObject.fromObject(ps));
    out.flush();
    out.close();
    return null;
  }

  public ActionForward consultContractProducts(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
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
    mparams.put("brandCode", request.getParameter("brandCode"));
    mparams.put("productCode", request.getParameter("productCode"));
    mparams.put("productName", request.getParameter("productName"));
    mparams.put("productBrand", request.getParameter("productBrand"));
    mparams.put("contractId", request.getParameter("contractId"));
    mparams.put("notHasYd", "yes");
    mparams.put("leaf", request.getParameter("leaf"));

    PaginationSupport ps = outStockService.consultContractProducts(mparams, Integer.valueOf(startIndex), Integer.valueOf(pageSize));

    out.println(JSONObject.fromObject(ps));
    out.flush();
    out.close();
    return null;
  }

  public ActionForward getWillOutStockContractDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();

    String contractId = request.getParameter("contractId");
    String leaf = request.getParameter("leaf");

    List<OutStockDetailDto> list = outStockService.getWillOutStockContractDetail(contractId, leaf);

    out.println(JSONArray.fromObject(list));
    out.flush();
    out.close();
    return null;
  }

  public ActionForward consultQuotationProducts(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
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
    mparams.put("brandCode", request.getParameter("brandCode"));
    mparams.put("productCode", request.getParameter("productCode"));
    mparams.put("productName", request.getParameter("productName"));
    mparams.put("productBrand", request.getParameter("productBrand"));
    mparams.put("quotationInforId", request.getParameter("quotationInforId"));
    mparams.put("leaf", request.getParameter("leaf"));
    PaginationSupport ps = outStockService.consultQuotationProducts(mparams, Integer.valueOf(startIndex), Integer.valueOf(pageSize));

    out.println(JSONObject.fromObject(ps));
    out.flush();
    out.close();
    return null;
  }

  public ActionForward getWillOutStockQuotationDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();

    String quotationInforId = request.getParameter("quotationInforId");
    String leaf = request.getParameter("leaf");

    List<OutStockDetailDto> list = outStockService.getWillOutStockQuotationDetail(quotationInforId,leaf);

    out.println(JSONArray.fromObject(list));
    out.flush();
    out.close();
    return null;
  }

  public ActionForward addOutStock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String outStockInforJsonStr = request.getParameter("OutStockInfor");
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();

    PrintWriter out = response.getWriter();
    JSONObject outStockInforJson = JSONObject.fromObject(outStockInforJsonStr);
    OutStockInforDto dto = (OutStockInforDto) JSONObject.toBean(outStockInforJson, OutStockInforDto.class);
    dto.setEditDate(new Date());
    dto.setUserName(user.getUserName());
    dto.setUserId(user.getId());
    if (1 == (dto.getOutStockType()) || 4 == (dto.getOutStockType())) {//1     合同出库(提取库存)，4     合同出库(采购),
      dto.setOutStockCode(billsCodeDefService.getBillCode("08", null, dto.getCustomerCode(), null));
    } else if (2 == (dto.getOutStockType()) || 5 == (dto.getOutStockType())) {//2     预定报价单出库(采购)，5     预定报价单出库(提取库存)
      dto.setOutStockCode(billsCodeDefService.getBillCode("18", null, dto.getCustomerCode(), null));
    } else if (6 == (dto.getOutStockType()) || 7 == (dto.getOutStockType())) {//6     试刀(提取库存),7     试刀(采购)
      dto.setOutStockCode(billsCodeDefService.getBillCode("19", null, dto.getCustomerCode(), null));
    } else if (0 == (dto.getOutStockType())) {//0   直接出库
      dto.setOutStockCode(billsCodeDefService.getBillCode("20", null, null, null));
    }
    //System.out.println("getFinalMoney:" + dto.getFinalMoney());
    //System.out.println(dto.getContractProductSorts());
    JSONArray productsArray = outStockInforJson.getJSONArray("outStockDetails");
    List<OutStockDetailDto> productsList = new ArrayList<OutStockDetailDto>();
    dto.setOutStockDetails(productsList);
    for (Iterator iterator = productsArray.iterator(); iterator.hasNext();) {
      JSONObject sjson = (JSONObject) iterator.next();
      OutStockDetailDto sortDto = (OutStockDetailDto) JSONObject.toBean(sjson, OutStockDetailDto.class);
      productsList.add(sortDto);
    }
    try {
      outStockService.addOutStockInfor(dto);
      out.println(1);
    } catch (RuntimeException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      //System.out.println("ssss:" + e.getMessage());
      out.println(e.getMessage());
    }

    out.flush();
    out.close();
    return null;
  }

  public ActionForward updateOutStock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String outStockInforJsonStr = request.getParameter("OutStockInfor");
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    //System.out.println(outStockInforJsonStr);
    PrintWriter out = response.getWriter();
    JSONObject outStockInforJson = JSONObject.fromObject(outStockInforJsonStr);
    OutStockInforDto dto = (OutStockInforDto) JSONObject.toBean(outStockInforJson, OutStockInforDto.class);
    dto.setEditDate(new Date());
    dto.setUserName(user.getUserName());
    dto.setUserId(user.getId());
    //System.out.println("getFinalMoney:" + dto.getFinalMoney());
    //System.out.println(dto.getContractProductSorts());
    JSONArray productsArray = outStockInforJson.getJSONArray("outStockDetails");
    List<OutStockDetailDto> productsList = new ArrayList<OutStockDetailDto>();
    dto.setOutStockDetails(productsList);
    for (Iterator iterator = productsArray.iterator(); iterator.hasNext();) {
      JSONObject sjson = (JSONObject) iterator.next();
      OutStockDetailDto sortDto = (OutStockDetailDto) JSONObject.toBean(sjson, OutStockDetailDto.class);
      productsList.add(sortDto);
    }
    try {
      outStockService.updateOutStockInfor(dto);
      out.println(1);
    } catch (RuntimeException e) {
      // TODO Auto-generated catch block
      out.println(e.getMessage());
    }

    out.flush();
    out.close();
    return null;
  }

  public ActionForward deleteOutStock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String[] ids = request.getParameterValues("ids");
    for (int i = 0; i < ids.length; i++) {
      outStockService.deleteOutStockInforDto(ids[i]);
    }
    PrintWriter out = response.getWriter();
    out.println(true);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward affirmOutStock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    String[] ids = request.getParameterValues("ids");
    for (int i = 0; i < ids.length; i++) {
      outStockService.affirmOutStock(ids[i], user);
    }
    PrintWriter out = response.getWriter();
    out.println(true);
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

  public BillsCodeDefService getBillsCodeDefService() {
    return billsCodeDefService;
  }

  public void setBillsCodeDefService(BillsCodeDefService billsCodeDefService) {
    this.billsCodeDefService = billsCodeDefService;
  }

}
