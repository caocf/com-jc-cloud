package org.jackychen.toolkits.session.zookeeper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.jackychen.toolkits.session.config.Configuration;
import org.jackychen.toolkits.session.helper.ConnectionWatcher;
import org.jackychen.toolkits.session.metadata.SessionMetaData;

public class ZooKeeperHelper {
	/** 日志 */
	private static Logger log = Logger.getLogger(ZooKeeperHelper.class);
	private static String hosts;
	private static ExecutorService pool = Executors.newCachedThreadPool();
	private static final String GROUP_NAME = "/SESSIONS";

	/**
	 * 初始化
	 */
	public static void initialize(Configuration config) {
		hosts = config.getServers();
	}

	/**
	 * 销毁
	 */
	public static void destroy() {
		if (pool != null) {
			// 关闭
			pool.shutdown();
		}
	}

	/**
	 * 连接服务器
	 * 
	 * @return
	 */
	public static ZooKeeper connect() {
		ConnectionWatcher cw = new ConnectionWatcher();
		ZooKeeper zk = cw.connection(hosts);
		return zk;
	}

	/**
	 * 关闭一个会话
	 */
	public static void close(ZooKeeper zk) {
		if (zk != null) {
			try {
				zk.close();
			} catch (InterruptedException e) {
				log.error(e);
			}
		}
	}

	/**
	 * 验证指定ID的节点是否有效
	 * 
	 * @param id
	 * @return
	 */
	public static boolean isValid(String id) {
		ZooKeeper zk = connect();
		if (zk != null) {
			try {
				return isValid(id, zk);
			} finally {
				close(zk);
			}
		}
		return false;
	}

	/**
	 * 验证指定ID的节点是否有效
	 * 
	 * @param id
	 * @param zk
	 * @return
	 */
	public static boolean isValid(String id, ZooKeeper zk) {
		if (zk != null) {
			// 获取元数据
			SessionMetaData metadata = getSessionMetaData(id, zk);
			// 如果不存在或是无效，则直接返回null
			if (metadata == null) {
				return false;
			}
			return metadata.getValidate();
		}
		return false;
	}

	/**
	 * 返回指定ID的Session元数据
	 * 
	 * @param id
	 * @return
	 */
	public static SessionMetaData getSessionMetaData(String id, ZooKeeper zk) {
		if (zk != null) {
			String path = GROUP_NAME + "/" + id;
			try {
				// 检查节点是否存在
				Stat stat = zk.exists(path, false);
				// stat为null表示无此节点
				if (stat == null) {
					return null;
				}
				// 获取节点上的数据
				byte[] data = zk.getData(path, false, null);
				if (data != null) {
					// 反序列化
					Object obj = SerializationUtils.deserialize(data);
					// 转换类型
					if (obj instanceof SessionMetaData) {
						SessionMetaData metadata = (SessionMetaData) obj;
						// 设置当前版本号
						metadata.setVersion(stat.getVersion());
						return metadata;
					}
				}
			} catch (KeeperException e) {
				log.error(e);
			} catch (InterruptedException e) {
				log.error(e);
			}
		}
		return null;
	}

	/**
	 * 更新Session节点的元数据
	 * 
	 * @param id
	 *            Session ID
	 * @param version
	 *            更新版本号
	 * @param zk
	 */
	public static void updateSessionMetaData(String id) {
		ZooKeeper zk = connect();
		try {
			// 获取元数据
			SessionMetaData metadata = getSessionMetaData(id, zk);
			if (metadata != null) {
				updateSessionMetaData(metadata, zk);
			}
		} finally {
			close(zk);
		}
	}

	/**
	 * 更新Session节点的元数据
	 * 
	 * @param id
	 *            Session ID
	 * @param version
	 *            更新版本号
	 * @param zk
	 */
	public static void updateSessionMetaData(SessionMetaData metadata,
			ZooKeeper zk) {
		try {
			if (metadata != null) {
				String id = metadata.getId();
				Long now = System.currentTimeMillis(); // 当前时间
				// 检查是否过期
				Long timeout = metadata.getLastAccessTm()
						+ metadata.getMaxIdle(); // 空闲时间
				// 如果空闲时间小于当前时间，则表示Session超时
				if (timeout < now) {
					metadata.setValidate(false);
					log.debug("Session节点已超时[" + id + "]");
				}
				// 设置最后一次访问时间
				metadata.setLastAccessTm(now);
				// 更新节点数据
				String path = GROUP_NAME + "/" + id;
				byte[] data = SerializationUtils.serialize(metadata);
				zk.setData(path, data, metadata.getVersion());
				log.debug("更新Session节点的元数据完成[" + path + "]");
			}
		} catch (KeeperException e) {
			log.error(e);
		} catch (InterruptedException e) {
			log.error(e);
		}
	}

