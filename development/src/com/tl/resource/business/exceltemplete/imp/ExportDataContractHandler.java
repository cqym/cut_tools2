package com.tl.resource.business.exceltemplete.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tl.common.context.SystemInstance;
import com.tl.common.util.MoneyUtil;
import com.tl.resource.business.exceltemplete.IExportDataBusinessHandler;
import com.tl.resource.dao.TCompanyInforDAO;
import com.tl.resource.dao.TContractInforDAO;
import com.tl.resource.dao.TContractProductDetailDAO;
import com.tl.resource.dao.TCustomersInforDAO;
import com.tl.resource.dao.TExchangeRateDAO;
import com.tl.resource.dao.pojo.TCompanyInfor;
import com.tl.resource.dao.pojo.TCompanyInforExample;
import com.tl.resource.dao.pojo.TContractInfor;
import com.tl.resource.dao.pojo.TContractProductDetail;
import com.tl.resource.dao.pojo.TContractProductDetailExample;
import com.tl.resource.dao.pojo.TCustomersInfor;
import com.tl.resource.dao.pojo.TCustomersInforExample;
import com.tl.resource.dao.pojo.TExchangeRate;

public class ExportDataContractHandler implements IExportDataBusinessHandler {
  private java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");

  private TContractInforDAO contractInforDAO = (TContractInforDAO) SystemInstance.getInstance().getBean("contractInforDAO");;

  private TContractProductDetailDAO contractProductDetailDAO = (TContractProductDetailDAO) SystemInstance.getInstance().getBean(
    "contractProductDetailDAO");;

  private TCompanyInforDAO companyInforDAO = (TCompanyInforDAO) SystemInstance.getInstance().getBean("TCompanyInforDAO");;

  private TCustomersInforDAO customersInforDAO = (TCustomersInforDAO) SystemInstance.getInstance().getBean("TCustomersInforDAO");;

  private TExchangeRateDAO exchangeRateDAO = (TExchangeRateDAO) SystemInstance.getInstance().getBean("TExchangeRateDAO");;

  private TContractInfor conPo;

  @Override
  public Map<String, Object> getBusinessData(String id) {
    Map<String, Object> model = new HashMap<String, Object>();
    conPo = contractInforDAO.selectByPrimaryKey(id);
    model.put("contractInfor", conPo);
    TContractProductDetailExample example = new TContractProductDetailExample();
    example.createCriteria().andContractInforIdEqualTo(conPo.getId()).andParentToolsIdEqualTo("root");
    example.setOrderByClause("contract_Project_Sort_Id,project_Code,serial_Number");
    List<TContractProductDetail> list = contractProductDetailDAO.selectByExample(example);
    model.put("contractDetail", list);
    TCompanyInforExample comExp = new TCompanyInforExample();
    comExp.createCriteria().andCompanyNameEqualTo(conPo.getSellerName());
    List<TCompanyInfor> companys = companyInforDAO.selectByExample(comExp);
    TCompanyInfor comInfor = null;
    if (companys != null && companys.size() > 0) {
      comInfor = companys.get(0);
    } else {
      comInfor = new TCompanyInfor();
    }
    model.put("companyInfor", comInfor);
    TCustomersInforExample cusExp = new TCustomersInforExample();
    cusExp.createCriteria().andCustomerCodeEqualTo(conPo.getCustomerCode());
    List<TCustomersInfor> cusList = customersInforDAO.selectByExample(cusExp);
    TCustomersInfor cusInfor = null;
    if (cusList != null && cusList.size() > 0) {
      cusInfor = cusList.get(0);
    } else {
      cusInfor = new TCustomersInfor();
    }
    model.put("customerInfor", cusInfor);
    TExchangeRate expo = exchangeRateDAO.selectByPrimaryKey(conPo.getCurrencyId());
    model.put("exchangeRate", expo);
    model.put("detailCount", list.size());
    model.put("chineseMoney", MoneyUtil.CNValueOf(df.format(conPo.getFinalMoney().doubleValue())));
    return model;
  }

  @Override
  public String getMainCode() {
    // TODO Auto-generated method stub
    return conPo.getContractCode();
  }

}
