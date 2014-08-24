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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;

import com.jc.base.util.LogUtils;
import com.jc.tools.ConfigurationUtils;

public class ApplicationContextListener implements ApplicationListener {
    /*
     * <bean id="ApplicationEventListener" class="org.sunivo.listener.event.ServletContextListener" />
     */
    /**
     * 日志工具类
     */
    @Autowired
    private LogUtils logUtils;
    
    /**
     */
    private static final Logger log = Logger.getLogger(ConfigurationUtils.class);

    /**
     * {方法的功能/动作描述}
    
     * @param event
     * @exception   {说明在某情况下,将发生什么异常}
     * @Author       ChenZhao
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextClosedEvent) {
            //System.out.println(event.getClass().getSimpleName() + "容器关闭了...");
        } else if (event instanceof ContextStartedEvent) {
            //System.out.println(event.getClass().getSimpleName() + " 启动了...");
        } else if (event instanceof ContextRefreshedEvent) {
            //System.out.println(event.getClass().getSimpleName() + " 容器刷新了...");
        } else if (event instanceof ContextStoppedEvent) {
            // 发送邮件
            //System.out.println(event.getClass().getSimpleName() + "容器关闭了...");
        } else {
            //System.out.println("Context Other:" + event.getClass().getName());
        }
    }
}
