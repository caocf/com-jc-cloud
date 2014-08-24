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
package com.jc.base.coresafe.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.jc.tools.ConfigurationUtils;

public class HttpCacheFilter implements Filter {

    /**
     * 定义日志类
     */
    private static final Logger logger = Logger.getLogger(HttpCacheFilter.class);

    /**
     * 设置是否缓存开关，通过配置文件systemConfig.properties项http.global.cache.enable=true|| false 来控制
     */
    private boolean enableCache = false;

    /**
     * Default constructor.
     */
    public HttpCacheFilter() {
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
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        // 如果不允许缓存
        if (!isEnableCache()) {
            ((HttpServletResponse) response).setHeader("Cache-Control", "no-cache");
            ((HttpServletResponse) response).setHeader("Pragma", "no-cache");
            ((HttpServletResponse) response).setDateHeader("Expires", -1);
        }
        chain.doFilter(request, response);
    }

   
    /**
     * {方法的功能/动作描述}
    
     * @param fConfig
     * @throws ServletException
     * @exception   {说明在某情况下,将发生什么异常}
     * @Author       ChenZhao
     */
    public void init(FilterConfig fConfig) throws ServletException {

        try {
            setEnableCache(ConfigurationUtils.getBoolean("http.global.cache.enable"));
        } catch (Exception e) {
            logger.error(e);
            enableCache = false;
        }
    }

    /**
     * 功能描述: 查看使用缓存状态 <br>
     *
     * @return
     * @see 

     */
    private boolean isEnableCache() {
        return enableCache;
    }

    private void setEnableCache(boolean enableCache) {
        this.enableCache = enableCache;
    }

}
