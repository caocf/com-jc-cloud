package org.jc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.jc.distributelock.DistributedLock;
public class DistributedLockTest {
	static String path = "/curator/distributed_lock";
	static CuratorFramework client;

	public static void main(String[] args) {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		String zookeeperConnectionString = "192.168.128.132:2181";//,192.168.128.131:2181";
		client = CuratorFrameworkFactory.builder()
				.connectString(zookeeperConnectionString)
				.sessionTimeoutMs(5000).connectionTimeoutMs(100000)
				.retryPolicy(retryPolicy).namespace("curator").build();
		ExecutorService exec = Executors.newCachedThreadPool();// 线程池的大小会根据执行的任务数动态分配
		client.start();
		for (int i = 0; i < 20; i++) {
			exec.submit(new TestDLock("client" + i, client));
		}
		exec.shutdown();
	}

	static class TestDLock implements Runnable {
		CuratorFramework client;
		String name;
		String path;

		TestDLock(String name, CuratorFramework client) {
			this.client = client;
			this.name = name;
		}

		@Override
		public void run() {
			DistributedLock lock = new DistributedLock(client,"/cuartor/distributedlock");
			try {
				//if (lock.lock()) {
				lock.lock();
					System.out.println(this.name + "________获得了锁");
					Thread.currentThread().sleep(5000L);
					System.out.println(this.name + "________生成订单号");
					System.out.println(this.name + "________结束");
				//}
			} catch (Exception e) {
			} finally {
				try {
					lock.unlock();
					System.out.println(this.name + "________释放");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
}
