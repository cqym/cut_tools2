package com.tl.resource.web.quotation.generalquo;

import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.tl.resource.dao.pojo.TQuotationProductDetail;

public class DeleteQuoProductAction extends Action {
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
		String resultStr = "{success : true, msg : '删除报价产品信息成功!'}";
		String quoProduct = request.getParameter("quoProduct");
		
		JSONObject jsonObj = JSONObject.fromObject(quoProduct);
		
		QuotationDetailDto quoDetailDto = (QuotationDetailDto)JSONObject.toBean(jsonObj, QuotationDetailDto.class);
		
		QuotationDetailDto dto = generalQuoService.getQuoDetailById(quoDetailDto.getId());
		
		if(dto != null) {
			try {
				deleteQuoPro(dto);
			} catch(Exception e) {
				e.printStackTrace();
				resultStr = "{success : false, msg : '删除报价产品信息失败!'}";
			}
			
		}
		
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		return null;
	}
	
	private void deleteQuoPro(QuotationDetailDto dto) {
		List<QuotationDetailDto> children = dto.getChildren();
		if(children != null) {
			for(QuotationDetailDto dto2 : children) {
				dto2 = generalQuoService.getQuoDetailById(dto2.getId());
				List<QuotationDetailDto> list = dto2.getChildren();
				
				if(list != null && list.size() > 0) {
					deleteQuoPro(dto2);
				}
				
				generalQuoService.deleteQuoDetail(dto2.getId());
			}
		}
		
		generalQuoService.deleteQuoDetail(dto.getId());
	}
	
	private ArrayList<QuotationDetailDto> deleteProtools(JSONObject proTools, QuotationDetailDto dto) {
		JSONArray arr = proTools.getJSONArray("children");
		ArrayList<QuotationDetailDto> list = new ArrayList<QuotationDetailDto>();
		for (Iterator iterator2 = arr.iterator(); iterator2.hasNext();) {			
			JSONObject top = (JSONObject) iterator2.next();
			QuotationDetailDto po2 = (QuotationDetailDto) JSONObject.toBean(top,TQuotationProductDetail.class);
			//po2.setParentToolsId(dto.getId());
			//po2.setId(GenerateSerial.getUUID());
			//po2.setQuotationInforId(dto.getQuotationInforId());
			
			list.add(po2);
			JSONArray arr2 = null;
			try {
				arr2 = top.getJSONArray("children");
				
			} catch(Exception e) {
				//po2.setLeaf(1);
			}
			
			//generalQuoService.(po2);
			//System.out.println("arr2="+arr2);
			if(arr2 != null && arr2.size() > 0){
				po2.setChildren(deleteProtools(top,po2));
			}else{
				//System.out.println("ddd:");
			}
		}
		dto.setChildren(list);
		return list;
	}
}
