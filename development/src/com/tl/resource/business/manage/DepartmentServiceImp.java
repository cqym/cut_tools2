package com.tl.resource.business.manage;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.tl.resource.business.dto.DepartmentsDto;
import com.tl.resource.dao.TDepartmentsInforDAO;
import com.tl.resource.dao.pojo.TDepartmentsInfor;
import com.tl.resource.dao.pojo.TDepartmentsInforExample;

public class DepartmentServiceImp implements DepartmentService {
	private TDepartmentsInforDAO departmentsInforDAO;
	@Override
	public void addDepartment(DepartmentsDto dto) {
		// TODO Auto-generated method stub
		TDepartmentsInfor po = new TDepartmentsInfor();
		try {
			BeanUtils.copyProperties(po, dto);
			po.setId(departmentsInforDAO.findNewId(dto.getParentId()));
			if("".equals(po.getParentId())){
				po.setParentId("root");
			}
			dto.setId(po.getId());
			po.setLeaf( 1);
			departmentsInforDAO.insert(po);
			
			TDepartmentsInfor parentNode = departmentsInforDAO.selectByPrimaryKey(po.getParentId());
			if(parentNode != null){
				parentNode.setLeaf(0);
				departmentsInforDAO.updateByPrimaryKey(parentNode);
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
	public void deleteDepartment(String id) {
		// TODO Auto-generated method stub
		TDepartmentsInfor cm = departmentsInforDAO.selectByPrimaryKey(id);
		
		
		TDepartmentsInforExample delEx = new TDepartmentsInforExample();
		delEx.createCriteria().andIdLike(id+"%");
		departmentsInforDAO.deleteByExample(delEx);
		
		TDepartmentsInforExample example = new TDepartmentsInforExample();
		example.createCriteria().andParentIdEqualTo(cm.getParentId());
		List list = departmentsInforDAO.selectByExample(example);
		if(list == null || list.size() == 0){
			TDepartmentsInfor record = new TDepartmentsInfor();
			record.setId(cm.getParentId());
			record.setLeaf(1);
			departmentsInforDAO.updateByPrimaryKeySelective(record );
		}
	}

	@Override
	public List<DepartmentsDto> findChildrenDepartments(String pid) {
		// TODO Auto-generated method stub
		
		if("".equals(pid)) pid = "root";
		TDepartmentsInforExample example = new TDepartmentsInforExample();
		example.createCriteria().andParentIdEqualTo(pid);
		return departmentsInforDAO.selectByExample(example);
	}

	@Override
	public List<DepartmentsDto> findDepartmentsAll() {
		// TODO Auto-generated method stub
		DepartmentsDto d = new DepartmentsDto();
		d.setId("root");
		return findAllChildren(d);
	}

	private List<DepartmentsDto> findAllChildren(DepartmentsDto vo){
		List<DepartmentsDto> list = new ArrayList<DepartmentsDto>();
		try {
			TDepartmentsInforExample example = new TDepartmentsInforExample();
			example.createCriteria().andParentIdEqualTo(vo.getId());
			
			List<TDepartmentsInfor> children = departmentsInforDAO.selectByExample(example);
			
			List<DepartmentsDto> tempChildren = convertToDto(children);
			
			list.addAll(tempChildren);
			vo.setChildren(tempChildren);
			
			for (Iterator iterator = tempChildren.iterator(); iterator.hasNext();) {
				DepartmentsDto o = (DepartmentsDto) iterator.next();	
				if(o.getLeaf() != 1){
					//System.out.println("ssss:" + o.getId());
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
	public void updateDepartment(DepartmentsDto dto) {
		// TODO Auto-generated method stub
		TDepartmentsInfor po = null;
		po = departmentsInforDAO.selectByPrimaryKey(dto.getId());
		po.setDepartName(dto.getDepartName());
		departmentsInforDAO.updateByPrimaryKeySelective(po);
	}

	public TDepartmentsInforDAO getDepartmentsInforDAO() {
		return departmentsInforDAO;
	}

	public void setDepartmentsInforDAO(TDepartmentsInforDAO departmentsInforDAO) {
		this.departmentsInforDAO = departmentsInforDAO;
	}
	private List<DepartmentsDto> convertToDto(List<TDepartmentsInfor> departsDefs)throws Exception {
		DepartmentsDto departsDto;
		if (null == departsDefs)
			return null;

		List<DepartmentsDto> target = new ArrayList<DepartmentsDto>();
		for (int i = 0; i < departsDefs.size(); i++) {
			TDepartmentsInfor def = departsDefs.get(i);
			departsDto = new DepartmentsDto();
			BeanUtils.copyProperties(departsDto, def);
			target.add(departsDto);
		}

		return target;
	}
}
