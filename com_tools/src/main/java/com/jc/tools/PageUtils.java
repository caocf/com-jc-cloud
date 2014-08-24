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
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

/**
 * 页面工具类
 * 〈功能详细描述〉
 *
 * @author chenzhao
 * @version [版本号, 2012-12-17]


 */
public class PageUtils {
	/**
	 *输出JS
	 * 〈功能详细描述〉
	 *
	 * @param body
	 * @return

	
	 */
	public static String outjs(String body) {
		String script = "<script>\n" + body + "</script>\n";
		return script;
	}

	/**
	 * 输出错误
	 * 〈功能详细描述〉
	 *
	 * @param alertText
	 * @return

	
	 */
	public static String outJsErr(String alertText) {
		String body = "window.alert('" + alertText + "');" + "history.back();";
		String script = outjs(body);
		return script;
	}

	/**
	 * 输出警告
	 * 〈功能详细描述〉
	 *
	 * @param alertText
	 * @return

	
	 */
	public static String outJsAlert(String alertText) {
		String body = "window.alert('" + alertText + "');";
		String script = outjs(body);
		return script;
	}

	/**
	 * JS转向
	 * 〈功能详细描述〉
	 *
	 * @param url
	 * @param tisText
	 * @return

	
	 */
	public static String outJsRedirect(String url, String tisText) {
		String body = "";
		if (tisText != null) {
			body = body + "window.alert('" + tisText + "');";
		}
		body = body + "document.location.replace('" + url + "');";
		String script = outjs(body);
		return script;
	}

	/**
	 * 格式化HTML
	 * 〈功能详细描述〉
	 *
	 * @param srcString
	 * @return

	
	 */
	public static String formatHtml(String srcString) {
		srcString = TextUtils.strReplace(srcString, "'", "&#039;");
		srcString = TextUtils.strReplace(srcString, "\"", "&quot;");
		srcString = TextUtils.strReplace(srcString, "<", "&lt;");
		srcString = TextUtils.strReplace(srcString, ">", "&gt;");
		return srcString;
	}

	/**
	 *还原格式化HTML
	 * 〈功能详细描述〉
	 *
	 * @param srcString
	 * @return

	
	 */
	public static String unformatHtml(String srcString) {
		srcString = TextUtils.strReplace(srcString, "&#039;", "'");
		srcString = TextUtils.strReplace(srcString, "&quot;", "\"");
		srcString = TextUtils.strReplace(srcString, "&lt;", "<");
		srcString = TextUtils.strReplace(srcString, "&gt;", ">");
		return srcString;
	}

	/**
	 * Servlet输出页面
	 * 〈功能详细描述〉
	 *
	 * @param res
	 * @param html
	 * @throws IOException

	
	 */
	public static void htmlPage(HttpServletResponse res, String html)
			throws IOException {
		res.setContentType("text/html;charset=utf-8");

		PrintWriter out = res.getWriter();
		out.println(html);
		out.flush();
		out.close();
	}

	/**
	 *  \n -->
	 * 〈功能详细描述〉
	 *
	 * @param str
	 * @return

	
	 */
	public static String n2br(String str) {
		str = TextUtils.strReplace(str, "\n", "<br/>");
		return str;
	}

	/**
	 *  截取字符串
	 * 〈功能详细描述〉
	 *
	 * @param str
	 * @param num
	 * @return

	
	 */
	public static String cutStr(String str, int num) {
		if (str.length() > num) {
			str = str.substring(0, num);
		}
		return str;
	}
}
