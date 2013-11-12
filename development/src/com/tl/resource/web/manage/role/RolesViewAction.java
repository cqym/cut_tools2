package com.tl.resource.web.manage.role;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.resource.business.dto.ModulesCheckBoxDto;
import com.tl.resource.business.dto.RolesPageDto;
import com.tl.resource.business.manage.RolesService;

public class RolesViewAction extends DispatchAction{
	private RolesService rolesService;
	public ActionForward getAllRoles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		RolesPageDto pageList = rolesService.getAllRoles();
		out.println(JSONObject.fromObject(pageList).toString());
		out.flush();
		out.close();
		return null;
	}
	
	public ActionForward getRoleModules(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String roleId = request.getParameter("roleId");
		//System.out.println("****************************************:" + roleId);
		List<ModulesCheckBoxDto> list = rolesService.getAllRoleModules(roleId);
		out.println(JSONArray.fromObject(list).toString());
		out.flush();
		out.close();
		return null;
	}

	public RolesService getRolesService() {
		return rolesService;
	}

	public void setRolesService(RolesService rolesService) {
		this.rolesService = rolesService;
	}
	
	
}
