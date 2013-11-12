package com.tl.resource.dao;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.tl.resource.business.dto.BillDocumentDto;

public class BillDocumentDaoImpl extends SqlMapClientDaoSupport implements BillDocumentDao {

	@Override
	public List<BillDocumentDto> getBillDoc(Map<String, Object> paramMap) {
		return this.getSqlMapClientTemplate().queryForList("billDocument.getBillDoc", paramMap);
	}

	@Override
	public Integer getBillDocTotal(Map<String, Object> paramMap) {
		return (Integer)this.getSqlMapClientTemplate().queryForObject("billDocument.getBillDocTotal", paramMap);
	}

	@Override
	public int updateBillDoc(Map<String, Object> paramMap) {
		return this.getSqlMapClientTemplate().update("billDocument.updateBillDoc", paramMap);
	}

}
