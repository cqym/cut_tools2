package com.tl.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tl.resource.business.contract.ContractEditService;

public class XjFrameworkTest {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml",
						

						"manage/manage-dao.xml",
						"manage/manage-business.xml",
						"manage/manage-web-spring.xml",

						"protoolsinfo/protools-dao.xml",
						"protoolsinfo/protools-business.xml",
						"protoolsinfo/protools-web-spring.xml",

						"audit/audit-dao.xml",
						"audit/audit-business.xml",
						"audit/audit-web-spring.xml",

						"quotation/generalQuo/generalQuo-dao.xml",
						"quotation/generalQuo/generalQuo-business.xml",
						"quotation/generalQuo/generalQuo-web-spring.xml",

						"quotation/projectQuo/projectQuo-dao.xml",
						"quotation/projectQuo/projectQuo-business.xml",
						"quotation/projectQuo/projectQuo-web-spring.xml",

						"reserveOrder/reserveOrder-dao.xml",
						"reserveOrder/reserveOrder-business.xml",
						"reserveOrder/reserveOrder-web-spring.xml",

						"contract/contract-dao.xml",
						"contract/contract-business.xml",
						"contract/contract-web-spring.xml"});
		ContractEditService ces = (ContractEditService) context.getBean("contractEditService");
		ces.consultGeneralQuo(new String[]{"0910290932310150a4f0b20ae50a4fbd"});
		//ces.getContractInforById("");
	}
	//quotation.generalQuo
	//IAuditService auditService = (IAuditService) context.getBean("auditService");
	//UserDto userDto = new UserDto();
	//userDto.setId("0910192119454067be46793ec0c8fd18");
	//System.out.println(JSONArray.fromObject(auditService.findWaitAuditBusinessInfor(new HashMap(), userDto, "001", "1", 0, 10)));
//	IAuditBusinessObject abo = (IAuditBusinessObject) context.getBean("GeneralQuoAudit");
//	auditService.setAuditBusinessObject(abo);
//	UserDto userDto = new UserDto();
//	userDto.setId("091019211106031490b6d04b9a2182de");
//	userDto.setDepartId("003");
//    userDto.setDepartName("物流部");
//	userDto.setUserName("1");
//	userDto.setTrueName("1");
//	System.out.println(auditService.submitBusiness(abo.getBusinessType(), "111", userDto ));
	//auditService.executeAudit("111", "001", "1", userDto, "过", 0);
	//auditService.findAuditHistoryInfor("111");
}
