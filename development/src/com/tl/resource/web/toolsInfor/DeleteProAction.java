package com.tl.resource.web.toolsInfor;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.common.util.PaginationSupport;
import com.tl.resource.business.baseInfo.ProductCorrelationService;
import com.tl.resource.business.dto.CurSalesPriceHistoryDto;
import com.tl.resource.business.dto.OrderPriceHistoryDto;
import com.tl.resource.business.dto.QuotationDetailDto;
import com.tl.resource.business.dto.TreeDto;
import com.tl.resource.business.protoolsinfo.ProToolsInforService;
import com.tl.resource.business.quotation.generalquo.GeneralQuoService;
import com.tl.resource.dao.pojo.TProductToolsInfor;

/**
 * 删除产品
 * @author Administrator
 *
 */
public class DeleteProAction extends Action {
	private ProToolsInforService proToolsInforService;
	private ProductCorrelationService productCorrelationService ;
	private GeneralQuoService generalQuoService;
	
	public ProToolsInforService getProToolsInforService() {
		return proToolsInforService;
	}

	public void setProToolsInforService(ProToolsInforService proToolsInforService) {
		this.proToolsInforService = proToolsInforService;
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String id = request.getParameter("id");
		String resultStr = "{success : true, msg : '删除产品成功！'}";
		boolean flag = true;
		//构造JSON对象
		try {
			//JSONObject jsonObj = JSONObject.fromObject(json);
			
			//String id = jsonObj.getString("id");
			TreeDto treeDto = proToolsInforService.getProductToolsInfoById(id);
			Map<String, Object> parmMap = new HashMap<String, Object>();
			parmMap.put("productToolsInforId", id);
			List<OrderPriceHistoryDto> list = productCorrelationService.getOrderHistoryProductByPageAndProductId(parmMap);
			List<CurSalesPriceHistoryDto> list2 = productCorrelationService.getCusSalesHistoryProductByPageAndProductId(parmMap);
			List<QuotationDetailDto> list3 = generalQuoService.getQuoDetaiByToolsId(id);
			String infoStr = "";
			
			if(list != null && list.size() > 0) {
				flag = false;
				infoStr = "对应采购历史价格已经存在";
			} 
			if(flag && list2.size() > 0) {
				flag = false;
				infoStr += "对应销售历史价格已经存在 ";
			}
			if(flag && list3.size() > 0) {
				flag = false;
				infoStr += "该工具信息已做报价 ";
			}
			if(flag) {
				proToolsInforService.deleteTools(treeDto);
			} else {
				resultStr = "{success : false, msg : '" + infoStr + "不允许删除！'}";
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			resultStr = "{success : false, msg : '删除产品失败！'}";
		}
		
		response.setContentType("text/html;charset=utf-8");
		
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		
		return null;
	}

	public ProductCorrelationService getProductCorrelationService() {
		return productCorrelationService;
	}

	public void setProductCorrelationService(
			ProductCorrelationService productCorrelationService) {
		this.productCorrelationService = productCorrelationService;
	}

	public GeneralQuoService getGeneralQuoService() {
		return generalQuoService;
	}

	public void setGeneralQuoService(GeneralQuoService generalQuoService) {
		this.generalQuoService = generalQuoService;
	}
}
