// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UpdateMetadataHandler.java

package org.jackychen.toolkits.session.zookeeper.handler;

import org.apache.log4j.Logger;
import org.apache.zookeeper.ZooKeeper;
import org.jackychen.toolkits.session.metadata.SessionMetaData;
import org.jackychen.toolkits.utils.SerializationUtils;

// Referenced classes of package org.jackychen.toolkits.session.zookeeper.handler:
//			GetMetadataHandler

public class UpdateMetadataHandler extends GetMetadataHandler
{
	private static final Logger LOGGER = Logger
			.getLogger(UpdateMetadataHandler.class);
	public UpdateMetadataHandler(String id)
	{
		super(id);
	}

	public Object handle()
		throws Exception
	{
		if (zookeeper != null)
		{
			SessionMetaData metadata = (SessionMetaData)super.handle();
			if (metadata != null)
			{
				updateMetadata(metadata, zookeeper);
				return metadata.getValidate();
			}
		}
		return Boolean.FALSE;
	}

	protected void updateMetadata(SessionMetaData metadata, ZooKeeper zk)
		throws Exception
	{
		if (metadata != null)
		{
			String id = metadata.getId();
			Long now = Long.valueOf(System.currentTimeMillis());
			Long timeout = Long.valueOf(metadata.getLastAccessTm().longValue() + metadata.getMaxIdle().longValue());
			if (timeout.longValue() < now.longValue())
			{
				metadata.setValidate(Boolean.valueOf(false));
				if (LOGGER.isInfoEnabled())
					LOGGER.info((new StringBuilder()).append("Session was timeout[").append(id).append("]").toString());
			}
			metadata.setLastAccessTm(now);
			String path = (new StringBuilder()).append("/SESSIONS/").append(id).toString();
			byte data[] = SerializationUtils.serialize(metadata);
			if(LOGGER.isInfoEnabled())
			LOGGER.info("READY TO UPDATE "+path+" VERSION:"+metadata.getVersion());
			zk.setData(path, data, metadata.getVersion());
			if (LOGGER.isInfoEnabled())
				LOGGER.info((new StringBuilder()).append("Updated Session node completed[").append(path).append("]").toString());
		}
	}
}
