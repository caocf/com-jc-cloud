// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GetNodesHandler.java

package org.jackychen.toolkits.session.zookeeper.handler;

import java.util.*;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.jackychen.toolkits.session.metadata.SessionMetaData;
import org.jackychen.toolkits.utils.SerializationUtils;

// Referenced classes of package org.jackychen.toolkits.session.zookeeper.handler:
//			GetMetadataHandler

public class GetNodesHandler extends GetMetadataHandler
{

	public GetNodesHandler(String id)
	{
		super(id);
	}

	public Object handle()
		throws Exception
	{
		Map nodeMap = new HashMap();
		if (zookeeper != null)
		{
			String path = (new StringBuilder()).append("/SESSIONS/").append(id).toString();
			SessionMetaData metadata = (SessionMetaData)super.handle();
			if (metadata == null || !metadata.getValidate().booleanValue())
				return null;
			List nodes = zookeeper.getChildren(path, false);
			Iterator i$ = nodes.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				String node = (String)i$.next();
				String dataPath = (new StringBuilder()).append(path).append("/").append(node).toString();
				Stat stat = zookeeper.exists(dataPath, false);
				if (stat != null)
				{
					byte data[] = zookeeper.getData(dataPath, false, null);
					if (data != null)
						nodeMap.put(node, SerializationUtils.deserialize(data));
					else
						nodeMap.put(node, null);
				}
			} while (true);
		}
		return nodeMap;
	}
}
