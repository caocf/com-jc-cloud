package org.curatorframework;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

public class CuratorTest {
	public static void main(String[] args) throws Exception {
		String path = "/b";
		CuratorFramework client = CuratorClient.getInstance();
		client.start();
		try {
			Stat stat = client.checkExists().forPath(path);
			if (stat == null) {
				
				/*  zkTools.create()//创建一个路径
			       .creatingParentsIfNeeded()//如果指定的节点的父节点不存在，递归创建父节点
			       .withMode(CreateMode.PERSISTENT)//存储类型（临时的还是持久的）
			       .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)//访问权限
			       .forPath("zk/test");//创建的路径
*/				String path1 = client.create().withMode(CreateMode.PERSISTENT)
						.forPath(path, new String("chenzhao").getBytes());
				System.out.println(path1);
			}
			else
			{
				/*client.inTransaction().create().forPath(path).and().delete().forPath(path).and()
				.commit();*/
				client.setData().inBackground().forPath(path,new String("chenzhao").getBytes());
			}
			System.out.println(new String(client.getData().watched().forPath(path)));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		 // 注册观察者，当节点变动时触发  
	     client.getData().usingWatcher(new Watcher() {  
	         @Override  
	         public void process(WatchedEvent event) {  
	             System.out.println("node is changed");  
	         }  
	     }).inBackground().forPath(path);  
		//client.close();
		System.out.println("done");
		while(true)
		{
			
		}
	}
}
