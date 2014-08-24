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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;


/**
 * Request工具类
 * 〈功能详细描述〉
 *
 * @author chenzhao
 * @version [版本号, 2012-12-17]


 */
public class RequestUtil {
	/**
	 * 返回参数，并且把null-->""
	 * 〈功能详细描述〉
	 *
	 * @param request
	 * @param prams
	 * @return

	
	 */
	public static String getPramsAsString(HttpServletRequest request,
			String prams) {
		String dist = "";
		if (request.getParameter(prams) != null) {
			dist = request.getParameter(prams).trim();
		}
		return dist;
	}

	/**
	 * 返回参数，并且把null-->""
	 * 〈功能详细描述〉
	 *
	 * @param request
	 * @param prams
	 * @return

	
	 */
	public static int getPramsAsInt(HttpServletRequest request, String prams) {
		int dist = 1;
		if (request.getParameter(prams) != null) {
			dist = Integer.parseInt(request.getParameter(prams));
		}
		return dist;
	}

	/**
	 * 返回Float类型的参数
	 * 〈功能详细描述〉
	 *
	 * @param request
	 * @param prams
	 * @return

	
	 */
	public static float getPramsAsFloat(HttpServletRequest request, String prams) {
		float dist = 1.0F;
		if (request.getParameter(prams) != null) {
			dist = Float.parseFloat(request.getParameter(prams));
		}
		return dist;
	}

	/**
	 * 返回Float类型的参数
	 * 〈功能详细描述〉
	 *
	 * @param request
	 * @param prams
	 * @return

	
	 */
	public static double getPramsAsDouble(HttpServletRequest request,
			String prams) {
		double dist = 1.0D;
		if (request.getParameter(prams) != null) {
			dist = Double.parseDouble(request.getParameter(prams));
		}
		return dist;
	}

	/**
	 * 返回java.util.Date类型的参数
	 * 〈功能详细描述〉
	 *
	 * @param request
	 * @param prams
	 * @param format
	 * @return

	
	 */
	public static Date getPramsAsDateTime(HttpServletRequest request,
			String prams, String format) {
		Date dist = null;
		if (request.getParameter(prams) != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			try {
				dist = formatter.parse(request.getParameter(prams));
			} catch (ParseException localParseException) {
			}
		}
		return dist;
	}

	/**
	 * 获得字符串，以UTF-8编码
	 * 〈功能详细描述〉
	 *
	 * @param request
	 * @param prams
	 * @return
	 * @throws IOException
	 * @throws ServletException

	
	 */
	public static String getPramsByUTF8(HttpServletRequest request, String prams)
			throws IOException, ServletException {
		String dist = "";
		if (request.getParameter(prams) != null) {
			dist = new String(request.getParameter(prams).trim().getBytes(
					"iso-8859-1"), "utf-8");
		}
		return dist;
	}

	/**
	 * 获得字符串，以GBK编码
	 * 〈功能详细描述〉
	 *
	 * @param request
	 * @param prams
	 * @return
	 * @throws IOException
	 * @throws ServletException

	
	 */
	public static String getPramsByGBK(HttpServletRequest request, String prams)
			throws IOException, ServletException {
		String dist = "";
		if (request.getParameter(prams) != null) {
			dist = new String(request.getParameter(prams).trim().getBytes(
					"iso-8859-1"), "GBK");
		}
		return dist;
	}

	/**
	 * 获得字符串数组
	 * 〈功能详细描述〉
	 *
	 * @param request
	 * @param prams
	 * @param code
	 * @return
	 * @throws IOException
	 * @throws ServletException

	
	 */
	public static String[] getPramsAsArray(HttpServletRequest request,
			String prams, String code) throws IOException, ServletException {
		String[] dist = (String[]) null;
		if (request.getParameterValues(prams) != null) {
			String[] strArray = request.getParameterValues(prams);
			if (code != null) {
				for (int i = 0; i < strArray.length; i++)
					dist[i] = new String(request.getParameter(prams).trim()
							.getBytes("iso-8859-1"), code);
			} else {
				dist = strArray;
			}
		}
		return dist;
	}

	/**
	 * 获得完整的URL路径
	 * 〈功能详细描述〉
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws ServletException

	
	 */
	public static String getURL(HttpServletRequest request) throws IOException,
			ServletException {
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort();
		Enumeration e = request.getParameterNames();
		String url = "";
		while (e.hasMoreElements()) {
			String prams = e.nextElement().toString();
			if (!prams.equals("page")) {
				url = url + prams + "=" + request.getParameter(prams) + "&";
			}
		}
		if(url.equals(""))
		{
			url = basePath + request.getRequestURI();
		}
		else
		{
			url = basePath + request.getRequestURI() + "?" + url;
		}
		return url;
	}
}
