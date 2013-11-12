package com.tl.test.springPlan;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.quartz.JobExecutionContext; 
import org.springframework.scheduling.quartz.QuartzJobBean; 

import com.tl.common.util.ClassPath;
import com.tl.test.ControlFileCount;
public class DemoJob extends QuartzJobBean { 
private JobData jobData; 
/**默认备份路径**/  
private static final String _mysql_bin_path = "MySQL Server 5.1\\bin\\mysqldump";

protected void executeInternal(JobExecutionContext arg0) { 
	System.out.println(jobData.getData() + " 第一个已经被执行了！！"); 
	backup();
} 

private boolean backup() {
	boolean bool = false;
	String cmd = "";//mysql路径
	String dir = "";//执行备份的目录
	String username = null;//mysql的用户名
	String password = null;//mysql的密码
	String mysql = "";//mysql路径
	/**得到环境变量中tomact路径**/
	Map pathMap = ClassPath.getEnv();
	/**判断环境变量中tomact路径(CATALINA_HOME)是否存在,如果存在则将备份路径设置为%CATALINA_HOME%\webapps\ROOT\backup\**/

	String backup_path = pathMap.get("CATALINA_HOME").toString() + "\\webapps\\ROOT\\data_backup";
	/**mysql路径**/
//		String[] str = pathMap.get("CATALINA_HOME").toString().split("\\");
	mysql = ControlFileCount.getUpFolder(pathMap.get("CATALINA_HOME").toString())+_mysql_bin_path;
	/**数据库配置文件的路径**/
	String mysql_properties_path = pathMap.get("CATALINA_HOME").toString() + "\\webapps\\ROOT\\WEB-INF\\classes\\MysqlConfig.properties";
	if(ControlFileCount.loadProperties(mysql_properties_path)!=null)
	{
		Properties pro = ControlFileCount.loadProperties(mysql_properties_path);
		username = pro.getProperty("username");
		password = pro.getProperty("password");
	}
	if(!ControlFileCount.FolderExit(backup_path))
	{
		ControlFileCount.createFolder(backup_path);
	}
	dir = backup_path;
	/**开始向指定目录备份数据**/
	if (!dir.substring(dir.length() - 1).equals(File.separator)) {
	dir += File.separator;
	}
	dir += "bak_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".sql";
	cmd = mysql + " -u " + username + " --password=" + password + " webstats > " + dir;
	 try {
		Process p = Runtime.getRuntime().exec("cmd /c" + cmd);
		p.waitFor();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	/**数据备份结束**/
	/**删除超过七天的数据备份文件**/
	ControlFileCount.fileCount(backup_path);
	
	 return true;
 }


public JobData getJobData() { 
return jobData; 
} 
public void setJobData(JobData jobData) { 
this.jobData = jobData; 
} 
} 