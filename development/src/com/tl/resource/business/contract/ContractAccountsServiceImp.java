package com.tl.resource.business.contract;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.tl.common.util.GenerateSerial;
import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.baseInfo.CustomerService;
import com.tl.resource.business.dto.ContractAccountsInforDto;
import com.tl.resource.dao.TContractAccountsInforDAO;
import com.tl.resource.dao.TContractInforDAO;
import com.tl.resource.dao.pojo.TContractAccountsInfor;
import com.tl.resource.dao.pojo.TContractAccountsInforExample;
import com.tl.resource.dao.pojo.TContractInfor;
import com.tl.resource.dao.pojo.TContractAccountsInforExample.Criteria;

public class ContractAccountsServiceImp implements ContractAccountsService {
  private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

  private TContractAccountsInforDAO contractAccountsInforDAO;

  private TContractInforDAO contractInforDAO;

  private CustomerService customerService;

  @Override
  public void addContractAccount(ContractAccountsInforDto dto) {
    TContractAccountsInfor po = new TContractAccountsInfor();
    try {
      BeanUtils.copyProperties(po, dto);
      po.setId(GenerateSerial.getUUID());
      po.setStatus(0);
      contractAccountsInforDAO.insert(po);
      contractInforDAO.updateContractAccountMoney(po.getContractInforId());

    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public void deleteContractAccount(String id) {
    // TODO Auto-generated method stub
    TContractAccountsInfor acc = contractAccountsInforDAO.selectByPrimaryKey(id);
    contractAccountsInforDAO.deleteByPrimaryKey(id);
    contractInforDAO.updateContractAccountMoney(acc.getContractInforId());

  }

  @Override
  public PaginationSupport findContractAccounts(Map params, int startIndex, int pageSize) {
    TContractAccountsInforExample example = new TContractAccountsInforExample();
    Criteria c = example.createCriteria();
    if (params.get("contractCode") != null && !"".equals(params.get("contractCode"))) {
      c.andContractCodeLike("%" + params.get("contractCode") + "%");
    }
    if (params.get("customerName") != null && !"".equals(params.get("customerName"))) {
      c.andCustomerNameLike("%" + params.get("customerName") + "%");
    }
    try {
      if (null != params.get("startDate") && !"".equals(params.get("startDate"))) {
        c.andEditDateGreaterThanOrEqualTo(df.parse(params.get("startDate").toString()));
      }
      if (null != params.get("endDate") && !"".equals(params.get("endDate"))) {
        c.andEditDateLessThanOrEqualTo(df.parse(params.get("endDate").toString()));
      }
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      //e.printStackTrace();
    }
    example.setStartIndex(startIndex);
    example.setPageSize(pageSize);
    example.setOrderByClause(" edit_date desc");
    List list = contractAccountsInforDAO.selectByExample(example);
    int count = contractAccountsInforDAO.countByExample(example);
    return new PaginationSupport(list, count, pageSize, startIndex);
  }

  @Override
  public void updateContractAccount(ContractAccountsInforDto dto) {
    // TODO Auto-generated method stub
    TContractAccountsInfor po = new TContractAccountsInfor();
    try {
      BeanUtils.copyProperties(po, dto);
      contractAccountsInforDAO.updateByPrimaryKeySelective(po);
      contractInforDAO.updateContractAccountMoney(po.getContractInforId());

    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public TContractAccountsInforDAO getContractAccountsInforDAO() {
    return contractAccountsInforDAO;
  }

  public void setContractAccountsInforDAO(TContractAccountsInforDAO contractAccountsInforDAO) {
    this.contractAccountsInforDAO = contractAccountsInforDAO;
  }

  @Override
  public void confirmContractAccount(String[] ids) {
    // TODO Auto-generated method stub
    TContractAccountsInfor po = new TContractAccountsInfor();
    for (int i = 0; i < ids.length; i++) {
      po.setId(ids[i]);
      po.setStatus(1);
      contractAccountsInforDAO.updateByPrimaryKeySelective(po);
      TContractAccountsInfor tpo = contractAccountsInforDAO.selectByPrimaryKey(ids[i]);
      TContractInfor cpo = contractInforDAO.selectByPrimaryKey(tpo.getContractInforId());
      if (cpo != null) {
        contractInforDAO.checkSetContractOver(cpo.getId());
      }
      customerService.setQianKuanDate(cpo.getCustomerCode());//设置客户的欠款时间
    }

  }

  @Override
  public PaginationSupport findContractList(Map params, int startIndex, int pageSize) {
    // TODO Auto-generated method stub
    List list = contractAccountsInforDAO.findContractList(params);
    int count = contractAccountsInforDAO.findContractListCount(params);
    return new PaginationSupport(list, count, pageSize, startIndex);
  }

  public TContractInforDAO getContractInforDAO() {
    return contractInforDAO;
  }

  public void setContractInforDAO(TContractInforDAO contractInforDAO) {
    this.contractInforDAO = contractInforDAO;
  }

  public CustomerService getCustomerService() {
    return customerService;
  }

  public void setCustomerService(CustomerService customerService) {
    this.customerService = customerService;
  }

}
