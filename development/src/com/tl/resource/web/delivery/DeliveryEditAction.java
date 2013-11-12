package com.tl.resource.web.delivery;

import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.tl.resource.business.delivery.DeliveryEditService;
import com.tl.resource.business.dto.DeliveryInforDto;
import com.tl.resource.business.dto.DeliveryProductDetailDto;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.manage.BillsCodeDefService;

public class DeliveryEditAction extends DispatchAction {
  private DeliveryEditService deliveryEditService;

  private BillsCodeDefService billsCodeDefService;

  public ActionForward consultContract(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String id = request.getParameter("id");
    PrintWriter out = response.getWriter();
    DeliveryInforDto infor = deliveryEditService.consultContract(id);
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    infor.setUserName(user.getTrueName());
    String rt = JSONObject.fromObject(infor).toString();

    out.println(rt);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward consultQuotation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String id = request.getParameter("id");
    PrintWriter out = response.getWriter();
    DeliveryInforDto infor = deliveryEditService.consultQuotation(id);
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    infor.setUserName(user.getTrueName());
    String rt = JSONObject.fromObject(infor).toString();

    out.println(rt);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward addDelivery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String jsonSring = request.getParameter("data");
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    PrintWriter out = response.getWriter();

    JSONObject jsonObj = JSONObject.fromObject(jsonSring);
    DeliveryInforDto dto = (DeliveryInforDto) JSONObject.toBean(jsonObj, DeliveryInforDto.class);
    JSONArray productSortsArray = jsonObj.getJSONArray("deliveryProductDetailDto");
    List<DeliveryProductDetailDto> detail = new ArrayList<DeliveryProductDetailDto>();
    for (Iterator iterator = productSortsArray.iterator(); iterator.hasNext();) {
      JSONObject sjson = (JSONObject) iterator.next();
      DeliveryProductDetailDto object = (DeliveryProductDetailDto) JSONObject.toBean(sjson, DeliveryProductDetailDto.class);
      detail.add(object);
    }
    dto.setDeliveryProductDetailDto(detail);
    dto.setUserId(user.getId());
    dto.setUserName(user.getTrueName());
    if (dto.getDeliveryType() == 0) {//0按合同交货
      dto.setDeliveryCode(billsCodeDefService.getBillCode("01", null, dto.getCustomerCode(), null));
    } else if (1 == (dto.getDeliveryType())) {//1按报价单交货(预定) 
      dto.setDeliveryCode(billsCodeDefService.getBillCode("21", null, dto.getCustomerCode(), null));
    } else if (2 == (dto.getDeliveryType())) {//2 按保价单交货(试刀)
      dto.setDeliveryCode(billsCodeDefService.getBillCode("22", null, dto.getCustomerCode(), null));
    }
    deliveryEditService.addDeliveryInfor(dto);
    String rt = null;
    out.println(rt);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward updateDelivery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String jsonSring = request.getParameter("data");
    JSONObject jsonObj = JSONObject.fromObject(jsonSring);
    DeliveryInforDto dto = (DeliveryInforDto) JSONObject.toBean(jsonObj, DeliveryInforDto.class);
    JSONArray productSortsArray = jsonObj.getJSONArray("deliveryProductDetailDto");
    List<DeliveryProductDetailDto> detail = new ArrayList<DeliveryProductDetailDto>();
    for (Iterator iterator = productSortsArray.iterator(); iterator.hasNext();) {
      JSONObject sjson = (JSONObject) iterator.next();
      DeliveryProductDetailDto object = (DeliveryProductDetailDto) JSONObject.toBean(sjson, DeliveryProductDetailDto.class);
      detail.add(object);
    }
    dto.setDeliveryProductDetailDto(detail);
    deliveryEditService.updateDeliveryInfor(dto);
    PrintWriter out = response.getWriter();
    String rt = null;
    out.println(rt);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward deleteDelivery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String[] ids = request.getParameterValues("ids");
    for (int i = 0; i < ids.length; i++) {
      deliveryEditService.deleteDeliveryInfor(ids[i]);
    }

    PrintWriter out = response.getWriter();
    String rt = null;
    out.println(rt);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward invoidDelivery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String[] ids = request.getParameterValues("ids");
    for (int i = 0; i < ids.length; i++) {
      deliveryEditService.invoidDeliveryInfor(ids[i]);
    }

    PrintWriter out = response.getWriter();
    String rt = null;
    out.println(rt);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward delDeliveryDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String[] ids = request.getParameterValues("ids");
    for (int i = 0; i < ids.length; i++) {
      deliveryEditService.delDeliveryDetail(ids[i]);
    }

    PrintWriter out = response.getWriter();
    String rt = null;
    out.println(rt);
    out.flush();
    out.close();
    return null;
  }

  public DeliveryEditService getDeliveryEditService() {
    return deliveryEditService;
  }

  public void setDeliveryEditService(DeliveryEditService deliveryEditService) {
    this.deliveryEditService = deliveryEditService;
  }

  public BillsCodeDefService getBillsCodeDefService() {
    return billsCodeDefService;
  }

  public void setBillsCodeDefService(BillsCodeDefService billsCodeDefService) {
    this.billsCodeDefService = billsCodeDefService;
  }

}
