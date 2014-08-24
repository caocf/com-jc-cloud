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

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Component;

/**
 * 关于随机数据静态类 〈功能详细描述〉
 * 
 * @author chenzhao
 * @version [版本号, 2013-7-30]
 */
@Component("randomUtils")
public class RandomUtils {
	/**
	 * 每位允许的字符
	 */
	private static final String POSSIBLE_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String POSSIBLE_NUMBERS = "0123456789";
	/**
	 * 生产一个指定长度的随机字符串
	 * 
	 * @param length
	 *            字符串长度
	 * @return
	 */
	public static String generateRandomString(int length) {
		StringBuilder sb = new StringBuilder(length);
		SecureRandom random = new SecureRandom();
		for (int i = 0; i < length; i++) {
			sb.append(POSSIBLE_CHARS.charAt(random.nextInt(POSSIBLE_CHARS
					.length())));
		}
		return sb.toString();
	}
	/**
	 * 生产一个指定长度的纯数字
	 * 
	 * @param length
	 *            数字串长度
	 * @return
	 */
	public static String generateRandomNumber(int length) {
		StringBuilder sb = new StringBuilder(length);
		SecureRandom random = new SecureRandom();
		for (int i = 0; i < length; i++) {
			sb.append(POSSIBLE_NUMBERS.charAt(random.nextInt(POSSIBLE_NUMBERS
					.length())));
		}
		return sb.toString();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Set<String> check = new HashSet<String>();
		RandomUtils obj = new RandomUtils();

		// 生成2000000个随机字符串，检查是否出现重复
		for (int i = 0; i < 2000000; i++) {
			String s = obj.generateRandomString(16);
			if (check.contains(s)) {
				throw new IllegalStateException("Repeated string found : " + s);
			} else {
				if (i % 1000 == 0)
					System.out
							.println("generated " + i / 1000 + "000 strings.");
				check.add(s);
			}
		}
	}

	/**
	 * 生成指定位数随即机 〈功能详细描述〉
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int NextInt(final int min, final int max) {
		Random rand = new Random();
		int tmp = Math.abs(rand.nextInt());
		return tmp % (max - min + 1) + min;
	}
}
