package service.consumer;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.jc.order.service.bussiness.order.IFirstDubboService;
import com.jc.order.service.bussiness.order.impl.FirstDubboServiceImpl;

public class InitConsumer {
	public static void main(String[] args) throws InterruptedException {
		// 当前应用配置
				ApplicationConfig application = new ApplicationConfig();
				application.setName("domain1_consumer");
				// 连接注册中心配置
				RegistryConfig registry = new RegistryConfig();
				registry.setAddress("192.168.128.131:2181");
				registry.setProtocol("zookeeper");
				registry.setUsername("jackychen");
				registry.setPassword("chenzhao");
				// 注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接
				// 引用远程服务
				ProtocolConfig protocol = new ProtocolConfig();
				protocol.setName("dubbo");
				protocol.setPort(20080);
				protocol.setThreads(200);
				
				ReferenceConfig<IFirstDubboService> reference = new ReferenceConfig<IFirstDubboService>();
				// 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
				reference.setApplication(application);
				reference.setRegistry(registry);// 多个注册中心可以用setRegistries()
				reference.setInterface(IFirstDubboService.class);
				//reference.setVersion("1.0.0");
				// 和本地bean一样使用xxxService
				IFirstDubboService xxxService = reference.get();// 注意：此代理对象内部封装了所有通讯细节，对象较重，请缓存复用
				String str=xxxService.dealSomething("jackyasdfchen");
				System.out.println(str.toString());
		}
}
