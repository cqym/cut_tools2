package com.tl.resource.audit;

import java.util.List;
import java.util.Map;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.audit.dto.AuditTypeFlowInforDto;
import com.tl.resource.audit.dto.TAuditFlowDetailDto;
import com.tl.resource.audit.dto.TAuditFlowInforDto;
import com.tl.resource.audit.dto.TAuditTypeDto;
import com.tl.resource.business.dto.UserDto;

public interface IAuditFlowDefinedService {
	public List<TAuditTypeDto> getAllAuditTypes();
	
	public List<TAuditFlowInforDto> getFlowInforsByAuditType(String auditType);
	
	public List<TAuditFlowDetailDto> getFlowDetailByFlowInforId(String inforId);
	
	public void createAuditInfor(TAuditFlowInforDto vo);
	
	public void createFlowDetail(TAuditFlowDetailDto vo);
	
	public void updateAuditInfor(TAuditFlowInforDto vo);
	
	public void updateFlowDetail(TAuditFlowDetailDto vo);
	
	public void deleteFlowDetail(String id);
	
	public void enableTheFlow(String inforId);
	
	public void disenableTheFlow(String inforId);
	
}
