package org.jackychen.toolkits.session.servlet;

import java.security.Principal;
import java.util.Enumeration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.*;
import org.apache.log4j.Logger;
import org.jackychen.toolkits.session.SessionManager;
//DistributedSessionFilter�а�װ����
//HttpServletRequest�е�getSession��ʵ�֣���װ���ʹ�������getSession��
public class ContainerRequestWrapper extends ServletRequestWrapper implements HttpServletRequest {
	protected Logger log;
	private SessionManager sessionManager;
	private HttpSession session;
	public ContainerRequestWrapper(ServletRequest request,SessionManager sessionManager) {
		super(request);
		log = Logger.getLogger(getClass());
		this.sessionManager = sessionManager;

	}
	private HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) super.getRequest();
	}
	/*retrieveFlashMaps(SessionFlashMapManager.java调用)
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServletRequest#getSession(boolean)
	 */
	public HttpSession getSession(boolean create) {
		HttpServletRequest request = (HttpServletRequest) getRequest();
		if (sessionManager == null && create)
			throw new IllegalStateException("No SessionHandler or SessionManager");
		if (session != null && sessionManager != null)
			return session;
		session = null;
		String id = sessionManager.getRequestSessionId(request);//get the cookieid by client cookie in browse 
		//DefaultSessionManger,getRequestSessionId
		log.info((new StringBuilder()).append("Get Session ID:[").append(id).append("]").toString());
		if (id != null && sessionManager != null) {
			//
			session = sessionManager.getHttpSession(id, request);//deal the session by zookeeper
			if (session == null && !create)
				return null;
		}
		if (session == null && sessionManager != null && create)//
			session = sessionManager.newHttpSession(request);
		return session;
	}

	public String getAuthType() {
		return getHttpServletRequest().getAuthType();
	}

	public Cookie[] getCookies() {
		return getHttpServletRequest().getCookies();
	}

	public long getDateHeader(String name) {
		return getHttpServletRequest().getDateHeader(name);
	}

	public String getHeader(String name) {
		return getHttpServletRequest().getHeader(name);
	}

	public Enumeration getHeaders(String name) {
		return getHttpServletRequest().getHeaders(name);
	}

	public Enumeration getHeaderNames() {
		return getHttpServletRequest().getHeaderNames();
	}

	public int getIntHeader(String name) {
		return getHttpServletRequest().getIntHeader(name);
	}

	public String getMethod() {
		return getHttpServletRequest().getMethod();
	}

	public String getPathInfo() {
		return getHttpServletRequest().getPathInfo();
	}

	public String getPathTranslated() {
		return getHttpServletRequest().getPathTranslated();
	}

	public String getContextPath() {
		return getHttpServletRequest().getContextPath();
	}

	public String getQueryString() {
		return getHttpServletRequest().getQueryString();
	}

	public String getRemoteUser() {
		return getHttpServletRequest().getRemoteUser();
	}

	public boolean isUserInRole(String role) {
		return getHttpServletRequest().isUserInRole(role);
	}

	public Principal getUserPrincipal() {
		return getHttpServletRequest().getUserPrincipal();
	}

	public String getRequestedSessionId() {
		return getHttpServletRequest().getRequestedSessionId();
	}

	public String getRequestURI() {
		return getHttpServletRequest().getRequestURI();
	}

	public StringBuffer getRequestURL() {
		return getHttpServletRequest().getRequestURL();
	}

	public String getServletPath() {
		return getHttpServletRequest().getServletPath();
	}

	public HttpSession getSession() {
		return getSession(true);
	}

	public boolean isRequestedSessionIdValid() {
		return getHttpServletRequest().isRequestedSessionIdValid();
	}

	public boolean isRequestedSessionIdFromCookie() {
		return getHttpServletRequest().isRequestedSessionIdFromCookie();
	}

	public boolean isRequestedSessionIdFromURL() {
		return getHttpServletRequest().isRequestedSessionIdFromURL();
	}

	public boolean isRequestedSessionIdFromUrl() {
		return getHttpServletRequest().isRequestedSessionIdFromUrl();
	}
}
