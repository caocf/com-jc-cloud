package org.jc.distributelock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
/*
 * 
 * 尼玛一定要主要版本的问题，curator-recopes要和curator-framework的版本要对应，
 * 坑爹！测试了很长时间
 * 此前是lock.release无法释放
 */
public class CuratorLockTest {
	public static String zookeeperConnectionString = "192.168.128.132:2181,192.168.128.131:2181";
	public static CuratorFramework client;
	private static String path = "/curator/distributed_lock";

	public static void main(String[] args) throws InterruptedException {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		client = CuratorFrameworkFactory.builder()
				.connectString(zookeeperConnectionString)
				.sessionTimeoutMs(5000).connectionTimeoutMs(100000)
				.retryPolicy(retryPolicy).namespace("curator").build();
		client.start();
		System.out.println("客户端启动。。。。");
		CountDownLatch latch = new CountDownLatch(15);
		ExecutorService exec = Executors.newCachedThreadPool();// 线程池的大小会根据执行的任务数动态分配
		for (int i = 0; i < 15; i++) {
			exec.submit(new DistributedLockExt("client" + i, client, latch, path));
		}
		exec.shutdown();
		latch.await();// 等待全部执行完毕
		System.out.println("所有分布式锁测试任务执行完毕");
		client.close();
		System.out.println("Zookeeper客户端关闭。。。。");
	}

	static class DistributedLockExt implements Runnable {
		private String name;
		private CuratorFramework client;
		private CountDownLatch latch;
		private String path;

		public DistributedLockExt(String name, CuratorFramework client,
				CountDownLatch latch, String path) {
			this.name = name;
			this.client = client;
			this.path = path;
			this.latch = latch;
		}

		@Override
		public void run() {
			InterProcessMutex lock = new InterProcessMutex(client, path);
			try {
				if (lock.acquire(120, TimeUnit.SECONDS)) {
					System.out.println("----------" + this.name
							+ "获得资源----------");
					Thread.sleep(5000);
					System.out.println("----------" + this.name
							+ "正在处理资源----------");
					Thread.sleep(1 * 1000);
					System.out.println("----------" + this.name
							+ "资源使用完毕----------");
					
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					lock.release();
					latch.countDown();
					System.out.println("----------" + this.name
							+ "资源释放完毕----------");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					
					System.out.println(this.name);
					e.printStackTrace();
				}
			}

		}

	}
}
