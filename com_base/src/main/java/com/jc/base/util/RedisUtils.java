/**  
 * Description: <类功能描述-必填> 
 * Copyright:   Copyright (c)2012  
 * Company:     ChunYu 
 * @author:     ChenZhao  
 * @version:    1.0  
 * Create at:   2012-12-21 下午4:22:51  
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2012-12-21   ChenZhao      1.0       如果修改了;必填  
 */
package com.jc.base.util;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author chenzhao
 * @version [版本号, 2012-12-18]


 */
//@Component("redisUtils")
public class RedisUtils {

	private static JedisPool pool;
	static {
		ResourceBundle bundle = ResourceBundle.getBundle("properties/redis");
		if (bundle == null) {
			throw new IllegalArgumentException(
					"[redis.properties] is not found!");
		}
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(Integer.valueOf(bundle
				.getString("redis.pool.maxActive")));
		config.setMaxIdle(Integer.valueOf(bundle
				.getString("redis.pool.maxIdle")));
		config.setMaxWait(Long.valueOf(bundle.getString("redis.pool.maxWait")));
		config.setTestOnBorrow(Boolean.valueOf(bundle
				.getString("redis.pool.testOnBorrow")));
		config.setTestOnReturn(Boolean.valueOf(bundle
				.getString("redis.pool.testOnReturn")));
		pool = new JedisPool(config, bundle.getString("redis.ip1"),
				Integer.valueOf(bundle.getString("redis.port")));
	}

	/**
	 * 数据源
	 */
	private ShardedJedisPool shardedJedisPool;
	private Jedis jedis;

	/**
	 * ======================================Strings============================
	 * ==========
	 */

	/**
	 * @param pool
	 *            the pool to set
	 */
	public void setPool(JedisPool pool) {
		this.pool = pool;
	}

