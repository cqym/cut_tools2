package com.tl.resource.web.quotation.generalquo;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.resource.business.dto.QuotationDetailDto;
import com.tl.resource.business.quotation.generalquo.GeneralQuoService;
import com.tl.resource.dao.pojo.TQuotationInfor;
import com.tl.resource.dao.pojo.TQuotationProductDetail;

public class DeleteQuoAction extends Action {
	private GeneralQuoService generalQuoService;

	public GeneralQuoService getGeneralQuoService() {
		return generalQuoService;
	}

	public void setGeneralQuoService(GeneralQuoService generalQuoService) {
		this.generalQuoService = generalQuoService;
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("id");
		String resultStr = "{success : true, msg : '删除报价信息成功!'}";
		if(null != id && !"".equals(id)) {
			
			JSONArray ids = JSONArray.fromObject(id);
			Iterator<JSONObject> iterator = ids.iterator();
			resultStr = generalQuoService.deleteQuotation(iterator);
			
			/*Iterator<JSONObject> iterator = ids.iterator();
			
			while(iterator.hasNext()) {
				JSONObject jsonObj = iterator.next();
				String quoId = jsonObj.getString("id");
				TQuotationInfor quoInfo = new TQuotationInfor();
				quoInfo.setId(quoId);
				
				//List<QuotationDetailDto> list = generalQuoService.getQuoDetailByQuo(quoId);
				
				try {
					//generalQuoService.deleteQuoDetail(list);
					generalQuoService.deleteByQuoId(quoId);
					generalQuoService.deleteQuotation(quoInfo);
				} catch(Exception e) {
					e.printStackTrace();
					resultStr = "{success : false, msg : '删除报价信息失败!'}";
				}
			}*/
			
		} else {
			resultStr = "{success : false, msg : '请选择报价单!'}";
		}
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		
		return null;
	}
	
	
}
