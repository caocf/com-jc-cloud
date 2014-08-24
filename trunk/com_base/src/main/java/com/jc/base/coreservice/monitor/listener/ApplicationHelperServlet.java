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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;

public class ApplicationHelperServlet extends HttpServlet {
    /**
     */
    private static final long serialVersionUID = 1822061516548271940L;
    /**
     */
    private ContextLoader contextLoader;


    /**
     * {方法的功能/动作描述}
    
     * @throws ServletException
     * @exception   {说明在某情况下,将发生什么异常}
     * @Author       ChenZhao
     */
    public void init() throws ServletException {
        ApplicationHelper.getInstance().setRootPath(this.getServletContext().getRealPath("/"));
        this.contextLoader = createContextLoader();
        this.contextLoader.initWebApplicationContext(getServletContext());
    }

    
    /**
     * {方法的功能/动作描述}
    
     * @return
     * @exception   {说明在某情况下,将发生什么异常}
     * @Author       ChenZhao
     */
    protected ContextLoader createContextLoader() {
        return new ContextLoader();
    }


    /**
     * {方法的功能/动作描述}
    
     * @return
     * @exception   {说明在某情况下,将发生什么异常}
     * @Author       ChenZhao
     */
    public ContextLoader getContextLoader() {
        return contextLoader;
    }

 
    /**
     * {方法的功能/动作描述}
    
     * @exception   {说明在某情况下,将发生什么异常}
     * @Author       ChenZhao
     */
    public void destroy() {
        ApplicationHelper.getInstance().removeAll();
        if (this.contextLoader != null) {
            this.contextLoader.closeWebApplicationContext(getServletContext());
        }
    }

  
    /**
     * {方法的功能/动作描述}
    
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @exception   {说明在某情况下,将发生什么异常}
     * @Author       ChenZhao
     */
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        getServletContext().log(
                "Attempt to call service method on ContextLoaderServlet as [" + request.getRequestURI()
                        + "] was ignored");

        response.sendError(400);
    }

   
    /**
     * {方法的功能/动作描述}
    
     * @return
     * @exception   {说明在某情况下,将发生什么异常}
     * @Author       ChenZhao
     */
    public String getServletInfo() {
        return getServletInfo();
    }
}
