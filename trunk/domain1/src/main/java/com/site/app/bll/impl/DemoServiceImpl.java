package com.site.app.bll.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.site.app.bll.DemoService;
@Service("demoService")
public class DemoServiceImpl implements DemoService {

	@Override
	public String sayHello(String name) {
		// TODO Auto-generated method stub
		return "ok";
	}

	@Override
	public List getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

}
