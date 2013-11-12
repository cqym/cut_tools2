package com.tl.resource.business.quotation.generalquo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.tl.common.smartupload.Constant;
import com.tl.resource.business.dto.QuotationDto;

/**
 * 报价单列表导出
 * @author ftl
 *
 */
public class QuoListToExcel {
	private String realPath;
	
	public String getRealPath() {
		return realPath;
	}
	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}
	
	private HSSFWorkbook createWorkBook() {
		HSSFWorkbook wb = null;
		FileInputStream is;
		try {
			File file = new File(this.getRealPath() + Constant.QUOLIST_FILE);
			is = new FileInputStream(file);
			wb=new HSSFWorkbook(is);
		}  catch (IOException e) {
			e.printStackTrace();
		}
		return wb;
	}
	
	private HSSFCell setCellValue(HSSFRow row, int cellIndex, String Value) {
    	HSSFCell cell1 = row.createCell(cellIndex);
        cell1.setCellValue(Value);
        return cell1;
    }
    
    private HSSFCell setCellValue(HSSFRow row, int cellIndex, int Value) {
    	HSSFCell cell1 = row.createCell(cellIndex);
        cell1.setCellValue(Value);
        return cell1;
    }
    
    private HSSFCell setCellValue(HSSFRow row, int cellIndex, Double Value) {
    	HSSFCell cell1 = row.createCell(cellIndex);
        cell1.setCellValue(Value);
        return cell1;
    }
    
    private String changeStatus(int status) {
		switch (status) {
			case 0 :
			return "编制";
			case 1 :
			return "待审批";
			case 2 :
			return "审批通过";
			case 3 :
			return "审批退回";
			case 4 :
			return "提交合同";
			case 5 :
			return "已经生成合同";
			case 6 :
			return "提交订货";
			default :
				return "";
		}
	}
    
    private String changeLevel(int value) {
		switch(value) {
			case 0 :
			return "一般";
			case 1 : 
			return "紧急";
			default : 
				return "";
		}
	}
	
	/**  
     * 创建行  
     * @param cells  
     * @param rowIndex  
     */  
    private void createTableRow(QuotationDto quoDto,HSSFRow row)   
    {   
        setCellValue(row, 0, quoDto.getQuotationCode());
        setCellValue(row, 1, changeStatus(quoDto.getStatus()));
        setCellValue(row, 2, changeLevel(quoDto.getUrgentLevel()));
        setCellValue(row, 3, quoDto.getCustomerName());
        setCellValue(row, 4, quoDto.getUserName());
        setCellValue(row, 5, quoDto.getEditorName());
        setCellValue(row, 6, quoDto.getQuotationDate());
        setCellValue(row, 7, quoDto.getPaymentCondition());
        setCellValue(row, 8, quoDto.getDeliveryType());
        setCellValue(row, 9, quoDto.getCurrencyName());
        setCellValue(row, 10, quoDto.getProductMoney().doubleValue());
        setCellValue(row, 11,  quoDto.getTaxRate().doubleValue());
        setCellValue(row, 12, quoDto.getTaxMoney().doubleValue());
        setCellValue(row, 13, quoDto.getTotalMoney().doubleValue());
        setCellValue(row, 14, quoDto.getEditTimeStr());
        setCellValue(row, 15, quoDto.getMemo());
    }
	
	public void insertRow(List<QuotationDto> list, HSSFWorkbook wb, HSSFSheet sheet, int startRow) {  
    	int rows = list.size();
        HSSFFont font = wb.createFont();
  	  	font.setFontHeightInPoints((short)10);
        for (int i = 0; i < rows; i++) {   
        	HSSFRow targetRow = null;  
        	targetRow = sheet.createRow(++startRow);  
        	QuotationDto dto = list.get(i);
        	this.createTableRow(dto, targetRow);
        } 
        
    } 
	
	public void exportExcel(OutputStream os, List<QuotationDto> list) throws IOException {
		HSSFWorkbook wb = this.createWorkBook();
		int sheetNum=wb.getNumberOfSheets();
		
		for(int i=0;i<sheetNum;i++) {   
			HSSFSheet childSheet = wb.getSheetAt(i);
			if(list != null && list.size() > 0) {
				this.insertRow(list, wb, childSheet, 0);
			}
        }
		
		wb.write(os);
        os.flush();
        os.close();
	}
}
