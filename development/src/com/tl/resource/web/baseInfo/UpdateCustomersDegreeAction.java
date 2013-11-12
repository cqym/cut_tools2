/**
 * 
 */
package com.tl.resource.web.baseInfo;

import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.resource.business.baseInfo.BaseInfoService;
import com.tl.resource.dao.pojo.TCustomersDegree;

/**
 * @author xtaia
 * 修改客户等级信息
 */
public class UpdateCustomersDegreeAction extends Action {

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
		String resultStr = "{success : true, msg : '修改客户等级信息成功'}";
		String modifCustDegRecordsPar = request.getParameter("modifCustDegRecordsPar");
		String modifCustDegRecordsParA = "[".concat(modifCustDegRecordsPar.substring(0, modifCustDegRecordsPar.length()-1)).concat("]");
		//工序列表信息
		JSONArray degreeRecords = JSONArray.fromObject(modifCustDegRecordsParA);
		
		try {
			
			Iterator<JSONObject> iter = degreeRecords.iterator();
			while(iter.hasNext()) {
				JSONObject custDegreeObject = iter.next();
				TCustomersDegree tcusDegree = null;
				tcusDegree = (TCustomersDegree)JSONObject.toBean(custDegreeObject, TCustomersDegree.class);
				baseInfoService.updateRebate(tcusDegree);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			resultStr = "{success : false, msg : '修改客户等级信息失败'}";
		}
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		
		return null;
	}



}
