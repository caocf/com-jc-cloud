
package org.jackychen.toolkits.session.zookeeper;

import org.apache.zookeeper.ZooKeeper;

public interface ZookeeperHandler
{

	public static final String GROUP_NAME = "/SESSIONS";
	public static final String NODE_SEP = "/";

	public abstract Object handle()
		throws Exception;

	public abstract void setZooKeeper(ZooKeeper zookeeper);
}
