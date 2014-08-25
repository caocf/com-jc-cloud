package org.jc;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jc.DistributedLocksTest.TestDLock;

public class PingServerTest {
	public static void main(String[] args) throws UnknownHostException,
			IOException {

		ExecutorService exec = Executors.newCachedThreadPool();// 线程池的大小会根据执行的任务数动态分配
		exec.submit(new PingServer(1, 30));
		exec.submit(new PingServer(30, 60));
		exec.submit(new PingServer(60, 90));
		exec.submit(new PingServer(90, 120));
		exec.submit(new PingServer(120, 150));
		exec.submit(new PingServer(150, 180));
		exec.submit(new PingServer(180, 210));
		exec.submit(new PingServer(210, 240));
		exec.submit(new PingServer(240, 255));
		exec.shutdown();

	}

	static class PingServer implements Runnable {
		private int start;
		private int end;

		public PingServer(int start, int end) {
			this.start = start;
			this.end = end;
		}

		@Override
		public void run() {
			boolean status;
			for (int i = start; i < end; i++) {
				String host = "192.168.128." + String.valueOf(i);
				int timeOut = 2000; // 超时应该在3钞以上
				try {
					status = InetAddress.getByName(host).isReachable(timeOut);
					if (status) {
						System.out.println(host);
						// break;
					} else {
						// System.out.println("false:" + host);
					}
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}
}
