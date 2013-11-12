package com.tl.resource.business.baseInfo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import com.tl.resource.dao.TContractAccountsInforDAO;
import com.tl.resource.dao.TContractInforDAO;
import com.tl.resource.dao.TCustomersInforDAO;
import com.tl.resource.dao.TQuotationInforDAO;
import com.tl.resource.dao.pojo.TCustomersInfor;
import com.tl.resource.dao.pojo.TCustomersInforExample;

public class CustomerServiceImp implements CustomerService {
  private TCustomersInforDAO tcustomersInforDAO;

  private TContractInforDAO contractInforDAO;

  private TContractAccountsInforDAO contractAccountsInforDAO;

  private TQuotationInforDAO quotationInforDAO;

  @Override
  public TCustomersInfor getCustomerInforById(String id) {
    return tcustomersInforDAO.selectByPrimaryKey(id);
  }

  @Override
  public BigDecimal getSumAccountByCustomerCode(String customerCode) {
    // TODO Auto-generated method stub
    return contractAccountsInforDAO.getSumAccountByCustomerCode(customerCode);
  }

  @Override
  public BigDecimal getSumContractMoneyByCustomerCode(String customerCode) {
    // TODO Auto-generated method stub
    return contractInforDAO.getSumContractMoneyByCustomerCode(customerCode);
  }

  public TCustomersInforDAO getTcustomersInforDAO() {
    return tcustomersInforDAO;
  }

  public void setTcustomersInforDAO(TCustomersInforDAO tcustomersInforDAO) {
    this.tcustomersInforDAO = tcustomersInforDAO;
  }

  public TContractInforDAO getContractInforDAO() {
    return contractInforDAO;
  }

  public void setContractInforDAO(TContractInforDAO contractInforDAO) {
    this.contractInforDAO = contractInforDAO;
  }

  public TContractAccountsInforDAO getContractAccountsInforDAO() {
    return contractAccountsInforDAO;
  }

  public void setContractAccountsInforDAO(TContractAccountsInforDAO contractAccountsInforDAO) {
    this.contractAccountsInforDAO = contractAccountsInforDAO;
  }

  @Override
  public void setQianKuanDate(String customerCode) {
    // TODO Auto-generated method stub
    BigDecimal sumAccount = contractAccountsInforDAO.getSumAccountByCustomerCode(customerCode);
    sumAccount = sumAccount == null ? BigDecimal.ZERO : sumAccount;//付款总金额
    BigDecimal sumContractMoney = contractInforDAO.getSumContractMoneyByCustomerCode(customerCode);
    sumContractMoney = sumContractMoney == null ? BigDecimal.ZERO : sumContractMoney;
    BigDecimal sumQuoMoney = quotationInforDAO.getSumYuDingAndShiDaoMoneyByCustomerCode(customerCode);
    sumQuoMoney = sumQuoMoney == null ? BigDecimal.ZERO : sumQuoMoney;
    BigDecimal sumYingFuMoney = sumContractMoney.add(sumQuoMoney);
    if (sumAccount.compareTo(sumYingFuMoney) == -1) {//如果此客户的付款金额合计，小于所签订的所有合同的总金额，则记录其欠款日期
      TCustomersInfor customer = getCustomerInforByCode(customerCode);
      if (customer.getQianKuanDate() == null) {//如果没有记录欠款日期，则记录之
        customer.setQianKuanDate(new Date());
      }
      BigDecimal qianKuanJinE = sumContractMoney.subtract(sumAccount);
      BigDecimal xinYuJinE = customer.getReputationMoney();
      xinYuJinE = xinYuJinE == null ? BigDecimal.ZERO : xinYuJinE;
      if (BigDecimal.ZERO.compareTo(xinYuJinE) != 0 && customer.getQianKuanOverDate() == null && xinYuJinE.compareTo(qianKuanJinE) == -1) {//如果信誉额度不是零，说明设置了信誉额度，则记录其超信誉额度欠款期
        customer.setQianKuanOverDate(new Date());
        customer.setLockStatus(1);
      } else {
        customer.setLockStatus(0);
        customer.setQianKuanOverDate(null);
      }
      tcustomersInforDAO.updateByPrimaryKey(customer);
    } else if (sumAccount.compareTo(sumYingFuMoney) >= 0) {
      TCustomersInfor customer = getCustomerInforByCode(customerCode);
      customer.setQianKuanDate(null);
      customer.setQianKuanOverDate(null);
      customer.setLockStatus(0);
      tcustomersInforDAO.updateByPrimaryKey(customer);
    }
  }

  @Override
  public TCustomersInfor getCustomerInforByCode(String code) {
    TCustomersInforExample example = new TCustomersInforExample();
    example.createCriteria().andCustomerCodeEqualTo(code);
    List li = tcustomersInforDAO.selectByExample(example);
    Assert.assertEquals("没有找到客户编号为:[" + code + "]的客户信息。", false, li.size() == 0);
    return (TCustomersInfor) li.get(0);
  }

  public TQuotationInforDAO getQuotationInforDAO() {
    return quotationInforDAO;
  }

  public void setQuotationInforDAO(TQuotationInforDAO quotationInforDAO) {
    this.quotationInforDAO = quotationInforDAO;
  }

}
