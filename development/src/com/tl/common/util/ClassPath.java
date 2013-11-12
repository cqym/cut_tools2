package com.tl.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ClassPath {
	
	public static Map getEnv() {
        Map map = new HashMap();
        String OS = System.getProperty("os.name").toLowerCase();
        Process p = null;
        if(OS.indexOf("windows") > -1) {
            try {
                p = Runtime.getRuntime().exec("cmd /c set");
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while((line = br.readLine()) != null) {
                    String[] str = line.split("=");
                    map.put(str[0], str[1]);
                }
            } catch(IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return map;
    }
}
