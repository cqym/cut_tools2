package com.tl.resource.web.delivery;

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
import com.tl.resource.business.delivery.DeliveryEditService;
import com.tl.resource.business.dto.DeliveryInforDto;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.UserDto;

public class DeliveryViewAction extends DispatchAction {
  private DeliveryEditService deliveryEditService;

  public ActionForward viewList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
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
    mparams.put("customerName", request.getParameter("customerName"));
    mparams.put("status", request.getParameter("status"));
    if ("-1".equals(mparams.get("status"))) {
      mparams.put("status", null);
    }
    mparams.put("startTime", request.getParameter("startTime"));
    mparams.put("endTime", request.getParameter("endTime"));
    mparams.put("memo", request.getParameter("memo"));

    mparams.put("deliveryType", request.getParameter("deliveryType"));
    mparams.put("deliveryCode", request.getParameter("deliveryCode"));
    mparams.put("year", request.getParameter("year"));

    String sort = request.getParameter("sort");
    if (sort != null) {
      if ("editDateString".equals(sort)) {
        sort = "editDate";
      }
      mparams.put("sort", RegexUtils.toDataBaseColName(sort));
      mparams.put("dir", request.getParameter("dir"));
    }
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    mparams.put("currentUserId", user.getId());
    //System.out.println("request.getParameter(\"contractIdIsNull\")------------------------"+request.getParameter("contractIdIsNull"));
    PaginationSupport page = deliveryEditService.findDeliveryInfors(mparams, Integer.valueOf(startIndex), Integer.valueOf(pageSize));
    PrintWriter out = response.getWriter();
    String rt = JSONObject.fromObject(page).toString();
    out.println(rt);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward deliveryViewById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String id = request.getParameter("id");

    DeliveryInforDto deInfor = deliveryEditService.getDeliveryInforById(id);
    String rt = JSONObject.fromObject(deInfor).toString();
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

}
