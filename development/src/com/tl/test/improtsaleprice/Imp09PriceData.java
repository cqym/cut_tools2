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

public class Imp09PriceData {
	static String filepath = "c:\\temp.xls" ;
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
			int num = 23541;
			for (int i = 0,len = sheet.getLastRowNum(); i <= len; i++) {
				cell = sheet.getRow(i);

				
				sb.append(getStringValue(cell.getCell(2),0).toString()).append(t);//product_sort_code
				sb.append(getStringValue(cell.getCell(4),0).toString()).append(t);//product_brand
				
//				String id = getId(num++);
//				sb.append(id).append(t);
//				sb.append(sort + "-" + id).append(t);
				sb.append("\r\n");
				if(i % 500 == 0){
					FileWriter writer = new FileWriter("c:\\temp.txt", true);
					writer.write(sb.toString());
					writer.close();
					sb = new StringBuffer(1024);
					System.err.println("....");
				}
				//System.out.println(i);
			}
			FileWriter writer = new FileWriter("c:\\temp.txt", true);
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
	private static String getId(int num) {
		String str = String.valueOf(num);
		if(7 - str.length() == 1) return "0" + str;
		if(7 - str.length() == 2) return "00" + str;
		return str;
	}
}
