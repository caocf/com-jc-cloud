package com.jc.dao.app.impl;

import org.springframework.stereotype.Service;

import com.jc.dao.app.ITest;

@Service
public class TestDaoImpl implements ITest {

	@Override
	public String r() {
		return "some";
	}

}
