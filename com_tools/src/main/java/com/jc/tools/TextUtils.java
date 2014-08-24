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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文本操作工具类
 * 〈功能详细描述〉
 *
 * @author chenzhao
 * @version [版本号, 2012-12-17]


 */
public class TextUtils {
    
	/**
	 *  获取字符串长度 参数：str - String 返回：int
	 * 〈功能详细描述〉
	 *
	 * @param str
	 * @return

	
	 */
	public static int len(String str) {
		if ((str == null) || (str.equals("")))
			return 0;
		int j = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) > 'ÿ')
				j += 2;
			else
				j++;
		}
		return j;
	}

	/**
	 * 字符串替换
	 * 〈功能详细描述〉
	 *
	 * @param srcStr
	 * @param from
	 * @param to
	 * @return

	
	 */
	public static String strReplace(String srcStr, String from, String to) {
		String strDest = "";
		int intFromLen = from.length();
		int intPos;
		while ((intPos = srcStr.indexOf(from)) != -1) {
			strDest = strDest + srcStr.substring(0, intPos);
			strDest = strDest + to;
			srcStr = srcStr.substring(intPos + intFromLen);
		}
		strDest = strDest + srcStr;

		return strDest;
	}

	/**
	 * 使用正则表达式，全部替换
	 * 〈功能详细描述〉
	 *
	 * @param srcStr 源字符串
	 * @param rep 目标字符串
	 * @param regEx 正则表达式
	 * @return

	
	 */
	public static String regReplace(String srcStr, String rep, String regEx) {
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(srcStr);
		String s = m.replaceAll(rep);
		return s;
	}

	/**
	 * 使用正则表达式，替换第一次
	 * 〈功能详细描述〉
	 *
	 * @param srcStr 源字符串
	 * @param rep 目标字符串
	 * @param regEx 正则表达式
	 * @return

	
	 */
	public static String regReplaceFirst(String srcStr, String rep, String regEx) {
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(srcStr);
		String s = m.replaceFirst(rep);
		return s;
	}


	/**
	 * 是否匹配
	 * 〈功能详细描述〉
	 *
	 * @param srcStr
	 * @param regEx
	 * @param start
	 * @return

	
	 */
	public static boolean regFind(String srcStr, String regEx, int start) {
		return Pattern.compile(regEx).matcher(srcStr).find(start);
	}


	/**
	 * null --> 空字符
	 * 〈功能详细描述〉
	 *
	 * @param str
	 * @return

	
	 */
	public static String dealNull(String str) {
		String returnStr = null;
		if (str == null)
			returnStr = "";
		else {
			returnStr = str;
		}
		return returnStr;
	}
}
