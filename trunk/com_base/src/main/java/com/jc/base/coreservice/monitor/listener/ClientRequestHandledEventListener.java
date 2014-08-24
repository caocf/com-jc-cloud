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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.ServletRequestHandledEvent;
import com.jc.base.util.DatetimeUtils;
import com.jc.base.util.LogUtils;

/**
 * 客户端请求事件<br> 
 *
 * @author chenzhao
 * @version 1.0, 2012-12-17
 * @see 

 */
/**
 * @author chenzhao
 *
 */
/**
 * @author chenzhao
 *
 */
public class ClientRequestHandledEventListener implements ApplicationListener<ServletRequestHandledEvent>,
        ApplicationContextAware, ServletContextAware {
    
    /**
     */
    private String noCheckFile = "";

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param noCheckFile
     * @see 

     */
    public void setNoCheckFile(String noCheckFile) {
        this.noCheckFile = noCheckFile;
    }

    /**
     */
    ApplicationContext applicationContext;
    
    /**
     */
    ServletContext servletContext;
    
    /**
     * 日志工具类
     */
    @Autowired
    private LogUtils logUtils;
    
    private AtomicLong count=new AtomicLong(0);

  
    /**
     * {方法的功能/动作描述}
    
     * @param event
     * @exception   {说明在某情况下,将发生什么异常}
     * @Author       ChenZhao
     */
    @SuppressWarnings("all")
    public void onApplicationEvent(ServletRequestHandledEvent event) {
        /*
         * if(event.wasFailure()) {
         */
    	
    	System.out.println(count.incrementAndGet());
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest();
            String[] noCheck = noCheckFile.split(",");
            for (String str : noCheck) {
                if (request.getRequestURL().indexOf(str) > -1) {
                    return;
                }
            }
            List<String> lsLog = new ArrayList<String>();
            String requestDate = DatetimeUtils.getCurDate("yyyy-MM-dd_HH:mm:ss");

            lsLog.add("*********[System Log-Request][ClientRequestHandledEvent]************");
            lsLog.add("Request Time=[" + requestDate + "]");
            lsLog.add("The Previous Url =[" + request.getHeader("Referer") + "]");
            lsLog.add("Current Url=[" + request.getRequestURL() + "]");
            lsLog.add("UserName=[" + request.getSession().getAttribute("sessionKey") + "]");
            lsLog.add("SessionID=[" + event.getSessionId() + "]");
            lsLog.add("ClientAddress=[" + event.getClientAddress() + "]");
            lsLog.add("GetParameterMap as below:");
            Map map = request.getParameterMap();
            for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
                Map.Entry element = (Map.Entry) iter.next();
                Object strKey = element.getKey();
                Object strObj = request.getParameter(strKey.toString());
                lsLog.add("Parameter Name:" + strKey + "Parameter Value：" + strObj.toString());
            }
            lsLog.add("Method=[" + event.getMethod() + "]");
            lsLog.add("Servlet=[" + event.getServletName() + "]");
            lsLog.add("Total Time=[" + event.getProcessingTimeMillis() + "ms]; ");
            lsLog.add("********************End***************************************");

            String key = "ClientRequestHandledEvent|" + request.getRequestURL();
//            logUtils.info(key, lsLog);
        } catch (Exception ex) {

        }
        /*
         * } else { }
         */

    }

  
    /**
     * {方法的功能/动作描述}
    
     * @param applicationContext
     * @throws BeansException
     * @exception   {说明在某情况下,将发生什么异常}
     * @Author       ChenZhao
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    /**
     * {方法的功能/动作描述}
    
     * @param servletContext
     * @exception   {说明在某情况下,将发生什么异常}
     * @Author       ChenZhao
     */
    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;

    }

}
