// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TimeoutHandler.java

package org.jackychen.toolkits.session.zookeeper.handler;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.apache.zookeeper.ZooKeeper;
import org.jackychen.toolkits.session.metadata.SessionMetaData;
import org.jackychen.toolkits.utils.SerializationUtils;

// Referenced classes of package org.jackychen.toolkits.session.zookeeper.handler:
//			GetMetadataHandler

public class TimeoutHandler extends GetMetadataHandler {
	private static final Logger LOGGER = Logger.getLogger(TimeoutHandler.class);

	public TimeoutHandler(String id) {
		super(id);
	}

	public Object handle() throws Exception {
		if (zookeeper != null) {
			String path = (new StringBuilder()).append("/SESSIONS/").append(id)
					.toString();
			SessionMetaData metadata = (SessionMetaData) super.handle();
			if (metadata == null)
				return Boolean.TRUE;
			if (!metadata.getValidate().booleanValue())
				return Boolean.TRUE;
			Long now = Long.valueOf(System.currentTimeMillis());
			Long timeout = Long.valueOf(metadata.getLastAccessTm().longValue()
					+ metadata.getMaxIdle().longValue());
			// Long partValue=timeout-now;
			// System.out.println("PartValue:"+Long.toString(partValue)+"      Now:"+Long.toString(now)+
			// "TimeOut:"+timeout);
			if (timeout.longValue() < now.longValue()) {
				metadata.setValidate(Boolean.valueOf(false));
				byte data[] = SerializationUtils.serialize(metadata);
				zookeeper.setData(path, data, metadata.getVersion());
				if (LOGGER.isInfoEnabled())
					LOGGER.info("Updated the sessionid!" + "_______" + id);
			} else {
				if (LOGGER.isInfoEnabled())
					LOGGER.info("The SessionID were timeout" + "_______" + id);

			}
			String timeoutStr = DateFormatUtils.format(timeout.longValue(),
					"yyyy-MM-dd HH:mm");
			if (LOGGER.isInfoEnabled())
				LOGGER.info((new StringBuilder()).append("Check the session timeout:[")
						.append(timeoutStr).append("]").toString());
		}
		return Boolean.FALSE;
	}

}
