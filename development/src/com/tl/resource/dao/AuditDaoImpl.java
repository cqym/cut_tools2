package com.tl.resource.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.tl.resource.audit.dto.TAuditHistoryDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.dao.pojo.AuditTypeFlowInfor;

public class AuditDaoImpl extends SqlMapClientDaoSupport implements AuditDao{

	@Override
	public List<AuditTypeFlowInfor> findAlreadyAuditTypeInfor(UserDto user) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("audit_fcc.findHistoryAuditTypeInfor", user.getId());
	}

	@Override
	public List<AuditTypeFlowInfor> findWaitAuditTypeInfor(UserDto user) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("audit_fcc.findWaitAuditTypeInfor", user.getId());
	}

	@Override
	public List<TAuditHistoryDto> findAuditHistoryInfor(String businessId,String batchNumber) {
		// TODO Auto-generated method stub
		Map params = new HashMap();
		params.put("business_id", businessId);
		if(batchNumber == null){
		    Integer maxNum = (Integer) getSqlMapClientTemplate().queryForObject("audit_fcc.getMaxBatchNumber",businessId);
		    params.put("batch_number", maxNum);
		}else{
			params.put("batch_number", batchNumber);
		}
		return getSqlMapClientTemplate().queryForList("audit_fcc.findAuditInfor", params);
	}

}
