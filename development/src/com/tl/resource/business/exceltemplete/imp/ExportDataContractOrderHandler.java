package com.tl.resource.business.exceltemplete.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tl.common.context.SystemInstance;
import com.tl.common.util.MoneyUtil;
import com.tl.resource.business.exceltemplete.IExportDataBusinessHandler;
import com.tl.resource.dao.TCompanyInforDAO;
import com.tl.resource.dao.TCustomersInforDAO;
import com.tl.resource.dao.TExchangeRateDAO;
import com.tl.resource.dao.TOrderDetailDAO;
import com.tl.resource.dao.TOrderInforDAO;
import com.tl.resource.dao.TSuppliersInforDAO;
import com.tl.resource.dao.pojo.TCompanyInfor;
import com.tl.resource.dao.pojo.TCustomersInfor;
import com.tl.resource.dao.pojo.TCustomersInforExample;
import com.tl.resource.dao.pojo.TExchangeRate;
import com.tl.resource.dao.pojo.TOrderDetail;
import com.tl.resource.dao.pojo.TOrderDetailExample;
import com.tl.resource.dao.pojo.TOrderInfor;
import com.tl.resource.dao.pojo.TSuppliersInfor;

public class ExportDataContractOrderHandler implements IExportDataBusinessHandler {
  private java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");

  private TOrderInforDAO orderInforDao = (TOrderInforDAO) SystemInstance.getInstance().getBean("TOrderInforDAOImpl");

  private TCompanyInforDAO companyInforDAO = (TCompanyInforDAO) SystemInstance.getInstance().getBean("TCompanyInforDAO");

  private TSuppliersInforDAO suppliersInforDao = (TSuppliersInforDAO) SystemInstance.getInstance().getBean("TSuppliersInforDAOImpl");

  private TOrderDetailDAO orderDetailDao = (TOrderDetailDAO) SystemInstance.getInstance().getBean("TOrderDetailDAOImpl");

  private TExchangeRateDAO exchangeRateDAO = (TExchangeRateDAO) SystemInstance.getInstance().getBean("TExchangeRateDAO");

  private TCustomersInforDAO customersInforDAO = (TCustomersInforDAO) SystemInstance.getInstance().getBean("TCustomersInforDAO");;

  TOrderInfor conPo;

  @Override
  public Map<String, Object> getBusinessData(String id) {
    Map<String, Object> model = new HashMap<String, Object>();
    conPo = orderInforDao.selectByPrimaryKey(id);
    model.put("contractInfor", conPo);
    TOrderDetailExample example = new TOrderDetailExample();
    example.createCriteria().andStockOrderInforIdEqualTo(id).andParentToolsIdEqualTo("root");
    example.setOrderByClause("contract_Project_Sort_Id,project_Code,serial_Number");
    List<TOrderDetail> list = orderDetailDao.selectByExample(example);
    model.put("contractDetail", list);

    TCompanyInfor company = companyInforDAO.selectByPrimaryKey(conPo.getCompanyId());
    model.put("companyInfor", company);

    TCustomersInfor customer = new TCustomersInfor();
    if (conPo.getCustomerCode() != null) {
      TCustomersInforExample cusexample = new TCustomersInforExample();
      cusexample.createCriteria().andCustomerCodeEqualTo(conPo.getCustomerCode());
      List cusList = customersInforDAO.selectByExample(cusexample);
      if (cusList != null && cusList.size() > 0) {
        customer = (TCustomersInfor) cusList.get(0);
      }
    }

    model.put("customerInfor", customer);
    TSuppliersInfor supplier = suppliersInforDao.selectByPrimaryKey(conPo.getSupplierId());
    model.put("supplierInfor", supplier);
    TExchangeRate expo = exchangeRateDAO.selectByPrimaryKey(conPo.getCurrencyId());
    model.put("exchangeRate", expo);
    model.put("detailCount", list.size());
    model.put("chineseMoney", MoneyUtil.CNValueOf(df.format(conPo.getFinalMoney().doubleValue())));
    model.put("orderTypeName", getOrderTypeName());
    return model;
  }

  @Override
  public String getMainCode() {
    // TODO Auto-generated method stub
    return conPo.getOrderCode();
  }

  public String getOrderTypeName() {
    Integer type = conPo.getOrderType();
    if (type == null)
      type = 0;
    if (type == 1) {
      return "合同订单";
    } else if (type == 2) {
      return "储备订单";
    } else if (type == 3) {
      return "加工订单";
    } else if (type == 4) {
      return "材料储备订单";
    } else if (type == 5) {
      return "预定订单";
    } else if (type == 6) {
      return "试刀订单";
    } else if (type == 7) {
      return "预定加工订单";
    } else if (type == 8) {
      return "试刀加工订单";
    }
    return null;
  }
}
