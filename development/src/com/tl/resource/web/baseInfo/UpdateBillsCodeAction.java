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
import com.tl.resource.business.manage.BillsCodeDefService;
import com.tl.resource.dao.pojo.TBillsCodeDef;

/**
 * @author xtaia
 * 更新单据编号
 */
public class UpdateBillsCodeAction extends Action {

	private BaseInfoService baseInfoService;

	private BillsCodeDefService billsCodeDefService;
	
	public BillsCodeDefService getBillsCodeDefService() {
		return billsCodeDefService;
	}

	public void setBillsCodeDefService(BillsCodeDefService billsCodeDefService) {
		this.billsCodeDefService = billsCodeDefService;
	}

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
		String resultStr = "{success : true, msg : '修改单据编号规则成功'}";

		String billsCodeFormInfoPar = request.getParameter("billsCodeFormInfoPar");
		JSONObject billsCodeObj = JSONObject.fromObject(billsCodeFormInfoPar);
		JSONObject billsCodeDate = billsCodeObj.getJSONObject("data");
		try {
			TBillsCodeDef billsCodeDefDto = (TBillsCodeDef)JSONObject.toBean(billsCodeDate, TBillsCodeDef.class);
			billsCodeDefService.update(billsCodeDefDto);
		} catch (Exception e) {
			e.printStackTrace();
			resultStr = "{success : false, msg : '修改单据编号规则失败'}";
		}
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();

		return null;
	
		
		
		
	}

}
