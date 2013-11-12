package com.tl.resource.business.contract;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.ContractDetailDto;
import com.tl.resource.business.dto.ContractInforDto;
import com.tl.resource.business.dto.LoginInforDto;

public interface ContractEditService {

  public ContractInforDto consultGeneralQuo(String[] ids);

  public ContractInforDto consultProjectQuo(String id);

  public void addContract(ContractInforDto dto);

  public void updateContract(ContractInforDto dto);

  public void deleteContracts(String id);

  public PaginationSupport findContractInfors(Map params, int startIndex, int pageSize);

  public ContractInforDto getContractInforById(String id);

  public String cancelAudit(String businessId);

  public String endAudit(String businessId);

  public String submitAudit(String businessId);

  public PaginationSupport findContractDetail(Map params, int startIndex, int pageSize);

  public void runContract(String conId, LoginInforDto loginInfor);

  public String endContract(String conId);

  public void voidContract(String conId);

  public void deleteContractDetail(String contractId, BigDecimal overallRebate, String[] ids);

  public void unTreadQuo2Audited(String[] ids);

  public ContractInforDto consultYuDingQuo(String[] ids, BigDecimal contractTaxRate);

  public List findSchedue2ConInfors(Map<String, String> mparams);

  public void updateShedQuo2ConDetail(List<ContractDetailDto> list);

  public boolean isFinancialContentChange(String id);

  public boolean isWuLiuContentChange(String id);
}
