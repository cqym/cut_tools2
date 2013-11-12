package com.tl.resource.web.purchaseOrder;

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
import com.tl.resource.business.dto.OrderDetialsDto;
import com.tl.resource.business.dto.OrderInfoDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.purchaseOrder.PurchaseOrderEditService;
import com.tl.resource.dao.pojo.TOrderInfor;

public class PurchaseOrderEditAction extends DispatchAction {
  private PurchaseOrderEditService purchaseOrderEditService;

  public ActionForward consultContractInfor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String contractId = request.getParameter("contractId");
    String supplierId = request.getParameter("supplierId");
    String brand = request.getParameter("brand");
    String leaf = request.getParameter("leaf");
    String toolsTypeName = request.getParameter("toolsTypeName");

    OrderInfoDto orderInfor = purchaseOrderEditService.consultContractInfor(contractId, supplierId, brand, leaf, toolsTypeName);
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    orderInfor.setUserId(user.getId());
    orderInfor.setUserName(user.getTrueName());
    out.print(JSONObject.fromObject(orderInfor));
    out.flush();
    out.close();
    return null;
  }

  public ActionForward consultScheduleQuo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String quotationId = request.getParameter("quotationId");
    String supplierId = request.getParameter("supplierId");
    String brand = request.getParameter("brand");
    String leaf = request.getParameter("leaf");
    String toolsTypeName = request.getParameter("toolsTypeName");

    OrderInfoDto orderInfor = purchaseOrderEditService.consultScheduleQuo(quotationId, supplierId, brand, leaf, toolsTypeName);
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    orderInfor.setUserId(user.getId());
    orderInfor.setUserName(user.getTrueName());
    out.print(JSONObject.fromObject(orderInfor));
    out.flush();
    out.close();
    return null;
  }

  public ActionForward consultTryQuo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String tryQuoId = request.getParameter("quotationId");
    String supplierId = request.getParameter("supplierId");
    String brand = request.getParameter("brand");
    String leaf = request.getParameter("leaf");
    String toolsTypeName = request.getParameter("toolsTypeName");

    OrderInfoDto orderInfor = purchaseOrderEditService.consultTryQuo(tryQuoId, supplierId, brand, leaf, toolsTypeName);
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    orderInfor.setUserId(user.getId());
    orderInfor.setUserName(user.getTrueName());
    out.print(JSONObject.fromObject(orderInfor));
    out.flush();
    out.close();
    return null;
  }

  public ActionForward consultPlans(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String[] planId = request.getParameterValues("planId");
    String supplierId = request.getParameter("supplierId");
    String brand = request.getParameter("brand");
    OrderInfoDto orderInfor = purchaseOrderEditService.consultPlans(planId, supplierId, brand);
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    orderInfor.setUserId(user.getId());
    orderInfor.setUserName(user.getTrueName());
    out.print(JSONObject.fromObject(orderInfor));
    out.flush();
    out.close();
    return null;
  }

  public ActionForward consultReserver(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String supplierId = request.getParameter("supplierId");
    OrderInfoDto orderInfor = purchaseOrderEditService.consultReserver(supplierId);
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    orderInfor.setUserId(user.getId());
    orderInfor.setUserName(user.getTrueName());
    out.print(JSONObject.fromObject(orderInfor));
    out.flush();
    out.close();
    return null;
  }

  public ActionForward addPurchaseOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String orderInforJsonStr = request.getParameter("orderInfor");
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();

    PrintWriter out = response.getWriter();
    JSONObject orderInforJson = JSONObject.fromObject(orderInforJsonStr);
    OrderInfoDto dto = (OrderInfoDto) JSONObject.toBean(orderInforJson, OrderInfoDto.class);
    dto.setEditDate(new Date());
    dto.setUserName(user.getTrueName());
    dto.setUserId(user.getId());

    JSONArray orderDetailArray = orderInforJson.getJSONArray("orderDetail");
    List<OrderDetialsDto> orderDetailList = new ArrayList<OrderDetialsDto>();
    dto.setOrderDetail(orderDetailList);
    for (Iterator iterator = orderDetailArray.iterator(); iterator.hasNext();) {
      JSONObject orderDetailjson = (JSONObject) iterator.next();
      OrderDetialsDto orderDetailDto = (OrderDetialsDto) JSONObject.toBean(orderDetailjson, OrderDetialsDto.class);
      orderDetailList.add(orderDetailDto);
    }

    try {
      purchaseOrderEditService.addPurchaseOrder(dto);
      out.print(dto.getId());
    } catch (RuntimeException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      out.print(false);
    }

    out.flush();
    out.close();
    return null;
  }

  public ActionForward updatePurchaseOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    String orderInforJsonStr = request.getParameter("orderInfor");
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();

    PrintWriter out = response.getWriter();
    JSONObject orderInforJson = JSONObject.fromObject(orderInforJsonStr);
    OrderInfoDto dto = (OrderInfoDto) JSONObject.toBean(orderInforJson, OrderInfoDto.class);
    //dto.setEditDate(new Date());
    dto.setUpdateDate(new Date());
    dto.setUserName(user.getTrueName());
    dto.setUserId(user.getId());

    JSONArray orderDetailArray = orderInforJson.getJSONArray("orderDetail");
    List<OrderDetialsDto> orderDetailList = new ArrayList<OrderDetialsDto>();
    dto.setOrderDetail(orderDetailList);
    for (Iterator iterator = orderDetailArray.iterator(); iterator.hasNext();) {
      JSONObject orderDetailjson = (JSONObject) iterator.next();
      OrderDetialsDto orderDetailDto = (OrderDetialsDto) JSONObject.toBean(orderDetailjson, OrderDetialsDto.class);
      orderDetailList.add(orderDetailDto);
    }

    try {
      purchaseOrderEditService.updatePurchaseOrder(dto);
      out.println(true);
    } catch (RuntimeException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      out.println(false);
    }

    out.flush();
    out.close();
    return null;
  }

  public ActionForward deletePurchaseOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String[] ids = request.getParameterValues("ids");
    purchaseOrderEditService.deleteOrderByIds(ids);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward placePurchaseOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    String[] ids = request.getParameterValues("ids");
    String rt = "{success : true, msg : '提交下单成功'}";
    try {
      if (ids != null && ids.length > 0) {
        for (int i = 0; i < ids.length; i++) {
          purchaseOrderEditService.placeOrder(ids[i]);
        }
      }
    } catch (RuntimeException e) {
      rt = e.getMessage();
    }
    PrintWriter out = response.getWriter();
    out.write(rt);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward deleteOrderDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String order = request.getParameter("orderInfor");
    JSONObject orderObj = JSONObject.fromObject(order);
    TOrderInfor orderInfor = (TOrderInfor) JSONObject.toBean(orderObj, TOrderInfor.class);
    String[] ids = request.getParameterValues("ids");
    purchaseOrderEditService.deleteOrderDetailByIds(ids, orderInfor);
    out.flush();
    out.close();
    return null;
  }

  public PurchaseOrderEditService getPurchaseOrderEditService() {
    return purchaseOrderEditService;
  }

  public void setPurchaseOrderEditService(PurchaseOrderEditService purchaseOrderEditService) {
    this.purchaseOrderEditService = purchaseOrderEditService;
  }

}
