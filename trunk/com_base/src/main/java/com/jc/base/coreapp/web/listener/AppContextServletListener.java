package com.jc.base.coreapp.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppContextServletListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		  ServletContext application = sce.getServletContext();
		  application.setAttribute("webSiteKey", "全局变量");
		 System.out.println(sce.getServletContext() + "被创建了");
		 //这里可以准备基础数据
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		 ServletContext application = sce.getServletContext();
		  application.removeAttribute("webSiteKey");
		System.out.println(sce.getServletContext() + "被销毁了");
		//其他的地方可以这样使用
		 /* ServletContext application = event.getSession().getServletContext();
		  Integer num = (Integer) application.getAttribute("onLineNum");*/
	}

}
