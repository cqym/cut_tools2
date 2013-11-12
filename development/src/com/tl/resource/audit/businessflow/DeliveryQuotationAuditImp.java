package com.tl.resource.audit.businessflow;

import java.util.ArrayList;
import java.util.List;

import com.tl.common.context.SystemInstance;
import com.tl.resource.audit.IAuditBusinessObject;
import com.tl.resource.audit.dto.LinkBusinessObject;
import com.tl.resource.audit.dto.PageHeaderInfor;
import com.tl.resource.business.delivery.DeliveryEditService;
import com.tl.resource.business.dto.DeliveryInforDto;
import com.tl.resource.dao.pojo.TAuditHistory;
/**
 * 预定 交货 审批
 * @author tonglian
 *
 */
public class DeliveryQuotationAuditImp implements IAuditBusinessObject {
	private DeliveryEditService deliveryEditService = (DeliveryEditService) SystemInstance.getInstance().getBean("DeliveryEditService");
	private TAuditHistory abo;
    private LinkBusinessObject bo;
	private String businessType = "010";
	@Override
	public String cancelAudit() {
		// TODO Auto-generated method stub
		return deliveryEditService.cancelAudit(abo.getBusinessId());
	}

	@Override
	public String endAudit() {
		// TODO Auto-generated method stub
		return deliveryEditService.endAudit(abo.getBusinessId());
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
		phi2.setHeader("交货单编号");
		phi2.setDataIndex("deliveryCode");
		phi2.setSortable(false);
		list.add(phi2);
		
//		PageHeaderInfor phi22 = new PageHeaderInfor();
//		phi22.setHeader("合同编号");
//		phi22.setDataIndex("contractCode");
//		phi22.setSortable(false);
//		list.add(phi22);
		
		PageHeaderInfor phi3 = new PageHeaderInfor();
		phi3.setHeader("客户名称");
		phi3.setDataIndex("customerName");
		phi3.setSortable(false);
		list.add(phi3);
		
//		PageHeaderInfor phi4 = new PageHeaderInfor();
//		phi4.setHeader("货品金额");
//		phi4.setDataIndex("productMoney");
//		phi4.setSortable(false);
//		list.add(phi4);
		
		PageHeaderInfor phi5 = new PageHeaderInfor();
		phi5.setHeader("交货时间");
		phi5.setDataIndex("deliveryDate");
		phi5.setSortable(false);
		list.add(phi5);
		
		PageHeaderInfor phi6 = new PageHeaderInfor();
		phi6.setHeader("联系人");
		phi6.setDataIndex("contactPerson");
		phi6.setSortable(false);
		list.add(phi6);
		
		PageHeaderInfor phi7 = new PageHeaderInfor();
		phi7.setHeader("收货人");
		phi7.setDataIndex("acceptPerson");
		phi7.setSortable(false);
		list.add(phi7);
		
		PageHeaderInfor phi8 = new PageHeaderInfor();
		phi8.setHeader("收货日期");
		phi8.setDataIndex("acceptDate");
		phi8.setSortable(false);
		list.add(phi8);
		
		PageHeaderInfor phi81 = new PageHeaderInfor();
		phi81.setHeader("编制人");
		phi81.setDataIndex("userName");
		phi81.setSortable(false);
		list.add(phi81);
		
		PageHeaderInfor phi82 = new PageHeaderInfor();
		phi82.setHeader("编制时间");
		phi82.setDataIndex("editDateString");
		phi82.setSortable(false);
		list.add(phi82);
		
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
		return "/js/delivery/consult_contract/delivery_edit_win.js";
	}

	@Override
	public void loadBo(String id) {
		// TODO Auto-generated method stub
		bo = new LinkBusinessObject();
		DeliveryInforDto vo = deliveryEditService.getDeliveryInforById(id);
		bo.setId(vo.getId());
		bo.setName(vo.getDeliveryCode());
		bo.addProperty("id", vo.getId());
		bo.addProperty("deliveryCode", vo.getDeliveryCode());
		//bo.addProperty("contractCode", vo.getContractCode());
		bo.addProperty("customerName", vo.getCustomerName());
		bo.addProperty("deliveryDate", vo.getDeliveryDate());
		bo.addProperty("contactPerson", vo.getContactPerson());
		bo.addProperty("acceptPerson", vo.getAcceptPerson());//
		bo.addProperty("userName", vo.getUserName());
		bo.addProperty("editDateString", vo.getEditDateString());
		bo.addProperty("acceptDate", vo.getAcceptDate());
		bo.addProperty("url", "/js/delivery/consult_contract/delivery_edit_win.js");
	}

	@Override
	public void setAbo(TAuditHistory bo) {
		// TODO Auto-generated method stub
		this.abo = bo;
	}

	@Override
	public String submitAudit() {
		// TODO Auto-generated method stub
		return deliveryEditService.submitAudit(abo.getBusinessId());
	}

	public DeliveryEditService getDeliveryEditService() {
		return deliveryEditService;
	}

	public void setDeliveryEditService(DeliveryEditService deliveryEditService) {
		this.deliveryEditService = deliveryEditService;
	}

	public void setBo(LinkBusinessObject bo) {
		this.bo = bo;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

}
