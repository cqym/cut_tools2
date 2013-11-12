package com.tl.resource.audit;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.tl.common.util.GenerateSerial;
import com.tl.resource.audit.dto.TAuditFlowDetailDto;
import com.tl.resource.audit.dto.TAuditFlowInforDto;
import com.tl.resource.audit.dto.TAuditTypeDto;
import com.tl.resource.dao.TAuditFlowDetailDAO;
import com.tl.resource.dao.TAuditFlowInforDAO;
import com.tl.resource.dao.TAuditTypeDAO;
import com.tl.resource.dao.pojo.TAuditFlowDetail;
import com.tl.resource.dao.pojo.TAuditFlowDetailExample;
import com.tl.resource.dao.pojo.TAuditFlowInfor;
import com.tl.resource.dao.pojo.TAuditTypeExample;

public class AuditFlowDefinedServiceImp implements IAuditFlowDefinedService{
	private TAuditFlowDetailDAO auditFlowDetailDAO;
	private TAuditFlowInforDAO auditFlowInforDAO;
	private TAuditTypeDAO auditTypeDAO;
  
	@Override
	public void createAuditInfor(TAuditFlowInforDto vo) {
		TAuditFlowInfor po = new TAuditFlowInfor();
		try {
			BeanUtils.copyProperties(po, vo);
			po.setId(GenerateSerial.getUUID());
			po.setDefineTime(new Date());
			po.setEnable(1);
			auditFlowInforDAO.insert(po);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void createFlowDetail(TAuditFlowDetailDto vo) {
		// TODO Auto-generated method stub
		TAuditFlowDetail po = new TAuditFlowDetail();
		try {
			BeanUtils.copyProperties(po, vo);
			po.setId(GenerateSerial.getUUID());
			auditFlowDetailDAO.insert(po);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteFlowDetail(String id) {
		// TODO Auto-generated method stub
		auditFlowDetailDAO.deleteByPrimaryKey(id);
	}

	@Override
	public void disenableTheFlow(String inforId) {
		// TODO Auto-generated method stub
		TAuditFlowInfor po = new TAuditFlowInfor();
		po.setId(inforId);
		po.setEnable(0);
		auditFlowInforDAO.updateByPrimaryKeySelective(po);
	}

	@Override
	public void enableTheFlow(String inforId) {
		// TODO Auto-generated method stub
		TAuditFlowInfor po = new TAuditFlowInfor();
		po.setId(inforId);
		po.setEnable(1);
		auditFlowInforDAO.updateByPrimaryKeySelective(po);
	}

	@Override
	public List<TAuditTypeDto> getAllAuditTypes() {
		// TODO Auto-generated method stub
		TAuditTypeExample example = new TAuditTypeExample();
		example.setOrderByClause("serial_number");
		return auditTypeDAO.selectByExample(example);
	}

	@Override
	public List<TAuditFlowDetailDto> getFlowDetailByFlowInforId(String inforId) {
		TAuditFlowDetailExample example = new TAuditFlowDetailExample();
		example.createCriteria().andFlowInforIdEqualTo(inforId);
		example.setOrderByClause("audit_order");
		return auditFlowDetailDAO.selectByExample(example);
	}

	@Override
	public List<TAuditFlowInforDto> getFlowInforsByAuditType(String auditType) {
		// TODO Auto-generated method stub
		
		return auditFlowInforDAO.getAuditInfor(auditType);
	}

	@Override
	public void updateAuditInfor(TAuditFlowInforDto vo) {
		// TODO Auto-generated method stub
		TAuditFlowInfor po = new TAuditFlowInfor();
		try {
			BeanUtils.copyProperties(po, vo);
			auditFlowInforDAO.updateByPrimaryKeySelective(po);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateFlowDetail(TAuditFlowDetailDto vo) {
		// TODO Auto-generated method stub
		TAuditFlowDetail po = new TAuditFlowDetail();
		try {
			BeanUtils.copyProperties(po, vo);
			auditFlowDetailDAO.updateByPrimaryKeySelective(po);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public TAuditFlowDetailDAO getAuditFlowDetailDAO() {
		return auditFlowDetailDAO;
	}

	public void setAuditFlowDetailDAO(TAuditFlowDetailDAO auditFlowDetailDAO) {
		this.auditFlowDetailDAO = auditFlowDetailDAO;
	}

	public TAuditFlowInforDAO getAuditFlowInforDAO() {
		return auditFlowInforDAO;
	}

	public void setAuditFlowInforDAO(TAuditFlowInforDAO auditFlowInforDAO) {
		this.auditFlowInforDAO = auditFlowInforDAO;
	}

	public TAuditTypeDAO getAuditTypeDAO() {
		return auditTypeDAO;
	}

	public void setAuditTypeDAO(TAuditTypeDAO auditTypeDAO) {
		this.auditTypeDAO = auditTypeDAO;
	}

}
