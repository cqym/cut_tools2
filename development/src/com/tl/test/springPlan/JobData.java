package com.tl.test.springPlan;

import java.text.SimpleDateFormat; 
import java.util.Date; 
public class JobData { 
public String getData() { 
SimpleDateFormat ddd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
return "Data from " + ddd.format(new Date()); 
} 
} 
