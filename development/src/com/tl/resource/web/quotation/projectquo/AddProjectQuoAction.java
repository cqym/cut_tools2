/**
 * 
 */
package com.tl.resource.web.quotation.projectquo;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.common.util.GenerateSerial;
import com.tl.common.util.LoginInforUtil;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.manage.BillsCodeDefService;
import com.tl.resource.business.quotation.projectquo.ProjectQuoService;
import com.tl.resource.dao.pojo.TProductToolsInfor;
import com.tl.resource.dao.pojo.TQuotationInfor;
import com.tl.resource.dao.pojo.TQuotationProductDetail;
import com.tl.resource.dao.pojo.TQuotationProjectSortInfor;

/**
 * @author xtaia
 *	添加项目报价单信息
 */
public class AddProjectQuoAction extends Action {

  private ProjectQuoService projectQuoService;

  public ProjectQuoService getProjectQuoService() {
    return projectQuoService;
  }

  public void setProjectQuoService(ProjectQuoService projectQuoService) {
    this.projectQuoService = projectQuoService;
  }

  private BillsCodeDefService billsCodeDefService;

  public BillsCodeDefService getBillsCodeDefService() {
    return billsCodeDefService;
  }

  public void setBillsCodeDefService(BillsCodeDefService billsCodeDefService) {
    this.billsCodeDefService = billsCodeDefService;
  }

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    response.setContentType("text/html;charset=utf-8");
    String resultStr = "{success : true, msg : '添加报价信息成功'}";

    String quoFormPar = request.getParameter("quoFormPar");
    String orderWorkerInfoPar = request.getParameter("orderWorkerInfoPar");
    String productInfoArrayPar = request.getParameter("productInfoArrayPar");

    //工单基本客户信息
    JSONObject quoObj = JSONObject.fromObject(quoFormPar);
    JSONObject quoForm = quoObj.getJSONObject("data");

    String orderWorkerInfoParA = "[".concat(orderWorkerInfoPar.substring(0, orderWorkerInfoPar.length() - 1)).concat("]");

    //工序列表信息
    JSONArray orderWorkerArray = JSONArray.fromObject(orderWorkerInfoParA);

    //工序对应的产品信息
    List<JSONArray> tabPanlProducts = new ArrayList<JSONArray>();
    JSONArray productArray = JSONArray.fromObject(productInfoArrayPar);
    for (Iterator iterator = productArray.iterator(); iterator.hasNext();) {
      JSONArray arr = (JSONArray) iterator.next();
      tabPanlProducts.add(arr);
    }

    UserDto userDto = null;
    if (LoginInforUtil.getLoginInfor(request) != null) {
      userDto = LoginInforUtil.getLoginInfor(request).getUser();
    }

