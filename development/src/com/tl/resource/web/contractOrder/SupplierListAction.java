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
import com.tl.resource.dao.pojo.TSuppliersInfor;

/**
 * 
 * @author ls
 *	供应商列表
 */
public class SupplierListAction extends Action {

  private ContractOrderService contractOrderService;

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    // TODO Auto-generated method stub
    response.setContentType("text/html;charset=utf-8");
    String size = request.getParameter("limit") == null ? "15" : request.getParameter("limit");
    String start = request.getParameter("start") == null ? "0" : request.getParameter("start");
    int pageSize = Integer.parseInt(size);
    int startIndex = Integer.parseInt(start);
    String contractId = request.getParameter("contractId");
    String supplierCode = "";
    String supplierName = "";
    String contactPerson = "";
    String brand = request.getParameter("brand");
    String toolsTypeName = request.getParameter("toolsTypeName");
    String searchStr = request.getParameter("searchStr");
    if (searchStr != null && !"".equals(searchStr)) {
      JSONObject search = JSONObject.fromObject(searchStr);
      try {
        supplierCode = search.getString("supplierCode");
        supplierName = search.getString("supplierName");
        contactPerson = search.getString("contactPerson");
        brand = search.getString("brand");
      } catch (Exception e) {
        //System.out.println(e);
      }
    }
    Map<String, Object> parmMap = new HashMap<String, Object>();
    parmMap.put("contractId", contractId);
    parmMap.put("pageSize", pageSize);
    parmMap.put("startIndex", startIndex);
    parmMap.put("supplierCode", supplierCode);
    parmMap.put("supplierName", supplierName);
    parmMap.put("contactPerson", contactPerson);
    parmMap.put("toolsTypeName", toolsTypeName);
    parmMap.put("brand", brand);
    parmMap.put("leaf", request.getParameter("leaf"));
    int total = contractOrderService.getSupplierListCount(parmMap);
    List<TSuppliersInfor> supplierList = contractOrderService.getSupplierList(parmMap);
    PrintWriter out = response.getWriter();
    out.println("{root:" + JSONArray.fromObject(supplierList).toString() + ",totalProperty:" + total + "}");
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
