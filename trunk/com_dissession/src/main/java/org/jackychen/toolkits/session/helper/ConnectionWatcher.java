package org.jackychen.toolkits.session.helper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import org.apache.log4j.Logger;
import org.apache.zookeeper.*;

public class ConnectionWatcher
	implements Watcher
{

	private static final int SESSION_TIMEOUT = 5000;
	private CountDownLatch signal;
	private Logger log;

	public ConnectionWatcher()
	{
		signal = new CountDownLatch(1);
		log = Logger.getLogger(getClass());
	}

	public ZooKeeper connection(String servers)
	{
		return connection(servers, 5000);
	}

	public ZooKeeper connection(String servers, int sessionTimeout)
	{
		try
	    {
	      ZooKeeper zk = new ZooKeeper(servers, sessionTimeout, this);
	      this.signal.await();
	      return zk;
	    } catch (IOException e) {
	      this.log.error(e);
	    } catch (InterruptedException e) {
	      this.log.error(e);
	    }
	    return null;
	}

	public void process(WatchedEvent event)
	{
		org.apache.zookeeper.Watcher.Event.KeeperState state = event.getState();
		if (state == org.apache.zookeeper.Watcher.Event.KeeperState.SyncConnected)
			signal.countDown();
	}
}
