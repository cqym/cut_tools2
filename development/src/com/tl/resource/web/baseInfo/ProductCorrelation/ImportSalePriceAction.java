package com.tl.resource.web.baseInfo.ProductCorrelation;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.common.smartupload.Constant;
import com.tl.resource.business.baseInfo.ProductCorrelationService;

public class ImportSalePriceAction extends Action {
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
    String resultStr = null;
    //		Map<String,Object>   importinfoMap = productCorrelationService.importSalesPriceData(request,response);
    Map<String, Object> importinfoMap = null;
    try {
      importinfoMap = productCorrelationService.importSalesPriceExcelData(request, response);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      resultStr = "{success : false, message : '" + e.getMessage() + "'} ";
      PrintWriter out = response.getWriter();
      out.write(resultStr);
      out.flush();
      out.close();
      return null;
    }
    Boolean importstate = (Boolean) importinfoMap.get("importstate");
    if (importstate) {
      resultStr = "{success : true} ";
    } else {
      String path = (String) importinfoMap.get("filepath");
      if (path != null) {
        int a = path.lastIndexOf("\\");
        String filename = path.substring(a + 1);
        resultStr = "{success : false, path : '" + request.getContextPath() + Constant.UPLOAD_DIR + "/" + filename + "'} ";
      }
    }

    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();

    return null;
  }

}
