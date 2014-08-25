package com.site.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jc.domain.OrderEntity;
import com.jc.domain.UserEntity;
import com.jc.order.service.bussiness.order.IFirstDubboService;
import com.site.web.base.BaseController;

@Controller
@RequestMapping("/index")
public class IndexController extends BaseController {
	@Autowired
	private IFirstDubboService firstDubboService;

	@RequestMapping("/ok")
	public String helloworld(HttpServletRequest request) {
		String hello = firstDubboService.dealSomething("toasfdasdfm");
		System.out.println(hello);
    	List<OrderEntity> listOrderEntity = firstDubboService.getOrderList(10);
		for (OrderEntity model : listOrderEntity) {
			System.out.println("订单号:" + model.getOrdernumber());
		}
		UserEntity user = this.GetLoginUserInfo(request);
		request.setAttribute("UserName", user.getUserName()+"OK");
		return "index/pages/ok";
	}
	
	@RequestMapping("/logout")
	public String mq(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//mqManage.sendMq("ORDERINFOLIST", "ARE YOU READY");
		request.getSession().invalidate();
		return "redirect:http://member.server.com/logout?service=http%3A%2F%2Fproduct.server.com%3A8888%2Fdomain2%2Findex%2Fok";
	}
}
