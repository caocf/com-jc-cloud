package org.jc.distributelock;

import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class DistributedLocks implements ConnectionStateListener {
	private String zookeeperConnectionString;
	private int sessionTimeoutMs;
	private int connectionTimeoutMs;

	private int retryTime;// baseSleepTimeMs initial amount of time to wait
							// between retries
	private int maxRetries;// maxRetries max number of times to retry
	private String namespace;// locak description
	private String lockpath;
	public CuratorFramework client;
	private String path = "/distributed_lock";
	private InterProcessMutex lock;

	public DistributedLocks(String zookeeperConnectionString,
			int sessionTimeoutMs, int connectionTimeoutMs, int retryTime,
			int maxRetries, String path) {
		this.zookeeperConnectionString = zookeeperConnectionString;
		this.sessionTimeoutMs = sessionTimeoutMs;
		this.connectionTimeoutMs = connectionTimeoutMs;
		this.retryTime = retryTime;
		this.maxRetries = maxRetries;
		this.client = CuratorFrameworkFactory
				.builder()
				.connectString(zookeeperConnectionString)
				.sessionTimeoutMs(5000)
				.connectionTimeoutMs(100000)
				.retryPolicy(new ExponentialBackoffRetry(retryTime, maxRetries))
				.namespace("zk_distributeLock").build();
		this.path = path;
	}

	public DistributedLocks(CuratorFramework client) {
		this.client = client;
	}

	public boolean getLock() {
		return getLock(120);
	}

	// 获取锁默认120天的等待的的获取时间
	public boolean getLock(int acquireTime) {
		boolean isSetSuccess = false;
		try {
			if (client == null) {
				isSetSuccess = false;
			} else {
				lock = new InterProcessMutex(client, path);
				isSetSuccess = lock.acquire(acquireTime, TimeUnit.DAYS);
			}
		} catch (Exception ex) {
			isSetSuccess = false;
		}
		return isSetSuccess;
	}

	public boolean releaseLock() {
		boolean isReleaseSuccess = false;
		try {
			if (client == null) {
				isReleaseSuccess = false;
			} else {
				lock.release();
			}
		} catch (Exception ex) {
			isReleaseSuccess = false;
		}
		return isReleaseSuccess;
	}

	public void clientStart() {
		client.start();
	}

	public void clientClose() {
		client.close();
	}

	@Override
	public void stateChanged(CuratorFramework client, ConnectionState newState) {
		// TODO Auto-generated method stub
		switch (newState) {
		case RECONNECTED:
			try {
				while (true) {
					System.out.println("Reconnect...");
					if (client.getZookeeperClient()
							.blockUntilConnectedOrTimedOut()) {
						client.create()
								.creatingParentsIfNeeded()
								.withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
								.forPath("/connectionState/distributedlock/",
										"ok".getBytes("UTF-8"));
						break;
					}
				}
				// 当链接重建之后,需要发送一个TAG消息,用于重新触发本地的watcher,以便获取新的children列表
				// queue.put(TAG);
			} catch (Exception e) {
				//
			}
			break;
		default:
			System.out.println(newState.toString());
		}
	}

}
