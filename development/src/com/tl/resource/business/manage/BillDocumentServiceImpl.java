package com.tl.resource.business.manage;

import java.util.List;
import java.util.Map;

import com.tl.resource.business.dto.BillDocumentDto;
import com.tl.resource.dao.BillDocumentDao;

public class BillDocumentServiceImpl implements BillDocumentService {
	private BillDocumentDao billDocumentDAO;
	
	@Override
	public List<BillDocumentDto> getBillDocument(Map<String, Object> paramMap) {
		return this.getBillDoc(paramMap);
	}
	
	public BillDocumentDao getBillDocumentDAO() {
		return billDocumentDAO;
	}

	public void setBillDocumentDAO(BillDocumentDao billDocumentDAO) {
		this.billDocumentDAO = billDocumentDAO;
	}


	@Override
	public List<BillDocumentDto> getBillDoc(Map<String, Object> paramMap) {
		return billDocumentDAO.getBillDoc(paramMap);
	}

	@Override
	public Integer getBillDocTotal(Map<String, Object> paramMap) {
		return billDocumentDAO.getBillDocTotal(paramMap);
	}

	@Override
	public int updateBillDoc(Map<String, Object> paramMap) {
		return billDocumentDAO.updateBillDoc(paramMap);
	}
	
	
}
