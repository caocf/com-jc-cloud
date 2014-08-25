package org.jackychen.toolkits.session.helper;

import javax.servlet.http.*;
import org.apache.log4j.Logger;
import org.jackychen.toolkits.session.config.Configuration;
import org.jackychen.toolkits.session.filter.CatalinaDistributedSessionFilter;

public class CookieHelper {

	private static final String DISTRIBUTED_SESSION_ID = "JACKYCHENJSESSIONID";
	private static final Logger LOGGER = Logger.getLogger(CookieHelper.class);

	public CookieHelper() {
	}

	public static Cookie writeSessionIdToNewCookie(String id,
			HttpServletResponse response, int expiry) {
		Cookie cookie = new Cookie(DISTRIBUTED_SESSION_ID, id);
		cookie.setMaxAge(expiry);
		cookie.setPath("/");
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Set The domain:"
					+ Configuration.getInstance().getString("domain"));
		}
		cookie.setDomain(Configuration.getInstance().getString("domain"));
		response.addCookie(cookie);
		return cookie;
	}

	public static Cookie writeSessionIdToCookie(String id,
			HttpServletRequest request, HttpServletResponse response, int expiry){
		Cookie cookie = findCookie(DISTRIBUTED_SESSION_ID, request);
		try {

			if (cookie == null) {
				return writeSessionIdToNewCookie(id, response, expiry);
			} else {
				cookie.setValue(id);
				cookie.setMaxAge(expiry);
				cookie.setPath("/");
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Set The domain:"
							+ Configuration.getInstance().getString("domain"));
				}
				cookie.setDomain(Configuration.getInstance()
						.getString("domain"));
				response.addCookie(cookie);
				
			}
		} catch (Exception ex) {
			LOGGER.error("Exception with writeSessionIdToNewCookie:",ex);
		}
		finally
		{
			return cookie;
		}
	}

	public static String findCookieValue(String name, HttpServletRequest request) {
		Cookie cookie = findCookie(name, request);
		if (cookie != null)
			return cookie.getValue();
		else
			return null;
	}

	public static Cookie findCookie(String name, HttpServletRequest request) {
		Cookie cookies[] = request.getCookies();
		if (cookies == null)
			return null;
		int i = 0;
		for (int n = cookies.length; i < n; i++)
			if (cookies[i].getName().equalsIgnoreCase(name))
				return cookies[i];

		return null;
	}

	public static String findSessionId(HttpServletRequest request) {
		return findCookieValue(DISTRIBUTED_SESSION_ID, request);
	}

}
