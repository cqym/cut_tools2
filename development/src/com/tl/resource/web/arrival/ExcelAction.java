package com.tl.resource.web.arrival;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.tl.common.util.LoginInforUtil;
import com.tl.resource.business.arrival.ArrivalListToExcel;
import com.tl.resource.business.arrival.ArrivalService;
import com.tl.resource.business.arrival.ArrivalToExcel;
import com.tl.resource.business.dto.ArrivalInforDto;
import com.tl.resource.business.dto.ArrivalOrderDetialsDto;
import com.tl.resource.business.dto.ProductArrivalDetailDto;
import com.tl.resource.business.dto.QuotationDetailDto;
import com.tl.resource.business.dto.QuotationDto;
import com.tl.resource.business.dto.UserDto;
import com.tl.resource.business.arrival.ExcelService;
import com.tl.resource.business.quotation.generalquo.GeneralQuoService;
import com.tl.resource.dao.pojo.TProductArrivalInfor;

public class ExcelAction extends DispatchAction {
	private ArrivalService arrivalService;
	private ArrivalToExcel arrivalExcelService;
	private ArrivalListToExcel listToExcelService;
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String method = request.getParameter("method");
		
		if("toExcel".equals(method)) {
			return toExcel(mapping, form, request, response);
		} else if("arrivalListToExcel".equals(method)) {
			return arrivalListToExcel(mapping, form, request, response);
		} else if("directArrToExcel".equals(method)) {
			return directArrToExcel(mapping, form, request, response);
		} else {
			return null;
		}
	}

	//直接入库导出Excel
	private ActionForward directArrToExcel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String arrId = request.getParameter("arrId");
		
		TProductArrivalInfor arrivalInfo = arrivalService.getArrivalInfoById(arrId);
		ArrivalInforDto arrDto = new ArrivalInforDto();
		BeanUtils.copyProperties(arrDto, arrivalInfo);
		List<ProductArrivalDetailDto> list = arrivalService.getDetail4Direct(arrId);
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Content-Disposition",
				"attachment; filename=arrival-" + arrDto.getArrivalCode() + ".xls");
		
		String logoPath = request.getSession().getServletContext().getRealPath("");
		arrivalExcelService.setLogoPath(logoPath);
		arrivalExcelService.exportExcel(response.getOutputStream(), list, arrDto);
		return null;
	}

	private ActionForward arrivalListToExcel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,HttpServletResponse response) throws IOException {
		String orderType = request.getParameter("orderType");
		String searchStr = request.getParameter("searchStr");

		
		UserDto userDto = null;
		if(LoginInforUtil.getLoginInfor(request) != null) {
			userDto = LoginInforUtil.getLoginInfor(request).getUser();
		}
		
		String userId = "";
		if(userDto != null) {
			userId = userDto.getId();
		}
		
		Integer oType = Integer.parseInt(orderType);
		Integer[] type = null;
		if(oType == 1) {
			type = new Integer[]{1, 3};
		} else if( oType == 2) {
			type = new Integer[]{2, 4};
		} else if( oType == 5) {
			type = new Integer[]{5};
		} else if( oType == 6) {
			type = new Integer[]{6};
		} else if(oType == 0) {
			//直接入库
			type = new Integer[]{0};
		}
		
		//参数Map
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		if(null != searchStr && !"".equals(searchStr)) {
			JSONObject  searchJson= JSONObject.fromObject(searchStr);
			Integer status = null;
			if("全部".equals(searchJson.getString("status")) || "".equals(searchJson.getString("status"))) {
				status = null;
			} else {
				status = Integer.valueOf(searchJson.getString("status"));
			}
			
			//到货单号
			paramMap.put("arrivalCode", searchJson.getString("arrivalCode"));
			//采购单号
			paramMap.put("orderCode", searchJson.getString("orderCode"));
			//开始日期
			paramMap.put("beginDate", searchJson.getString("beginDate"));
			//结束日期
			paramMap.put("endDate", searchJson.getString("endDate"));
			paramMap.put("status", status);
		}
		
		
		StringBuffer orderTypeStr = new StringBuffer();
		if(type != null) {
			for (int i = 0; i < type.length; i++) {
				orderTypeStr.append(type[i].toString());
				if(i < type.length-1) {
					orderTypeStr.append(",");
				}
			}
		}
		//paramMap.put("orderType", orderTypeStr);
		paramMap.put("userId", userId);
		paramMap.put("resourceType", 4);
		
		//到货单总数
		int total = 0;
		//到货单列表
		List<TProductArrivalInfor> list = new ArrayList<TProductArrivalInfor>();
		
		if(orderTypeStr.toString().indexOf('0') == -1) {
			paramMap.put("orderType", orderTypeStr);
			total = arrivalService.getArrInfoTotalBySearch(paramMap);
			paramMap.put("start", 0);
			paramMap.put("limit", total);
			list = arrivalService.getArrivalInfoBySearch(paramMap);
		} else {
			//直接入库
			paramMap.put("arrivalType", orderTypeStr);
			total = arrivalService.getDirectArrTotalBySearch(paramMap);
			paramMap.put("start", 0);
			paramMap.put("limit", total);
			list = arrivalService.getDirectArrivalBySearch(paramMap);
		}
		
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Content-Disposition",
				"attachment; filename=arrival-" + total + ".xls");
		
		String realPath = request.getSession().getServletContext().getRealPath("");
		listToExcelService.setRealPath(realPath);
		listToExcelService.exportExcel(response.getOutputStream(), list);
		
		return null;
	}

	//导出Excel
	private ActionForward toExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		String arrId = request.getParameter("arrId");
		
		ArrivalInforDto arrDto = arrivalService.getArrInfoWithOrderType(arrId);
		List<ProductArrivalDetailDto> list = arrivalService.getArrivalDetail(arrId);
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Content-Disposition",
				"attachment; filename=arrival-" + arrDto.getArrivalCode() + ".xls");
		
		String logoPath = request.getSession().getServletContext().getRealPath("");
		arrivalExcelService.setLogoPath(logoPath);
		arrivalExcelService.exportExcel(response.getOutputStream(), list, arrDto);
		return null;
	}

	public ArrivalService getArrivalService() {
		return arrivalService;
	}

	public void setArrivalService(ArrivalService arrivalService) {
		this.arrivalService = arrivalService;
	}

	public ArrivalToExcel getArrivalExcelService() {
		return arrivalExcelService;
	}

	public void setArrivalExcelService(ArrivalToExcel arrivalExcelService) {
		this.arrivalExcelService = arrivalExcelService;
	}

	public ArrivalListToExcel getListToExcelService() {
		return listToExcelService;
	}

	public void setListToExcelService(ArrivalListToExcel listToExcelService) {
		this.listToExcelService = listToExcelService;
	}

	

	

	
	
}
