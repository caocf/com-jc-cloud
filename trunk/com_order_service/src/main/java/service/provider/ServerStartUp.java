package service.provider;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.helpers.LogLog;

import utils.GetProperties;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.jc.order.service.bussiness.order.IFirstDubboService;
import com.jc.order.service.bussiness.order.impl.FirstDubboServiceImpl;

public class ServerStartUp {
	private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";

	public static void main(String[] args) throws InterruptedException {
		try {
			ApplicationStart.instance();
		/*	ServiceServer serviceServer = ServiceServer.getInstance();
			serviceServer.beforeStart();
			serviceServer.startUp();*/
			System.out
					.println("Service Server startup successfully. see logs in logs/service.log");
			while (true) {
				Thread.sleep(300 * 1000);
			}
		} catch (Exception ex) {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			LogLog.error(sdf.format(new Date()) + " startup error", ex);
			System.exit(-1);
		}

	}
}
