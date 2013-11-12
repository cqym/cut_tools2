/**
 * 
 */
package com.tl.resource.web.baseInfo;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.resource.business.baseInfo.BaseInfoService;
import com.tl.resource.dao.pojo.TSuppliersInfor;

/**
 * @author xtaia
 * 更新供应商信息
 */
public class UpdateSupplierAction extends Action {


	private BaseInfoService baseInfoService;

	public BaseInfoService getBaseInfoService() {
		return baseInfoService;
	}

	public void setBaseInfoService(BaseInfoService baseInfoService) {
		this.baseInfoService = baseInfoService;
	}


	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setContentType("text/html;charset=utf-8");
		String resultStr = "{success : true, msg : '修改供应商信息成功'}";

		String updateSupplierInfoPar = request.getParameter("supplierFormInfoPar");
		JSONObject supplierObj = JSONObject.fromObject(updateSupplierInfoPar);
		

		try {
			TSuppliersInfor supplierInfo = (TSuppliersInfor)JSONObject.toBean(supplierObj, TSuppliersInfor.class);
			baseInfoService.updateSupplier(supplierInfo);
		} catch (Exception e) {
			e.printStackTrace();
			resultStr = "{success : false, msg : '修改供应商信息失败'}";
		}
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();

		return null;
	}


	
	

}
