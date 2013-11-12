package com.tl.resource.business.manage;

import java.util.List;
import java.util.Map;

import com.tl.resource.business.dto.BillDocumentDto;

public interface BillDocumentService {
	public List<BillDocumentDto> getBillDocument(Map<String, Object> paramMap);
	
	public List<BillDocumentDto> getBillDoc(Map<String, Object> paramMap);
	
	Integer getBillDocTotal(Map<String, Object> paramMap);
	
	int updateBillDoc(Map<String, Object> paramMap);
}
