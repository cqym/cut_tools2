package com.tl.resource.business.outStock;

import java.io.IOException;
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
import com.tl.resource.dao.pojo.TOutStockInfor;

public class OutStockListOutExcelImp implements OutStockListOutExcel {
	private OutStockService outStockService = null;
	private static final int sheetMaxRowCount = 60000;
	private static final String[] headers = {"出库单编号","合同编号","报价单编号","备注","出库类型","编制人","编制时间","状态"};
	@Override
	public void exportExcel(Map<String, Object> mparams,
			HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		PaginationSupport page = outStockService.findOutStockInfors(mparams,0,Integer.MAX_VALUE);
		List list = page.getItems();
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow r = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = r.createCell(i);
			cell.setCellValue(headers[i]);
		}
		int rowCount = 1;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TOutStockInfor c = (TOutStockInfor) iterator.next();
			if(rowCount > sheetMaxRowCount){
				rowCount = 1;
				sheet = workbook.createSheet();
			}
			HSSFRow row = sheet.createRow(rowCount);
			HSSFCell cell = row.createCell(0);
			cell.setCellValue(c.getOutStockCode());
				
			cell = row.createCell(1);
			cell.setCellValue(c.getContractCode());
				
		    cell = row.createCell(2);
			cell.setCellValue(c.getQuotationCode());
				
			cell = row.createCell(3);
			cell.setCellValue(c.getMemo());
				
			cell = row.createCell(4);
			cell.setCellValue(getTypeText(c.getOutStockType()));
				
			cell = row.createCell(5);
			cell.setCellValue(c.getUserName());
				
			cell = row.createCell(6);
			cell.setCellValue(c.getEditDateString());
				
			cell = row.createCell(7);
			cell.setCellValue(getStatusText(c.getStatus()));
						
			rowCount++;
		}
		
		response.setContentType("application/vnd.ms-excel");
		WebUtils.setDownloadableHeader(response, "contractList.xls");
		workbook.write(response.getOutputStream());
		response.getOutputStream().flush();
	}
	private String getStatusText(Integer status) {
		// TODO Auto-generated method stub0编制，1已确认,2作废
		if(status == 0){
			return "编制";
		}else if(status == 1){
		    return "已确认";	
		}else if(status == 2){
			return "作废";
		}
		return null;
	}
	private String getTypeText(Integer outStockType) {
		switch (outStockType) {
		case 0:
			return "直接出库";
		case 1:
			return "合同出库(提取库存)";
		case 2:		
			return "预定报价单出库(采购)";
		case 3:
			return "材料出库";
		case 4:
			return "合同出库(采购)";
		case 5:
			return "预定报价单出库(提取库存)";
		case 6:
			return "试刀(提取库存)";
		case 7:
			return "试刀(采购)";
		default:
			break;
		}
		return null;
	}
	public OutStockService getOutStockService() {
		return outStockService;
	}
	public void setOutStockService(OutStockService outStockService) {
		this.outStockService = outStockService;
	}

}
