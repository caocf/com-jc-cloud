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

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContextException;

public class InitialCacheListener implements ServletContextListener {
    /**
     */
    public static final String CONFIG_Cache_PARAM = "isCreateCache";
    
    /**
     */
    public ServletContext servletContext;

    /**
     * {方法的功能/动作描述}
    
     * @param arg0
     * @exception   {说明在某情况下,将发生什么异常}
     * @Author       ChenZhao
     */
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        servletContext.log("Destroyed project cache ServletContext");
    }

  
    /**
     * {方法的功能/动作描述}
    
     * @param event
     * @exception   {说明在某情况下,将发生什么异常}
     * @Author       ChenZhao
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            servletContext = event.getServletContext();
            servletContext.log("Initializing project cache ServletContext");
            String initParam = servletContext.getInitParameter(CONFIG_Cache_PARAM);
            if (initParam != null) {
                servletContext.log("Get the initparam value is " + initParam);
            }
        } catch (Exception ex) {
            throw new ApplicationContextException(ex.getMessage(), ex);
        }
    }

}
