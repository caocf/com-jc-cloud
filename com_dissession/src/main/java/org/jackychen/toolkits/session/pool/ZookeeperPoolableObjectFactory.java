package org.jackychen.toolkits.session.pool;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.log4j.Logger;
import org.apache.zookeeper.ZooKeeper;
import org.jackychen.toolkits.session.config.Configuration;
import org.jackychen.toolkits.session.helper.ConnectionWatcher;
import org.jackychen.toolkits.session.helper.CookieHelper;

public class ZookeeperPoolableObjectFactory implements PoolableObjectFactory {

	private static final Logger LOGGER = Logger
			.getLogger(ZookeeperPoolableObjectFactory.class);
	private Configuration config;

	public ZookeeperPoolableObjectFactory(Configuration config) {
		this.config = config;
	}

	public ZooKeeper makeObject() throws Exception {
		ConnectionWatcher cw = new ConnectionWatcher();
		String servers = config.getString("servers");
		int timeout = NumberUtils.toInt(config.getString("timeout"));
		ZooKeeper zk = cw.connection(servers, timeout);
		if (zk != null) {
			if (LOGGER.isInfoEnabled())
				LOGGER.info((new StringBuilder())
						.append("Instance zk client objectzk.sessionId=")
						.append(zk.getSessionId()).toString());
		} else {
			LOGGER.error("Exeption with instance zk client object");
		}
		return zk;
	}

	public void destroyObject(Object obja) throws Exception {
		try
		{
		ZooKeeper obj=(ZooKeeper)obja;
		if (obj != null) {
			obj.close();
			if (LOGGER.isInfoEnabled())
				LOGGER.info((new StringBuilder())
						.append("Destroyed client zk instance .sessionId=")
						.append(obj.getSessionId()).toString());
		}
		}catch(Exception ex)
		{
			LOGGER.error("Exception with destroy the client instance",ex);
		}
	}

	public boolean validateObject(Object obj) {
		ZooKeeper obja=(ZooKeeper)obj;
		if (obja != null
				&& obja.getState() == org.apache.zookeeper.ZooKeeper.States.CONNECTED) {
			if (LOGGER.isInfoEnabled())
				LOGGER.info((new StringBuilder())
						.append("Validated the client object successfully.sessionId=")
						.append(obja.getSessionId()).toString());
			return true;
		}
		if (LOGGER.isInfoEnabled())
			LOGGER.info((new StringBuilder())
					.append("Validated the client object failed zk.sessionId=")
					.append(obja.getSessionId()).toString());
		return false;
	}

	@Override
	public void activateObject(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void passivateObject(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}


}
