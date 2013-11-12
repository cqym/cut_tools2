/**
 * 
 */
package com.tl.resource.web.baseInfo;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.resource.business.baseInfo.MatReserveInforService;
import com.tl.resource.dao.pojo.TMatReserveInfor;

/**
 * @author xtaia
 * 材料库存信息
 */
public class MatReserveManageAction extends DispatchAction {

  private MatReserveInforService matReserveInforService;

  public MatReserveInforService getMatReserveInforService() {
    return matReserveInforService;
  }

  public void setMatReserveInforService(MatReserveInforService matReserveInforService) {
    this.matReserveInforService = matReserveInforService;
  }

  public ActionForward getResInforByProdCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    response.setContentType("text/html;charset=utf-8");
    String productCode = request.getParameter("productCode");
    TMatReserveInfor mri = matReserveInforService.getMatReserveInforByProductCode(productCode);
    PrintWriter out = response.getWriter();
    out.write(JSONObject.fromObject(mri).toString());
    out.flush();
    out.close();

    return null;
  }

}
