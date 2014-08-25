package org.jackychen.toolkits.session.catalina;

import javax.servlet.ServletContext;
import javax.servlet.http.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.jackychen.toolkits.session.DefaultSessionManager;
import org.jackychen.toolkits.session.helper.CookieHelper;
import org.jackychen.toolkits.session.metadata.SessionMetaData;
import org.jackychen.toolkits.session.zookeeper.handler.*;

// Referenced classes of package org.jackychen.toolkits.session.catalina:
//			CatalinaDistributedSession, CatalinaDistributedSessionFacade

public class CatalinaDistributedSessionManager extends DefaultSessionManager {

	public CatalinaDistributedSessionManager(ServletContext sc) {
		super(sc);
	}
	//ContainerRequestWrapper��ʹ��
	public HttpSession getHttpSession(String id, HttpServletRequest request) {
		HttpSession session = (HttpSession) sessions.get(id);
		Boolean valid = Boolean.FALSE;
		try {
			valid = (Boolean) client.execute(new UpdateMetadataHandler(id));//
		} catch (Exception ex) {
			LOGGER.error("execute UpdateMetadataHandler error:", ex);
		}
		if (!valid.booleanValue()) {
			if (session != null)
				session.invalidate();
			else
				try {
					client.execute(new RemoveNodeHandler(id));//
				} catch (Exception ex) {
					LOGGER.error("execute RemoveNodeHandler error:", ex);
				}
			return null;
		}
		if (session != null) {
			return session;
		} else {
			CatalinaDistributedSession sess = new CatalinaDistributedSession(
					this, id);
			sess.access();
			session = new CatalinaDistributedSessionFacade(sess);
			addHttpSession(session);//
			return session;
		}
	}

	public HttpSession newHttpSession(HttpServletRequest request){
		String id = getNewSessionId(request);
		CatalinaDistributedSession sess = new CatalinaDistributedSession(this,
				id);
		HttpSession session = new CatalinaDistributedSessionFacade(sess);
		Cookie cookie = CookieHelper.writeSessionIdToCookie(id, request,
				getResponse(), 0x1e13380);
		if (cookie != null && LOGGER.isInfoEnabled())
			LOGGER.info((new StringBuilder())
					.append("Wrote sid to Cookie,name:[")
					.append(cookie.getName()).append("],value:[")
					.append(cookie.getValue()).append("]").toString());
		SessionMetaData metadata = new SessionMetaData();
		metadata.setId(id);
		Long sessionTimeout = Long.valueOf(NumberUtils.toLong(config
				.getString("sessionTimeout")));
		metadata.setMaxIdle(Long.valueOf(sessionTimeout.longValue() * 60L * 1000L));
		try {
			client.execute(new CreateNodeHandler(id, metadata));
		} catch (Exception ex) {
			LOGGER.error("execute CreateNodeHandler error:", ex);
		}
		addHttpSession(session);
		return session;
	}
	
	

}
