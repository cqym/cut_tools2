package com.tl.resource.business.dto;

import java.math.BigDecimal;

/**
 * 预订详细Dto
 * @author ftl
 *
 */
public class QuotationDetailReserveDto extends QuotationDetailDto {
  /**
   * 订单数量
   */
  private BigDecimal orderAmount = BigDecimal.ZERO;

  /**
   * 交货数量
   */
  private BigDecimal deliveryAmount = BigDecimal.ZERO;

  /**
   * 已入库数量
   */
  private BigDecimal arrivalAmount = BigDecimal.ZERO;

  /**
   * 转合同数量
   */
  private BigDecimal contractAmount = BigDecimal.ZERO;

  public BigDecimal getOrderAmount() {
    return orderAmount;
  }

  public void setOrderAmount(BigDecimal orderAmount) {
    this.orderAmount = orderAmount;
  }

  public BigDecimal getDeliveryAmount() {
    return deliveryAmount;
  }

  public void setDeliveryAmount(BigDecimal deliveryAmount) {
    this.deliveryAmount = deliveryAmount;
  }

  public BigDecimal getArrivalAmount() {
    return arrivalAmount;
  }

  public void setArrivalAmount(BigDecimal arrivalAmount) {
    this.arrivalAmount = arrivalAmount;
  }

  public BigDecimal getContractAmount() {
    return contractAmount;
  }

  public void setContractAmount(BigDecimal contractAmount) {
    this.contractAmount = contractAmount;
  }

}
