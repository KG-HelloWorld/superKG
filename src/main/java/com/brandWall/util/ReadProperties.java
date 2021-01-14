package com.brandWall.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {
	
	/**
	 * 获取文件值
	 *@author wsy
	 *@time 2016年6月29日下午5:22:34
	 *@param file
	 *@param key
	 *@return
	 */
	public static String getValue(String filePath,String key){
		String value = "";
		Properties prop = new Properties();
		try {
        InputStream in = new BufferedInputStream(new FileInputStream(filePath));
        prop.load(in);
        value = prop.getProperty(key);
        in.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return value;
	}
}
