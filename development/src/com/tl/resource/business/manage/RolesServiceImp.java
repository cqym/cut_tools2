package com.tl.resource.business.manage;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.tl.common.util.GenerateSerial;
import com.tl.resource.business.dto.ModulesCheckBoxDto;
import com.tl.resource.business.dto.ModulesDto;
import com.tl.resource.business.dto.RolesDto;
import com.tl.resource.business.dto.RolesPageDto;
import com.tl.resource.dao.TRolesDAO;
import com.tl.resource.dao.TRolesModulesDAO;
import com.tl.resource.dao.pojo.TModulesDef;
import com.tl.resource.dao.pojo.TModulesDefExample;
import com.tl.resource.dao.pojo.TRoles;
import com.tl.resource.dao.pojo.TRolesExample;
import com.tl.resource.dao.pojo.TRolesModulesExample;
import com.tl.resource.dao.pojo.TRolesModulesKey;
import com.tl.resource.dao.pojo.TModulesDefExample.Criteria;

public class RolesServiceImp implements RolesService{

	private TRolesDAO rolesDAO;
	private TRolesModulesDAO rolesModulesDAO;
	
	@Override
	public void createRoles(RolesDto dto, String[] modulesIds) {
		// TODO Auto-generated method stub
		TRoles r = new TRoles();
		try {
			BeanUtils.copyProperties(r, dto);
			r.setId(GenerateSerial.getUUID());
			dto.setId(r.getId());
			rolesDAO.insert(r);
			
			for (int j = 0; j < modulesIds.length; j++) {
				TRolesModulesKey rm = new TRolesModulesKey();
				rm.setId(r.getId());
				rm.setModuleId(modulesIds[j]);
				rolesModulesDAO.insert(rm);
			}
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteRoles(String id) {
		TRolesModulesExample example = new TRolesModulesExample();
		com.tl.resource.dao.pojo.TRolesModulesExample.Criteria c = example.createCriteria();
		c.andIdEqualTo(id);
		rolesModulesDAO.deleteByExample(example);
		rolesDAO.deleteByPrimaryKey(id);
	}

	@Override
	public RolesPageDto getAllRoles() {
		// TODO Auto-generated method stub
		TRolesExample example = new TRolesExample();
		RolesPageDto rpd = new RolesPageDto();
		rpd.setTotalCount(rolesDAO.countByExample(example));
		rpd.setRoles(rolesDAO.selectByExample(example ));
		return rpd;
	}

	@Override
	public void updateRolesDetail(String roleId, String[] modulesIds) {
		TRolesModulesExample example = new TRolesModulesExample();
		com.tl.resource.dao.pojo.TRolesModulesExample.Criteria c = example.createCriteria();
		c.andIdEqualTo(roleId);
		
		rolesModulesDAO.deleteByExample(example );
		
		for (int j = 0; j < modulesIds.length; j++) {
			TRolesModulesKey rm = new TRolesModulesKey();
			rm.setId(roleId);
			rm.setModuleId(modulesIds[j]);
			rolesModulesDAO.insert(rm);
		}
	}

	@Override
	public void updateRolesInfor(RolesDto dto) {
		// TODO Auto-generated method stub
		try {
			TRoles record = new TRoles();
			BeanUtils.copyProperties(record, dto);
			rolesDAO.updateByPrimaryKey(record);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public TRolesDAO getRolesDAO() {
		return rolesDAO;
	}

	public void setRolesDAO(TRolesDAO rolesDAO) {
		this.rolesDAO = rolesDAO;
	}

	public TRolesModulesDAO getRolesModulesDAO() {
		return rolesModulesDAO;
	}

	public void setRolesModulesDAO(TRolesModulesDAO rolesModulesDAO) {
		this.rolesModulesDAO = rolesModulesDAO;
	}

	@Override
	public List<ModulesDto> getRoleModules(String roleId) {
		// TODO Auto-generated method stub
		List<ModulesDto> dtoList = new ArrayList<ModulesDto>();
		List<TRolesModulesKey> list = rolesModulesDAO.selectRoleModules(roleId);
		try {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				TRolesModulesKey rolesModulesKey = (TRolesModulesKey) iterator
						.next();
				ModulesDto dest = new ModulesDto();
				BeanUtils.copyProperties(dest, rolesModulesKey);
				dtoList.add(dest);
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dtoList;
	}

	@Override
	public List<ModulesCheckBoxDto> getAllRoleModules(String roleId) {
		ModulesCheckBoxDto d = new ModulesCheckBoxDto();
		d.setId("root");
		return findAllChildren(roleId,d);
	}
	
	private List<ModulesCheckBoxDto> findAllChildren(String roleId,ModulesCheckBoxDto vo){
		List<ModulesCheckBoxDto> list = new ArrayList<ModulesCheckBoxDto>();
		try {
			TRolesModulesExample example = new TRolesModulesExample();
			List<ModulesCheckBoxDto> children = rolesModulesDAO.getRoleCheckModulesChildren(roleId,vo.getId());
			list.addAll(children);
			vo.setChildren(children);
			for (Iterator iterator = children.iterator(); iterator.hasNext();) {
				ModulesCheckBoxDto o = (ModulesCheckBoxDto) iterator.next();	
				if(o.getLeaf() != 1){
					o.setChildren(findAllChildren(roleId,o));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	private List<ModulesDto> convertToDto(List<TModulesDef> modulesDefs)throws Exception {
		ModulesDto modulesDto;
		if (null == modulesDefs)
			return null;
		List<ModulesDto> target = new ArrayList<ModulesDto>();
		for (int i = 0; i < modulesDefs.size(); i++) {
			TModulesDef modulesDef = modulesDefs.get(i);
			modulesDto = new ModulesDto();
			BeanUtils.copyProperties(modulesDto, modulesDef);
			target.add(modulesDto);
		}
		return target;
	}
}
