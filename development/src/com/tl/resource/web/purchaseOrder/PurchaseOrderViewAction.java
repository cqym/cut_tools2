package com.tl.resource.web.purchaseOrder;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.resource.business.dto.OrderInfoDto;
import com.tl.resource.business.purchaseOrder.PurchaseOrderViewService;

public class PurchaseOrderViewAction extends DispatchAction {
  private PurchaseOrderViewService purchaseOrderViewService;

  public ActionForward orderViewById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String id = request.getParameter("id");

    OrderInfoDto conInfor = purchaseOrderViewService.getPurchaseOrderById(id);
    String rt = JSONObject.fromObject(conInfor).toString();
    out.println(rt);
    out.flush();
    out.close();
    return null;
  }

  public PurchaseOrderViewService getPurchaseOrderViewService() {
    return purchaseOrderViewService;
  }

  public void setPurchaseOrderViewService(PurchaseOrderViewService purchaseOrderViewService) {
    this.purchaseOrderViewService = purchaseOrderViewService;
  }

}
