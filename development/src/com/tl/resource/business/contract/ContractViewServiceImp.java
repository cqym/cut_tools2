package com.tl.resource.business.contract;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.dao.TContractInforDAO;
import com.tl.resource.dao.TContractProductDetailDAO;
import com.tl.resource.dao.pojo.TContractInfor;
import com.tl.resource.dao.pojo.TContractProductDetail;
import com.tl.resource.dao.pojo.TContractProductDetailExample;

public class ContractViewServiceImp implements ContractViewService {
  private TContractInforDAO contractInforDAO;

  private TContractProductDetailDAO contractProductDetailDAO;

  @Override
  public PaginationSupport findContractViewPanelInfors(Map params, int startIndex, int pageSize) {
    // TODO Auto-generated method stub
    return contractInforDAO.findContractViewPanelInfors(params, startIndex, pageSize);
  }

  public TContractInforDAO getContractInforDAO() {
    return contractInforDAO;
  }

  public void setContractInforDAO(TContractInforDAO contractInforDAO) {
    this.contractInforDAO = contractInforDAO;
  }

  @Override
  public Map getContractTotalInfor(Map params) {
    return contractInforDAO.getContractTotalInfor(params);
  }

  @Override
  public List<Map<String, Object>> getContractMonthMoneys(Map<String, Object> parmMap) {
    // TODO Auto-generated method stub
    return contractInforDAO.getContractMonthMoneys(parmMap);
  }

  @Override
  public List<Map<String, Object>> getContractMoneysForOwnPerson(Map<String, Object> parmMap) {
    // TODO Auto-generated method stub
    return contractInforDAO.getContractMoneysForOwnPerson(parmMap);
  }

  @Override
  public List<Map<String, Object>> getContractMoneysGroupByMonthForTowYear(Map<String, Object> parmMap) {
    String year1 = (String) parmMap.get("year1");
    Map<String, Object> parmMapDao = new HashMap<String, Object>();
    parmMapDao.put("startTime", year1 + "-01");
    parmMapDao.put("endTime", year1 + "-12");
    parmMapDao.put("customerName", parmMap.get("customerName"));
    parmMapDao.put("ownContactPerson", parmMap.get("ownContactPerson"));
    List<Map<String, Object>> list1 = contractInforDAO.getContractMonthMoneys(parmMapDao);

    String year2 = (String) parmMap.get("year2");
    parmMapDao.clear();
    parmMapDao.put("startTime", year2 + "-01");
    parmMapDao.put("endTime", year2 + "-12");
    parmMapDao.put("customerName", parmMap.get("customerName"));
    parmMapDao.put("ownContactPerson", parmMap.get("ownContactPerson"));
    List<Map<String, Object>> list2 = contractInforDAO.getContractMonthMoneys(parmMapDao);

    List<Map<String, Object>> rt = new ArrayList<Map<String, Object>>();
    for (int i = 1; i < 13; i++) {
      Map<String, Object> newMap = new HashMap<String, Object>();
      String rtMonth = null;
      if (i < 10) {
        rtMonth = "0" + i;
      } else {
        rtMonth = String.valueOf(i);
      }
      newMap.put("month", rtMonth);
      BigDecimal m = getMonthMoneyFromYearMList(year1.substring(year1.length() - 2) + "-" + rtMonth, list1);
      newMap.put("money1", m == null ? BigDecimal.ZERO : m);
      m = getMonthMoneyFromYearMList(year2.substring(year2.length() - 2) + "-" + rtMonth, list2);
      newMap.put("money2", m == null ? BigDecimal.ZERO : m);
      rt.add(newMap);
    }

    return rt;
  }

  private BigDecimal getMonthMoneyFromYearMList(String rtMonth, List<Map<String, Object>> list1) {
    for (Iterator iterator = list1.iterator(); iterator.hasNext();) {
      Map<String, Object> map = (Map<String, Object>) iterator.next();
      if (map.get("NAME").equals(rtMonth)) {
        return (BigDecimal) map.get("visits");
      }
    }
    return null;
  }

  public TContractProductDetailDAO getContractProductDetailDAO() {
    return contractProductDetailDAO;
  }

  public void setContractProductDetailDAO(TContractProductDetailDAO contractProductDetailDAO) {
    this.contractProductDetailDAO = contractProductDetailDAO;
  }

  @Override
  public List getContractInforsByYuDingDetailId(String detailId) {
    List<HashMap> conList = new ArrayList<HashMap>();
    TContractProductDetailExample example = new TContractProductDetailExample();
    example.createCriteria().andIdLike(detailId + "#%");
    List list = contractProductDetailDAO.selectByExample(example);
    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
      TContractProductDetail pd = (TContractProductDetail) iterator.next();
      TContractInfor infor = contractInforDAO.selectByPrimaryKey(pd.getContractInforId());
      HashMap hm = new HashMap();
      hm.put("contractCode", infor.getContractCode());
      hm.put("amount", pd.getAmount());
      conList.add(hm);
    }

    return conList;
  }
}
