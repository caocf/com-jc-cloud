/**  
 * Description: 日志帮助工具包
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
package com.jc.base.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
@SuppressWarnings("all")
@Component("logUtils")
public class LogUtils {
	/** 分隔符 */
	private final static String STR_SEP = "|";
	/** 日志系统MAP集合 */
	private static Map<String, Logger> loggerMap = new HashMap<String, Logger>();
	private ApplicationContext applicationContext;

	@Autowired
	@Qualifier(value = "redis1")
	private RedisUtils redisUtils;
	//@Value("#{sysConfig['redis.isinlogfil']}")
	//private static Boolean redisIsinlogfile;// false代表不记录到硬盘文件，也不打印到控制台
	// true代表记录到硬盘文件和打印到控制台
	//@Value("#{sysConfig['redis.logindb']}")
	//private static Boolean redisLogindb = false;// //false代表不记录到redis
	// true代表记录到redis
/*	static {
		try {
			ConfigurationUtils.init("redis.properties");
			redisIsinlogfile = ConfigurationUtils
					.getBoolean("redis.isinlogfile");
			redisLogindb = ConfigurationUtils.getBoolean("redis.logindb");
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}*/

	/**
	 * 获取日志系统<br> 
	 * 〈功能详细描述〉
	 *
	 * @param logName 日志系统名
	 * @return 日志系统
	 * @see 
	 * @since [1.0]
	 */
	public Logger getLogger(String logName) {
		if (!loggerMap.containsKey(logName)) {
			loggerMap.put(logName, Logger.getLogger(logName));
		}
		return loggerMap.get(logName);
	}

	/**
	 * 获取日志系统
	 * 
	 * @param logClass
	 *            要记录日志的Class
	 * @return 日志系统
     * @see 
     * @since [1.0]
	 */
	public Logger getLogger(Class<?> logClass) {
		if (!loggerMap.containsKey(logClass.getName())) {
			loggerMap.put(logClass.getName(), Logger.getLogger(logClass));
		}
		return loggerMap.get(logClass.getName());
	}

	/**
	 * 记录日志<br> 
	 * 〈功能详细描述〉
	 *
	 * @param mssage
	 * @see 
	 * @since [1.0]
	 */
	public void warn(String mssage) {
		//if (redisIsinlogfile) {
			StackTraceElement[] stacks = new Exception().getStackTrace();
			getLogger(stacks[1].getClassName()).warn(
					formatMsg(stacks[1].getClassName(), stacks[1]
							.getMethodName(), mssage));
		//}
	}

	/**
	 * 〈一句话功能简述〉<br> 
	 * 〈功能详细描述〉
	 *
	 * @param key
	 * @param ls
	 * @see 
	 * @since [1.0]
	 */
	public void warn(String key, List<String> ls) {
			for (String str : ls) {
				warn(str);
			}
	}

	/**
	 * 记录日志<br> 
	 * 〈功能详细描述〉
	 *
	 * @param mssage
	 * @see 
	 * @since [1.0]
	 */
	public void fatal(String mssage) {
			StackTraceElement[] stacks = new Exception().getStackTrace();
			getLogger(stacks[1].getClassName()).fatal(
					formatMsg(stacks[1].getClassName(), stacks[1]
							.getMethodName(), mssage));
	}

	/**
	 * 〈一句话功能简述〉<br> 
	 * 〈功能详细描述〉
	 *
	 * @param key
	 * @param ls
	 * @see 
	 * @since [1.0]
	 */
	public void fatal(String key, List<String> ls) {
			for (String str : ls) {
				fatal(str);
			}
	}

	/**
	 * 记录日志<br> 
	 * 〈功能详细描述〉
	 *
	 * @param mssage
	 * @see 
	 * @since [1.0]
	 */
	public void info(String mssage) {
		StackTraceElement[] stacks = new Exception().getStackTrace();
			getLogger(stacks[1].getClassName()).info(
					formatMsg(stacks[1].getClassName(), stacks[1]
							.getMethodName(), mssage));
	}

	/**
	 * 〈一句话功能简述〉<br> 
	 * 〈功能详细描述〉
	 *
	 * @param key
	 * @param ls
	 * @see 
	 * @since [1.0]
	 */
	public void info(String key, List<String> ls) {
			for (String str : ls) {
				info(str);
			}
	}

	/**
	 * 记录日志<br> 
	 * 〈功能详细描述〉
	 *
	 * @param mssage
	 * @see 
	 * @since [1.0]
	 */
	public void error(String mssage) {
		StackTraceElement[] stacks = new Exception().getStackTrace();
			getLogger(stacks[1].getClassName()).error(
					formatMsg(stacks[1].getClassName(), stacks[1]
							.getMethodName(), mssage));
	}

	/**
	 * 〈一句话功能简述〉<br> 
	 * 〈功能详细描述〉
	 *
	 * @param key
	 * @param ls
	 * @see 
	 * @since [1.0]
	 */
	public void error(String key, List<String> ls) {
			for (String str : ls) {
				info(str);
			}
	}

	/**
	 * 记录日志<br> 
	 * 〈功能详细描述〉
	 *
	 * @param mssage
	 * @see 
	 * @since [1.0]
	 */
	public void debug(String mssage) {

		StackTraceElement[] stacks = new Exception().getStackTrace();
			getLogger(stacks[1].getClassName()).debug(
					formatMsg(stacks[1].getClassName(), stacks[1]
							.getMethodName(), mssage));
		
	}

	/**
	 * 记录日志<br> 
	 * 〈功能详细描述〉
	 *
	 * @param key
	 * @param ls
	 * @see 
	 * @since [1.0]
	 */
	public void debug(String key, List<String> ls) {
			for (String str : ls) {
				debug(str);
			}
		
	}

	/**
	 * 格式化日志信息
	 * 
	 * @param cls
	 *            报告日志的类
	 * @param methodName
	 *            报告日志的方法
	 * @param mssage
	 *            消息
	 * @return 格式化后的日志信息
     * @see 
     * @since [1.0]
	 */
	public String formatMsg(String clsName, String methodName, String mssage) {
		StringBuilder strBuilder = new StringBuilder();
		SimpleDateFormat dataFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		strBuilder.append(dataFormat.format(new Date()));
		strBuilder.append(STR_SEP);
		strBuilder.append(clsName);
		strBuilder.append(STR_SEP);
		strBuilder.append(methodName);
		strBuilder.append(STR_SEP);
		strBuilder.append(mssage);
		return strBuilder.toString();
	}

	/**
	 * 格式化日志信息
	 * 
	 * @param cls
	 *            报告日志的类
	 * @param methodName
	 *            报告日志的方法
	 * @param mssage
	 *            消息
	 * @return 格式化后的日志信息
     * @see 
     * @since [1.0]
	 */
	public String formatMsg(Class<?> cls, String methodName, String mssage) {
		return formatMsg(cls.getName(), methodName, mssage);
	}

}
