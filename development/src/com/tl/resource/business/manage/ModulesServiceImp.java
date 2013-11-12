package com.tl.resource.business.manage;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.tl.resource.business.dto.Modules4AsyncDto;
import com.tl.resource.business.dto.ModulesDto;
import com.tl.resource.dao.TModulesDefDAO;
import com.tl.resource.dao.pojo.TModulesDef;
import com.tl.resource.dao.pojo.TModulesDefExample;
import com.tl.resource.dao.pojo.TModulesDefExample.Criteria;

public class ModulesServiceImp implements ModulesService{
	private TModulesDefDAO modulesDefDAO;
	@Override
	public void addModule(ModulesDto dto) {
		// TODO Auto-generated method stub
		TModulesDef po = new TModulesDef();
		try {
			BeanUtils.copyProperties(po, dto);
			po.setId(modulesDefDAO.findNewId(dto.getParentId()));
			if("".equals(po.getParentId())){
				po.setParentId("root");
			}
			dto.setId(po.getId());
			po.setLeaf((short) 1);
			modulesDefDAO.insert(po);
			
			TModulesDef parentNode = modulesDefDAO.selectByPrimaryKey(po.getParentId());
			if(parentNode != null){
				parentNode.setLeaf((short)0);
				modulesDefDAO.updateByPrimaryKey(parentNode);
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
	public void deleteModule(String id) {
		// TODO Auto-generated method stub
		TModulesDef cm = modulesDefDAO.selectByPrimaryKey(id);
		
		TModulesDefExample delEx = new TModulesDefExample();
		delEx.createCriteria().andIdLike(id+"%");
		modulesDefDAO.deleteByExample(delEx);
		
		TModulesDefExample example = new TModulesDefExample();
		example.createCriteria().andParentIdEqualTo(cm.getParentId());
		List list = modulesDefDAO.selectByExample(example);
		if(list == null || list.size() == 0){
			TModulesDef record = new TModulesDef();
			record.setId(cm.getParentId());
			record.setLeaf((short)1);
			modulesDefDAO.updateByPrimaryKeySelective(record );
		}
	}

	@Override
	public void updateModule(ModulesDto dto) {
		// TODO Auto-generated method stub
		TModulesDef po = null;
		po = modulesDefDAO.selectByPrimaryKey(dto.getId());
		po.setModuleName(dto.getModuleName());
		po.setUrl(dto.getUrl());
		modulesDefDAO.updateByPrimaryKeySelective(po);
	}

	public TModulesDefDAO getModulesDefDAO() {
		return modulesDefDAO;
	}

	public void setModulesDefDAO(TModulesDefDAO modulesDefDAO) {
		this.modulesDefDAO = modulesDefDAO;
	}

	@Override
	public List<Modules4AsyncDto> findChildrenModules(String pid) {
		if("".equals(pid)) pid = "root";
		TModulesDefExample example = new TModulesDefExample();
		Criteria cre = example.createCriteria();
		cre.andParentIdEqualTo(pid);
		example.setOrderByClause("serial_number");
		List children = modulesDefDAO.selectByExample(example);
		List<Modules4AsyncDto> tempChildren = new ArrayList<Modules4AsyncDto>();
		try {
			
			for (Iterator iterator = children.iterator(); iterator
					.hasNext();) {
				TModulesDef modulespo = (TModulesDef) iterator.next();
				Modules4AsyncDto d = new Modules4AsyncDto();
				BeanUtils.copyProperties(d, modulespo);
				tempChildren.add(d);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tempChildren;
	}

	@Override
	public List<ModulesDto> findModulesAll() {
		ModulesDto d = new ModulesDto();
		d.setId("root");
		return findAllChildren(d);
	}

	private List<ModulesDto> findAllChildren(ModulesDto vo){
		List<ModulesDto> list = new ArrayList<ModulesDto>();
		try {
			TModulesDefExample example = new TModulesDefExample();
			Criteria cre = example.createCriteria();
			cre.andParentIdEqualTo(vo.getId());
			example.setOrderByClause("serial_number");
			List<TModulesDef> children = modulesDefDAO.selectByExample(example);
			List<ModulesDto> tempChildren = convertToDto(children);
			list.addAll(tempChildren);
			vo.setChildren(tempChildren);
			for (Iterator iterator = tempChildren.iterator(); iterator.hasNext();) {
				ModulesDto o = (ModulesDto) iterator.next();	
				if(o.getLeaf() != 1){
					o.setChildren(findAllChildren(o));
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
	
	public List<ModulesDto> findUserModulesAll(String userId) {
		ModulesDto d = new ModulesDto();
		d.setId("root");
		return findUserModulesAllChildren(d,userId);
	}

	private List<ModulesDto> findUserModulesAllChildren(ModulesDto vo,String userId){
		List<ModulesDto> list = new ArrayList<ModulesDto>();
		try {
			
			List<TModulesDef> children = modulesDefDAO.getUsersModules(userId,vo.getParentId());
			List<ModulesDto> tempChildren = convertToDto(children);
			list.addAll(tempChildren);
			vo.setChildren(tempChildren);
			for (Iterator iterator = tempChildren.iterator(); iterator.hasNext();) {
				ModulesDto o = (ModulesDto) iterator.next();	
				if(o.getLeaf() != 1){
					o.setChildren(findAllChildren(o));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void updateModuleSerialNumbers(ArrayList<ModulesDto> list) {
		// TODO Auto-generated method stub
		try {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				ModulesDto modulesDto = (ModulesDto) iterator.next();
				TModulesDef modulesDef = new TModulesDef();
				BeanUtils.copyProperties( modulesDef,modulesDto );
				modulesDefDAO.updateByPrimaryKeySelective( modulesDef);
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
}
