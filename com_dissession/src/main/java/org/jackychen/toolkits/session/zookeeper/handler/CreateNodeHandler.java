// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CreateNodeHandler.java

package org.jackychen.toolkits.session.zookeeper.handler;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.jackychen.toolkits.session.metadata.SessionMetaData;
import org.jackychen.toolkits.session.zookeeper.AbstractZookeeperHandler;
import org.jackychen.toolkits.utils.SerializationUtils;

public class CreateNodeHandler extends AbstractZookeeperHandler
{

	private SessionMetaData metadata;

	private static final Logger LOGGER = Logger
			.getLogger(CreateNodeHandler.class);
	public CreateNodeHandler(String id, SessionMetaData metadata)
	{
		super(id);
		this.metadata = metadata;
	}

	public Object handle()
		throws Exception
	{
		if (zookeeper != null)
		{
			String path = id;
			if (!StringUtils.startsWithIgnoreCase(id, "/SESSIONS"))
				path = (new StringBuilder()).append("/SESSIONS/").append(id).toString();
			Stat stat = zookeeper.exists(path, false);
			if (stat == null)
			{
				byte arrData[] = null;
				if (metadata != null)
					arrData = SerializationUtils.serialize(metadata);
				String createPath = zookeeper.create(path, arrData, org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				if (LOGGER.isInfoEnabled())
					LOGGER.info((new StringBuilder()).append("Created the node:[").append(createPath).append("]").toString());
			} else
			if (LOGGER.isInfoEnabled())
				LOGGER.info((new StringBuilder()).append("Node has existed[").append(path).append("]").toString());
		}
		return (Object)null;
	}
}
