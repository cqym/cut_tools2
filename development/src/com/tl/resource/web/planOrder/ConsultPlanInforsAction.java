package com.tl.resource.web.planOrder;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.common.util.LoginInforUtil;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.OrderDetialsDto;
import com.tl.resource.business.planOrder.PlanOrderService;

public class ConsultPlanInforsAction extends Action {
  private PlanOrderService planOrderService;

  public PlanOrderService getPlanOrderService() {
    return planOrderService;
  }

  public void setPlanOrderService(PlanOrderService planOrderService) {
    this.planOrderService = planOrderService;
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String supplierId = request.getParameter("supplierId");
    String[] planId = request.getParameterValues("planId");
    List<OrderDetialsDto> list = planOrderService.consultPlanInfors(supplierId, planId);
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    out.println(JSONArray.fromObject(list));
    out.flush();
    out.close();
    return null;
  }
}
