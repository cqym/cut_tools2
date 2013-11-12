package com.tl.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestLog {
  public static void main(String[] args) {
    ApplicationContext atcx = new ClassPathXmlApplicationContext("applicationContext.xml");

    System.out.println(atcx.getBean("dataSource"));
  }
}
