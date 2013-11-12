package com.tl.test.improtsaleprice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.tl.common.util.GenerateSerial;

public class ImpRebateData {
	static String filepath = "c:\\rebate.xls" ;
	public static void main(String[] args) {
		
		try {
			InputStream fs;
			HSSFWorkbook workbook;
			HSSFSheet sheet;
			HSSFRow cell;
			String[] khbhs = {"xxx","W0","W1","W2","W3","W4","W5","W6","W7","W8","W9","W10","O"};
			fs = new FileInputStream(filepath);
			workbook = new HSSFWorkbook(fs);
			sheet = workbook.getSheetAt(1);
			StringBuffer sb = new StringBuffer(1024);
			String t = "\t";
			for (int i = 1,len = sheet.getLastRowNum(); i <= len; i++) {
				cell = sheet.getRow(i);
				String proBrand = cell.getCell(1).getStringCellValue();//品牌
				String proGroup = cell.getCell(2).getStringCellValue();//产品组别
				for (int j = 0; j < khbhs.length; j++) {
					String khbh = khbhs[j];
					if(cell.getCell(j + 3) != null){
						sb.append(GenerateSerial.getUUID()).append(t);
						sb.append(proBrand).append(t);
						sb.append(proGroup).append(t);
						sb.append(khbh).append(t);
						sb.append(cell.getCell(j + 3).getNumericCellValue()).append(t);
						sb.append("\r\n");
					}
				}
				
				if(i % 500 == 0){
					FileWriter writer = new FileWriter("c:\\test.txt", true);
					writer.write(sb.toString());
					writer.close();
					sb = new StringBuffer(1024);
					System.err.println("....");
				}
				//System.out.println(i);
			}
			FileWriter writer = new FileWriter("c:\\rebate.txt", true);
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
