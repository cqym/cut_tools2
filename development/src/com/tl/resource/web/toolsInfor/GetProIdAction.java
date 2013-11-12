package com.tl.resource.web.toolsInfor;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.dto.TreeDto;
import com.tl.resource.business.protoolsinfo.ProToolsInforService;
import com.tl.resource.dao.pojo.TProductToolsInfor;

public class GetProIdAction extends Action {
	private ProToolsInforService proToolsInforService;

	public ProToolsInforService getProToolsInforService() {
		return proToolsInforService;
	}

	public void setProToolsInforService(ProToolsInforService proToolsInforService) {
		this.proToolsInforService = proToolsInforService;
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		response.setContentType("text/html;charset=utf-8");
		String id = proToolsInforService.getId("");
		
		PrintWriter out = response.getWriter();
		out.write("{success : true, id : \""+ id +"\"}");
		out.flush();
		out.close();
		
		return null;
	}
	
	
}
