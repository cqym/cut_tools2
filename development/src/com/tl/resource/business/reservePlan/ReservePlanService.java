package com.tl.resource.business.reservePlan;

import java.util.List;
import java.util.Map;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.ReservePlanDetailDto;
import com.tl.resource.business.dto.ReservePlanMainInforDto;

public interface ReservePlanService {
  public ReservePlanMainInforDto consultReserveInfors(String orderId);

  public void addReservePlan(ReservePlanMainInforDto dto);

  public void updateReservePlan(ReservePlanMainInforDto dto);

  public void deleteReservePlanById(String id);

  public void confirmReservePlanById(String id);

  public ReservePlanMainInforDto getReservePlanMainInfor(String planId);

  public PaginationSupport findReservePlans(Map params, int startIndex, int pageSize);

  public List<ReservePlanDetailDto> findPlanDetailByMainId(String mainId, String status);
}
