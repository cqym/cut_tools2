/**
 * 
 */
package com.tl.resource.web.quotation.projectquo;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.common.util.GenerateSerial;
import com.tl.resource.business.quotation.projectquo.ProjectQuoService;
import com.tl.resource.dao.pojo.TProductToolsInfor;
import com.tl.resource.dao.pojo.TQuotationInfor;
import com.tl.resource.dao.pojo.TQuotationProductDetail;
import com.tl.resource.dao.pojo.TQuotationProjectSortInfor;

/**
 * @author xtaia
 * 
 */
public class DeteleProjectQuoAction extends Action {

	private ProjectQuoService projectQuoService;

	public ProjectQuoService getProjectQuoService() {
		return projectQuoService;
	}

	public void setProjectQuoService(ProjectQuoService projectQuoService) {
		this.projectQuoService = projectQuoService;
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setContentType("text/html;charset=utf-8");
		String resultStr = "{success : true, msg : '删除报价信息成功'}";

	
		String quoId = request.getParameter("quoId");

		JSONArray jsonArray = JSONArray.fromObject(quoId);
		Iterator<JSONObject> iterator = jsonArray.iterator();
		try {
			while (iterator.hasNext()) {
				JSONObject obj = iterator.next();
				String id = obj.getString("id");
				projectQuoService.deleteQuoInfoById(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultStr = "{success : false, msg : '删除报价信息失败'}";
		}
		
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();

		return null;
	}

}
