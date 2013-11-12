package com.tl.resource.business.manage;

import java.util.List;

import com.tl.resource.business.dto.ModulesCheckBoxDto;
import com.tl.resource.business.dto.ModulesDto;
import com.tl.resource.business.dto.RolesDto;
import com.tl.resource.business.dto.RolesPageDto;

public interface RolesService {

	public void createRoles(RolesDto dto,String[] modulesIds);
	
	public void updateRolesInfor(RolesDto dto);
	
	public void updateRolesDetail(String roleId,String[] modulesIds);
	
	public void deleteRoles(String id);
	
	public RolesPageDto getAllRoles();
	 
	public List<ModulesDto> getRoleModules(String roleId);
	
	public List<ModulesCheckBoxDto> getAllRoleModules(String roleId);
}
