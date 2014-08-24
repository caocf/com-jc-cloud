/**  
 * Description: <类功能描述-必填> 过滤SQL注入字符、跨站脚本攻击字符
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
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;



public class XSSFilter implements Filter {

	/**
	 * 不需要过滤的URL
	 */
	private Set<String> unFilterUrls = null; 

	/**
	 * Default constructor.
	 */
	public XSSFilter() {
	}

	
	/**
	 * {方法的功能/动作描述}
	
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
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
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
	    request.setAttribute("requestself", (HttpServletRequest)request);
	    if(inUnfilterUrls((HttpServletRequest)request)){
	    	chain.doFilter(request, response);
	    }else{
	    	chain.doFilter(new XssHttpServletRequestWrapper(request), response);
	    }
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
		// Begin 读取不需要过滤的URL参数 陈钊 2011-11-03
		String unfilterUrls = fConfig.getInitParameter("unFilterUrls");
		if (unfilterUrls != null) {
			unfilterUrls = unfilterUrls.trim();
			if (unfilterUrls.length() > 0) {
				if (unfilterUrls.indexOf("UN:") != -1) {
					unFilterUrls = new HashSet<String>();
					String urls[] = unfilterUrls.split("UN:");
					for (int i = 0; i < urls.length; i++) {
						String url = urls[i].trim();
						if (url.length() > 0) {
							unFilterUrls.add(url);
						}
					}
				}
			}
		}
		// End
	}

	/**
	 * 功能描述: 判断请求的URI是否在无需过滤列表的白名单中<br>
	 *
	 * @param request
	 * @return
	 * @see 
	 */
	private boolean inUnfilterUrls(HttpServletRequest request) {
		if(unFilterUrls==null||unFilterUrls.size()<1){
            return false;
        }
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();

		String passed = uri.replaceFirst(contextPath, "");

		if (unFilterUrls != null) {
			if (unFilterUrls.contains(passed)) {
				return true;
			}
		}
		return false;

	}
}
