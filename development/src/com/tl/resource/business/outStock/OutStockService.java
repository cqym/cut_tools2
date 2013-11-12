package com.tl.resource.business.outStock;

import java.util.List;
import java.util.Map;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.OutStockDetailDto;
import com.tl.resource.business.dto.OutStockInforDto;
import com.tl.resource.business.dto.UserDto;

public interface OutStockService {

  public PaginationSupport consultReserveInfors(Map params, int startIndex, int pageSize);

  public PaginationSupport consultContractProducts(Map params, int startIndex, int pageSize);

  public PaginationSupport consultQuotationProducts(Map params, int startIndex, int pageSize);

  public void addOutStockInfor(OutStockInforDto dto) throws Exception;

  public void updateOutStockInfor(OutStockInforDto dto) throws Exception;

  public void deleteOutStockInforDto(String id);

  public OutStockInforDto getOutStockInforDtoById(String id);

  public PaginationSupport findOutStockInfors(Map params, int startIndex, int pageSize);

  public String cancelAudit(String businessId);

  public String endAudit(String businessId);

  public String submitAudit(String businessId);

  public void affirmOutStock(String id, UserDto user);

  public List<OutStockDetailDto> getWillOutStockContractDetail(String contractId, String leaf);

  public List<OutStockDetailDto> getWillOutStockQuotationDetail(String quotationInforId, String leaf);

}
