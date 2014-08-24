/**  
 * Description: <类功能描述-必填>监控方法执行健康状况 
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
package com.jc.base.coreapp.background.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import com.jc.base.util.DatetimeUtils;
import com.jc.base.util.LogUtils;



public class LogAdvice implements AfterReturningAdvice, MethodBeforeAdvice, MethodInterceptor, ThrowsAdvice {
    /**
     * 日志帮助工具包
     */
    @Autowired
    private LogUtils logUtils;
    /**
     * 
     */
    private static final Logger logger = Logger.getLogger(LogAdvice.class);

 
    /**
     * {方法的功能/动作描述}
    
     * @param method
     * @param args
     * @param target
     * @throws Throwable
     * @exception   {说明在某情况下,将发生什么异常}
     * @Author       ChenZhao
     */
    public void before(Method method, Object[] args, Object target) throws Throwable {
        List<String> lsLog = new ArrayList<String>();
        String requestDate = DatetimeUtils.getCurDate("yyyy-MM-dd_HH:mm:ss");
        StackTraceElement[] traces = new Throwable().getStackTrace();
        lsLog.add("*****[SystemLog-AOP<Before-StackTrace>][LogAdvice-" + method.getName() + "]*********");
        for (StackTraceElement element : traces) {
            //if (element.getClassName().indexOf("com.sunivo") > -1) {
                logUtils.info("Call From:" + element.getClassName() + "  Method Name:" + element.getMethodName()
                        + "   LineNumber:" + element.getLineNumber());
            //}
        }
        lsLog.add("LogAdvice Time=[" + requestDate + "]");
        lsLog.add("Method Description:" + method.getName());
        lsLog.add("Parameter Value:" + (Arrays.toString(args)));
        lsLog.add("******************************************************");
        String key = "LogAdvice" + "|Before" + "|" + method.getName();
        logUtils.info(key, lsLog);
    }


    /**
     * {方法的功能/动作描述}
    
     * @param returnValue
     * @param method
     * @param args
     * @param target
     * @throws Throwable
     * @exception   {说明在某情况下,将发生什么异常}
     * @Author       ChenZhao
     */
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
    }

 
    /**
     * {方法的功能/动作描述}
    
     * @param invocation
     * @return
     * @throws Throwable
     * @exception   {说明在某情况下,将发生什么异常}
     * @Author       ChenZhao
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        List<String> lsLog = new ArrayList<String>();
        String targetName = invocation.getThis().getClass().getName();
        String methodName = invocation.getMethod().getName();
        Object[] arguments = invocation.getArguments();
        Object result = new Object();
        // String requestDate = DatetimeUtils.getCurDate("yyyy-MM-dd_HH:mm:ss");
        long procTime = System.currentTimeMillis();
        try {
            synchronized (this) {
                result = invocation.proceed();
            }
        } finally {
            procTime = System.currentTimeMillis() - procTime;
            lsLog.add("*******[SystemLog-AOP<invoke>][LogAdvice]************");
            lsLog.add("TargetName:" + targetName);
            lsLog.add("MethodName:" + methodName);
            // logger.info("Result:" + result);
            lsLog.add("TargetName:" + (Arrays.toString(arguments)));
            lsLog.add("Time=[" + procTime + "ms]");
            lsLog.add("***********************************************");
            String key = "LogAdvice" + "|invoke" + "|" + methodName;
            logUtils.info(key, lsLog);

        }
        return result;
    }

    /**
     * 对未知异常的处理 <br>
     * 
     * 
     * @param method
     * @param args
     * @param target
     * @param ex
     * @throws Throwable
     * @see

     */
    public void afterThrowing(Method method, Object[] args, Object target, Exception ex) throws Throwable {
        List<String> lsLog = new ArrayList<String>();
        // String requestDate = DatetimeUtils.getCurDate("yyyy-MM-dd HH:mm:ss");
        StackTraceElement[] traces = new Throwable().getStackTrace();
        lsLog.add("*********[SystemLog-AOP<Throwing>][LogAdvice-" + method.getName() + "]*******************");
        for (StackTraceElement element : traces) {
            if (element.getClassName().indexOf("com.sunivo") > -1) {
                lsLog.add(element.getClassName() + element.getMethodName() + "   LineNumber:" + element.getLineNumber());
            }
        }
        lsLog.add("Error happened in class: " + target.getClass().getName());
        lsLog.add("Error happened in method: " + method.getName());
        for (int i = 0; i < args.length; i++) {
            lsLog.add("arg[" + i + "]: " + args[i]);
        }
        lsLog.add("Exception class: " + ex.getClass().getName());
        lsLog.add("Exception Desc:: " + ex.getMessage());
        lsLog.add("****************************************************");
        String key = "LogAdvice" + "|afterThrowing" + "|" + method.getName();
        logUtils.info(key, lsLog);
    }
}
