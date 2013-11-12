package com.tl.resource.business.baseInfo;

import java.util.List;
import java.util.Map;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.ReserveInforDto;
import com.tl.resource.dao.pojo.TMatAccountsInfor;
import com.tl.resource.dao.pojo.TMatReserveInfor;

public interface MatReserveInforService {
  public void addReserveInfor(ReserveInforDto dto);

  public void updateReserveInfor(ReserveInforDto dto);

  public void deleteReserveInfor(ReserveInforDto dto);

  public PaginationSupport findReserveInfor(Map params, int startIndex, int pageSize);

  public List<TMatAccountsInfor> getAccountsInfoListByByReserveId(Map<String, Object> parmMap);

  public TMatReserveInfor getMatReserveInforByProductCode(String productCode);
}
