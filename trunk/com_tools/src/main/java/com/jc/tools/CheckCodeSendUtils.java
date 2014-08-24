/**  
 * Description: <类功能描述-必填> 验证码发送次数限制器 
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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;



public class CheckCodeSendUtils {

	private static final String MAX_SEND_TIMES = "max.sent.times.per.day";

	private static final Logger logger = Logger.getLogger(CheckCodeSendUtils.class);

	/**
	 * 发送次数
	 */
	private static int sendTimes = 5;

	/**
	 * 保存短信发送次数
	 */
	private static Map<String, DaySendTimes> timesMap = new HashMap<String, DaySendTimes>();

	static {
		init();
	}


	/**
	 * 初始化操作，读取配置文件获取配置项设置，若没有配置项或配置项错误，则使用默认值
	
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
	private static void init() {
		try {
			sendTimes =ArgumentMemoryUtils.getInstance().getIntValueByName(MAX_SEND_TIMES);
			sendTimes = Math.max(1, sendTimes);
		} catch (Exception e) {
			logger.error("读取配置文件中的配置项 [E-mail, SMS Max sent times per day ]发生错误，发送次数参数将获取默认值。");
		}
	}

	/**
	 * 是否超过最多发送次数 : <br>
	 * 
	 *
	 * @param account 邮箱或手机号码
     * @return true 如果超过最多发送次数
	 */
	public static boolean isExceedMaxSendTimes(String account) {

		if (account != null) {
			account = account.trim().toUpperCase();
		}
		DaySendTimes times = (DaySendTimes) timesMap.get(account);
		
		if (times != null) {
			if (now().equals(times.getYyyy_mm_dd())) {
				if (times.getTimes() >= sendTimes) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 发送短信或者邮件时调用此方法（发送成功时添加） ，用以添加发送次数 : <br>
	 * 
	 *
	 * @param account 邮箱地址或手机号码
	 */
	public static void addSendTimes(String account) {
		if (account == null) {
			return;
		}
		account = account.trim().toUpperCase();
		DaySendTimes times = (DaySendTimes) timesMap.get(account);
		if (times == null || (!now().equals(times.getYyyy_mm_dd()))) {
			times = new DaySendTimes();
			timesMap.put(account, times);
		} else {
			times.setTimes(times.getTimes() + 1);
		}

	}

	/**
     * 获取当天向某个帐号已发送的校验码的次数 : <br>
     * 
     *
     * @param account 帐号
     * @return int 当天发送次数
     */
    public static int sendTime(String account) {
        if (account == null) {
            return 0;
        }
        account = account.trim().toUpperCase();
        DaySendTimes times = (DaySendTimes) timesMap.get(account);
        if (times == null || (!now().equals(times.getYyyy_mm_dd()))) {
            return 0;
        }
        else {
            return times.getTimes();
        }
    }


	/**
	 * 封装发送日期及次数<br> 
	 * 
	 *
	 * @author chenzhao
	 * @version [1.0, 2012-12-17]
	 */
	private static final class DaySendTimes {

		public DaySendTimes() {
			this.yyyy_mm_dd = now();
			this.times = 1;
		}

		private String yyyy_mm_dd = "";
		private int times = 0;

		public String getYyyy_mm_dd() {
			return yyyy_mm_dd;
		}

		public int getTimes() {
			return times;
		}

		public void setTimes(int times) {
			this.times = times;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + times;
			result = prime * result
					+ ((yyyy_mm_dd == null) ? 0 : yyyy_mm_dd.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			DaySendTimes other = (DaySendTimes) obj;
			if (times != other.times)
				return false;
			if (yyyy_mm_dd == null) {
				if (other.yyyy_mm_dd != null)
					return false;
			} else if (!yyyy_mm_dd.equals(other.yyyy_mm_dd))
				return false;
			return true;
		}
	}


	/**
	 * 获取当前日期
	
	 * @return
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
	private static String now() {
		GregorianCalendar calendar = new GregorianCalendar();
		StringBuffer now = new StringBuffer();
		now.append(calendar.get(Calendar.YEAR));
		now.append(calendar.get(Calendar.MONTH) + 1);
		now.append(calendar.get(Calendar.DATE));
		return now.toString();
	}

}
