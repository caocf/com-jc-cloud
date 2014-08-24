/**  
 * Description: 字符编码过滤器 
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

package com.jc.base.coresafe.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;



public class EncodingFilter implements Filter {
    public EncodingFilter() {
    }

    public void destroy() {
    }

   
    /**
     * {方法的功能/动作描述}
    
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     * @exception   {说明在某情况下,将发生什么异常}
     * @Author       ChenZhao
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        request.setAttribute("requestself", (HttpServletRequest) request);
       HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (httpRequest.getMethod().equalsIgnoreCase("GET")) {
            HttpServletRequest encodingRequest = new EncodingtRequestWrapper(request);
            encodingRequest.setCharacterEncoding("UTF-8");
            chain.doFilter(encodingRequest, response);
            return;
        }
        chain.doFilter(request, response);
        return;
    }

   
    /**
     * {方法的功能/动作描述}
    
     * @param fConfig
     * @throws ServletException
     * @exception   {说明在某情况下,将发生什么异常}
     * @Author       ChenZhao
     */
    public void init(FilterConfig fConfig) throws ServletException {

    }

}
