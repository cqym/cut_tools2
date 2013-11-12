package com.tl.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.StringTokenizer;

public class ControlFileCount {
	
	/**
	 * 根据指定路径判断文件夹是否存在
	 * @param filePath
	 * @return 存在返回true ，否则返回false
	 */
	public static boolean FolderExit(String filePath)
	{
		File file = new File(filePath);
		if (file.exists()) {
			return true;
		}
		else
			return false;
	}
	
	
	/**
	 * 根据指定路径判断文件夹下文件数量，如果文件数量大于七，则删除较早的文件
	 * @param filePath
	 * @return 如果该文件夹不存在或者不为文件夹则返回-1
	 */
	public static void  fileCount(String filePath)
	{
		File file = new File(filePath);
		if (file.exists()&&file.isDirectory()&&file.list().length>7) {
			for(int i=0;i<file.list().length-7;i++)
			{
				File myDelFile = new File(filePath+"\\"+file.list()[i]);
				if (myDelFile.exists()) {
					myDelFile.delete();
				} 
			}
		}
	}
	
	/**
	 * 根据指定路径创建文件夹
	 * @param folderPath
	 * @return
	 */
	public static boolean createFolder(String folderPath) {
		String txt = folderPath;
		try {
			java.io.File myFilePath = new java.io.File(txt);
			txt = folderPath;
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * 根据指定路径得到Properties
	 * @param filePath
	 * @return
	 */
	 public static Properties loadProperties(String filePath) {  
        Properties props = new Properties();  
        try {  
        	if(FolderExit(filePath))
        	{
	            InputStream in = new BufferedInputStream(new FileInputStream(filePath));  
	            props.load(in);  
	            in.close();  
	            return props;
        	}
        	else
        		return null;
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
	
	 	/**
		 * 多级目录创建
		 * 
		 * @param folderPath
		 *            准备要在本级目录下创建新目录的 目录路径 例如 c:myf
		 * @param paths
		 *            无限级目录参数，各级目录以单数线区 分 例如 a|b|c
		 * @return 返回创建文件后的路径 例如 c:myfa c
		 */
		public static boolean createFolders(String folderPath, String paths) {
			String txts = folderPath;
			try {
				String txt;
				txts = folderPath;
				StringTokenizer st = new StringTokenizer(paths, "|");
				for (int i = 0; st.hasMoreTokens(); i++) {
					txt = st.nextToken().trim();
					if (txts.lastIndexOf("/") != -1) {
						createFolder(txts + txt);
					} else {
						createFolder(txts + txt + "/");
					}
				}
			} catch (Exception e) {
				return false;
			}
			return true;
		}
		
		/**
		 * 返回上一级目录的路径
		 * @param folderPath
		 * @return
		 */
		public static String  getUpFolder(String folderPath)
		{
			 String[] str = folderPath.split("\\\\");
			 String newPath = "";
			 for(int i = 0 ;i<str.length-1;i++)
			 {
				 newPath += str[i]+"\\";
			 }
			return newPath;
		}
}
