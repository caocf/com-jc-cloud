/**  
 * Description: <类功能描述-必填> 
 * Copyright:   Copyright (c)2012  
 * Company:     ChunYu 
 * @author:     ChenZhao  
 * @version:    1.0  
 * Create at:   2012-12-21 下午4:22:51  
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2012-12-21   ChenZhao      1.0       如果修改了;必填  
 */ 
package com.jc.tools;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;



/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author chenzhao
 * @version [版本号, 2012-12-18]


 */
public class ProjectToken extends SimpleTagSupport {

	/**
	 */
	private String name;

	/**
	 */
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/** (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	@Override
	public void doTag() throws JspException, IOException {
		PageContext pc = (PageContext)this.getJspContext();
		JspWriter out = pc.getOut();
		long token = System.currentTimeMillis();
		pc.getSession().setAttribute("token",token); 
		out.println("<input type='hidden' value='"+token+"' name='token'/>");

	}
}
