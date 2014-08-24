/**  
 * Description: <类功能描述-必填>限制整站只显示中文和英文 
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

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;


public class UserLocaleUtils {

	/**
	 * 
	 */
	private static Set<String> localeSet = new HashSet<String>();
	static {
		localeSet.add(Locale.CHINESE.getLanguage());
		// localeSet.add(Locale.US.getLanguage());
	}


	/**
	 * {方法的功能/动作描述}
	
	 * @param locale
	 * @return
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
	public static Locale filterUserLocale(Locale locale) {
		if (locale != null) {
			if (localeSet.contains(locale.getLanguage())) {
				return locale;
			}
		}
		return Locale.CHINESE;
	}

}
