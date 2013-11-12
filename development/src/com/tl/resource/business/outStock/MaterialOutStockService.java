package com.tl.resource.business.outStock;

import com.tl.resource.business.dto.OutStockInforDto;

public interface MaterialOutStockService {

  public OutStockInforDto consultOrderProducts(String orderId);

  public void updateOutStockInfor(OutStockInforDto dto);

  public OutStockInforDto getOutStockInforById(String id);

  public OutStockInforDto consultPlanProducts(String[] mainIds);
}
