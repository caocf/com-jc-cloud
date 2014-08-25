package org.jackychen.toolkits.session.pool;

import java.util.NoSuchElementException;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.StackObjectPool;
import org.apache.log4j.Logger;
import org.apache.zookeeper.ZooKeeper;
import org.jackychen.toolkits.session.config.Configuration;

// Referenced classes of package org.jackychen.toolkits.session.pool:
//			ZookeeperPoolableObjectFactory

public class ZookeeperPoolManager {

	private static final Logger LOGGER = Logger
			.getLogger(ZookeeperPoolManager.class);
	protected static ZookeeperPoolManager instance;
	private ObjectPool pool;

	protected ZookeeperPoolManager() {
	}

	public static ZookeeperPoolManager getInstance() {
		if (instance == null)
			instance = new ZookeeperPoolManager();
		return instance;
	}

	public void init(Configuration config) {
		org.apache.commons.pool.PoolableObjectFactory factory = new ZookeeperPoolableObjectFactory(
				config);
		int maxIdle = NumberUtils.toInt(config.getString("maxIdle"));
		int initIdleCapacity = NumberUtils.toInt(config
				.getString("initIdleCapacity"));
		pool = new StackObjectPool(factory, maxIdle, initIdleCapacity);
	}

	public ZooKeeper borrowObject() {
		if (this.pool != null) {
			try {
				ZooKeeper zk = (ZooKeeper) this.pool.borrowObject();
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Return the zk instance from pool zk.sessionId=" + zk.getSessionId());
				}
				return zk;
			} catch (NoSuchElementException ex) {
				LOGGER.error("Return zk instance error", ex);
			} catch (IllegalStateException ex) {
				LOGGER.error("Return zk instance error", ex);
			} catch (Exception e) {
				LOGGER.error("Return zk instance error", e);
			}
		}
		return null;
	}

	public void returnObject(ZooKeeper zk) {
		if (pool != null && zk != null)
			try {
				pool.returnObject(zk);
				if (LOGGER.isInfoEnabled())
					LOGGER.info((new StringBuilder())
							.append("Return the zk instance to pool zk.sessionId=")
							.append(zk.getSessionId()).toString());
			} catch (Exception ex) {
				LOGGER.error("Exception with return th zk instance", ex);
			}
	}

	public void close() {
		if (pool != null)
			try {
				pool.close();
				if (LOGGER.isInfoEnabled())
					LOGGER.info("Closed the zk pool");
			} catch (Exception ex) {
				LOGGER.error("Exception with colse zk pool", ex);
			}
	}

}
