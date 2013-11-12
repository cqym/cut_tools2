package com.tl.resource.business.arrival;

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
import com.tl.resource.dao.pojo.TProductArrivalInfor;

public class ArrivalListToExcel {
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
			File file = new File(this.getRealPath() + Constant.ARRIVALLIST_FILE);
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
			return "入库 未确认";
			case 1 :
			return "入库 已确认";
			default :
				return "";
		}
	}
    
    /**  
     * 创建行  
     * @param cells  
     * @param rowIndex  
     */  
    private void createTableRow(TProductArrivalInfor quoDto,HSSFRow row)   
    {   
        setCellValue(row, 0, quoDto.getArrivalCode());
        setCellValue(row, 1, quoDto.getUserName());
        setCellValue(row, 2, quoDto.getEditDateString());
        setCellValue(row, 3, quoDto.getDeliveryDate());
        setCellValue(row, 4, quoDto.getOrderCode());
        setCellValue(row, 5, quoDto.getSupplierName());
        setCellValue(row, 6, quoDto.getContractCode());
        setCellValue(row, 7, changeStatus(quoDto.getStatus()));
        setCellValue(row, 8, quoDto.getCustomerName());
        setCellValue(row, 9, quoDto.getMemo());
    }
	
	public void insertRow(List<TProductArrivalInfor> list, HSSFWorkbook wb, HSSFSheet sheet, int startRow) {  
    	int rows = list.size();
        HSSFFont font = wb.createFont();
  	  	font.setFontHeightInPoints((short)10);
        for (int i = 0; i < rows; i++) {   
        	HSSFRow targetRow = null;  
        	targetRow = sheet.createRow(++startRow);  
        	TProductArrivalInfor dto = list.get(i);
        	this.createTableRow(dto, targetRow);
        } 
        
    } 
	
	public void exportExcel(OutputStream os, List<TProductArrivalInfor> list) throws IOException {
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
