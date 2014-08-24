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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.jc.tools.ConfigurationUtils;


public class EncodingtRequestWrapper extends ServletRequestWrapper implements
		HttpServletRequest, ServletRequest {
	private static final Logger logger = Logger
			.getLogger(EncodingtRequestWrapper.class);

	private HttpServletRequest request = null;

	/**
	 * 构造函数
	 * 
	 * @param request
	 */
	public EncodingtRequestWrapper(ServletRequest request) {
		super(request);
		this.request = (HttpServletRequest) request;
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public String getAuthType() {

		return request.getAuthType();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public String getContextPath() {

		return request.getContextPath();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public Cookie[] getCookies() {

		return request.getCookies();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param arg0
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public long getDateHeader(String arg0) {

		return request.getDateHeader(arg0);
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param name
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public String getHeader(String name) {

		return request.getHeader(name);

	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Enumeration getHeaderNames() {

		return request.getHeaderNames();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param arg0
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Enumeration getHeaders(String arg0) {

		return request.getHeaders(arg0);
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param arg0
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public int getIntHeader(String arg0) {

		return request.getIntHeader(arg0);
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public String getMethod() {

		return request.getMethod();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public String getPathInfo() {

		return request.getPathInfo();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public String getPathTranslated() {

		return request.getPathTranslated();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public String getQueryString() {

		return request.getQueryString();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public String getRemoteUser() {

		return request.getRemoteUser();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public String getRequestURI() {

		return request.getRequestURI();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public StringBuffer getRequestURL() {

		return request.getRequestURL();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public String getRequestedSessionId() {

		return request.getRequestedSessionId();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public String getServletPath() {

		return request.getServletPath();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public HttpSession getSession() {

		return request.getSession();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param arg0
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public HttpSession getSession(boolean arg0) {

		return request.getSession(arg0);
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public Principal getUserPrincipal() {

		return request.getUserPrincipal();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public boolean isRequestedSessionIdFromCookie() {

		return request.isRequestedSessionIdFromCookie();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public boolean isRequestedSessionIdFromURL() {

		return request.isRequestedSessionIdFromURL();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@SuppressWarnings("deprecation")
	@Override
	public boolean isRequestedSessionIdFromUrl() {

		return request.isRequestedSessionIdFromUrl();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public boolean isRequestedSessionIdValid() {

		return request.isRequestedSessionIdValid();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param arg0
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public boolean isUserInRole(String arg0) {

		return request.isUserInRole(arg0);
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param arg0
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public Object getAttribute(String arg0) {

		return request.getAttribute(arg0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Enumeration getAttributeNames() {

		return request.getAttributeNames();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public String getCharacterEncoding() {

		return request.getCharacterEncoding();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public int getContentLength() {

		return request.getContentLength();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public ServletRequest getRequest() {
		return request;
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param request
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public void setRequest(ServletRequest request) {
		this.request = (HttpServletRequest) request;
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public String getContentType() {

		return request.getContentType();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @throws IOException
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public ServletInputStream getInputStream() throws IOException {

		return request.getInputStream();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public String getLocalAddr() {

		return request.getLocalAddr();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public String getLocalName() {

		return request.getLocalName();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public int getLocalPort() {

		return request.getLocalPort();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public Locale getLocale() {
		return request.getLocale();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Enumeration getLocales() {

		return request.getLocales();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param parameter
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public String getParameter(String parameter) {
		String[] vals = getParameterMap().get(parameter);
		if (vals != null && vals.length > 0)
			return vals[0];
		else
			return null;

	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String[]> getParameterMap() {

		if (!ConfigurationUtils.getBoolean("need.encoding")) {
			return request.getParameterMap();
		} else {
			Map<String, String[]> orig = request.getParameterMap();

			Map<String, String[]> encMap = new HashMap<String, String[]>();
			if (orig != null) {
				for (String key : (Set<String>) orig.keySet()) {
					String[] origVals = orig.get(key);
					String[] encodStrings = new String[origVals.length];
					for (int i = 0; i < origVals.length; i++) {
						try {
							encodStrings[i] = new String(
									origVals[i].getBytes("ISO8859-1"), "UTF-8");
						} catch (UnsupportedEncodingException e) {
							logger.error(e);
						}
					}
					encMap.put(key, encodStrings);
				}
			}
			return encMap;
		}

	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Enumeration getParameterNames() {

		return request.getParameterNames();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param parameter
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public String[] getParameterValues(String parameter) {

		return getParameterMap().get(parameter);
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public String getProtocol() {

		return request.getProtocol();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @throws IOException
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public BufferedReader getReader() throws IOException {

		return request.getReader();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param arg0
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@SuppressWarnings("deprecation")
	@Override
	public String getRealPath(String arg0) {

		return request.getRealPath(arg0);
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public String getRemoteAddr() {

		return request.getRemoteAddr();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public String getRemoteHost() {

		return request.getRemoteHost();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public int getRemotePort() {

		return request.getRemotePort();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param arg0
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public RequestDispatcher getRequestDispatcher(String arg0) {

		return request.getRequestDispatcher(arg0);
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public String getScheme() {

		return request.getScheme();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public String getServerName() {

		return request.getServerName();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletRequestWrapper#getServerPort()
	 */
	@Override
	public int getServerPort() {

		return request.getServerPort();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @return
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public boolean isSecure() {

		return request.isSecure();
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param arg0
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public void removeAttribute(String arg0) {
		request.removeAttribute(arg0);

	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param arg0
	 * @param arg1
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public void setAttribute(String arg0, Object arg1) {

		request.setAttribute(arg0, arg1);
	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param arg0
	 * @throws UnsupportedEncodingException
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	@Override
	public void setCharacterEncoding(String arg0)
			throws UnsupportedEncodingException {

		request.setCharacterEncoding(arg0);
	}


}
