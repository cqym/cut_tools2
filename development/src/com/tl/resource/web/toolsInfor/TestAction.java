package com.tl.resource.web.toolsInfor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.resource.business.TestService;
import com.tl.resource.business.dto.AccountsInforPagerDto;

public class TestAction extends Action {
	private TestService testService;
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		AccountsInforPagerDto pager = testService.getList();
		out.println(JSONObject.fromObject(pager).toString());
		out.flush();
		return null;
	}
	public TestService getTestService() {
		return testService;
	}
	public void setTestService(TestService testService) {
		this.testService = testService;
	}
}
