package com.tl.test.improtsaleprice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.tl.common.util.GenerateSerial;

public class ImpKcData {
	static String filepath = "C:\\data\\kucun\\黄盒刀片.xls" ;
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
			for (int i = 2,len = sheet.getLastRowNum(); i <= len; i++) {
				cell = sheet.getRow(i);
				sb.append(GenerateSerial.getUUID()).append(t);
				sb.append(getStringValue(cell.getCell(1),0)).append(t);//
				sb.append(getStringValue(cell.getCell(2),0)).append(t);//
				sb.append(getStringValue(cell.getCell(3),0)).append(t);//
				sb.append(getStringValue(cell.getCell(4),0)).append(t);//
				sb.append(getStringValue(cell.getCell(5),0)).append(t);//
				sb.append(getStringValue(cell.getCell(6),0)).append(t);//
				sb.append(getStringValue(cell.getCell(7),0)).append(t);//
				sb.append(getStringValue(cell.getCell(8),0)).append(t);//
				sb.append("\r\n");
				if(i % 500 == 0){
					FileWriter writer = new FileWriter("C:\\data\\kucun\\黄盒刀片.txt", true);
					writer.write(sb.toString());
					writer.close();
					sb = new StringBuffer(1024);
					System.err.println("....");
				}
				//System.out.println(i);
			}
			FileWriter writer = new FileWriter("C:\\data\\kucun\\黄盒刀片.txt", true);
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
		NumberFormat format = new DecimalFormat("#0");
		return format.format(cell.getNumericCellValue());
	}
	
}
