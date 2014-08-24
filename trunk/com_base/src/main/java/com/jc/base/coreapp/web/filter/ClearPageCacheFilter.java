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
package com.jc.base.coreapp.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * 让浏览器不缓存资源<br>
 * 使用该过滤器可以让浏览器不缓存资源。在Struts2中该过滤器应该配置在核心控制器（FilterDispatcher）前面。使用该过滤器后，点击浏览器的“后退”按钮
 * 的结果与浏览器的实现方式有关，有的浏览器会退回前一个页面并刷新页面，有的浏览器会出现一个页面已过期的提示。<br>
 * 由于filter-mapping中的url-pattern不能很好的实现exclude功能，所以对于该过滤器定义了一个名为“excludesPatterns”的init-param，该参数的
 * 值的写法和url-pattern一样，多个的话，可以用逗号隔开。
 * 
 * @author chenzhao
 * @version 1.0 , 2012-12-17
 * @see

 */

public class ClearPageCacheFilter implements Filter {

    private String[] excludesPatterns = {};

    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {

    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
     * javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String servletPath = httpRequest.getServletPath();
            if (needFilter(servletPath)) {

                // 前面已经判断了是否是HTTP方式，所以该处不需要重复判断，直接转换即可
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setHeader("Pragma", "no-cache");
                httpResponse.setHeader("cache-Control", "no-cache");
                httpResponse.setHeader("Expires", "0");
            }
        }

        chain.doFilter(request, response);
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig config) throws ServletException {
        String excludes = config.getInitParameter("excludesPatterns");
        if (StringUtils.isNotBlank(excludes)) {
            String[] urls = excludes.split(",");
            for (int i = 0; i < urls.length; i++) {
                excludesPatterns[i] = excludesPatterns[i].replace("\r\n", "").trim();
            }
        }
    }

    private boolean needFilter(String servletPath) {
        for (String pattern : excludesPatterns) {
            if (matchPattern(pattern, servletPath)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 
     * <br>
     * 该方法用于判断servletPath是否符合指定的pattern.
     * 代码是完全从Tomcat中复制过来的，该段代码在Tomcat中被用来验证请求地址是否符合web.xml中定义的filter-mapping中url-pattern.
     * 
     * @param pattern 该参数的写法和web.xml中url-pattern的写法相同
     * @param servletPath
     * @return
     * @see

     */
    private boolean matchPattern(String pattern, String servletPath) {

        if (pattern == null)
            return (false);

        // Case 1 - Exact Match
        if (pattern.equals(servletPath))
            return (true);

        // Case 2 - Path Match ("/.../*")
        if (pattern.equals("/*"))
            return (true);
        if (pattern.endsWith("/*")) {
            if (pattern.regionMatches(0, servletPath, 0, pattern.length() - 2)) {
                if (servletPath.length() == (pattern.length() - 2)) {
                    return (true);
                } else if ('/' == servletPath.charAt(pattern.length() - 2)) {
                    return (true);
                }
            }
            return (false);
        }

        // Case 3 - Extension Match
        if (pattern.startsWith("*.")) {
            int slash = servletPath.lastIndexOf('/');
            int period = servletPath.lastIndexOf('.');
            if ((slash >= 0) && (period > slash) && (period != servletPath.length() - 1)
                    && ((servletPath.length() - period) == (pattern.length() - 1))) {
                return (pattern.regionMatches(2, servletPath, period + 1, pattern.length() - 2));
            }
        }

        // Case 4 - "Default" Match
        return (false); // NOTE - Not relevant for selecting filters

    }

}
