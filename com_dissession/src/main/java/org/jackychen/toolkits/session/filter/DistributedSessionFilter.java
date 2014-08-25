package org.jackychen.toolkits.session.filter;

import java.io.IOException;
import javax.servlet.*;
import org.apache.log4j.Logger;
import org.jackychen.toolkits.session.SessionManager;
import org.jackychen.toolkits.session.catalina.CatalinaDistributedSession;
import org.jackychen.toolkits.session.config.Configuration;
import org.jackychen.toolkits.session.pool.ZookeeperPoolManager;
import org.jackychen.toolkits.session.servlet.ContainerRequestWrapper;
import org.jackychen.toolkits.session.zookeeper.DefaultZooKeeperClient;
import org.jackychen.toolkits.session.zookeeper.ZooKeeperClient;
import org.jackychen.toolkits.session.zookeeper.handler.CreateGroupNodeHandler;

public abstract class DistributedSessionFilter implements Filter
{
	private static final Logger LOGGER = Logger
			.getLogger(DistributedSessionFilter.class);
	protected SessionManager sessionManager;
	protected ZooKeeperClient client;
	public DistributedSessionFilter()
	{
		client = DefaultZooKeeperClient.getInstance();
	}

	public void init(FilterConfig filterConfig)
		throws ServletException
	{
		Configuration conf = Configuration.getInstance();
		if (LOGGER.isInfoEnabled())
			LOGGER.info((new StringBuilder()).append("1. Read the system config successfully").append(conf).toString());
		ServletContext sc = filterConfig.getServletContext();
		sc.setAttribute(".cfg.properties", conf);
		ZookeeperPoolManager.getInstance().init(conf);
		if (LOGGER.isInfoEnabled())
			LOGGER.info("2.Initialized the client object pool");
		try
		{
			client.execute(new CreateGroupNodeHandler());
			if (LOGGER.isInfoEnabled())
				LOGGER.info("3. Created the 'SEESSIONS' Node successfully");
		}
		catch (Exception ex)
		{
			LOGGER.error("Created the 'SEESSIONS' Node failed", ex);
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException
	{
		javax.servlet.http.HttpServletRequest req = new ContainerRequestWrapper(request, sessionManager);
		chain.doFilter(req, response);
	}

	public void destroy()
	{
		if (sessionManager != null)
			try
			{
				sessionManager.stop();
			}
			catch (Exception ex)
			{
				LOGGER.error("Closed the SessionManager tool with exception.", ex);
			}
		ZookeeperPoolManager.getInstance().close();
		if (LOGGER.isInfoEnabled())
			LOGGER.info("DistributedSessionFilter.destroy completed.");
	}

}
