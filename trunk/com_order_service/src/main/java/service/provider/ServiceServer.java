package service.provider;

import org.apache.log4j.Logger;


import utils.GetProperties;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.jc.order.service.bussiness.order.IFirstDubboService;
import com.jc.order.service.bussiness.order.impl.FirstDubboServiceImpl;

public class ServiceServer {
	private static final long LOG_WATCH_DELAY = 60000L;
	private static final String NAME="com_service_order";
	private static String REGISTRY_HOST="";
	private static String REGISTRY_ZOOKEEPER_HOST="";
	private static final Logger LOGGER = Logger.getLogger("ServiceServer");
	private static final ServiceServer INSTANCE = new ServiceServer();
	public static final ServiceServer getInstance() {
		return INSTANCE;
	}
	public void beforeStart() {
	/*	String home = SystemConfig.getHomePath();
		Log4jInitializer.configureAndWatch(home + "/conf/log4j.xml",
				LOG_WATCH_DELAY);*/
	}
	public void startUp()
	{
		// server startup
				LOGGER.info("===============================================");
				LOGGER.info(NAME + " is ready to startup ...");
				REGISTRY_ZOOKEEPER_HOST=GetProperties.readValue("zookeeper.registry");
				REGISTRY_HOST=GetProperties.readValue("host");
				LOGGER.info("Registry Host:"+REGISTRY_HOST);
				LOGGER.info("Registry ZOOKEEPER_Host:"+REGISTRY_ZOOKEEPER_HOST);
				IFirstDubboService firstDubboService = new FirstDubboServiceImpl();
				ApplicationConfig application = new ApplicationConfig();
				application.setName("com_service_order_provider");
				RegistryConfig registry = new RegistryConfig();
				registry.setAddress(GetProperties.readValue("zookeeper.registry"));
				System.out.println("Registry Address:"+GetProperties.readValue("zookeeper.registry"));
				ProtocolConfig protocol=new ProtocolConfig();
				protocol.setHost(GetProperties.readValue("host"));
				System.out.println("Registry Address:"+GetProperties.readValue("host"));
				protocol.setPort(22288);
				ServiceConfig<IFirstDubboService> service = new ServiceConfig<IFirstDubboService>();
				service.setApplication(application);
				service.setRegistry(registry);//
				service.setInterface(IFirstDubboService.class);
				service.setRef(firstDubboService);
				service.setProtocol(protocol);
				//service.setVersion("1.0.0");
				service.export();
				LOGGER.info("===============================================");
				LOGGER.info(NAME + " Registry Provider successfully");
	}
}
