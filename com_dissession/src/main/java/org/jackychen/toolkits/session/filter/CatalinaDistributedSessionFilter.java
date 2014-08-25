package org.jackychen.toolkits.session.filter;


import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.jackychen.toolkits.session.SessionManager;
import org.jackychen.toolkits.session.catalina.CatalinaDistributedSession;
import org.jackychen.toolkits.session.catalina.CatalinaDistributedSessionManager;

// Referenced classes of package org.jackychen.toolkits.session.filter:
//			DistributedSessionFilter

public class CatalinaDistributedSessionFilter extends DistributedSessionFilter
{
	private static final Logger LOGGER = Logger
			.getLogger(CatalinaDistributedSessionFilter.class);

	public CatalinaDistributedSessionFilter()
	{
	}

	public void init(FilterConfig filterConfig)
		throws ServletException
	{
		super.init(filterConfig);
		sessionManager = new CatalinaDistributedSessionManager(filterConfig.getServletContext());
		try
		{
			sessionManager.start();
			if (LOGGER.isInfoEnabled())
				LOGGER.info("DistributedSessionFilter.init completed.");
		}
		catch (Exception ex)
		{
			LOGGER.error("SessionManager Start Error-CatalinaDitributed-Init", ex);
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException
	{
		sessionManager.setHttpServletResponse((HttpServletResponse)response);
		super.doFilter(request, response, chain);
	}
}
