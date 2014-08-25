// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AbstractZookeeperHandler.java

package org.jackychen.toolkits.session.zookeeper;

import org.apache.log4j.Logger;
import org.apache.zookeeper.ZooKeeper;
import org.jackychen.toolkits.session.pool.ZookeeperPoolableObjectFactory;

// Referenced classes of package org.jackychen.toolkits.session.zookeeper:
//			ZookeeperHandler

public abstract class AbstractZookeeperHandler
	implements ZookeeperHandler
{

	private static final Logger LOGGER = Logger
			.getLogger(AbstractZookeeperHandler.class);
	protected ZooKeeper zookeeper;
	protected String id;

	public AbstractZookeeperHandler(String id)
	{
		this.id = id;
	}

	public void setZooKeeper(ZooKeeper zookeeper)
	{
		this.zookeeper = zookeeper;
	}

}
