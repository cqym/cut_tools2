package com.tl.resource.web.toolsInfor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.resource.business.protoolsinfo.ImportToolsService;

public class ImportToolsAction extends DispatchAction {
	private ImportToolsService importToolsService;
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String method = request.getParameter("method");
		if("importTools".equals(method)) {
			return importTools(mapping, form, request, response);
		} else if("exportTools".equals(method)) {
			return exportTools(mapping, form, request, response);
		} else {
			return null;
		}
	}
	
	//导出工具信息
	private ActionForward exportTools(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		importToolsService.exportTools(request, response);
		return null;
	}
	
	//导入工具信息
	private ActionForward importTools(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String agent = request.getHeader("USER-AGENT");
		int index = agent.indexOf("MSIE");
		String error = importToolsService.importTools(request, response);
		
		String resultStr = "{success : true, msg : 数据导入失败！}";//"{success : true, msg : '数据成功导入 共导入 " + error  + " 条数据！'}";
		if(error != null) {
			if(this.isNumeric(error)) {
				if(Integer.valueOf(error) == 0) {
					resultStr = "{success : true, msg : '请为模板添加导入数据！'}";
				} else {
					resultStr = "{success : true, msg : '数据成功导入 共导入 " + error  + " 条数据！'}";
				}
				
			} else {
				resultStr = "{success : false, msg : " + error + "}";
			}
		}
		
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		return null;
	}
	
	public ImportToolsService getImportToolsService() {
		return importToolsService;
	}
	public void setImportToolsService(ImportToolsService importToolsService) {
		this.importToolsService = importToolsService;
	}
	
	private boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	} 
}
