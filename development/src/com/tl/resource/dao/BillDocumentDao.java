package com.tl.resource.dao;

import java.util.List;
import java.util.Map;

import com.tl.resource.business.dto.BillDocumentDto;

public interface BillDocumentDao {
	List<BillDocumentDto> getBillDoc(Map<String, Object> paramMap);
	Integer getBillDocTotal(Map<String, Object> paramMap);
	int updateBillDoc(Map<String, Object> paramMap);
}
