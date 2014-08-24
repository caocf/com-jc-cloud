/**  
 * Description: <类功能描述-必填>配置文件工具类 
 * Copyright:   Copyright (c)2012  
 * Company:     ChunYu 
 * @author:     ChenZhao  
 * @version:    1.0  
 * Create at:   2012-12-21 下午4:22:51  
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2012-12-21   ChenZhao      1.0       如果修改了;必填  
 */
package com.jc.tools;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

public class ConfigurationUtils {
	/**
     * 
     */
	private static final Logger logger = Logger
			.getLogger(ConfigurationUtils.class);
	/**
     * 
     */
	public static boolean I18N_ENABLE = false;
	/**
     * 
     */
	private static Configuration config;

	/**
	 * 
	 * 初始化<br>
	 * 加载系统配置信息
	 * 
	 * @param propertiesFile
	 * @throws ConfigurationException
	 * @see
	 */
	public static void init(String propertiesFile)
			throws ConfigurationException {
		config = new PropertiesConfiguration(propertiesFile);
	}

	// 加载系统配置信息
/*	static {
		try {
			config = new PropertiesConfiguration("/WEB-INF/classes/properties/systemConfig.properties");
			// I18N_ENABLE = getBoolean("i18n.international.enable");

		} catch (ConfigurationException e) {
			logger.error(e);
		}
	}*/

	/*
	 * protected static Properties p = new Properties();
	 *//** * 静态读入属性文件到Properties p变量中 */
	/*
	 * protected static void init(String propertyFileName) { InputStream in =
	 * null; try { in = ConfigurationUtils.class.getClassLoader()
	 * .getResourceAsStream(propertyFileName); if (in != null) p.load(in); }
	 * catch (IOException e) { logger.error("load " + propertyFileName +
	 * " into Constants error!"); } finally { if (in != null) { try {
	 * in.close(); } catch (IOException e) { logger.error("close " +
	 * propertyFileName + " error!"); } } } }
	 */

	/**
	 * 获取BigDecimal类型的数据<br>
	 * 
	 * 
	 * @param key
	 * @return
	 * @see
	 */
	public static BigDecimal getBigDecimal(String key) {
		return config.getBigDecimal(key);
	}

	/**
	 * 获取boolean类型的数据<br>
	 * 
	 * 
	 * @param key
	 * @return
	 * @see
	 */
	public static boolean getBoolean(String key) {
		return config.getBoolean(key, false);
	}

	/**
	 * 获取String类型的数据<br>
	 * 
	 * 
	 * @param key
	 * @return
	 * @see
	 */
	public static String getString(String key) {
		return config.getString(key);
	}

	/**
	 * 获取整型<br>
	 * 
	 * 
	 * @param key
	 * @return
	 * @see
	 */
	public static int getInt(String key) {
		return config.getInt(key);
	}

	/**
	 * 获取集合数据，允许在Properties文件中用相同的key配置 <br>
	 * 
	 * 
	 * @param key
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getList(String key) {
		List<String> list = (List<String>) config.getList(key);
		return list;
	}

	/**
	 * 根据key获取解密字符串<br>
	 * 
	 * 
	 * @param key
	 * @return
	 * @see
	 */
	public static String getDecryptionString(String key) {
		return null;

	}

	/**
	 * @author chenzhao
	 * @throws ConfigurationException 
	 * @description j读取和遍历properties文件
	 */

	public static Map<String, String> getPropertiesIO(String propertiesFile) throws ConfigurationException {
		Properties pp = new Properties();
		Map<String, String> propMap = new HashMap<String, String>();
		InputStream in = ConfigurationUtils.class.getResourceAsStream(propertiesFile);
		try {
			pp.load(in);
			Set<Object> keyset = pp.keySet();
			for (Object object : keyset) {
				String value = pp.getProperty(object.toString())
						.toString();
				propMap.put(object.toString(),value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return propMap;
	}

}
