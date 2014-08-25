// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PutDataHandler.java

package org.jackychen.toolkits.session.zookeeper.handler;

import java.io.Serializable;
import org.apache.log4j.Logger;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.jackychen.toolkits.utils.SerializationUtils;

// Referenced classes of package org.jackychen.toolkits.session.zookeeper.handler:
//			GetDataHandler

public class PutDataHandler extends GetDataHandler
{

	private static final Logger LOGGER = Logger
			.getLogger(PutDataHandler.class);
	private Serializable data;

	public PutDataHandler(String id, String key, Serializable data)
	{
		super(id, key);
		this.data = data;
	}

	public Object handle()
		throws Exception
	{
		if (zookeeper != null)
		{
			String path = (new StringBuilder()).append("/SESSIONS/").append(id).toString();
			Stat stat = zookeeper.exists(path, false);
			if (stat != null)
			{
				String dataPath = (new StringBuilder()).append(path).append("/").append(key).toString();
				stat = zookeeper.exists(dataPath, false);
				if (stat == null)
				{
					zookeeper.create(dataPath, null, org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					if (LOGGER.isInfoEnabled())
						LOGGER.info((new StringBuilder()).append("Created the data of node successfully").append(dataPath).append("]").toString());
				}
				if (data instanceof Serializable)
				{
					int dataNodeVer = -1;
					if (stat != null)
						dataNodeVer = stat.getVersion();
					byte arrData[] = data.toString().getBytes();//SerializationUtils.serialize(data);
					stat = zookeeper.setData(dataPath, arrData, dataNodeVer);
					if (LOGGER.isInfoEnabled())
						LOGGER.info((new StringBuilder()).append("Updated the data of node successfully [").append(dataPath).append("][").append(data).append("]").toString());
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
	}
}
