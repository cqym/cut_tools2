package com.tl.resource.business.baseInfo;

import com.tl.resource.business.dto.OrderPriceHistoryDto;
import com.tl.resource.business.dto.UserDto;

public interface OrderPriceService {
	public void addOrderPrice(OrderPriceHistoryDto dto,UserDto user);
	
    public void deleteOrderPriceById(String id);
    
    public void updateOrderPrice(OrderPriceHistoryDto dto);
}
