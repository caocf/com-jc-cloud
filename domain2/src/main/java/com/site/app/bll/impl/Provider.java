package com.site.app.bll.impl;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Provider {

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml" });
		context.start();
		System.in.read(); // 为保证服务一直开�?��利用输入流的阻塞来模�?
	}
}