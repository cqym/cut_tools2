package com.tl.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.tl.resource.dao.pojo.TContractInfor;
import com.tl.resource.dao.pojo.TContractProductDetail;

public class ExcelUtilsTest {
  public static void main(String[] args) {
    TContractInfor c = new TContractInfor();
    c.setContractCode("a");
    ArrayList list = new ArrayList(3);
    list.add(new Object());
    list.add(new Object());
    list.add(new Object());
//    TContractProductDetail cpd = new TContractProductDetail();
//    cpd.setProductName("中文");
//    list.add(cpd);
//    TContractProductDetail cpd2 = new TContractProductDetail();
//    cpd2.setProductName("中文2");
//    list.add(cpd2);
//    TContractProductDetail cpd3 = new TContractProductDetail();
//    cpd3.setProductName("中文3");
//    list.add(cpd3);
//    TContractProductDetail cpd4 = new TContractProductDetail();
//    cpd4.setProductName("中文4");
//    list.add(cpd4);
//    TContractProductDetail cpd5 = new TContractProductDetail();
//    cpd5.setProductName("中文5");
//    list.add(cpd5);
    
    
    //    TContractProductDetail cpd6 = new TContractProductDetail();
    //    cpd6.setProductName("中文6");
    //    list.add(cpd6);
    //    TContractProductDetail cpd7 = new TContractProductDetail();
    //    cpd7.setProductName("中文7");
    //    list.add(cpd7);
    //    TContractProductDetail cpd8 = new TContractProductDetail();
    //    cpd8.setProductName("中文8");
    //    list.add(cpd8);
    //    TContractProductDetail cpd9 = new TContractProductDetail();
    //    cpd9.setProductName("中文9");
    //    list.add(cpd9);
    //    ExcelUtils.addValue("model", c);
    //    try {
    //      ExcelUtils.export("E:\\projects\\cut_tools3\\development\\WebRoot\\upload\\templete\\contract_templete.xls", new FileOutputStream("c:\\a.xls"));
//    } catch (FileNotFoundException e) {
    //      // TODO Auto-generated catch block
    //      e.printStackTrace();
    //    } catch (ExcelException e) {
    //      // TODO Auto-generated catch block
    //      e.printStackTrace();
    //    }
    XLSTransformer tf = new XLSTransformer();
    Map beanParams = new HashMap();
    beanParams.put("contractInfor", c);
    beanParams.put("contractDetail", list);
    beanParams.put("count", list.size());
    beanParams.put("sheetName", "abc");
    try {
      tf.transformXLS("E:\\contract_templete.xlsx", beanParams, "e:\\a.xlsx");

    } catch (ParsePropertyException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvalidFormatException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
