package com.tl.resource.business.baseInfo;

import java.math.BigDecimal;

import com.tl.resource.dao.pojo.TCustomersInfor;

public interface CustomerService {
  /**
   * 获取客户的合同金额合计
   * @param customerCode
   */
  public BigDecimal getSumContractMoneyByCustomerCode(String customerCode);

  /**
   * 获取客户的的付款合计
   * @param customerCode
   * @return
   */
  public BigDecimal getSumAccountByCustomerCode(String customerCode);

  /**
   * 获取客户信息
   * @param customerCode
   * @return
   */
  public TCustomersInfor getCustomerInforById(String id);

  /**
   * 给客户设置欠款时间，包括欠款时间，和超过过信誉额度的欠款时间
   * @param customerId
   */
  public void setQianKuanDate(String customerCode);

  /**
   * 根据客户编号获取客户信息
   * @param id
   * @return
   */
  public TCustomersInfor getCustomerInforByCode(String code);
}
