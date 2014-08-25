package com.site.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jc.domain.EmployeeEntity;
import com.jc.domain.UserEntity;
import com.jc.order.service.bussiness.order.IFirstDubboService;
import com.jc.order.service.bussiness.order.impl.FirstDubboServiceImpl;
import com.site.web.base.BaseController;

@Controller
@RequestMapping("/index")
public class IndexController extends BaseController {
	@Autowired
	private IFirstDubboService firstDubboService;
	@RequestMapping("/ok")
	public String helloworld(HttpServletRequest request) {
		//request.getSession().setAttribute("uocl", uocl);//
		String hello = firstDubboService.dealSomething("enen,1focus");
		//EmployeeEntity entity=employeeDao.getById(1);
		System.out.println(hello);
		/*List<OrderEntity> listOrderEntity=firstDubboService.getOrderList(10);
		for(OrderEntity model:listOrderEntity)
		{
			//System.out.println("订单号1d2:"+model.getOrdernumber());
		}*/
		UserEntity user=this.GetLoginUserInfo(request);
		String ok="test";
		request.setAttribute("UserName",user.getUserName()+"a_1_"+ok);
		return "index/pages/ok";
	}
	@RequestMapping("/logout")
	public String mq(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//mqManage.sendMq("ORDERINFOLIST", "ARE YOU READY");
		request.getSession().invalidate();
		return "redirect:http://member.server.com/logout?service=http%3A%2F%2Forder.server.com%3A8011%2Fdomain1%2Findex%2Fok";
	}
}
