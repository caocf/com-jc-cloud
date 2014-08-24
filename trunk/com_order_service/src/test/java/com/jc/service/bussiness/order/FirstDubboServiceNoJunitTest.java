package com.jc.service.bussiness.order;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import service.provider.ApplicationStart;

import com.jc.order.service.bussiness.order.IFirstDubboService;

public class FirstDubboServiceNoJunitTest {
	static ApplicationContext ctx;

	public static void beforeStart() {
		ApplicationStart.instance();
		ctx = ApplicationStart.ctx;

	}

	private static IFirstDubboService firstDubboService;

	public static void dealSomething() {
		beforeStart();
		firstDubboService = (IFirstDubboService) ctx
				.getBean("firstDubboService");
		String val = firstDubboService.dealSomething("CHENZHAO");
		System.out.println(val);
	}

	public static void main(String[] args) {
		dealSomething();
	}
}