	/**
	 * 返回ZooKeeper服务器上的Session节点的所有数据，并装载为Map
	 * 
	 * @param id
	 * @return
	 */
	public static Map getSessionMap(String id) {
		ZooKeeper zk = connect();
		if (zk != null) {
			String path = GROUP_NAME + "/" + id;
			try {
				// 获取元数据
				SessionMetaData metadata = getSessionMetaData(path, zk);
				// 如果不存在或是无效，则直接返回null
				if (metadata == null || !metadata.getValidate()) {
					return null;
				}
				// 获取所有子节点
				List nodes = zk.getChildren(path, false);
				// 存放数据
				Map sessionMap = new HashMap();
				for (Object node : nodes) {
					String dataPath = path + "/" + node.toString();
					Stat stat = zk.exists(dataPath, false);
					// 节点存在
					if (stat != null) {
						// 提取数据
						byte[] data = zk.getData(dataPath, false, null);
						if (data != null) {
							sessionMap.put(node,
									SerializationUtils.deserialize(data));
						} else {
							sessionMap.put(node, null);
						}
					}
				}
				return sessionMap;
			} catch (KeeperException e) {
				log.error(e);
			} catch (InterruptedException e) {
				log.error(e);
			} finally {
				close(zk);
			}
		}
		return null;
	}

