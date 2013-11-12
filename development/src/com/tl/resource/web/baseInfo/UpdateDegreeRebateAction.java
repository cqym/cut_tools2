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

import com.tl.common.util.GenerateSerial;
import com.tl.resource.business.baseInfo.BaseInfoService;
import com.tl.resource.dao.pojo.TQuotationProductDetail;
import com.tl.resource.dao.pojo.TRebate;

/**
 * @author xtaia
 *
 */
public class UpdateDegreeRebateAction extends Action {

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
		String resultStr = "{success : true, msg : '修改折扣信息成功'}";
		String modifDegRebRecordsPar = request.getParameter("modifDegRebRecordsPar");
		String modifDegRebRecordsParA = "[".concat(modifDegRebRecordsPar.substring(0, modifDegRebRecordsPar.length()-1)).concat("]");
		//工序列表信息
		JSONArray degreeRecords = JSONArray.fromObject(modifDegRebRecordsParA);
		
		try {
			
			Iterator<JSONObject> iter = degreeRecords.iterator();
			while(iter.hasNext()) {
				JSONObject rebateObject = iter.next();
				TRebate trebate = null;
				trebate = (TRebate)JSONObject.toBean(rebateObject, TRebate.class);
				
				if((trebate.getId() == null) || ("".equals(trebate.getId()))){
					trebate.setId(GenerateSerial.getUUID());
					baseInfoService.saveRebate(trebate);
				}else{
					baseInfoService.updateRebate(trebate);
				}
				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			resultStr = "{success : false, msg : '修改折扣信息失败'}";
		}
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		
		return null;
	}

}
