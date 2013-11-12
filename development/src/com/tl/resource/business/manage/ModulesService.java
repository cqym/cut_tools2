package com.tl.resource.business.manage;

import java.util.ArrayList;
import java.util.List;

import com.tl.resource.business.dto.Modules4AsyncDto;
import com.tl.resource.business.dto.ModulesDto;

public interface ModulesService {

	public void addModule(ModulesDto dto);
	
	public void updateModule(ModulesDto dto);
	
	public void deleteModule(String id);
	
	public List<ModulesDto> findModulesAll();
	
	public List<Modules4AsyncDto> findChildrenModules(String pid);

	public void updateModuleSerialNumbers(ArrayList<ModulesDto> list);
}
