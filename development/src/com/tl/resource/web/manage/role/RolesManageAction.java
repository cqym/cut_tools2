package com.tl.resource.web.manage.role;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.resource.business.dto.RolesDto;
import com.tl.resource.business.manage.RolesService;

public class RolesManageAction  extends DispatchAction{
	private RolesService rolesService;
	public ActionForward createRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String content = request.getParameter("content");
		
		JSONObject json = JSONObject.fromObject(content);
		RolesDto dto = (RolesDto) JSONObject.toBean(json,RolesDto.class);
		String[] modulesIds = new String[0];
		rolesService.createRoles(dto, modulesIds);
		out.println(JSONObject.fromObject(dto).toString());
		out.flush();
		out.close();
		return null;
	}
	
	public ActionForward updateRoleInfor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String content = request.getParameter("content");
		JSONObject json = JSONObject.fromObject(content);
		RolesDto dto = (RolesDto) JSONObject.toBean(json,RolesDto.class);
		rolesService.updateRolesInfor(dto);
		out.println(JSONObject.fromObject(dto).toString());
		out.flush();
		out.close();
		return null;
	}
	
	public ActionForward updateRoleDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String roleId = request.getParameter("roleId");
		String modulesIdsjson = request.getParameter("modulesIds");
		JSONArray json = JSONArray.fromObject(modulesIdsjson);
		Object[] arr = json.toArray();
		String[] modulesIds = new String[arr.length];
		for (int i = 0; i < arr.length; i++) {
			modulesIds[i] = (String) arr[i];
		}
		rolesService.updateRolesDetail(roleId, modulesIds );
		out.println(true);
		out.flush();
		out.close();
		return null;
	}
	
	public ActionForward deleteRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String roleId = request.getParameter("roleId");
		rolesService.deleteRoles(roleId);
		out.println(true);
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
