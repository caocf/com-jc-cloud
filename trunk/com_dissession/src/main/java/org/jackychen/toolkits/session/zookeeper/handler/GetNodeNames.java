// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GetNodeNames.java

package org.jackychen.toolkits.session.zookeeper.handler;

import org.apache.zookeeper.ZooKeeper;
import org.jackychen.toolkits.session.metadata.SessionMetaData;

// Referenced classes of package org.jackychen.toolkits.session.zookeeper.handler:
//			GetMetadataHandler

public class GetNodeNames extends GetMetadataHandler
{

	public GetNodeNames(String id)
	{
		super(id);
	}

	public Object handle()
		throws Exception
	{
		if (zookeeper != null)
		{
			String path = (new StringBuilder()).append("/SESSIONS/").append(id).toString();
			SessionMetaData metadata = (SessionMetaData)super.handle();
			if (metadata == null || !metadata.getValidate().booleanValue())
				return null;
			else
				return zookeeper.getChildren(path, false);
		} else
		{
			return (Object)null;
		}
	}
}
