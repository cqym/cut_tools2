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

import com.tl.common.util.GenerateSerial;

public class ImpPriceData {
	static String filepath = "D:\\DORMER.xls" ;
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
				sb.append("xx").append(t);
				sb.append("1").append(t);
				sb.append("DORMER").append(t);
				sb.append(cell.getCell(4).getNumericCellValue()).append(t);
				sb.append(cell.getCell(1).getStringCellValue()).append(t);
				sb.append("2009-10-12 11:41:21").append(t);
				sb.append("091023161113000fb76044fd721c5f43").append(t);
				sb.append("ffc").append(t);
				sb.append("2010-01-27").append(t);
				sb.append("root").append(t);
				sb.append("1").append(t);
				sb.append("\r\n");
				if(i % 500 == 0){
					FileWriter writer = new FileWriter("c:\\test.txt", true);
					writer.write(sb.toString());
					writer.close();
					sb = new StringBuffer(1024);
					System.err.println("....");
				}
				//System.out.println(i);
			}
			FileWriter writer = new FileWriter("c:\\test.txt", true);
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
	
	
}
