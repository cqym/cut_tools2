package com.tl.resource.web.quotation.generalquo;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tl.common.util.GenerateSerial;
import com.tl.common.util.LoginInforUtil;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.manage.BillsCodeDefService;
import com.tl.resource.business.quotation.generalquo.GeneralQuoService;
import com.tl.resource.dao.pojo.TProductToolsInfor;
import com.tl.resource.dao.pojo.TQuotationInfor;
import com.tl.resource.dao.pojo.TQuotationProductDetail;

public class AddGeneralQuoAction extends Action {
	private GeneralQuoService generalQuoService;
	private BillsCodeDefService billsCodeDefService ;

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
		String resultStr = "{success : true, msg : '添加报价信息成功'}";
		UserDto userDto = null;
		if(LoginInforUtil.getLoginInfor(request) != null) {
			userDto = LoginInforUtil.getLoginInfor(request).getUser();
		}
		//报价单信息
		String quoStr = request.getParameter("quoForm");
		//报价产品信息
		String quoProductStr = request.getParameter("quoProduct");
		String quoTypeStr = request.getParameter("quoType");
		Integer quoType = Integer.valueOf(quoTypeStr);
		
		JSONObject quoObj = JSONObject.fromObject(quoStr);
		
		JSONObject quoForm = quoObj.getJSONObject("data");
		JSONArray quoProductArray = JSONArray.fromObject(quoProductStr);
		
		resultStr = generalQuoService.insertQuotation(quoForm, quoProductArray, userDto, billsCodeDefService);
		
		PrintWriter out = response.getWriter();
		out.write(resultStr);
		out.flush();
		out.close();
		
		return null;
	}
	
	private ArrayList<TQuotationProductDetail> addChildrenProtools(JSONObject proTools, TQuotationProductDetail dto) {
		// TODO Auto-generated method stub
		JSONArray arr = proTools.getJSONArray("children");
		ArrayList<TQuotationProductDetail> list = new ArrayList<TQuotationProductDetail>();
		for (Iterator iterator2 = arr.iterator(); iterator2.hasNext();) {			
			JSONObject top = (JSONObject) iterator2.next();
			TQuotationProductDetail po2 = (TQuotationProductDetail) JSONObject.toBean(top,TQuotationProductDetail.class);
			po2.setParentToolsId(dto.getId());
			po2.setId(GenerateSerial.getUUID());
			po2.setQuotationInforId(dto.getQuotationInforId());
			po2.putLeaf(top.getString("leaf"));
			po2.setPrice(BigDecimal.valueOf(top.getDouble("salePrice")));
			//po2.setToolsId(top.getString("id"));
			//改变价格变动状态0 未改变，1 已改变
			if("".equals(top.getString("priceChange"))){
				po2.setPriceChange(0);
			} else if(!"".equals(top.getString("priceChange")) && "1".equals(top.getString("priceChange"))) {
				po2.setPriceChange(1);
			}
			list.add(po2);
			JSONArray arr2 = null;
			try {
				arr2 = top.getJSONArray("children");
				
			} catch(Exception e) {
				po2.setLeaf(1);
			}
			generalQuoService.insertQuoDetail(po2);
			//System.out.println("arr2="+arr2);
			if(arr2 != null && arr2.size() > 0){
				po2.setChildren(addChildrenProtools(top,po2));
			}else{
				//System.out.println("ddd:");
			}
		}
		dto.setChildren(list);
		return list;
	}

	public BillsCodeDefService getBillsCodeDefService() {
		return billsCodeDefService;
	}

	public void setBillsCodeDefService(BillsCodeDefService billsCodeDefService) {
		this.billsCodeDefService = billsCodeDefService;
	}
	
}
