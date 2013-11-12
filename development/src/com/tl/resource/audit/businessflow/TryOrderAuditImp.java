package com.tl.resource.audit.businessflow;

import java.util.ArrayList;
import java.util.List;

import com.tl.common.context.SystemInstance;
import com.tl.resource.audit.IAuditBusinessObject;
import com.tl.resource.audit.dto.LinkBusinessObject;
import com.tl.resource.audit.dto.PageHeaderInfor;
import com.tl.resource.business.tryOrder.TryOrderService;
import com.tl.resource.dao.pojo.TAuditHistory;
import com.tl.resource.dao.pojo.TOrderInfor;

public class TryOrderAuditImp implements IAuditBusinessObject {

  private TryOrderService tryOrderService = (TryOrderService) SystemInstance.getInstance().getBean("TryOrderServiceImpl");

  private TAuditHistory abo;

  private LinkBusinessObject bo;

  private String businessType = "019";

  @Override
  public String cancelAudit() {
    // TODO Auto-generated method stub
    return tryOrderService.cancelAudit(abo.getBusinessId());
  }

  @Override
  public String endAudit() {
    // TODO Auto-generated method stub
    return tryOrderService.endAudit(abo.getBusinessId());
  }

  @Override
  public String submitAudit() {
    // TODO Auto-generated method stub
    return tryOrderService.submitAudit(abo.getBusinessId());
  }

  @Override
  public TAuditHistory getAbo() {
    // TODO Auto-generated method stub
    return abo;
  }

  @Override
  public LinkBusinessObject getBo() {
    // TODO Auto-generated method stub
    return bo;
  }

  @Override
  public String getBusinessType() {
    // TODO Auto-generated method stub
    return businessType;
  }

  @Override
  public List getShowColumns() {
    // TODO Auto-generated method stub
    List list = new ArrayList();
    PageHeaderInfor phi1 = new PageHeaderInfor();
    phi1.setHeader("id");
    phi1.setDataIndex("id");
    phi1.setSortable(false);
    phi1.setHidden(true);
    list.add(phi1);

    PageHeaderInfor phi2 = new PageHeaderInfor();
    phi2.setHeader("订单编号");
    phi2.setDataIndex("orderCode");
    phi2.setSortable(false);
    list.add(phi2);

    PageHeaderInfor phi3 = new PageHeaderInfor();
    phi3.setHeader("订货日期");
    phi3.setDataIndex("orderDate");
    phi3.setSortable(false);
    list.add(phi3);

    //		PageHeaderInfor phi4 = new PageHeaderInfor();
    //		phi4.setHeader("状态");
    //		phi4.setDataIndex("status");
    //		phi4.setSortable(false);
    //		list.add(phi4);

    PageHeaderInfor phi5 = new PageHeaderInfor();
    phi5.setHeader("交货日期");
    phi5.setDataIndex("deliveryDate");
    phi5.setSortable(false);
    list.add(phi5);

    PageHeaderInfor phi6 = new PageHeaderInfor();
    phi6.setHeader("紧急程度");
    phi6.setDataIndex("urgentLevel");
    phi6.setSortable(false);
    list.add(phi6);

    PageHeaderInfor phi7 = new PageHeaderInfor();
    phi7.setHeader("供应商");
    phi7.setDataIndex("supplierName");
    phi7.setSortable(false);
    list.add(phi7);

    //		PageHeaderInfor phi8 = new PageHeaderInfor();
    //		phi8.setHeader("联系人");
    //		phi8.setDataIndex("contactPerson");
    //		phi8.setSortable(false);
    //		list.add(phi8);

    PageHeaderInfor phi9 = new PageHeaderInfor();
    phi9.setHeader("币别");
    phi9.setDataIndex("currencyName");
    phi9.setSortable(false);
    list.add(phi9);

    PageHeaderInfor phi10 = new PageHeaderInfor();
    phi10.setHeader("税率");
    phi10.setDataIndex("taxRate");
    phi10.setSortable(false);
    list.add(phi10);

    PageHeaderInfor phi11 = new PageHeaderInfor();
    phi11.setHeader("货品金额");
    phi11.setDataIndex("productMoney");
    phi11.setSortable(false);
    list.add(phi11);

    PageHeaderInfor phi12 = new PageHeaderInfor();
    phi12.setHeader("税价合计");
    phi12.setDataIndex("totalMoney");
    phi12.setSortable(false);
    list.add(phi12);

    PageHeaderInfor phi13 = new PageHeaderInfor();
    phi13.setHeader("编制时间");
    phi13.setDataIndex("editDate");
    phi13.setSortable(false);
    list.add(phi13);

    PageHeaderInfor phi14 = new PageHeaderInfor();
    phi14.setHeader("编制人");
    phi14.setDataIndex("userName");
    phi14.setSortable(false);
    list.add(phi14);

    PageHeaderInfor phi15 = new PageHeaderInfor();
    phi15.setHeader("查看详细");
    phi15.setDataIndex("url");
    phi15.setSortable(false);
    phi15.setHidden(true);
    list.add(phi15);
    return list;
  }

  @Override
  public String getUrl() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void loadBo(String id) {
    // TODO Auto-generated method stub
    bo = new LinkBusinessObject();
    TOrderInfor vo = tryOrderService.getOrderInforById(id);
    bo.setId(vo.getId());
    bo.setName(vo.getOrderCode());
    bo.addProperty("id", vo.getId());
    bo.addProperty("orderCode", vo.getOrderCode());
    bo.addProperty("orderDate", vo.getOrderDate());
    //bo.addProperty("status", vo.getStatus());
    bo.addProperty("deliveryDate", vo.getDeliveryDate());
    bo.addProperty("urgentLevel", vo.getUrgentLevel() == 0 ? "一般" : "紧急");
    bo.addProperty("supplierName", vo.getSupplierName());
    //bo.addProperty("contactPerson", vo.getContactPerson());
    bo.addProperty("currencyName", vo.getCurrencyName());
    bo.addProperty("taxRate", vo.getTaxRate());
    bo.addProperty("productMoney", vo.getProductMoney());
    bo.addProperty("totalMoney", vo.getTotalMoney());
    bo.addProperty("editDate", vo.getEditDateCopy());
    bo.addProperty("userName", vo.getUserName());
    bo.addProperty("url", "/js/orders/purchaseOrder/purchaseOrderWin.js");
  }

  @Override
  public void setAbo(TAuditHistory bo) {
    // TODO Auto-generated method stub
    this.abo = bo;
  }

  public void setBusinessType(String businessType) {
    this.businessType = businessType;
  }

  public TryOrderService getTryOrderService() {
    return tryOrderService;
  }

  public void setTryOrderService(TryOrderService tryOrderService) {
    this.tryOrderService = tryOrderService;
  }

}
