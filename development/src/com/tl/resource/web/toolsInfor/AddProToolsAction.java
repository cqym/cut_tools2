package com.tl.resource.web.toolsInfor;

import java.io.PrintWriter;
import java.util.Date;

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
public class AddProToolsAction extends Action {
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
    String id = "";
    String resultStr = "";

    //构造JSON对象
    try {
      JSONObject jsonObj = JSONObject.fromObject(json);
      String brandCode = jsonObj.getString("brandCode");
      String proName = jsonObj.getString("productName");
      String proUnit = jsonObj.getString("productUnit");
      String proBrand = jsonObj.getString("productBrand");
      String proSortCode = jsonObj.getString("sortCode");
      String proSortId = jsonObj.getString("productSortId");
      int leaf = jsonObj.getInt("leaf");
      String proSource = jsonObj.getString("productSource");
      String memo = jsonObj.getString("memo");
      String toolsTypeId = jsonObj.getString("toolsTypeId");
      String toolsTypeName = jsonObj.getString("toolsTypeName");

      TProductToolsInfor proToolsInfo = new TProductToolsInfor();
      proToolsInfo.setBrandCode(brandCode.trim());
      proToolsInfo.setProductName(proName);
      proToolsInfo.setProductUnit(proUnit);
      proToolsInfo.setProductBrand(proBrand);
      proToolsInfo.setProductSource(proSource);
      proToolsInfo.setProductSortId(proSortId);
      proToolsInfo.setMemo(memo);
      proToolsInfo.setProductSortCode(proSortCode);
      proToolsInfo.setLeaf(leaf);
      proToolsInfo.setStockPriceDate(new Date());
      proToolsInfo.setParentId(TProductToolsInfor.ROOT_PRARENT_ID);
      proToolsInfo.setToolsTypeId(toolsTypeId);
      proToolsInfo.setToolsTypeName(toolsTypeName);

      /*if(!TProductToolsInfor.ROOT_PRARENT_ID.equals(parentId) && !"".equals(parentId)) {
      	id = proToolsInforService.getId(parentId);
      } else {*/
      id = proToolsInforService.getId("");
      //}
      proToolsInfo.setId(id);
      proToolsInfo.setProductCode(new StringBuffer(proSortCode).append("-").append(id).toString());
      resultStr = "{success : true, msg: '添加产品成功！', id : '" + id + "'}";
      proToolsInforService.insertProTools(proToolsInfo);
    } catch (Exception e) {
      e.printStackTrace();
      resultStr = "{success : false, msg: '添加产品失败，请重试！', id : '" + id + "'}";
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
