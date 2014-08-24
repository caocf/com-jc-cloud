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

import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import javax.imageio.*;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 生成验证码并保存到session，key为ValidateCode<br>
 * 在web.xml里面配置servlet.然后页面通过src="../servlet/validateCode"获取
 * 
 * @author chenzhao
 * @version 1.0, 2012-12-17
 * @see

 */
public class ValidateCodeUtils extends HttpServlet implements Servlet {

	
	/**
	 * {方法的功能/动作描述}
	
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		getCode(req, resp);
	}


	/**
	 * {方法的功能/动作描述}
	
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	private static final long serialVersionUID = -2876356395262984473L;


	/**
	 *  获得验证码字符串
	
	 * @param i
	 * @return
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
	private static String getRandStr(int i) {
		String lStr = "0125abcefg69l4medjkxn78hiyvq3rswzoptu";
		char c = lStr.charAt(i);
		return c + "";
	}


	/**
	 * 客户端请求获得验证码
	
	 * @param request
	 * @param response
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
	public static void getCode(HttpServletRequest request,
			HttpServletResponse response) {
		getCode(new Color(0xDF, 0xE3, 0xCE), new Color(0x39, 0x39, 0x12),
				"bpoValidateCode", request, response);
	}

	/**
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 * 
	 * @param bgColor
	 * @param fontColor
	 * @param sessionName
	 * @param request
	 * @param response
	 */
	public static void getCode(Color bgColor, Color fontColor,
			String sessionName, HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		HttpSession session = request.getSession();

		int width = 58, height = 18;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Random random = new Random();
		g.setColor(bgColor);
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("宋体", Font.PLAIN, 14));

		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		String sRand = "";
		for (int i = 0; i < 5; i++) {
			String rand = getRandStr(random.nextInt(36));
			sRand += rand;
			g.setColor(fontColor);
			g.drawString(rand, 10 * i + 6, 13);
		}

		session.setAttribute(sessionName, sRand);
		g.dispose();
		response.reset();
		try {
			OutputStream os = response.getOutputStream();
			ImageIO.write(image, "JPEG", os);
			os.flush();
			os.close();
			os = null;
			response.flushBuffer();
		} catch (Exception e) {

		}

	}
}
