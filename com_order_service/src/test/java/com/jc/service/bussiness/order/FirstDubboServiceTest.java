package com.jc.service.bussiness.order;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jc.order.service.bussiness.order.IFirstDubboService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/servicexmlconfig/applicationContext.xml","classpath*:/daoxmlconfigs/applicationContext.xml"})
public class FirstDubboServiceTest extends AbstractJUnit4SpringContextTests {
	@Autowired
	private IFirstDubboService firstDubboService;
	@Test
	public void dealSomething()
	{
		String val=firstDubboService.dealSomething("enen");
		Assert.assertEquals(val,"enen:jackychen2some");
	}
}
