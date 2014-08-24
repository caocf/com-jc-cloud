package com.jc.order.service.bussiness.order.impl;
import org.springframework.stereotype.Service;

import com.jc.order.service.bussiness.order.ITestBean;

@Service
public class TestBeanImpl implements ITestBean {
	@Override
	public String dealSomething(String val) {
		
		return val+":"+"sdfasdf";
	}

}
