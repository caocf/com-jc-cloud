/**  
 * Description: <类功能描述-必填> 
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
package com.jc.base.coreservice.monitor.listener;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;

/**
 * Application辅助类<br>
 * 
 * @author chenzhao
 * @version 1.0, 2012-12-17
 * @see
 */
public class ApplicationHelper implements ApplicationContextAware {
	/**
	 * 定义日志类
	 */
	private static final Logger logger = Logger
			.getLogger(ApplicationHelper.class);

	/**
	 * Application辅助类实例
	 */
	private static ApplicationHelper instance = new ApplicationHelper();

	/**
	 * 根目录路径
	 */
	private String rootPath;

	/**
	 * Application的集合
	 */
	private Set apps = new HashSet();

	/**
	 * 构造器私有，不可在外部进行初始化实例
	 */
	private ApplicationHelper() {
	}

	/**
	 * 声明一个静态变量保存
	 */
	private static ApplicationContext applicationContext;

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param applicationContext
	 * @throws BeansException
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@SuppressWarnings("all")
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static String getMessage(String key) {
		return applicationContext.getMessage(key, null, Locale.getDefault());
	}

	/**
	 * 根据Bean名称获取Bean对象
	 * 
	 * @param name
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public static <T> T getObject(String beanName)
			throws BeanNotOfRequiredTypeException,
			NoSuchBeanDefinitionException, BeansException {
		return (T) applicationContext.getBean(beanName);
	}

	/**
	 * 功能描述: 清空Application的集合
	 * 
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public void removeAll() {
		apps.clear();
		apps = null;
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param context
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@SuppressWarnings("all")
	public void addApplicationContext(ApplicationContext context) {
		apps.add(context);
		if (context.getParent() != null) {
			// 递归，将context的所有上一级放入apps中
			this.addApplicationContext(context.getParent());
		}
		if (context instanceof WebApplicationContext) {
			this.setRootPath(((WebApplicationContext) context)
					.getServletContext().getRealPath("/"));

		}
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public String getRootPath() {
		if (rootPath != null)
			return rootPath;
		return "./webapp/";
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param rootPath
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public static ApplicationHelper getInstance() {
		return instance;
	}
}
