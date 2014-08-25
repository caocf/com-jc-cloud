// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RemoveDataHandler.java

package org.jackychen.toolkits.session.zookeeper.handler;

import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.jackychen.toolkits.utils.SerializationUtils;

// Referenced classes of package org.jackychen.toolkits.session.zookeeper.handler:
//			GetDataHandler

public class RemoveDataHandler extends GetDataHandler
{

	public RemoveDataHandler(String id, String key)
	{
		super(id, key);
	}

	public Object handle()
		throws Exception
	{
		Object value = null;
		if (zookeeper != null)
		{
			String path = (new StringBuilder()).append("/SESSIONS/").append(id).toString();
			Stat stat = zookeeper.exists(path, false);
			if (stat != null)
			{
				String dataPath = (new StringBuilder()).append(path).append("/").append(key).toString();
				stat = zookeeper.exists(dataPath, false);
				if (stat != null)
				{
					byte data[] = zookeeper.getData(dataPath, false, null);
					if (data != null)
						value = SerializationUtils.deserialize(data);
					zookeeper.delete(dataPath, -1);
				}
			}
		}
		return value;
	}
}
