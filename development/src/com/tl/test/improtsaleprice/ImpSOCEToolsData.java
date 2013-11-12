package com.tl.test.improtsaleprice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ImpSOCEToolsData {
	static String filepath = "c:\\soce_price.xls" ;
	public static void main(String[] args) {
		
		try {
			InputStream fs;
			HSSFWorkbook workbook;
			HSSFSheet sheet;
			HSSFRow cell;

			fs = new FileInputStream(filepath);
			workbook = new HSSFWorkbook(fs);
			sheet = workbook.getSheetAt(0);
			StringBuffer sb = new StringBuffer(1024);
			String t = "\t";
			for (int i = 1,len = sheet.getLastRowNum(); i <= len; i++) {
				cell = sheet.getRow(i);
				sb.append(getStringValue(cell.getCell(0),0)).append(t);
				sb.append("1").append(t);
				sb.append("个").append(t);
				sb.append(getStringValue(cell.getCell(9),0)).append(t);
				sb.append("SECO").append(t);
				sb.append("采购").append(t);
				sb.append("人民币").append(t);
				sb.append(getStringValue(cell.getCell(7),0)).append(t);
				sb.append(getStringValue(cell.getCell(8),0)).append(t);
				sb.append("\r\n");
				if(i % 500 == 0){
					FileWriter writer = new FileWriter("c:\\soce_price.txt", true);
					writer.write(sb.toString());
					writer.close();
					sb = new StringBuffer(1024);
					System.err.println("....");
				}
				//System.out.println(i);
			}
			FileWriter writer = new FileWriter("c:\\soce_price.txt", true);
			writer.write(sb.toString());
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static Object getStringValue(HSSFCell cell,int type){
		if(cell == null) return "";
		if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING){
	 	    return cell.getStringCellValue();
		}
		return cell.getNumericCellValue();
	}
	
}
