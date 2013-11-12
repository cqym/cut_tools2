package com.tl.resource.business.contract;

import java.util.Map;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.ContractAccountsInforDto;

public interface ContractAccountsService {
	
	public void addContractAccount(ContractAccountsInforDto dto);
	
	public void updateContractAccount(ContractAccountsInforDto dto);
	
	public void deleteContractAccount(String id);
	
	public void confirmContractAccount(String[] id);
	
	public PaginationSupport findContractAccounts(Map params,int startIndex,int pageSize);
	
	public PaginationSupport findContractList(Map params,int startIndex,int pageSize);
}
