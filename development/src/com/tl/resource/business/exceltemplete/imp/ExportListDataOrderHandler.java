package com.tl.resource.business.exceltemplete.imp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tl.common.context.SystemInstance;
import com.tl.resource.business.contractOrder.ContractOrderService;
import com.tl.resource.business.exceltemplete.IExportListDataBusinessHandler;
import com.tl.resource.business.purchaseOrder.PurchaseOrderViewService;
import com.tl.resource.dao.pojo.TOrderInfor;

public class ExportListDataOrderHandler implements IExportListDataBusinessHandler {
  private ContractOrderService contractOrderService = (ContractOrderService) SystemInstance.getInstance().getBean("ContractOrderServiceImpl");

  //private ScheduleOrderService scheduleOrderService = (ScheduleOrderService) SystemInstance.getInstance().getBean("ScheduleOrderServiceImpl");

  //private ReserveOrderService reserveOrderService = (ReserveOrderService) SystemInstance.getInstance().getBean("ReserveOrderServiceImpl");
  private PurchaseOrderViewService purchaseOrderViewService = (PurchaseOrderViewService) SystemInstance.getInstance().getBean(
    "PurchaseOrderViewService");

  private String orderType = null;

  @Override
  public Map<String, Object> getBusinessData(Map para) {
    // TODO Auto-generated method stub
    para.put("startIndex", 0);
    para.put("pageSize", Integer.MAX_VALUE);
    orderType = (String) para.get("orderType");
    List inforList = contractOrderService.getContractOrderList(para);
    for (Iterator iterator = inforList.iterator(); iterator.hasNext();) {
      TOrderInfor orderInfor = (TOrderInfor) iterator.next();

      orderInfor.setDetail(getDetail(orderInfor));
    }
    Map<String, Object> rt = new HashMap<String, Object>();
    rt.put("list", inforList);
    return rt;
  }

  private List getDetail(TOrderInfor orderInfor) {
    return purchaseOrderViewService.getOrderDetailByOrderInforId(orderInfor.getId(), orderInfor.getOrderType());
  }

  @Override
  public String getMainCode() {
    // TODO Auto-generated method stub
    if ("3".equals(orderType) || "8".equals(orderType) || "7".equals(orderType)) {
      return "orderSelfDetail";
    }
    return "orderDetail";
  }
}
