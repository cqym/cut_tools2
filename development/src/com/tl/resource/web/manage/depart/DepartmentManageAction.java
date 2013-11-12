package com.tl.resource.web.manage.depart;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.resource.business.dto.DepartmentsDto;
import com.tl.resource.business.manage.DepartmentService;

public class DepartmentManageAction extends DispatchAction{
	private DepartmentService departmentService;
	public ActionForward addNode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String departName = request.getParameter("departName");
		String pid = request.getParameter("parentId");
		 
		DepartmentsDto dto = new DepartmentsDto();
		dto.setDepartName(departName);
		dto.setParentId(pid);
		departmentService.addDepartment(dto);
		out.println(JSONObject.fromObject(dto).toString());
		out.flush();
		out.close();
		return null;
	}
	
	public ActionForward updateNode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String departName = request.getParameter("departName");
		String id = request.getParameter("id");
		DepartmentsDto dto = new DepartmentsDto();
		dto.setDepartName(departName);
		dto.setId(id);
		departmentService.updateDepartment(dto);
		out.println(JSONObject.fromObject(dto).toString());
		out.flush();
		out.close();
		return null;
	}
	
	public ActionForward deleteNode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		PrintWriter out = response.getWriter();
		String id = request.getParameter("id");
		departmentService.deleteDepartment(id);
		out.println(true);
		out.flush();
		out.close();
		return null;
	}
	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	
}
