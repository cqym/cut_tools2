package com.tl.resource.web.outStock;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.common.util.LoginInforUtil;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.OutStockDetailDto;
import com.tl.resource.business.dto.OutStockInforDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.manage.BillsCodeDefService;
import com.tl.resource.business.outStock.OutStockService;

public class MaterialOutStockEditAction extends DispatchAction {
  private OutStockService outStockService;

  private BillsCodeDefService billsCodeDefService;

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
    dto.setOutStockCode(billsCodeDefService.getBillCode("29", null, null, null));

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
      e.printStackTrace();
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
    PrintWriter out = response.getWriter();
    JSONObject outStockInforJson = JSONObject.fromObject(outStockInforJsonStr);
    OutStockInforDto dto = (OutStockInforDto) JSONObject.toBean(outStockInforJson, OutStockInforDto.class);
    dto.setEditDate(new Date());
    dto.setUserName(user.getUserName());
    dto.setUserId(user.getId());
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