	/**
	 * @param shardedJedisPool
	 *            the shardedJedisPool to set
	 */
	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}

	/**
	 * @param jedis
	 *            the jedis to set
	 */
	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

	/**
	 * Set the string value as value of the key. The string can't be longer than
	 * 1073741824 bytes (1 GB). Time complexity: O(1)
	 * 
	 * @param key
	 * @param value
	 * @return

	
	 */
	public String setString(String key, String value) {

		Jedis jedis = pool.getResource();
		try {
			return jedis.set(key, value);
		} finally {
			pool.returnResource(jedis);
		}
	}

	/**
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 * 
	 * @param key
	 * @param value
	 * @return

	
	 */
	public String set(byte[] key, byte[] value) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.set(key, value);
		} finally {
			pool.returnResource(jedis);
		}
	}

	/**
	 * 存储JAVABEAN 〈功能详细描述〉 将字符串值 value 关联到 key 。
	 * 
	 * @param key
	 * @param value
	 * @return 总是返回 OK ，因为 SET 不可能失败。

	
	 */
	public String set(String key, Object value) {
		Jedis jedis = pool.getResource();
		try {
			return set(key.getBytes(), SerializeUtils.serializeObject(value));
		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 存储值，并设置有效期 〈功能详细描述〉
	 * 
	 * @param key
	 * @param value
	 * @return

	
	 */
	public Long set(String key, Object value, int seconds) {

		Jedis jedis = pool.getResource();
		try {
			set(key.getBytes(), SerializeUtils.serializeObject(value));
			return expire(key, seconds);

		} finally {
			pool.returnResource(jedis);
		}
	}

	/**
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 * 
	 * @param key
	 * @return

	
	 */
	public byte[] get(byte[] key) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.get(key);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 * 
	 * @param key
	 * @return

	
	 */
	public Object get(String key) {
		Jedis jedis = pool.getResource();
		try {
			return SerializeUtils.deserializeObject(get(key.getBytes()));

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 判断是否存在键值 〈功能详细描述〉
	 * 
	 * @param key
	 * @return

	
	 */
	public Boolean exists(String key) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.exists(key);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 设置键值的有效时间 〈功能详细描述〉
	 * 
	 * @param key
	 * @param seconds
	 * @return

	
	 */
	public Long expire(String key, int seconds) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.expire(key, seconds);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 删除指定的Key
	 * 
	 * @param key
	 * @return 返回Key的数目
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public Long del(String key) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.del(key);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 为key重新命名 原子的重命名一个key，如果newkey存在，将会被覆盖，
	 * 返回1表示成功，0失败。可能是oldkey不存在或者和newkey相同
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public String rename(String oldkey, String newkey) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.rename(oldkey, newkey);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 同上，但是如果newkey存在返回失败
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public String renamenx(String oldkey, String newkey) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.rename(oldkey, newkey);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 返回当前数据库的key数量
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public long dbsize() {

		Jedis jedis = pool.getResource();
		try {
			return jedis.dbSize();

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 返回key值的类型
	 * 
	 * @param key的美称
	 * @return 返回key值的类型
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public String type(String key) {

		Jedis jedis = pool.getResource();
		try {
			return jedis.type(key);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。 假如 key 不存在，则创建一个只包含
	 * member 元素作成员的集合。 当 key 不是集合类型时，返回一个错误。
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public Long sadd(String key, String... members) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.sadd(key, members);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 返回集合 key 的基数(集合中元素的数量)。
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return 集合的基数。/当 key 不存在时，返回 0 。
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public Long scard(String key) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.scard(key);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 返回集合 key 中的所有成员。 不存在的 key 被视为空集合。
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return 集合中的所有成员。
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public Set<String> smembers(String key) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.smembers(key);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 判断 member 元素是否包含集合 key 的成员。
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return 如果 member 元素是集合的成员，返回 1 。/ 如果 member 元素不是集合的成员，或 key 不存在，返回 0 。
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public Boolean sismember(String key, String member) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.sismember(key, member);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 返回一个集合的全部成员，该集合是所有给定集合的交集。 不存在的 key 被视为空集。
	 * 当给定集合当中有一个空集时，结果也为空集(根据集合运算定律)。
	 * 
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return 交集成员的列表。
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public Boolean sinter(String key, String member) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.sismember(key, member);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 返回一个集合的全部成员，该集合是所有给定集合的并集。 不存在的 key 被视为空集。
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public Set<String> sunion(String... keys) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.sunion(keys);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 返回一个集合的全部成员，该集合是所有给定集合之间的差集。 不存在的 key 被视为空集。
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return 交集成员的列表。
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public Set<String> sdiff(String... keys) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.sdiff(keys);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 获取key的匹配值t*;t[ia]st;keys t?st
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public Set<String> keys(String pattern) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.keys(pattern);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 * 
	 * @param key
	 * @param unixTime
	 * @return

	
	 */
	public Long expireAt(String key, long unixTime) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.expireAt(key, unixTime);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 获得一个key的活动时间 〈功能详细描述〉
	 * 
	 * @param key
	 * @return

	
	 */
	public Long ttl(String key) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.ttl(key);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 如果不存在名称为key的string，则向库中添加string，名称为key，值为value 〈功能详细描述〉 如果key已经存在，返回0 。nx
	 * 是not exist的意思
	 * 
	 * @param key
	 * @param value
	 * @return

	
	 */
	public Long setnx(String key, String value) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.setnx(key, value);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 返回所有(一个或多个)给定 key 的值。 如果给定的 key 里面，有某个 key 不存在， 那么这个 key 返回特殊值 nil
	 * 。因此，该命令永不失败。
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public List<String> mget(String... keys) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.mget(keys);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 同时设置一个或多个 key-value 对。 如果某个给定 key 已经存在，那么 MSET 会用新值覆盖原来的旧值，
	 * 如果这不是你所希望的效果，请考虑使用 MSETNX 命令：它只会在所有给定 key 都不存在的情况下进行设置操作。 MSET
	 * 是一个原子性(atomic)操作，所有给定 key 都会在同一时间内被设置， 某些给定 key 被更新而另一些给定 key
	 * 没有改变的情况，不可能发生。
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public String mset(String... keysvalues) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.mset(keysvalues);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 同上，但是不会覆盖已经存在的key
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public Long msetnx(String key, String value) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.setnx(key, value);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 将哈希表 key 中的域 field 的值设为 value 。 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。 如果域
	 * field 已经存在于哈希表中，旧值将被覆盖 HSET website google "www.g.cn"
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public Long hset(String key, String field, String value) {
		Jedis jedis = pool.getResource();
		try {
			if (null != value){
				return jedis.hset(key, field, value);
			} else {
				return 0L;
			}
		} finally {
			pool.returnResource(jedis);
		}
	}

	/**
	 * 将哈希表 key 中的域 field 的值设置为 value ，当且仅当域 field 不存在。 若域 field 已经存在，该操作无效。 如果
	 * key 不存在，一个新哈希表被创建并执行 HSETNX 命令。
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return 设置成功，返回 1 。 如果给定域已经存在且没有操作被执行，返回 0
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public void hsetnx(String key, String field, String value) {
		Jedis jedis = pool.getResource();
		try {
			jedis.hsetnx(key, field, value);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 同时将多个 field-value (域-值)对设置到哈希表 key 中。 此命令会覆盖哈希表中已存在的域。 如果 key
	 * 不存在，一个空哈希表被创建并执行 HMSET 操作。 HMSET website google www.google.com yahoo
	 * www.yahoo.com HGET website google/HGET website yahoo
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return 如果命令执行成功，返回 OK 。/当 key 不是哈希表(hash)类型时，返回一个错误。
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public String hmset(String key, Map<String, String> hash) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.hmset(key, hash);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 返回哈希表 key 中给定域 field 的值。 HSET site redis redis.com redis> HGET site redis
	 * "redis.com"
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return 给定域的值。/当给定域不存在或是给定 key 不存在时，返回 nil 。
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public String hget(String key, String field) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.hget(key, field);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 返回哈希表 key 中，一个或多个给定域的值。 如果给定的域不存在于哈希表，那么返回一个 nil 值。 因为不存在的 key
	 * 被当作一个空哈希表来处理， 所以对一个不存在的 key 进行 HMGET 操作将返回一个只带有 nil 值的表。
	 * 一个包含多个给定域的关联值的表，表值的排列顺序和给定域参数的请求顺序一样。 HMSET pet dog "doudou" cat "nounou"
	 * HMGET pet dog cat fake_pet 1) "doudou" 2) "nounou" 3) (nil)
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public List<String> hmget(String key, String... fields) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.hmget(key, fields);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 返回哈希表 key 中，所有的域和值。 在返回值里，紧跟每个域名(field name)
	 * 之后是域的值(value)，所以返回值的长度是哈希表大小的两倍。 HSET people jack "Jack Sparrow" HSET
	 * people gump "Forrest Gump"
	 * 
	 * HGETALL people 1) "jack" # 域 2) "Jack Sparrow" # 值 3) "gump" 4)
	 * "Forrest Gump"
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return 以列表形式返回哈希表的域和域的值。 若 key 不存在，返回空列表。
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public Map<String, String> hgetall(String key) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.hgetAll(key);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return 被成功移除的域的数量，不包括被忽略的域。
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public Long hdel(String key, String... fields) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.hdel(key, fields);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 返回哈希表 key 中域的数量。 HSET db redis redis.com HSET db mysql mysql.com HLEN
	 * db==>2
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return 哈希表中域的数量。/当 key 不存在时，返回 0 。
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public Long hlen(String key) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.hlen(key);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 查看哈希表 key 中，给定域 field 是否存在。
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return 如果哈希表含有给定域，返回 1 。/如果哈希表不含有给定域，或 key 不存在，返回 0 。
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public boolean hexists(String key, String field) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.hexists(key, field);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * {方法的功能/动作描述}
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public Long hincrby(String key, String field, long value) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.hincrBy(key, field, value);

		} finally {
			pool.returnResource(jedis);
		}

	}

	public Long hincrbyfloat(String key, String field, long value) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.hincrBy(key, field, value);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 返回哈希表 key 中的所有域。 HMSET website google www.google.com yahoo www.yahoo.com
	 * HKEYS website 1) "google" 2) "yahoo"
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return 一个包含哈希表中所有域的表。 当 key 不存在时，返回一个空表。
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public Set<String> hkeys(String key) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.hkeys(key);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 返回哈希表 key 中所有域的值。 HMSET website google www.google.com yahoo www.yahoo.com
	 * OK redis> HVALS website 1) "www.google.com" 2) "www.yahoo.com"
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return 一个包含哈希表中所有值的表。/当 key 不存在时，返回一个空表。
	 * @exception {说明在某情况下,将发生什么异常}
	 */

	public List<String> hvals(String key) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.hvals(key);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 向库中添加string（名称为key，值为value）同时，设定过期时间time 〈功能详细描述〉
	 * 
	 * @param key
	 * @param seconds
	 * @param value
	 * @return

	
	 */
	public String setex(String key, int seconds, String value) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.setex(key, seconds, value);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 名称为key的string增1操作 〈功能详细描述〉
	 * 
	 * @param key
	 * @return

	
	 */
	public Long incr(String key) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.incr(key);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 名称为key的string的值附加value 〈功能详细描述〉
	 * 
	 * @param key
	 * @param value
	 * @return

	
	 */
	public Long append(String key, String value) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.append(key, value);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 返回名称为key的string的value的子串 〈功能详细描述〉
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return

	
	 */
	public String substr(String key, int start, int end) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.substr(key, start, end);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 在名称为key的list尾添加一个值为value的元素 〈功能详细描述〉
	 * 
	 * @param key
	 * @param strings
	 * @return

	
	 */
	public Long rpush(String key, String... strings) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.rpush(key, strings);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 在名称为key的list头添加一个值为value的 元素 〈功能详细描述〉
	 * 
	 * @param key
	 * @param strings
	 * @return

	
	 */
	public long lpush(String key, String... strings) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.lpush(key, strings);
		} finally {
			pool.returnResource(jedis);
		}
	}

	/**
	 * 设置键值的有效期
	 * 
	 * @param {引入参数名} {引入参数说明}
	 * @return {返回参数名} {返回参数说明}
	 * @exception {说明在某情况下,将发生什么异常}
	 * @Author ChenZhao
	 */
	public Long lpush(int seconds, String key, String... strings) {
		Jedis jedis = pool.getResource();
		try {
			lpush(key, strings);
			return expire(key, seconds);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 返回名称为key的list的长度 〈功能详细描述〉
	 * 
	 * @param key
	 * @return

	
	 */
	public Long llen(String key) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.llen(key);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * <功能详细描述>返回名称为key的list中start至end之间的元素（下标从0开始，下同） 返回列表 key 中指定区间内的元素，区间以偏移量
	 * start 和 stop 指定。 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元 素，以
	 * 1 表示列表的第二个元素，以此类推。 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return

	
	 */
	public List<String> lrange(String key, long start, long end) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.lrange(key, start, end);

		} finally {
			pool.returnResource(jedis);
		}
	}

	/**
	 * 截取名称为key的list，保留start至end之间的元素 〈功能详细描述〉
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return

	
	 */
	public String ltrim(String key, long start, long end) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.ltrim(key, start, end);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 返回名称为key的list中index位置的元素 〈功能详细描述〉
	 * 
	 * @param key
	 * @param index
	 * @return

	
	 */
	public String lindex(String key, long index) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.lindex(key, index);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 给名称为key的list中index位置的元素赋值为value 〈功能详细描述〉
	 * 
	 * @param key
	 * @param index
	 * @param value
	 * @return

	
	 */
	public String lset(String key, long index, String value) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.lset(key, index, value);

		} finally {
			pool.returnResource(jedis);
		}
	}

	/**
	 * <功能详细描述>删除count个名称为key的list中值为value的元素。count为0，
	 * 删除所有值为value的元素，count>0从头至尾删除count个值为value的元素，
	 * count<0从尾到头删除|count|个值为value的元素。
	 * 
	 * @param key
	 * @param count
	 * @param value
	 * @return

	
	 */
	public Long lrem(String key, long count, String value) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.lrem(key, count, value);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * 判断是否连接OK 〈功能详细描述〉
	 * 
	 * @return

	
	 */
	public boolean isconnect() {

		Jedis jedis = pool.getResource();
		try {
			jedis.connect();
			return jedis.isConnected();

		} 
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		finally {
			pool.returnResource(jedis);
		}
	}

	/**
	 * 关闭连接 〈功能详细描述〉
	 * 

	
	 */
	public void disconnect() {
		Jedis jedis = pool.getResource();
		try {
			jedis.disconnect();

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * Get the value of the specified key. If the key does not exist the special
	 * value 'nil' is returned. If the value stored at key is not a string an
	 * error is returned because GET can only handle string values. Time
	 * 
	 * @param key
	 * @return

	
	 */
	public String getString(String key) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.get(key);

		} finally {
			pool.returnResource(jedis);
		}

	}

	/**
	 * Return all the fields and associated values in a hash. Time complexity:
	 * O(N), where N is the total number of entries
	 * 
	 * @param key
	 * @return All the fields and values contained into a hash.

	
	 */
	public Map<String, String> hashGetAll(String key) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.hgetAll(key);

		} finally {
			pool.returnResource(jedis);
		}

	}

}
