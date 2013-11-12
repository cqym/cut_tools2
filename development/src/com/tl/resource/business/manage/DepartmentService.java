package com.tl.resource.business.manage;

import java.util.List;

import com.tl.resource.business.dto.DepartmentsDto;

public interface DepartmentService {
	public void addDepartment(DepartmentsDto dto);
	
	public void updateDepartment(DepartmentsDto dto);
	
	public void deleteDepartment(String id);
	
	public List<DepartmentsDto> findDepartmentsAll();
	
	public List<DepartmentsDto> findChildrenDepartments(String pid);
}
