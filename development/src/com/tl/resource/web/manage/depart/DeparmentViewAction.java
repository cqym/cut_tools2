package com.tl.resource.web.manage.depart;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.resource.business.dto.DepartmentsDto;
import com.tl.resource.business.manage.DepartmentService;

public class DeparmentViewAction extends DispatchAction{
	private DepartmentService departmentService;
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
	
		List<DepartmentsDto> list = departmentService.findDepartmentsAll();
		
		out.println(JSONArray.fromObject(list).toString());
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