	/**
	 * 创建一个组节点
	 */
	public static void createGroupNode() {
		ZooKeeper zk = connect();
		if (zk != null) {
			try {
				// 检查节点是否存在
				Stat stat = zk.exists(GROUP_NAME, false);
				// stat为null表示无此节点，需要创建
				if (stat == null) {
					// 创建组件点
					String createPath = zk.create(GROUP_NAME, null,
							Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					log.debug("创建节点完成:[" + createPath + "]");
				} else {
					log.debug("组节点已存在，无需创建[" + GROUP_NAME + "]");
				}
			} catch (KeeperException e) {
				log.error(e);
			} catch (InterruptedException e) {
				log.error(e);
			} finally {
				close(zk);
			}
		}
	}

	/**
	 * 创建指定Session ID的节点
	 * 
	 * @param sid
	 *            Session ID
	 * @return
	 */
	public static String createSessionNode(SessionMetaData metadata) {
		if (metadata == null) {
			return null;
		}
		ZooKeeper zk = connect(); // 连接服务期
		if (zk != null) {
			String path = GROUP_NAME + "/" + metadata.getId();
			try {
				// 检查节点是否存在
				Stat stat = zk.exists(path, false);
				// stat为null表示无此节点，需要创建
				if (stat == null) {
					// 创建组件点
					String createPath = zk.create(path, null,
							Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					log.debug("创建Session节点完成:[" + createPath + "]");
					// 写入节点数据
					zk.setData(path, SerializationUtils.serialize(metadata), -1);
					return createPath;
				}
			} catch (KeeperException e) {
				log.error(e);
			} catch (InterruptedException e) {
				log.error(e);
			} finally {
				close(zk);
			}
		}
		return null;
	}

	/**
	 * 创建指定Session ID的节点(异步操作)
	 * 
	 * @param sid
	 * @param waitFor
	 *            是否等待执行结果
	 * @return
	 */
	public static String asynCreateSessionNode(final SessionMetaData metadata,
			boolean waitFor) {
		Callable task = new Callable() {
			@Override
			public String call() throws Exception {
				return createSessionNode(metadata);
			}
		};
		try {
			Future result = pool.submit(task);
			// 如果需要等待执行结果
			if (waitFor) {
				while (true) {
					if (result.isDone()) {
						return (String) result.get();
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	/**
	 * 删除指定Session ID的节点
	 * 
	 * @param sid
	 *            Session ID
	 * @return
	 */
	public static boolean deleteSessionNode(String sid) {
		ZooKeeper zk = connect(); // 连接服务期
		if (zk != null) {
			String path = GROUP_NAME + "/" + sid;
			try {
				// 检查节点是否存在
				Stat stat = zk.exists(path, false);
				// 如果节点存在则删除之
				if (stat != null) {
					// 先删除子节点
					List nodes = zk.getChildren(path, false);
					if (nodes != null) {
						for (Object node : nodes) {
							zk.delete(path + "/" + node.toString(), -1);
						}
					}
					// 删除父节点
					zk.delete(path, -1);
					log.debug("删除Session节点完成:[" + path + "]");
					return true;
				}
			} catch (KeeperException e) {
				log.error(e);
			} catch (InterruptedException e) {
				log.error(e);
			} finally {
				close(zk);
			}
		}
		return false;
	}

	/**
	 * 删除指定Session ID的节点(异步操作)
	 * 
	 * @param sid
	 * @param waitFor
	 *            是否等待执行结果
	 * @return
	 */
	public static boolean asynDeleteSessionNode(final String sid,
			boolean waitFor) {
		Callable task = new Callable() {
			@Override
			public Boolean call() throws Exception {
				return deleteSessionNode(sid);
			}
		};
		try {
			Future result = pool.submit(task);
			// 如果需要等待执行结果
			if (waitFor) {
				while (true) {
					if (result.isDone()) {
						return (Boolean) result.get();
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
		return false;
	}

	/**
	 * 在指定Session ID的节点下添加数据节点
	 * 
	 * @param sid
	 *            Session ID
	 * @param name
	 *            数据节点的名称
	 * @param value
	 *            数据
	 * @return
	 */
	public static boolean setSessionData(String sid, String name, Object value) {
		boolean result = false;
		ZooKeeper zk = connect(); // 连接服务器
		if (zk != null) {
			String path = GROUP_NAME + "/" + sid;
			try {
				// 检查指定的Session节点是否存在
				Stat stat = zk.exists(path, false);
				// 如果节点存在则删除之
				if (stat != null) {
					// 查找数据节点是否存在，不存在就创建一个
					String dataPath = path + "/" + name;
					stat = zk.exists(dataPath, false);
					if (stat == null) {
						// 创建数据节点
						zk.create(dataPath, null, Ids.OPEN_ACL_UNSAFE,
								CreateMode.PERSISTENT);
						log.debug("创建数据节点完成[" + dataPath + "]");
					}
					// 在节点上设置数据，所有数据必须可序列化
					if (value instanceof Serializable) {
						int dataNodeVer = -1;
						if (stat != null) {
							// 记录数据节点的版本
							dataNodeVer = stat.getVersion();
						}
						byte[] data = SerializationUtils
								.serialize((Serializable) value);
						stat = zk.setData(dataPath, data, dataNodeVer);
						log.debug("更新数据节点数据完成[" + dataPath + "][" + value + "]");
						result = true;
					}
				}
			} catch (KeeperException e) {
				log.error(e);
			} catch (InterruptedException e) {
				log.error(e);
			} finally {
				close(zk);
			}
		}
		return result;
	}

	/**
	 * 删除指定Session ID的节点(异步操作)
	 * 
	 * @param sid
	 * @param waitFor
	 *            是否等待执行结果
	 * @return
	 */
	public static boolean asynSetSessionData(final String sid,
			final String name, final Object value, boolean waitFor) {

		Callable task = new Callable() {
			@Override
			public Boolean call() throws Exception {
				return setSessionData(sid, name, value);
			}
		};
		try {
			Future result = pool.submit(task);
			// 如果需要等待执行结果
			if (waitFor) {
				while (true) {
					if (result.isDone()) {
						return (Boolean) result.get();
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
		return false;
	}

	/**
	 * 返回指定Session ID的节点下数据
	 * 
	 * @param sid
	 *            Session ID
	 * @param name
	 *            数据节点的名称
	 * @param value
	 *            数据
	 * @return
	 */
	public static Object getSessionData(String sid, String name) {
		ZooKeeper zk = connect(); // 连接服务器
		if (zk != null) {
			String path = GROUP_NAME + "/" + sid;
			try {
				// 检查指定的Session节点是否存在
				Stat stat = zk.exists(path, false);
				if (stat != null) {
					// 查找数据节点是否存在
					String dataPath = path + "/" + name;
					stat = zk.exists(dataPath, false);
					Object obj = null;
					if (stat != null) {
						// 获取节点数据
						byte[] data = zk.getData(dataPath, false, null);
						if (data != null) {
							// 反序列化
							obj = SerializationUtils.deserialize(data);
						}
					}
					return obj;
				}
			} catch (KeeperException e) {
				log.error(e);
			} catch (InterruptedException e) {
				log.error(e);
			} finally {
				close(zk);
			}
		}
		return null;
	}

	/**
	 * 删除指定Session ID的节点下数据
	 * 
	 * @param sid
	 *            Session ID
	 * @param name
	 *            数据节点的名称
	 * @param value
	 *            数据
	 * @return
	 */
	public static void removeSessionData(String sid, String name) {
		ZooKeeper zk = connect(); // 连接服务器
		if (zk != null) {
			String path = GROUP_NAME + "/" + sid;
			try {
				// 检查指定的Session节点是否存在
				Stat stat = zk.exists(path, false);
				if (stat != null) {
					// 查找数据节点是否存在
					String dataPath = path + "/" + name;
					stat = zk.exists(dataPath, false);
					if (stat != null) {
						// 删除节点
						zk.delete(dataPath, -1);
					}
				}
			} catch (KeeperException e) {
				log.error(e);
			} catch (InterruptedException e) {
				log.error(e);
			} finally {
				close(zk);
			}
		}
	}
}