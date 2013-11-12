/**
 * 
 */
package com.tl.test.quotation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tl.resource.business.quotation.projectquo.ProjectQuoService;
import com.tl.resource.dao.pojo.TQuotationProjectSortInfor;

/**
 * @author xtaia
 *
 */
public class WorkOrder {
	
	
	private ProjectQuoService projectQuoService;
	
	@Before
	public void textBegin(){
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml",
						"quotation/projectQuo/projectQuo-dao.xml", "quotation/projectQuo/projectQuo-business.xml",
						"quotation/generalQuo/generalQuo-dao.xml", "quotation/generalQuo/generalQuo-business.xml"
				});
	
		projectQuoService = (ProjectQuoService) context.getBean("ProjectQuoService");
		
		
		
	}
	
	@Test
	public void textWorklist(){
		
		Map<String, Object> parmMap = new HashMap<String, Object>();
		
		List<TQuotationProjectSortInfor> list = projectQuoService.getWorkOrderList(parmMap);
		
		String jsonStr = JSONArray.fromObject(list).toString();
		String resultStr = "{workOrderList : "  + jsonStr + "}";
		
		//System.out.println("*********"+resultStr+"********");
	}

}
