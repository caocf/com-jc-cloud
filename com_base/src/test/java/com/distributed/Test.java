package com.distributed;

import java.util.ArrayList;
import java.util.List;

public class Test {
	public static void main(String[] args) {
		List<S> LsComputer = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			S s = new S();
			s.setIp("192.168.128.12" + i);
			s.setName("HOST" + i);
			s.setPassword("pwd" + i);
			s.setPort("808" + i);
			LsComputer.add(s);
		}
		Shard<S> s = new Shard<S>(LsComputer);
		S sComputer=s.getShardInfo("wodetian");
		System.out.println(sComputer);
	}
}
