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

import java.util.Collection;

/**
 * 断言功能 <br> 
 * 该类实现了断言功能，如果不满足条件就抛出<code>java.lang.IllegalArgumentException</code>
 *
 * @author chenzhao
 * @version [1.0, 2012-12-17]
 * @see 
 
 */
public class Assert {

	/**
	 * 判断目标对象是否为NULL: <br>
	 * 判断目标对象是否为NULL，如果不为NULL，抛出IllegalArgumentException异常，异常信息为message
	 *
	 * @param obj 目标对象
	 * @param message 异常信息
	 * @see 
	 
	 */
	public static void isNull(Object obj, String message) {
		if(obj != null) 
			throw new IllegalArgumentException(message);
	}

	/**
	 * 判断目标对象为NULL : <br>
	 * 判断目标对象为NULL，如果不为NULL，抛出IllegalArgumentException异常，
	 * 异常信息为"[Assertion failed] - the Object argument must be null"
	 *
	 * @param obj 目标对象
	 * @see 
	 
	 */
	public static void isNull(Object obj) {
		isNull(obj, "[Assertion failed] - the Object argument must be null");
	}

	/**
	 * 判断目标对象不为NULL: <br>
	 * 判断目标对象不为NULL，如果为NULL，抛出IllegalArgumentException异常，异常信息是message
	 *
	 * @param obj 目标对象
	 * @param message 异常信息
	 * @see 
	 
	 */
	public static void notNull(Object obj, String message) {
		if(obj == null) 
			throw new IllegalArgumentException(message);
	}

	/**
	 * 判断目标对象不为NULL: <br>
     * 判断目标对象不为NULL，如果为NULL，抛出IllegalArgumentException异常，
     * 异常信息是"[Assertion failed] - the Object argument must not be null"
	 * 
	 * 
	 * @param obj
	 * @see 
	 
	 */
	public static void notNull(Object obj) {
		notNull(obj, "[Assertion failed] - the Object argument must not be null");
	}

	/**
	 * : <br>
	 * 
	 *
	 * @param str 目标字符串
	 * @param message 异常信息
	 * @see StringExtUtils.hasText
	 
	 */
	public static void hasText(String str, String message) {
		if(!StringExtUtils.hasText(str)) 
			throw new IllegalArgumentException(message);
	}

	/**
	 * : <br>
	 * 
	 *
	 * @param str 目标字符串
	 * @see 
	 
	 */
	public static void hasText(String str) {
		hasText(str, "[Assertion failed] - this String argument must have text; " +
				"it cannot be <code>null</code>, empty, or blank");
	}

	/**
	 * 判断对象数组不为空: <br>
	 * 如果对象数组为NULL，或长度为0，抛出IllegalArgumentException异常，异常信息是message
	 *
	 * @param array 目标对象数组
	 * @param message 异常信息
	 * @see 
	 
	 */
	public static void notEmpty(Object[] array, String message) {
		if (array == null || array.length == 0) 
			throw new IllegalArgumentException(message);
	}

	/**
	 * 
	 * @param array
	 */
	/**
	 * 判断对象数组不为空: <br>
     * 如果对象数组为NULL，或长度为0，抛出IllegalArgumentException异常，
     * 异常信息是"[Assertion failed] - this array must not be empty: it must contain at least 1 element"
     *
     * @param array 目标对象数组
     * @param message 异常信息
	 * @see 
	 
	 */
	public static void notEmpty(Object[] array) {
		notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
	}

	/**
	 * 判断集合不为空: <br>
     * 如果集合为NULL，或不含元素，抛出IllegalArgumentException异常，异常信息是message
	 *
	 * @param c 目标集合
	 * @param message 异常信息
	 * @see 
	 
	 */
	@SuppressWarnings("unchecked")
	public static void notEmpty(Collection c, String message) {
		if (c == null || c.isEmpty()) 
			throw new IllegalArgumentException(message);
	}

	/**
	 * 判断集合不为空: <br>
     * 如果集合为NULL，或不含元素，抛出IllegalArgumentException异常，
     * 异常信息是"[Assertion failed] - this collection must not be empty: it must contain at least 1 element"
     *
     * @param c 目标集合
	 * @see 
	 
	 */
	@SuppressWarnings("unchecked")
	public static void notEmpty(Collection c) {
		notEmpty(c,"[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
	}
	
	/**
	 * 判断两个字符串一致: <br>
	 * 判断两个字符串一致，如果内容不等，抛出IllegalArgumentException异常
	 *
	 * @param str1 目标字符串1
	 * @param str2 目标字符串2
	 * @param message 异常信息
	 * @see 
	 
	 */
	public static void notSame(String str1,String str2,String message){
	    if(!str1.equals(str2)){
	        throw new IllegalArgumentException(message);
	    }
	}
	
	/**
	 * 判断字符串为null或空串: <br>
	 * 
	 *
	 * @param str
	 * @param message
	 * @see 
	 
	 */
	public static void notEmpty(String str,String message){
	    if(null == str || ("").equals(str.trim())){
	        throw new IllegalArgumentException(message);
	    }
	}
	/**
	 * 判断邮箱格式是否正确: <br>
	 * 判断邮箱格式是否正确，如果不正确，抛出IllegalArgumentException异常，异常信息message
	 *
	 * @param email 目标邮箱
	 * @param message 异常信息
	 * @see 
	 */
	public static void isNumber(Object obj){
	    if (!RegexUtils.checkDigit(obj.toString())) {
	        throw new IllegalArgumentException("不是整数");
	    }
	}
	/**
	 * 判断邮箱格式是否正确: <br>
	 * 判断邮箱格式是否正确，如果不正确，抛出IllegalArgumentException异常，异常信息message
	 *
	 * @param email 目标邮箱
	 * @param message 异常信息
	 * @see 
	 
	 */
	public static void emailFormat(String email,String message){
	    String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
	    if (!email.matches(regex)) {
	        throw new IllegalArgumentException(message);
	    }
	}
	
	/**
	 * 字符长度必须为start-end位之间 : <br>
	 * 字符长度必须为start-end位之间，否则抛出IllegalArgumentException异常，异常信息message
	 *
	 * @param str 目标字符串
	 * @param start 最小长度
	 * @param end 最大长度
	 * @param message 异常信息
	 * @see 
	 
	 */
	public static void stringLength(String str,int start,int end,String message){
	    if(str.length()<start || str.length() >end){
	        throw new IllegalArgumentException(message);
	    }
	}

	/**
	 * 字符串不能全为数字: <br>
	 * 暂时未用
	 *
	 * @param str
	 * @param message
	 * @see 
	 
	 */
	public static void stringNotAllNum(String str,String message){
	    /*
	    if(StringUtil.isNum(str)){
	        throw new IllegalArgumentException(message);
	    }
	    */
	}
	
	/**
	 * 字符串不能全为英文字母: <br>
	 * 字符串不能全为英文字母，否则抛出IllegalArgumentException异常，异常信息message
	 * 
	 * @param str 目标字符串
	 * @param message 异常信息
	 * @see 
	 
	 */
	public static void stringNotAllEnglishLetter(String str,String message){
	    if(str.matches("[a-zA-Z]+")){
	        throw new IllegalArgumentException(message);
	    }
	}
	
}
