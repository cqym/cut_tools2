package com.tl.resource.web.toolsInfor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.resource.business.protoolsinfo.ProToolsInforService;
import com.tl.resource.dao.pojo.TProductToolsInfor;

/**
 * 增加标准品
 * @author Administrator
 *
 */
public class UpdateProToolsAction extends Action {
  private ProToolsInforService proToolsInforService;

  public ProToolsInforService getProToolsInforService() {
    return proToolsInforService;
  }

  public void setProToolsInforService(ProToolsInforService proToolsInforService) {
    this.proToolsInforService = proToolsInforService;
  }

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    String json = request.getParameter("content");
    String resultStr = "{success : true}";
    //构造JSON对象
    try {
      JSONObject jsonObj = JSONObject.fromObject(json);
      String id = jsonObj.getString("id");

      String brandCode = jsonObj.getString("brandCode");
      String proName = jsonObj.getString("productName");
      String proUnit = jsonObj.getString("productUnit");
      String proBrand = jsonObj.getString("productBrand");
      String proSortCode = jsonObj.getString("sortCode");
      String proSource = jsonObj.getString("productSource");
      String memo = jsonObj.getString("memo");
      String toolsTypeId = jsonObj.getString("toolsTypeId");
      String toolsTypeName = jsonObj.getString("toolsTypeName");
      String sycQuoToosInfor = null;
      if (jsonObj.containsKey("sycQuoToosInfor")) {
        sycQuoToosInfor = jsonObj.getString("sycQuoToosInfor");
      }

      String proSortId = null;
      if (jsonObj.has("productSortId"))
        proSortId = jsonObj.getString("productSortId");

      TProductToolsInfor proToolsInfo = proToolsInforService.getProToolsById(id);
      if (proToolsInfo != null) {
        proToolsInfo.setBrandCode(brandCode);
        proToolsInfo.setProductName(proName);
        proToolsInfo.setProductUnit(proUnit);
        proToolsInfo.setProductBrand(proBrand);
        proToolsInfo.setProductSource(proSource);
        proToolsInfo.setMemo(memo);
        proToolsInfo.setProductSortCode(proSortCode);
        proToolsInfo.setProductCode(new StringBuffer(proSortCode).append("-").append(id).toString());
        proToolsInfo.setProductSortId(proSortId);
        proToolsInfo.setToolsTypeId(toolsTypeId);
        proToolsInfo.setToolsTypeName(toolsTypeName);

        //proToolsInfo.putParentId(TProductToolsInfor.ROOT_PRARENT_ID);
        proToolsInforService.updateProToolsById(proToolsInfo, sycQuoToosInfor);
      } else {
        resultStr = "{success : false, message: '修改失败，请重试！'}";
      }

    } catch (Exception e) {
      e.printStackTrace();
      resultStr = "{success : false, message: '" + e.getMessage() + ", 修改失败！'}";
    }
    response.setContentType("text/html;charset=utf-8");

    //System.out.println(JSONObject.fromObject(ps));
    //System.out.println(json + ";" + pageName + "=" + JSONObject.fromObject(ps));
    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();

    return null;
  }

}
