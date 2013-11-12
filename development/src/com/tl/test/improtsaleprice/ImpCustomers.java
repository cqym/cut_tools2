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

public class ImpCustomers {
	static String filepath = "c:\\customers.xls" ;
	static String outFilePath = "c:\\suppliers.txt";
	public static void main(String[] args) {
		
		try {
			InputStream fs;
			HSSFWorkbook workbook;
			HSSFSheet sheet;
			HSSFRow cell;

			fs = new FileInputStream(filepath);
			workbook = new HSSFWorkbook(fs);
			sheet = workbook.getSheetAt(2);
			StringBuffer sb = new StringBuffer(1024);
			String t = "','";
			for (int i = 1,len = sheet.getLastRowNum(); i <= len; i++) {
				cell = sheet.getRow(i);
				sb.append("insert into t_suppliers_infor(id,supplier_code,supplier_name,supplier_short_name,fax_first,phone_first,contact_person_first,phone_sec,contact_person_sec,fax_sec,contract_address,com_adress,bank,account_number,brand_first,brand_sec,brand_third,brand_fourth,postcode,home_page,email) values('");
				sb.append(GenerateSerial.getUUID()).append(t);
				for (int j = 0; j < 20; j++) {
					sb.append(getStringValue(cell.getCell(j),0)).append(t);
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.deleteCharAt(sb.length() - 1);
				sb.append(");\n");
				if(i % 500 == 0){
					FileWriter writer = new FileWriter(outFilePath, true);
					writer.write(sb.toString());
					writer.close();
					sb = new StringBuffer(1024);
					System.err.println("....");
				}
				//System.out.println(i);
			}
			FileWriter writer = new FileWriter(outFilePath, true);
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
