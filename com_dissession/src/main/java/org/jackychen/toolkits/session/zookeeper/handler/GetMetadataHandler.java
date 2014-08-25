// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GetMetadataHandler.java

package org.jackychen.toolkits.session.zookeeper.handler;

import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.jackychen.toolkits.session.metadata.SessionMetaData;
import org.jackychen.toolkits.session.zookeeper.AbstractZookeeperHandler;
import org.jackychen.toolkits.utils.SerializationUtils;

public class GetMetadataHandler extends AbstractZookeeperHandler
{

	public GetMetadataHandler(String id)
	{
		super(id);
	}

	public Object handle()
		throws Exception
	{
		if (zookeeper != null)
		{
			String path = (new StringBuilder()).append("/SESSIONS/").append(id).toString();
			Stat stat = zookeeper.exists(path, false);
			if (stat == null)
				return null;
			byte data[] = zookeeper.getData(path, false, null);
			if (data != null)
			{
				Object obj = SerializationUtils.deserialize(data);
				if (obj instanceof SessionMetaData)
				{
					SessionMetaData metadata = (SessionMetaData)obj;
					metadata.setVersion(stat.getVersion());
					return metadata;
				}
			}
		}
		return (Object)null;
	}
}
