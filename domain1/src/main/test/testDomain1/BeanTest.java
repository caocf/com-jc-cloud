package testDomain1;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jc.order.service.bussiness.order.IFirstDubboService;
import com.site.app.bll.DemoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/servicexmlconfig/applicationContext.xml",
									"classpath*:/daoxmlconfigs/applicationContext.xml",
									"classpath*:/application/applicationContext.xml"})
public class BeanTest extends AbstractJUnit4SpringContextTests {
	@Autowired
	private IFirstDubboService firstDubboService;
	@Test
	public void dealSomething()
	{
		String val=firstDubboService.dealSomething("CHENZHAO");
		Assert.assertEquals(val,"CHENZHAO:jackychen2");
	}
}
