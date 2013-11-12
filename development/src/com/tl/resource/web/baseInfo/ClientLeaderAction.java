package com.tl.resource.web.baseInfo;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.baseInfo.BaseInfoService;
import com.tl.resource.dao.pojo.TOwnContactPerson;
import com.tl.resource.dao.pojo.TUserInfor;

public class ClientLeaderAction extends DispatchAction {
	private BaseInfoService baseInfoService;
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return super.execute(mapping, form, request, response);
	}
	
	public ActionForward getSelectedUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String userId = request.getParameter("userId");
		PrintWriter out = response.getWriter();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		List<TOwnContactPerson> list = baseInfoService.getSelectedUser(params);
		out.println(JSONArray.fromObject(list).toString());
		out.flush();
		out.close();
		return null;
	}
	
	public ActionForward getWillSelectUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String userId = request.getParameter("userId");
		PrintWriter out = response.getWriter();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		int startIndex = request.getParameter("start") == null ? 0 : Integer.valueOf(request.getParameter("start"));
		int pageSize = request.getParameter("limit") == null ? PaginationSupport.PAGESIZE : Integer.valueOf(request.getParameter("limit"));
	    params.put("startIndex", startIndex);
	    params.put("pageSize", pageSize);
		PaginationSupport pageInfor = baseInfoService.getWillSelectUser(params);
	    String rt = JSONObject.fromObject(pageInfor).toString();
		out.println(rt);
		out.flush();
		out.close();
		return null;
	}
	
	public ActionForward saveClientLeader(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String userId = request.getParameter("userId");
		String[] ids = request.getParameterValues("userIds");
		List<String> idlist = new ArrayList<String>();
		if(ids != null) {
			for(String id : ids) {
				idlist.add(id);
			}
		}
		
		baseInfoService.saveClientLeader(userId, idlist);
		out.println(true);
		out.flush();
		out.close();
		return null;
	}
	
	public BaseInfoService getBaseInfoService() {
		return baseInfoService;
	}
	public void setBaseInfoService(BaseInfoService baseInfoService) {
		this.baseInfoService = baseInfoService;
	}
	
	
}
