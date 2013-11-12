package com.tl.resource.business.delivery;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.tl.common.util.PaginationSupport;
import com.tl.common.util.WebUtils;
import com.tl.resource.dao.TDeliveryInforDAO;

public class DeliveryListOutExcelImp implements DeliveryListOutExcel{
	private TDeliveryInforDAO deliveryInforDAO;
	private static final int sheetMaxRowCount = 60000;
	private static final String[] headers = {"交货单编号","合同编号","客户名称","客户我方联系人","交货日期","编制人","编制时间","状态","附件"};
	@Override
	public void exportExcel(Map<String,Object> params, HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		boolean hasQuo = !"0".equals(params.get("deliveryCode"));
		PaginationSupport page = deliveryInforDAO.findDeliveryInfors(params, 0,Integer.MAX_VALUE);
		List list = page.getItems();
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow r = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = r.createCell(i);
			cell.setCellValue(headers[i]);
		}
		if(hasQuo){
			HSSFCell cell = r.createCell(headers.length);
			cell.setCellValue("报价单编号");
		}
		int rowCount = 1;
		
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			com.tl.resource.dao.pojo.TDeliveryInfor c = (com.tl.resource.dao.pojo.TDeliveryInfor) iterator.next();
			if(rowCount > sheetMaxRowCount){
				rowCount = 1;
				sheet = workbook.createSheet();
			}
			HSSFRow row = sheet.createRow(rowCount);
			HSSFCell cell = row.createCell(0);
			cell.setCellValue(c.getDeliveryCode());
				
			cell = row.createCell(1);
			cell.setCellValue(c.getContractCode());
				
			cell = row.createCell(2);
			cell.setCellValue(c.getCustomerName());
				
			cell = row.createCell(3);
			cell.setCellValue(c.getContactPerson());
				
			cell = row.createCell(4);
			cell.setCellValue(c.getDeliveryDate());
				
			cell = row.createCell(5);
			cell.setCellValue(c.getUserName());
				
			cell = row.createCell(6);
			cell.setCellValue(c.getEditDateString());
			
			cell = row.createCell(7);
			cell.setCellValue(getStatusText(c.getStatus()));
			
			cell = row.createCell(8);
			cell.setCellValue(c.getFileCount() > 0 ? "已传附件" : "未传附件");
			
			if(hasQuo){
				cell = row.createCell(9);
				cell.setCellValue(c.getQuotationCode());
			}
			rowCount++;
		}
		
		response.setContentType("application/vnd.ms-excel");
		WebUtils.setDownloadableHeader(response, "deliveryList.xls");
		workbook.write(response.getOutputStream());
		response.getOutputStream().flush();
	}
	private String getStatusText(Integer status) {
		String rt = "";
		switch (status) {
		case 0:
			rt = "编制";
			break;
		case 1:
			rt = "待审批";
			break;
	    case 2:
			rt = "审批通过";
			break;
		case 3:
			rt = "审批退回";
			break;
		default:
			break;
		}
		return rt;
	}
	public TDeliveryInforDAO getDeliveryInforDAO() {
		return deliveryInforDAO;
	}
	public void setDeliveryInforDAO(TDeliveryInforDAO deliveryInforDAO) {
		this.deliveryInforDAO = deliveryInforDAO;
	}

}
