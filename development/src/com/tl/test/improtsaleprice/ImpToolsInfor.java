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


public class ImpToolsInfor {
	static String filepath = "c:\\toolsDORMER.xls" ;
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
			for (int i = 2,len = sheet.getLastRowNum(); i <= len; i++) {
				cell = sheet.getRow(i);

				//sb.append(GenerateSerial.getUUID()).append(t);
				sb.append(getStringValue(cell.getCell(0),0).toString()).append(t);//`brand_code`
				sb.append(getStringValue(cell.getCell(1),0).toString()).append(t);//product_name
				sb.append(getStringValue(cell.getCell(3),0).toString()).append(t);//product_unit
				String sort = getStringValue(cell.getCell(4),0).toString();//product_sort_code
				if(sort == null)sort = "xxx";
				sb.append(sort).append(t);
				sb.append("DORMER").append(t);//product_brand
				sb.append("采购").append(t);//product_source
				sb.append("人民币").append(t);//currency_name
				sb.append("0").append(t);//stock_price
				sb.append(getStringValue(cell.getCell(8),0).toString()).append(t);//sale_price
				
//				String id = getId(num++);
//				sb.append(id).append(t);
//				sb.append(sort + "-" + id).append(t);
				sb.append("\r\n");
				if(i % 500 == 0){
					FileWriter writer = new FileWriter("c:\\toolsDORMER.txt", true);
					writer.write(sb.toString());
					writer.close();
					sb = new StringBuffer(1024);
					System.err.println("....");
				}
				//System.out.println(i);
			}
			FileWriter writer = new FileWriter("c:\\toolsDORMER.txt", true);
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
