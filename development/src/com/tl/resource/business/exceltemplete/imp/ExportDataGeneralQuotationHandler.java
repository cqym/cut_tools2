package com.tl.resource.business.exceltemplete.imp;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tl.common.context.SystemInstance;
import com.tl.common.util.DateComparator;
import com.tl.resource.business.dto.QuotationDetailDto;
import com.tl.resource.business.exceltemplete.IExportDataBusinessHandler;
import com.tl.resource.dao.TCompanyInforDAO;
import com.tl.resource.dao.TCustomersInforDAO;
import com.tl.resource.dao.TExchangeRateDAO;
import com.tl.resource.dao.TQuotationInforDAO;
import com.tl.resource.dao.TQuotationProductDetailDAO;
import com.tl.resource.dao.pojo.TCompanyInfor;
import com.tl.resource.dao.pojo.TCustomersInfor;
import com.tl.resource.dao.pojo.TCustomersInforExample;
import com.tl.resource.dao.pojo.TExchangeRate;
import com.tl.resource.dao.pojo.TQuotationInfor;

public class ExportDataGeneralQuotationHandler implements IExportDataBusinessHandler {
  private TCompanyInforDAO tcompanyInforDAO = (TCompanyInforDAO) SystemInstance.getInstance().getBean("TCompanyInforDAO");

  private TExchangeRateDAO exchangeRateDAO = (TExchangeRateDAO) SystemInstance.getInstance().getBean("TExchangeRateDAO");

  private TQuotationInforDAO quoInfoDAO = (TQuotationInforDAO) SystemInstance.getInstance().getBean("TQuotationInfoDAO");

  private TQuotationProductDetailDAO quoDetailDAO = (TQuotationProductDetailDAO) SystemInstance.getInstance().getBean("TQuotationProductDetailDAO");

  private TCustomersInforDAO customersInforDAO = (TCustomersInforDAO) SystemInstance.getInstance().getBean("TCustomersInforDAO");;

  private TQuotationInfor quoInfor;

  @Override
  public Map<String, Object> getBusinessData(String id) {
    Map<String, Object> rt = new HashMap<String, Object>();
    quoInfor = quoInfoDAO.selectByPrimaryKey(id);
    rt.put("quoInfor", quoInfor);

    List list = quoDetailDAO.getQuoDetailByQuo(quoInfor.getId());
    rt.put("quoDetail", list);
    String deliveryDate = "";
    if (list != null && list.size() > 0) {
      DateComparator comp = new DateComparator();
      Collections.max(list, comp);
      deliveryDate = ((QuotationDetailDto) list.get(0)).getDeliveryDate();
    }
    rt.put("deliveryDate", deliveryDate);
    TCompanyInfor comInfo = tcompanyInforDAO.getCompanyByName(quoInfor.getSellerName());
    rt.put("companyInfor", comInfo);
    TExchangeRate expo = exchangeRateDAO.selectByPrimaryKey(quoInfor.getCurrency());
    rt.put("exchangeRate", expo);

    TCustomersInforExample cusExp = new TCustomersInforExample();
    cusExp.createCriteria().andCustomerCodeEqualTo(quoInfor.getCustomerCode());
    List<TCustomersInfor> cusList = customersInforDAO.selectByExample(cusExp);
    TCustomersInfor cusInfor = null;
    if (cusList != null && cusList.size() > 0) {
      cusInfor = cusList.get(0);
    } else {
      cusInfor = new TCustomersInfor();
    }
    rt.put("customerInfor", cusInfor);
    return rt;
  }

  @Override
  public String getMainCode() {
    // TODO Auto-generated method stub
    return quoInfor.getQuotationCode();
  }

}
