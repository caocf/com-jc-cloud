/**
 * Description: //模块目的、功能描述 
 * Copyright:	Copyright(C), 2002-2013
 * Company:		SUNIVO
 * User:		lukejia
 * Version:		1.0
 * Create at:	13-9-23 上午11:27
 *
 * Modification History:
 * Date			User		Version		Description
 * --------------------------------------------------------
 * 13-9-23		lukejia		1.0			Initial
 */
package com.jc.base.coresafe.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 功能详细描述:<br>
 * 〈功能详细描述〉
 * <p/>
 * User		lukejia
 * Version	1.0
 * Date		13-9-23
 * Since	[产品/模块版本]
 */
public class ReverseXSSFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		WrapperedResponse wrapper = new WrapperedResponse((HttpServletResponse) response);
		chain.doFilter(request, wrapper);
		byte[] b1 = wrapper.getResponseData();
		//输出处理后的数据
		ServletOutputStream output = response.getOutputStream();
		output.write(b1);
		output.flush();
		output.close();
	}

	@Override
	public void destroy() {
	}
}
