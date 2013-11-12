package com.tl.resource.web.manage.modules;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.resource.business.dto.ModulesDto;
import com.tl.resource.business.manage.ModulesService;

public class ModulesManageAction extends DispatchAction{
	private ModulesService modulesService;
	public ActionForward addNode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String moduleName = request.getParameter("moduleName");
		String url = request.getParameter("url");
		String pid = request.getParameter("parentId");
		ModulesDto dto = new ModulesDto();
		dto.setModuleName(moduleName);
		dto.setParentId(pid);
		dto.setUrl(url);
		modulesService.addModule(dto);
		out.println(JSONObject.fromObject(dto).toString());
		out.flush();
		out.close();
		return null;
	}
	
	public ActionForward updateNode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String moduleName = request.getParameter("moduleName");
		String id = request.getParameter("id");
		String url = request.getParameter("url");
		ModulesDto dto = new ModulesDto();
		dto.setModuleName(moduleName);
		dto.setId(id);
		dto.setUrl(url);
		modulesService.updateModule(dto);
		out.println(JSONObject.fromObject(dto).toString());
		out.flush();
		out.close();
		return null;
	}
	
	public ActionForward deleteNode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		PrintWriter out = response.getWriter();
		String id = request.getParameter("id");
		modulesService.deleteModule(id);
		out.println(true);
		out.flush();
		out.close();
		return null;
	}
	public ActionForward updateSerialNumbers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String datasString = request.getParameter("datas");
		JSONArray productSortsArray = JSONArray.fromObject(datasString);
		ArrayList<ModulesDto> list = new ArrayList<ModulesDto>();
		for (Iterator iterator = productSortsArray.iterator(); iterator
				.hasNext();) {
			JSONObject sortjson = (JSONObject) iterator.next();
			ModulesDto d = (ModulesDto) JSONObject.toBean(sortjson, ModulesDto.class);
			list.add(d);
		}
		modulesService.updateModuleSerialNumbers(list);
		out.print("true");
		out.flush();
		out.close();
		return null;
	}
	public ModulesService getModulesService() {
		return modulesService;
	}
	public void setModulesService(ModulesService modulesService) {
		this.modulesService = modulesService;
	}
	
}
