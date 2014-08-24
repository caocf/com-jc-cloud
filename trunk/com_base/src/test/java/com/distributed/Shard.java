package com.distributed;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.zip.CRC32;

public class Shard<S> { // S类封装了机器节点的信息 ，如name、password、ip、port等

	private TreeMap<Long, S> nodes; // 虚拟节点
	private List<S> shards; // 真实机器节点
	private final int NODE_NUM = 5; // 每个机器节点关联的虚拟节点个数

	public Shard(List<S> shards) {
		super();
		this.shards = shards;
		init();
	}

	private void init() { // 初始化一致性hash环
		nodes = new TreeMap<Long, S>();
		for (int i = 0; i != shards.size(); ++i) { // 每个真实机器节点都需要关联虚拟节点
			final S shardInfo = shards.get(i);

			for (int n = 0; n < NODE_NUM; n++)
				// 一个真实机器节点关联NODE_NUM个虚拟节点
				nodes.put(hash("SHARD-" + i + "-NODE-" + n), shardInfo);

		}
	}

	public S getShardInfo(String key) {
		SortedMap<Long, S> tail = nodes.tailMap(hash(key)); // 沿环的顺时针找到一个虚拟节点
		if (tail.size() == 0) {
			return nodes.get(nodes.firstKey());
		}
		return tail.get(tail.firstKey()); // 返回该虚拟节点对应的真实机器节点的信息
	}

	/**
	 * MurMurHash算法，是非加密HASH算法，性能很高，
	 * 比传统的CRC32,MD5，SHA-1（这两个算法都是加密HASH算法，复杂度本身就很高，带来的性能上的损害也不可避免）
	 * 等HASH算法要快很多，而且据说这个算法的碰撞率很低. http://murmurhash.googlepages.com/
	 */
	private Long hash(String key) {

		CRC32 checksum = new CRC32();
		checksum.update(key.getBytes());
		int crc = (int) checksum.getValue();
		return (long) ((crc >> 16) & 0x7fff);
	}

}