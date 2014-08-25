// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SessionManager.java

package org.jackychen.toolkits.session;

import java.util.EventListener;

import javax.servlet.ServletContext;
import javax.servlet.http.*;
import org.jackychen.toolkits.component.LifeCycle;

public interface SessionManager extends LifeCycle {

	public static final int COOKIE_EXPIRY = 0x1e13380;

	public abstract HttpSession getHttpSession(String s,
			HttpServletRequest httpservletrequest);

	public abstract HttpSession newHttpSession(
			HttpServletRequest httpservletrequest);

	public abstract String getRequestSessionId(
			HttpServletRequest httpservletrequest);

	public abstract void addHttpSession(HttpSession httpsession);

	public abstract void removeHttpSession(HttpSession httpsession);

	public abstract String getNewSessionId(HttpServletRequest httpservletrequest);

	public abstract ServletContext getServletContext();

	public abstract void setServletContext(ServletContext servletcontext);

	public abstract HttpServletResponse getResponse();

	public abstract void setHttpServletResponse(
			HttpServletResponse httpservletresponse);

	
}
