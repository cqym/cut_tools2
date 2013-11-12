package com.tl.resource.web.outStock;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.common.util.LoginInforUtil;
import com.tl.resource.business.dto.LoginInforDto;
import com.tl.resource.business.dto.OutStockInforDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.outStock.MaterialOutStockService;

public class MaterialOutStockViewAction extends DispatchAction {
  private MaterialOutStockService materialOutStockService;

  public ActionForward consultPlanoducts(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {

    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    LoginInforDto loginInfor = LoginInforUtil.getLoginInfor(request);
    UserDto user = loginInfor.getUser();
    OutStockInforDto osInfor = materialOutStockService.consultPlanProducts(request.getParameterValues("planIds"));
    osInfor.setUserName(user.getUserName());
    String rt = JSONObject.fromObject(osInfor).toString();
    //System.out.println(rt);
    out.print(rt);
    out.flush();
    out.close();
    return null;
  }

  public ActionForward outStockViewById(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
    String id = request.getParameter("id");

    OutStockInforDto osInfor = materialOutStockService.getOutStockInforById(id);
    String rt = JSONObject.fromObject(osInfor).toString();
    //System.out.println(rt);
    out.println(rt);
    out.flush();
    out.close();
    return null;
  }

  public MaterialOutStockService getMaterialOutStockService() {
    return materialOutStockService;
  }

  public void setMaterialOutStockService(MaterialOutStockService materialOutStockService) {
    this.materialOutStockService = materialOutStockService;
  }

}
