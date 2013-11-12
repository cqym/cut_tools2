package com.tl.resource.business.manage;

import java.util.List;

import com.tl.resource.business.dto.BillsCodeDefDto;
import com.tl.resource.dao.pojo.TBillsCodeDef;

public interface BillsCodeDefService {
	public void update(TBillsCodeDef dto);
	
	public List<BillsCodeDefDto> getAll();
	/**
	 * 
	 * @param type
	 * @param gysbh 供应商编号
	 * @param khbh  客户编号
	 * gsbh 买方编号
	 * @return
	 */
	public String getBillCode(String type,String gysbh,String khbh,String gsbh);
}
