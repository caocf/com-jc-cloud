package utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

public final class GetProperties {
    static Properties prop =null;
    static {
    	prop=new Properties();
        InputStream in = Object.class.getResourceAsStream("/conf/systemConfig.properties");   
        try {   
            prop.load(in);  
        } catch (IOException e) {   
            e.printStackTrace();   
        }   
    } 
	// 根据key读取value
	public static String readValue(String key) {
		return prop.getProperty(key).trim();
	}
	public static void main(String[] args) {
		System.out.println(prop.getProperty("host").trim());
	}
}
