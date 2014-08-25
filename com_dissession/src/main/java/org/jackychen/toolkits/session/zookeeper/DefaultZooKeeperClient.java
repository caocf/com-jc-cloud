// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DefaultZooKeeperClient.java

package org.jackychen.toolkits.session.zookeeper;

import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.jackychen.toolkits.session.pool.ZookeeperPoolManager;
import org.jackychen.toolkits.session.zookeeper.handler.UpdateMetadataHandler;

// Referenced classes of package org.jackychen.toolkits.session.zookeeper:
//			ZooKeeperClient, ZookeeperHandler

public class DefaultZooKeeperClient implements ZooKeeperClient {
	private static final Logger LOGGER = Logger
			.getLogger(DefaultZooKeeperClient.class);
	private static ZooKeeperClient instance;
	private ZookeeperPoolManager pool;

	protected DefaultZooKeeperClient() {
		if (pool == null)
			pool = ZookeeperPoolManager.getInstance();
	}

	public static ZooKeeperClient getInstance() {
		if (instance == null)
			instance = new DefaultZooKeeperClient();
		return instance;
	}

	public Object execute(ZookeeperHandler handler) throws Exception {
		ZooKeeper zk = this.pool.borrowObject();
		if (zk != null) {
			try {
				handler.setZooKeeper(zk);
				return handler.handle();
			} catch (KeeperException ex) {
				LOGGER.error("Exception with operate the zk node: ", ex);
			} catch (InterruptedException ex) {
				LOGGER.error("Exception with operate the zk node: ", ex);
			} finally {
				this.pool.returnObject(zk);
			}
		}
		return (Object) null;
	}

}
