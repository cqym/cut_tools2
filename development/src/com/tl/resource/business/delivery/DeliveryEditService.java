package com.tl.resource.business.delivery;

import java.util.Map;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.DeliveryInforDto;

public interface DeliveryEditService {
  /**
   * 参照合同
   * @param id
   * @return
   */
  public DeliveryInforDto consultContract(String id);

  /**
   * 参照报价单
   * @param id
   * @return
   */
  public DeliveryInforDto consultQuotation(String id);

  /**
   * 增加交货单
   * @param dto
   */
  public void addDeliveryInfor(DeliveryInforDto dto);

  /**
   * 修改交货单
   * @param dto
   */
  public void updateDeliveryInfor(DeliveryInforDto dto);

  /**
   * 根据交货单id，获得详细信息
   * @param id
   * @return
   */
  public DeliveryInforDto getDeliveryInforById(String id);

  /**
   * 删除交货单
   * @param id
   */
  public void deleteDeliveryInfor(String id);

  public PaginationSupport findDeliveryInfors(Map params, int startIndex, int pageSize);

  public String cancelAudit(String businessId);

  public String endAudit(String businessId);

  public String submitAudit(String businessId);

  public void invoidDeliveryInfor(String string);

  public void delDeliveryDetail(String string);
}
