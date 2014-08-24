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
package com.jc.base.coreapp.web.listener;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.jc.base.util.JDBCUtils;
import com.jc.base.util.RedisUtils;
import com.jc.tools.FTPFileUtils;

/**
 * 
 * 测试FTP服务器,MemoryCache服务器，<br>
 * 
 * 
 * @author chenzhao
 * @version 1.0 , 2012-12-17
 * @see
 */
public class RelationServiceListener implements ServletContextListener,
		ApplicationContextAware {
	private static final Logger logger = Logger
			.getLogger(RelationServiceListener.class);
	private ApplicationContext applicationContext;

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent context) {
		// CacheService.flushAllCache();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		if (!testRedis(event)) {
			System.exit(1);
		}

	/*	if (!testFTP()) {
			System.out.println("FTP服务器连接失败~...");
			System.exit(1);
		}*/

		try {
			if (!testDB(event)) {
				System.out.println("数据库连接失败~...");
				System.exit(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 测试连接数据库是否正常
	 * 
	 * @return
	 * @author
	 * @throws Exception
	 * @create date 2012-12-15下午01:11:55
	 */
	private boolean testDB(ServletContextEvent sce) {
		boolean flag = false;
		try {

			JDBCUtils jDBCUtils = (JDBCUtils) WebApplicationContextUtils
					.getWebApplicationContext(sce.getServletContext()).getBean(
							"jDBCUtils");
			if (jDBCUtils.getConnection() != null) {
				flag = true;
			}
			return flag;
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
			return flag;
		}

	}

	/**
	 * 测试FTP服务器是否正常
	 * 
	 * @return
	 * @author
	 * @create date 2012-12-15下午01:11:55
	 */
	private boolean testFTP() {
		/*boolean flag = false;
		ByteArrayInputStream inputStream = new ByteArrayInputStream(
				"java".getBytes());
		try {
			FTPFileItem ftpFileItem = FTPClientUtils.saveToFtp(inputStream,
					"a.txt");
			if (ftpFileItem.isSuccessed()) {
				flag = true;
			}
		} catch (IOException e) {
			logger.error("ftp保存数据出现异常", e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				logger.error("关闭输入流出现异常", e);
			}
		}
		return flag;*/
		return FTPFileUtils.connectServer();
	}

	/**
	 * <功能详细描述>:测试Redis是否正常
	 * 
	 * @param
	 * @return return_type
	 * @exception /throws
	 * @author chenzhao
	 * @date 2012-12-10 下午01:22:55
	 */

	private boolean testRedis(ServletContextEvent event) {
		ApplicationContext applicationContext = WebApplicationContextUtils
				.getWebApplicationContext(event.getServletContext());
		try {
			RedisUtils redisUtils = (RedisUtils) applicationContext
					.getBean("redis1");

			if (!redisUtils.isconnect()) {
				event.getServletContext().log(
						"Redis数据库初始化失败;请检查Redis服务是否启动;容器终止启动!");
				return false;
			} else {
				event.getServletContext().log("Redis数据库测试连接成功...");
				redisUtils.disconnect();
				return true;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			event.getServletContext().log(
					"Redis数据库初始化失败;请检查Redis服务是否启动;容器终止启动!" + ex.getMessage());
			return false;
		}
	}

	/**
	 * 
	 * @param
	 * @param
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
}
