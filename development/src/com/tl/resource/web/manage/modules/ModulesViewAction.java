package com.tl.resource.web.manage.modules;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.resource.business.dto.Modules4AsyncDto;
import com.tl.resource.business.dto.ModulesDto;
import com.tl.resource.business.manage.ModulesService;

public class ModulesViewAction extends DispatchAction{
	private ModulesService modulesService;
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
	
		List<ModulesDto> list = modulesService.findModulesAll();
		
		out.println(JSONArray.fromObject(list).toString());
		out.flush();
		out.close();
		
		return null;
	}
	public ActionForward checkboxQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String pid = request.getParameter("node");
		//System.out.println(pid);
		List<Modules4AsyncDto> list = modulesService.findChildrenModules(pid);
		String rt = JSONArray.fromObject(list).toString();
		//System.out.println(rt);
		out.println(rt);
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
