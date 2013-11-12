package com.tl.resource.web.contractOrder;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.resource.business.contractOrder.ContractOrderService;
import com.tl.resource.business.dto.OrderDetialsDto;
import com.tl.resource.business.dto.OrderInfoDto;

/**
 * 
 * @author ls
 *	合同订单编制界面订单详细，产品添加界面产品列表
 */
public class ContractDetailListAction extends Action {

  private ContractOrderService contractOrderService;

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    // TODO Auto-generated method stub
    response.setContentType("text/html;charset=utf-8");
    String contractId = request.getParameter("contractId");
    String supplierId = request.getParameter("supplierId");
    String productBrand = request.getParameter("productBrand");
    String leaf = request.getParameter("leaf");
    String toolsTypeName = request.getParameter("toolsTypeName");
    //搜索字符串
    String searchStr = request.getParameter("searchStr");
    String contractProjectSortId = "";
    String brandCode = "";
    String productName = "";
    if (searchStr != null && !"".equals(searchStr)) {
      JSONObject search = JSONObject.fromObject(searchStr);
      try {
        contractProjectSortId = search.getString("id");
        brandCode = search.getString("brandCode");
        productName = search.getString("productName");
      } catch (Exception e) {
        //System.out.println(e);
      }
    }
    Map<String, Object> parmMap = new HashMap<String, Object>();
    parmMap.put("contractId", contractId);
    parmMap.put("supplierId", supplierId);
    parmMap.put("contractProjectSortId", contractProjectSortId);
    parmMap.put("brandCode", brandCode);
    parmMap.put("productName", productName);
    parmMap.put("leaf", leaf);
    parmMap.put("toolsTypeName", toolsTypeName);
    parmMap.put("productBrand", productBrand);
    if (OrderInfoDto.ORDER_TYPE_CONTRACT.equals(request.getParameter("orderType"))) {
      parmMap.put("leaf", "1");
    } else if (OrderInfoDto.ORDER_TYPE_CONTRACT_SELF.equals(request.getParameter("orderType"))) {
      parmMap.put("leaf", "0");
    }
    //int total = contractOrderService.getContractDetailListCount(parmMap);
    List<OrderDetialsDto> contractDetailList = contractOrderService.getContractDetailList(parmMap);
    PrintWriter out = response.getWriter();
    out.println("{root:" + JSONArray.fromObject(contractDetailList).toString() + "}");
    out.flush();
    out.close();
    return null;

  }

  public ContractOrderService getContractOrderService() {
    return contractOrderService;
  }

  public void setContractOrderService(ContractOrderService contractOrderService) {
    this.contractOrderService = contractOrderService;
  }

}
