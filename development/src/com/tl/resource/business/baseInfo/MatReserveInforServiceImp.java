package com.tl.resource.business.baseInfo;

import java.util.List;
import java.util.Map;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.ReserveInforDto;
import com.tl.resource.dao.TMatAccountsInforDAO;
import com.tl.resource.dao.TMatReserveInforDAO;
import com.tl.resource.dao.pojo.TMatAccountsInfor;
import com.tl.resource.dao.pojo.TMatAccountsInforExample;
import com.tl.resource.dao.pojo.TMatReserveInfor;
import com.tl.resource.dao.pojo.TMatReserveInforExample;
import com.tl.resource.dao.pojo.TMatReserveInforExample.Criteria;

public class MatReserveInforServiceImp implements MatReserveInforService {
  private TMatReserveInforDAO matReserveInforDAO;

  private TMatAccountsInforDAO matAccountsInforDAO;

  @Override
  public void addReserveInfor(ReserveInforDto dto) {
    // TODO Auto-generated method stub

  }

  @Override
  public void deleteReserveInfor(ReserveInforDto dto) {
    // TODO Auto-generated method stub

  }

  @Override
  public PaginationSupport findReserveInfor(Map params, int startIndex, int pageSize) {
    TMatReserveInforExample example = new TMatReserveInforExample();
    Criteria cri = example.createCriteria();
    if (params.get("productName") != null && !"".equals(params.get("productName"))) {
      cri.andProductNameLike("%" + params.get("productName") + "%");
    }
    if (params.get("productCode") != null && !"".equals(params.get("productCode"))) {
      cri.andProductCodeLike("%" + params.get("productCode") + "%");
    }
    if (params.get("brandCode") != null && !"".equals(params.get("brandCode"))) {
      cri.andBrandCodeLike("%" + params.get("brandCode") + "%");
    }
    if (params.get("productBrand") != null && !"".equals(params.get("productBrand"))) {
      cri.andProductBrandEqualTo(params.get("productBrand").toString());
    }
    if (params.get("productSource") != null && !"".equals(params.get("productSource"))) {
      cri.andProductSourceEqualTo(params.get("productSource").toString());
    }
    if (params.get("sortCode") != null && !"".equals(params.get("sortCode"))) {
      cri.andProductSortEqualTo(params.get("sortCode").toString());
    }
    List list = matReserveInforDAO.selectByExample(example);
    int count = matReserveInforDAO.countByExample(example);
    PaginationSupport pageInfor = new PaginationSupport(list, count, pageSize, startIndex);
    return pageInfor;
  }

  @Override
  public void updateReserveInfor(ReserveInforDto dto) {
    // TODO Auto-generated method stub

  }

  public TMatReserveInforDAO getMatReserveInforDAO() {
    return matReserveInforDAO;
  }

  public void setMatReserveInforDAO(TMatReserveInforDAO matReserveInforDAO) {
    this.matReserveInforDAO = matReserveInforDAO;
  }

  public TMatAccountsInforDAO getMatAccountsInforDAO() {
    return matAccountsInforDAO;
  }

  public void setMatAccountsInforDAO(TMatAccountsInforDAO matAccountsInforDAO) {
    this.matAccountsInforDAO = matAccountsInforDAO;
  }

  @Override
  public List<TMatAccountsInfor> getAccountsInfoListByByReserveId(Map<String, Object> parmMap) {
    TMatAccountsInforExample example = new TMatAccountsInforExample();
    example.createCriteria().andReserveInforIdEqualTo((String) parmMap.get("reserveInforId"));
    List list = matAccountsInforDAO.selectByExample(example);
    return list;
  }

  @Override
  public TMatReserveInfor getMatReserveInforByProductCode(String productCode) {
    TMatReserveInforExample example = new TMatReserveInforExample();
    example.createCriteria().andProductCodeEqualTo(productCode);
    List<TMatReserveInfor> list = matReserveInforDAO.selectByExample(example);
    if (list.size() > 0) {
      return list.get(0);
    }
    return null;
  }

}
