/**
 * 
 */
package com.tl.resource.web.baseInfo.ProductCorrelation;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.resource.business.baseInfo.ProductCorrelationService;
import com.tl.resource.dao.pojo.TProductSort;

/**
 * @author xtaia
 * 更新组别信息
 */
public class UpdateProductSortAction extends Action {

  private ProductCorrelationService productCorrelationService;

  public ProductCorrelationService getProductCorrelationService() {
    return productCorrelationService;
  }

  public void setProductCorrelationService(ProductCorrelationService productCorrelationService) {
    this.productCorrelationService = productCorrelationService;
  }

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    response.setContentType("text/html;charset=utf-8");
    String resultStr = "{success : true, msg : '修改产品组别信息成功'}";

    String updateProductSortInfoPar = request.getParameter("updateProductSortInfoPar");
    JSONObject productSortObj = JSONObject.fromObject(updateProductSortInfoPar);

    try {
      TProductSort productSortInfo = (TProductSort) JSONObject.toBean(productSortObj, TProductSort.class);
      //System.out.println("!!!!!!!!!!!!!" + companyInfo.getId());
      productCorrelationService.updateObject(productSortInfo);
    } catch (Exception e) {
      e.printStackTrace();
      resultStr = "{success : false, msg : '修改产品组别信息失败'}";
    }
    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();

    return null;
  }

}
