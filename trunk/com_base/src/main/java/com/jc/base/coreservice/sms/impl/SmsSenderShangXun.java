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
package com.jc.base.coreservice.sms.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jc.base.coreservice.sms.SmsConstants;

/**
 * 
 * 短信发送具体实现抽象类 <br>
 * 
 * 
 * @author chenzhao
 * @version 1.0 , 2012-12-17
 * @see
 */
@Component("smsSenderShangXun")
public class SmsSenderShangXun {
	@Value("#{sysConfig['sms.user.api.sunivo.com']}")
	private  String UserName = "";
	@Value("#{sysConfig['sms.password.api.sunivo.com']}")
	private  String passWord = "";
	public SmsSenderShangXun()
	{
		
	}
	/**
	 * 短信发送具体实现
	 * 
	 * @throws Exception
	 * 
	 * @see org.sunivo.service.net.sms.SmsSender#sendSms(java.lang.String,
	 *      java.lang.String)
	 */
	public int sendSms(String telephone, String smsConents,String sendTime) throws Exception {
		int result = SmsConstants.FAILED_TO_SEND;
		try {
			SmsSenderShangXun sms = new SmsSenderShangXun(UserName,passWord);
			sms.massSend(telephone, smsConents, sendTime, null, null);
			return SmsConstants.SEND_SUCCESS;
		} catch (Exception ex) {
			return result;
		}
	}

/*	public SmsSenderShangXun() {
		this(UserName, passWord);
	}*/

	public SmsSenderShangXun(String name, String pwd) {
		comName = name;
		comPwd = pwd;
		Server = "http://www.china-sms.com";
	}

	public SmsSenderShangXun(String name, String pwd, int serverNum) {
		comName = name;
		comPwd = pwd;
		if (serverNum == 2)
			Server = "http://www6.china-sms.com";
		else
			Server = "http://www.china-sms.com";
	}

	public String massSend(String dst, String msg, String time, String subNo,
			String txt) {
		String sUrl = null;
		try {
			sUrl = Server + "/send/gsend.asp?name=" + comName + "&pwd="
					+ comPwd + "&dst=" + dst + "&msg="
					+ URLEncoder.encode(msg, "GB2312") + "&time=" + URLEncoder.encode(time,"GB2312")
					+ "&sender=" + subNo + "&txt=" + txt;// 这里必须GB2312否则发到手机乱码


		} catch (UnsupportedEncodingException uee) {
			System.out.println(uee.toString());
		}

		return getUrl(sUrl);
	}

	public String readSms() {
		String sUrl = null;
		sUrl = Server + "/send/readsms.asp?name=" + comName + "&pwd=" + comPwd;
		try {
			URLEncoder.encode(sUrl, "GB2312");
		} catch (UnsupportedEncodingException uee) {
			System.out.println(uee.toString());
		}
		return getUrl(sUrl);
	}

	public String getFee() {
		String sUrl = null;
		sUrl = Server + "/send/getfee.asp?name=" + comName + "&pwd=" + comPwd;
		return getUrl(sUrl);
	}

	public String changePwd(String newPwd) {
		String sUrl = null;
		sUrl = Server + "/send/cpwd.asp?name=" + comName + "&pwd=" + comPwd
				+ "&newpwd=" + newPwd;
		try {
			URLEncoder.encode(sUrl, "GB2312");
		} catch (UnsupportedEncodingException uee) {
			System.out.println(uee.toString());
		}
		return getUrl(sUrl);
	}

	public String checkContent(String content) {
		String sUrl = null;
		sUrl = Server + "/send/checkcontent.asp?name=" + comName + "&pwd="
				+ comPwd + "&content=" + content;
		try {
			URLEncoder.encode(sUrl, "GB2312");
		} catch (UnsupportedEncodingException uee) {
			System.out.println(uee.toString());
		}
		return getUrl(sUrl);
	}

	public String getUrl(String urlString) {
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			for (String line = null; (line = reader.readLine()) != null;)
				sb.append(line + "\n");

			reader.close();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return sb.toString();
	}

	private String comName;
	private String comPwd;
	private String Server;

}
