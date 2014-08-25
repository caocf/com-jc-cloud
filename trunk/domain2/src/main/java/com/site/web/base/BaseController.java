package com.site.web.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;

import com.jc.domain.UserEntity;


public class BaseController {
	private UserEntity user = null;
	private HttpServletRequest request;
	public UserEntity GetLoginUserInfo(HttpServletRequest request) {
		Assertion assertion = (Assertion) request.getSession().getAttribute(
				AbstractCasFilter.CONST_CAS_ASSERTION);
		UserEntity userEnitiy = new UserEntity();
		if (null != assertion) {
			AttributePrincipal principal = assertion.getPrincipal();
			if(null!=principal)
			{
				userEnitiy.setUserID("88");
				userEnitiy.setUserName(principal.getName());
			}
				
		}
		else
		{
			return null;
		}
		return userEnitiy;
	}
}
