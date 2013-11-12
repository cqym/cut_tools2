package com.tl.resource.audit.businessflow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tl.common.context.SystemInstance;
import com.tl.resource.audit.IAuditBusinessObject;
import com.tl.resource.audit.IAuditContentBusinessObject;
import com.tl.resource.audit.IAuditContentService;
import com.tl.resource.audit.dto.LinkBusinessObject;
import com.tl.resource.audit.dto.PageHeaderInfor;
import com.tl.resource.audit.dto.TAuditContentDefDto;
import com.tl.resource.business.dto.QuotationDto;
import com.tl.resource.business.quotation.generalquo.GeneralQuoService;
import com.tl.resource.dao.pojo.TAuditHistory;

public class ReserveQuoAuditImpl implements IAuditBusinessObject ,IAuditContentBusinessObject {

	private GeneralQuoService generalQuoService = (GeneralQuoService) SystemInstance.getInstance().getBean("GeneralQuoService");
	private IAuditContentService auditContentService = (IAuditContentService) SystemInstance.getInstance().getBean("AuditContentService");
	private TAuditHistory abo;
    private LinkBusinessObject bo;
	private String businessType = "014";
	@Override
	public String cancelAudit() {
		// TODO Auto-generated method stub
		return generalQuoService.cancelAudit(abo.getBusinessId());
	}

	@Override
	public String endAudit() {
		// TODO Auto-generated method stub
		return generalQuoService.endAudit(abo.getBusinessId());
	}
	
	@Override
	public String submitAudit() {
		// TODO Auto-generated method stub
		return generalQuoService.submitAudit(abo.getBusinessId());
	}

	@Override
	public TAuditHistory getAbo() {
		// TODO Auto-generated method stub
		return abo;
	}

	@Override
	public LinkBusinessObject getBo() {
		// TODO Auto-generated method stub
		return bo;
	}

	@Override
	public String getBusinessType() {
		// TODO Auto-generated method stub
		return businessType;
	}

	@Override
	public List getShowColumns() {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		PageHeaderInfor phi1 = new PageHeaderInfor();
		phi1.setHeader("id");
		phi1.setDataIndex("id");
		phi1.setSortable(false);
		phi1.setHidden(true);
		list.add(phi1);
		
		PageHeaderInfor phi2 = new PageHeaderInfor();
		phi2.setHeader("报价单编号");
		phi2.setDataIndex("quotationCode");
		phi2.setSortable(false);
		list.add(phi2);
		
		PageHeaderInfor phi3 = new PageHeaderInfor();
		phi3.setHeader("客户名称");
		phi3.setDataIndex("customerName");
		phi3.setSortable(false);
		list.add(phi3);
		
		PageHeaderInfor phi4 = new PageHeaderInfor();
		phi4.setHeader("货品金额");
		phi4.setDataIndex("productMoney");
		phi4.setSortable(false);
		list.add(phi4);
		
		PageHeaderInfor phi5 = new PageHeaderInfor();
		phi5.setHeader("最终金额");
		phi5.setDataIndex("totalMoney");
		phi5.setSortable(false);
		list.add(phi5);
		
		PageHeaderInfor phi6 = new PageHeaderInfor();
		phi6.setHeader("报价人");
		phi6.setDataIndex("editorName");
		phi6.setSortable(false);
		list.add(phi6);
		
		PageHeaderInfor phi7 = new PageHeaderInfor();
		phi7.setHeader("报价日期");
		phi7.setDataIndex("quotationDate");
		phi7.setSortable(false);
		list.add(phi7);
		
		PageHeaderInfor phi8 = new PageHeaderInfor();
		phi8.setHeader("备注");
		phi8.setDataIndex("memo");
		phi8.setSortable(false);
		list.add(phi8);
		
		PageHeaderInfor phi9 = new PageHeaderInfor();
		phi9.setHeader("查看详细");
		phi9.setDataIndex("url");
		phi9.setSortable(false);
		phi9.setHidden(true);
		list.add(phi9);
		return list;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return "/js/quotation/reserveQuo/QuoDetailWindow.js";
	}

	@Override
	public void loadBo(String id) {
		// TODO Auto-generated method stub
		bo = new LinkBusinessObject();
		QuotationDto vo = generalQuoService.getGeneralQuoInforById(id);
		bo.setId(vo.getId());
		bo.setName(vo.getQuotationCode());
		bo.addProperty("id", vo.getId());
		bo.addProperty("quotationCode", vo.getQuotationCode());
		bo.addProperty("customerName", vo.getCustomerName());
		bo.addProperty("productMoney", vo.getProductMoney());
		bo.addProperty("totalMoney", vo.getTotalMoney());
		bo.addProperty("editorName", vo.getEditorName());
		bo.addProperty("quotationDate", vo.getQuotationDate());
		bo.addProperty("memo", vo.getMemo());
		bo.addProperty("url", "/js/quotation/reserveQuo/QuoDetailWindow.js");
	}

	@Override
	public void setAbo(TAuditHistory bo) {
		// TODO Auto-generated method stub
		this.abo = bo;
	}

	
	public GeneralQuoService getGeneralQuoService() {
		return generalQuoService;
	}

	public void setGeneralQuoService(GeneralQuoService generalQuoService) {
		this.generalQuoService = generalQuoService;
	}

	@Override
	public List<TAuditContentDefDto> getBusinessAuditContents(String id) {
		boolean change = generalQuoService.isPriceChange(id);
		boolean hasNonStand = generalQuoService.validator(id);
		boolean closingAccountModeChange = generalQuoService.isClosingAccountModeChange(id);
		List<TAuditContentDefDto> list = auditContentService.findAllAuditContentList();
		List<TAuditContentDefDto> rtList = new ArrayList<TAuditContentDefDto>();
		if(change && hasNonStand && closingAccountModeChange) return list;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TAuditContentDefDto auditContentDefDto = (TAuditContentDefDto) iterator.next();
			if(!change && PRICE.equals(auditContentDefDto.getId())){
				continue;
			}else if(!hasNonStand && TECHNOLOGY.equals(auditContentDefDto.getId())){
				continue;
			}else if(!closingAccountModeChange && CLOSING_ACCOUNT_MODE.equals(auditContentDefDto.getId())){
				continue;
			}
			rtList.add(auditContentDefDto);
		}
		return rtList;
	}

}
