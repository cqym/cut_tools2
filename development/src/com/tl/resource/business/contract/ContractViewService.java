package com.tl.resource.business.contract;

import java.util.List;
import java.util.Map;

import com.tl.common.util.PaginationSupport;

public interface ContractViewService {
  PaginationSupport findContractViewPanelInfors(Map params, int startIndex, int pageSize);

  Map getContractTotalInfor(Map params);

  public List<Map<String, Object>> getContractMonthMoneys(Map<String, Object> parmMap);

  public List<Map<String, Object>> getContractMoneysForOwnPerson(Map<String, Object> parmMap);

  public List<Map<String, Object>> getContractMoneysGroupByMonthForTowYear(Map<String, Object> parmMap);

  List getContractInforsByYuDingDetailId(String detailId);
}