    try {
      TQuotationInfor quoInfo = (TQuotationInfor) JSONObject.toBean(quoForm, TQuotationInfor.class);//this.quoJsonObj2Pojo(quoForm);
      quoInfo.setId(GenerateSerial.getUUID());

      String cusCode = quoInfo.getCustomerCode();
      String customerCode = "";
      String customerName = "";
      if (cusCode != null && !"".equals(cusCode)) {
        int index = cusCode.indexOf("-");
        customerCode = cusCode.substring(0, index);
        customerName = cusCode.substring(index + 1, cusCode.length());
      }

      quoInfo.setCustomerCode(customerCode);
      quoInfo.setCustomerName(customerName);
      quoInfo.setQuotationType(1);
      quoInfo.setEditDate(new Date());
      quoInfo.setStatus(0);
      if (userDto != null) {
        quoInfo.setEditorId(userDto.getId());
        quoInfo.setEditorName(userDto.getUserName());
      }
      quoInfo.setQuotationCode(billsCodeDefService.getBillCode("27", null, customerCode, "WW"));
      //添加报价单基本信息
      projectQuoService.insertQuotation(quoInfo);

      for (int i = 0; i < orderWorkerArray.size(); i++) {
        //获取工单信息
        JSONObject orderWork = orderWorkerArray.getJSONObject(i);
        JSONObject workorder = orderWork.getJSONObject("data");
        //工单名称
        String proSortName = workorder.getString("proSortName");
        //机床型号
        String machineModel = workorder.getString("machineModel");
        //机床台数
        String machineCount = workorder.getString("machineCount");
        //配套刀具数
        String support_amount = workorder.getString("supportAmount");
        //备用刀具数
        String backup_amount = workorder.getString("backupAmount");
        //小计
        String total_money = workorder.getString("totalMoney");
        //工单PO
        TQuotationProjectSortInfor tquoproinfo = new TQuotationProjectSortInfor();
        tquoproinfo.setId(GenerateSerial.getUUID());
        tquoproinfo.setProSortName(proSortName);
        tquoproinfo.setMachineModel(machineModel);
        tquoproinfo.setMachineCount(new Integer(machineCount));
        tquoproinfo.setSupportAmount(new BigDecimal(support_amount));
        tquoproinfo.setBackupAmount(new BigDecimal(backup_amount));
        tquoproinfo.setTotalMoney(new BigDecimal(total_money));
        tquoproinfo.setQuotationInforId(quoInfo.getId());

        //添加工单信息
        projectQuoService.insertQuoProSort(tquoproinfo);

        //工单对应的产品信息列表
        JSONArray quoProductArray = tabPanlProducts.get(i);

        Iterator<JSONObject> iter = quoProductArray.iterator();
        while (iter.hasNext()) {
          //获取某一个产品（标品、非标品）
          JSONObject quoProduct = iter.next();
          TQuotationProductDetail quoDetail = null;
          quoDetail = (TQuotationProductDetail) JSONObject.toBean(quoProduct, TQuotationProductDetail.class);
          if (quoProduct.has("salePrice"))
            quoDetail.setPrice(BigDecimal.valueOf(quoProduct.getDouble("salePrice")));
          quoDetail.setId(GenerateSerial.getUUID());
          //报价单id
          quoDetail.setQuotationInforId(quoInfo.getId());
          //工单信息id
          quoDetail.setQuotationProjectSortId(tquoproinfo.getId());
          //设置其根节点
          quoDetail.setParentToolsId(TProductToolsInfor.ROOT_PRARENT_ID);
          quoDetail.putLeaf(quoProduct.getString("leaf"));

          if ("".equals(quoProduct.getString("priceChange"))) {
            quoDetail.setPriceChange(0);
          } else if (!"".equals(quoProduct.getString("priceChange")) && "1".equals(quoProduct.getString("priceChange"))) {
            quoDetail.setPriceChange(1);
          }

          //添加父节点
          projectQuoService.insertQuoDetail(quoDetail);

          /*boolean hasChildNodes = true;
          
          try {
          	quoProduct.getJSONArray("children");
          } catch(Exception e) {
          	hasChildNodes = false;
          }
          
          if(hasChildNodes) {
          	addChildrenProtools(quoProduct, quoDetail,tquoproinfo);
          }				*/
          //System.out.println("产品名称:"+quoDetail.getBrandCode());	
        }

      }

    } catch (Exception e) {
      e.printStackTrace();
      resultStr = "{success : false, msg : '添加报价信息失败'}";
    }
    //		
    PrintWriter out = response.getWriter();
    out.write(resultStr);
    out.flush();
    out.close();

    return null;
  }

  private ArrayList<TQuotationProductDetail> addChildrenProtools(JSONObject proTools, TQuotationProductDetail dto,
    TQuotationProjectSortInfor tquoproinfo) {
    JSONArray arr = proTools.getJSONArray("children");
    ArrayList<TQuotationProductDetail> list = new ArrayList<TQuotationProductDetail>();
    for (Iterator iterator2 = arr.iterator(); iterator2.hasNext();) {
      JSONObject top = (JSONObject) iterator2.next();
      TQuotationProductDetail po2 = (TQuotationProductDetail) JSONObject.toBean(top, TQuotationProductDetail.class);
      po2.setParentToolsId(dto.getId());
      po2.setId(GenerateSerial.getUUID());
      //报价单id
      po2.setQuotationInforId(dto.getQuotationInforId());
      //工单id
      po2.setQuotationProjectSortId(tquoproinfo.getId());
      po2.putLeaf(top.getString("leaf"));
      list.add(po2);
      JSONArray arr2 = null;
      try {
        arr2 = top.getJSONArray("children");

      } catch (Exception e) {
        po2.setLeaf(1);
      }
      projectQuoService.insertQuoDetail(po2);
      //System.out.println("arr2="+arr2);
      if (arr2 != null && arr2.size() > 0) {
        po2.setChildren(addChildrenProtools(top, po2, tquoproinfo));
      } else {
        //System.out.println("ddd:");
      }
    }
    dto.setChildren(list);
    return list;
  }

}
