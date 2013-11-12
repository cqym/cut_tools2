package com.tl.resource.business;

import com.tl.common.util.GenerateSerial;
import com.tl.resource.business.dto.AccountsInforPagerDto;
import com.tl.resource.dao.TAccountsInforDAO;
import com.tl.resource.dao.pojo.TAccountsInfor;
import com.tl.resource.dao.pojo.TAccountsInforExample;

public class TestService {
	
	private TAccountsInforDAO TAccountsInforDAO;
	public void test(){
		TAccountsInfor record = new TAccountsInfor();
		record.setId(GenerateSerial.getUUID());
		record.setBrandCode("fdsfds");
		TAccountsInforDAO.insert(record);
		//System.out.println("sssss");
		record.setAccountType(1);
		TAccountsInforDAO.updateByPrimaryKeySelective(record);
	}
	
	public AccountsInforPagerDto getList(){
		TAccountsInforExample example = new TAccountsInforExample();
		AccountsInforPagerDto p = new AccountsInforPagerDto();
		p.setRoot(TAccountsInforDAO.selectByExample(example ));
		p.setTotalProperty(TAccountsInforDAO.countByExample(example));
		return p;
	}
	public TAccountsInforDAO getTAccountsInforDAO() {
		return TAccountsInforDAO;
	}
	public void setTAccountsInforDAO(TAccountsInforDAO accountsInforDAO) {
		TAccountsInforDAO = accountsInforDAO;
	}
	
	
}
