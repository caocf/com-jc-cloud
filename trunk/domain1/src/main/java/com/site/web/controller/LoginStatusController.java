package com.site.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jc.domain.UserEntity;
import com.site.web.base.BaseController;

@Controller
@RequestMapping("/login")
public class LoginStatusController extends BaseController {
	@RequestMapping("/status")
	public String status(HttpServletRequest request) {
		UserEntity user = this.GetLoginUserInfo(request);
		if (user != null) {
			request.setAttribute("userName", user.getUserName());
			request.setAttribute("userId", user.getUserID());
		}
		return "login/pages/status";
	}
}
