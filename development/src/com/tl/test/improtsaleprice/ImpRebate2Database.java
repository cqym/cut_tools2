package com.tl.test.improtsaleprice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ImpRebate2Database {
	private static ApplicationContext appContext = null;
	
	public static Map contextMap = new HashMap();
	
	static{
		appContext = new ClassPathXmlApplicationContext(
				(new StringBuilder("classpath*:")).append("applicationContext.xml").toString());
		contextMap.put("root", appContext);
	}
	private ImpRebate2Database() {
	}

	public static Object getBean(String configId, String daoName)
    {
        ApplicationContext context = (ApplicationContext)contextMap.get(configId);
        if(context == null)
        {
//            BeanDefinitions beanDefinitions = (BeanDefinitions)appContext.getBean("beanDefinitions");
//            String value = (String)beanDefinitions.getBeanDefinitions().get(configId);
//            String beanDefinitionsArray[] = value.split(",");
//            for(int i = 0; i < beanDefinitionsArray.length; i++)
//                beanDefinitionsArray[i] = (new StringBuilder("classpath*:")).append(beanDefinitionsArray[i]).toString();
//
//            context = new ClassPathXmlApplicationContext(beanDefinitionsArray, appContext);
//            contextMap.put(configId, context);
        }
        return context.getBean(daoName);
    }
	public static void main(String[] args) {
		
//		IRolesService bean = (IRolesService) SpringBeanFactory.getBean("adminService", "rolesServiceImp");
//		IModuleActionsService mabean = (IModuleActionsService) SpringBeanFactory.getBean("adminService", "moduleActionsServiceImp");
	}
}
