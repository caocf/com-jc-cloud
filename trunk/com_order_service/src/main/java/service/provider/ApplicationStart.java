package service.provider;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationStart {
	public static ApplicationContext ctx;
	public static class ApplicationStartInstance {
		public static final  ApplicationStart app = new ApplicationStart();
	}
	public static ApplicationStart instance()
	{
		return ApplicationStartInstance.app;
	}
	public ApplicationStart() {
		 ctx = new ClassPathXmlApplicationContext(
			  "classpath*:/servicexmlconfig/applicationContext.xml",
			  "classpath*:/servicexmlconfig/applicationContext-serviceprovider.xml",
			  "classpath*:/daoxmlconfigs/applicationContext.xml");
	/*	ctx.start();*/
	}
}
