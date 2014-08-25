package org.curatorframework;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;

public class CuratorClient {
	public CuratorFramework client;
	private String connectionString = "192.168.128.131";
	private String port = "2181";
	private String nameSpace = "chenzhao";
	private int retryTimes = 1000;
	private int connectionTimeOutMs = 5000;

	public CuratorClient() {
		/*
		 * this.connectionString = builder.connectionString;
		 * this.connectionTimeOutMs = builder.connectionTimeOutMs;
		 * this.nameSpace = builder.nameSpace; this.retryTimes =
		 * builder.retryTimes;
		 */

		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, retryTimes);
		client = CuratorFrameworkFactory.builder()
				.connectString(connectionString + ":" + port)
				.sessionTimeoutMs(5000)
				.connectionTimeoutMs(connectionTimeOutMs)
				.retryPolicy(retryPolicy).namespace(nameSpace).build();

	}

	public static class CuratorClientLoad {
		static CuratorClient newCurator = new CuratorClient();
	}

	public static CuratorFramework getInstance() {
		return CuratorClientLoad.newCurator.client;
	}
}
