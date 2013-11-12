package com.tl.resource.dao;

import java.util.List;

import com.tl.resource.audit.dto.TAuditHistoryDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.dao.pojo.AuditTypeFlowInfor;

public interface AuditDao {
	public List<AuditTypeFlowInfor> findWaitAuditTypeInfor(UserDto user);
	public List<AuditTypeFlowInfor> findAlreadyAuditTypeInfor(UserDto user);
	public List<TAuditHistoryDto> findAuditHistoryInfor(String businessId,String batchNumber);
	
}
