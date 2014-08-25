package org.jackychen.toolkits.session.catalina;

import java.io.Serializable;
import java.security.Principal;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.http.*;

import org.apache.catalina.Context;
import org.apache.catalina.Manager;
import org.apache.catalina.Session;
import org.apache.catalina.SessionListener;
import org.apache.catalina.session.StandardSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jackychen.toolkits.session.SessionManager;
import org.jackychen.toolkits.session.zookeeper.DefaultZooKeeperClient;
import org.jackychen.toolkits.session.zookeeper.ZooKeeperClient;
import org.jackychen.toolkits.session.zookeeper.handler.*;
//��CatalinaDistributedSessionManager�б�����
public class CatalinaDistributedSession  implements HttpSession,Session,Serializable{
	private static final Logger LOGGER = Logger
			.getLogger(CatalinaDistributedSession.class);
	private SessionManager sessionManager;
	private String id;
	private long creationTm;
	private long lastAccessedTm;
	private int maxInactiveInterval;
	private boolean newSession;
	private ZooKeeperClient client = DefaultZooKeeperClient.getInstance();

	public CatalinaDistributedSession(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
		this.creationTm = System.currentTimeMillis();
		this.lastAccessedTm = this.creationTm;
		this.newSession = true;
	}

	public CatalinaDistributedSession(SessionManager sessionManager, String id) {
		this(sessionManager);
		this.id = id;
	}

	public long getCreationTime() {
		return this.creationTm;
	}

	public String getId() {
		return this.id;
	}

	public long getLastAccessedTime() {
		return this.lastAccessedTm;
	}

	public ServletContext getServletContext() {
		return this.sessionManager.getServletContext();
	}

	public void setMaxInactiveInterval(int interval) {
		this.maxInactiveInterval = interval;
	}

	public int getMaxInactiveInterval() {
		return this.maxInactiveInterval;
	}

	@Deprecated
	public HttpSessionContext getSessionContext() {
		return null;
	}

	public Object getAttribute(String name) {
		access();

		String id = getId();
		if (StringUtils.isNotBlank(id)) {
			try {
				return this.client.execute(new GetDataHandler(id,name));
			} catch (Exception ex) {
				LOGGER.error("Exception with getAttribute", ex);
			}
		}
		return null;
	}

	public Object getValue(String name) {
		return getAttribute(name);
	}

	public Enumeration getAttributeNames() {
		access();

		String id = getId();
		if (StringUtils.isNotBlank(id)) {
			try {
				List names = (List) this.client.execute(new GetNodeNames(id));
				if (names != null)
					return Collections.enumeration(names);
			} catch (Exception ex) {
				LOGGER.error("Exception with call the get attribute method", ex);
			}
		}

		return null;
	}

	public String[] getValueNames() {
		List names = new ArrayList();
		Enumeration n = getAttributeNames();
		while (n.hasMoreElements()) {
			names.add((String) n.nextElement());
		}
		return (String[]) names.toArray(new String[0]);
	}

	public void setAttribute(String name, Object value) {
		if (!(value instanceof Serializable)) {
			LOGGER.warn("Instance [" + value + "]cann't save as distributed session,because no inplement interface Serializable");
			return;
		}
		access();

		String id = getId();
		if (StringUtils.isNotBlank(id)) {
			try {
			Object	value1 = this.client.execute(new PutDataHandler(id, name,
						(Serializable) value));
			} catch (Exception ex) {
				LOGGER.error("Exception with call setattribute", ex);
			}
		}

		fireHttpSessionBindEvent(name, value);
	}

	public void putValue(String name, Object value) {
		setAttribute(name, value);
	}

	public void removeAttribute(String name) {
		access();
		Object value = null;

		String id = getId();
		if (StringUtils.isNotBlank(id)) {
			try {
				Object value1 = this.client.execute(new RemoveDataHandler(id, name));
			} catch (Exception ex) {
				LOGGER.error("Exception with call removeattribute", ex);
			}
		}

		fireHttpSessionUnbindEvent(name, value);
	}

	public void removeValue(String name) {
		removeAttribute(name);
	}

	public void invalidate()
	{
		String id = getId();
		if (StringUtils.isNotBlank(id))
			try
			{
				Map sessionMap = (Map)client.execute(new RemoveNodeHandler(id));
				if (sessionMap != null)
				{
					Set keys = sessionMap.keySet();
					String key;
					Object value;
					for (Iterator i$ = keys.iterator(); i$.hasNext(); fireHttpSessionUnbindEvent(key, value))
					{
						key = (String)i$.next();
						value = sessionMap.get(key);
					}

				}
			}
			catch (Exception ex)
			{
				LOGGER.error("Exception with call invalidate", ex);
			}
		sessionManager.removeHttpSession(this);
	}

	public boolean isNew() {
		return this.newSession;
	}
	/* ÿ��һ��Session�����õ�ʱ�򣬶������access�����������������ʱ��ʹ���
	 * (non-Javadoc)
	 * @see org.apache.catalina.Session#access()
	 */
	public void access() {
		this.newSession = false;
		this.lastAccessedTm = System.currentTimeMillis();
	}
	
	protected void fireHttpSessionBindEvent(String name, Object value) {
		if ((value != null) && ((value instanceof HttpSessionBindingListener))) {
			HttpSessionBindingEvent event = new HttpSessionBindingEvent(this,
					name, value);
			((HttpSessionBindingListener) value).valueBound(event);
		}
	}

	protected void fireHttpSessionUnbindEvent(String name, Object value) {
		if ((value != null) && ((value instanceof HttpSessionBindingListener))) {
			HttpSessionBindingEvent event = new HttpSessionBindingEvent(this,
					name, value);
			((HttpSessionBindingListener) value).valueUnbound(event);
		}
	}

	//--------------------------------------------------------------------------------//
	  protected transient ArrayList<SessionListener> listeners =
		        new ArrayList<SessionListener>();
	public void addSessionListener(SessionListener listener) {
		// TODO Auto-generated method stub
		   listeners.add(listener);
	}

	@Override
	public void endAccess() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void expire() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAuthType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getIdInternal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getLastAccessedTimeInternal() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Manager getManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getNote(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator getNoteNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Principal getPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpSession getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		 long timeNow = System.currentTimeMillis();
		return false;
	}

	@Override
	public void recycle() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeNote(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeSessionListener(SessionListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAuthType(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCreationTime(long arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setId(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setManager(Manager arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNew(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNote(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPrincipal(Principal arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValid(boolean arg0) {
		// TODO Auto-generated method stub
		
	}
	public void tellNew()
	{
		/*  Context context = (Context) sessionManager.getServletContext().getc
	      Object listeners[] = context.getApplicationLifecycleListeners();*/
	}
}