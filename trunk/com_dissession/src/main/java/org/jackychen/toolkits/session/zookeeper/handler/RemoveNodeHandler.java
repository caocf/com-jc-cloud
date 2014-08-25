// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RemoveNodeHandler.java

package org.jackychen.toolkits.session.zookeeper.handler;

import java.io.Serializable;
import java.util.*;
import org.apache.log4j.Logger;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.jackychen.toolkits.session.zookeeper.AbstractZookeeperHandler;
import org.jackychen.toolkits.utils.SerializationUtils;

public class RemoveNodeHandler extends AbstractZookeeperHandler {
	private static final Logger LOGGER = Logger
			.getLogger(RemoveNodeHandler.class);

	public RemoveNodeHandler(String id) {
		super(id);
	}

	public Object handle() throws Exception {
		Map datas = new HashMap();
		if (zookeeper != null) {
			String path = (new StringBuilder()).append("/SESSIONS/").append(id)
					.toString();
			Stat stat = zookeeper.exists(path, false);
			if (stat != null) {
				List nodes = zookeeper.getChildren(path, false);
				if (nodes != null) {
					String dataPath;
					for (Iterator i$ = nodes.iterator(); i$.hasNext(); zookeeper.delete(dataPath, -1)) {
						String node = (String) i$.next();
						dataPath = (new StringBuilder()).append(path)
								.append("/").append(node).toString();
						byte data[] = zookeeper.getData(dataPath, false, null);
						String result = new String(data);
						if (LOGGER.isInfoEnabled())
							LOGGER.info("RemoveHandler String:" + result);
						if (data != null) {
							Object obj = new String(data);//SerializationUtils.deserialize(data);
							datas.put(node, (Serializable) obj);
						}
					}

				}
				zookeeper.delete(path, -1);
				if (LOGGER.isInfoEnabled())
					LOGGER.info((new StringBuilder()).append("Deleted the node successfully:[")
							.append(path).append("]").toString());
			}
		}
		return datas;
	}
}
