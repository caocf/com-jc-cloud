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
package com.jc.base.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 属性文件操作类
 * 〈功能详细描述〉
 *
 * @author chenzhao
 * @version [版本号, 2012-12-17]


 */
public class PropsUtil {
	/**
	 * 读取资源文件
	 * 〈功能详细描述〉
	 *
	 * @param props
	 * @return
	 * @throws Exception

	
	 */
	public String props2string(String props) throws Exception {
		String str = "";
		str = stream2string(props2stream(props));
		return str;
	}

	/**
	 * 按流方式读取属性文件
	 * 〈功能详细描述〉
	 *
	 * @param props
	 * @return
	 * @throws Exception

	
	 */
	public InputStream props2stream(String props) throws Exception {
		InputStream stream = getClass().getClassLoader().getResourceAsStream(
				props);
		return stream;
	}

	/**
	 * InputStream -> String
	 * 〈功能详细描述〉
	 *
	 * @param is
	 * @return
	 * @throws IOException

	
	 */
	public String stream2string(InputStream is) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		int n;
		while ((n = is.read(b)) != -1) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}

	/**
	 * String -> InputStream
	 * 〈功能详细描述〉
	 *
	 * @param str
	 * @return

	
	 */
	public InputStream string2stream(String str) {
		ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
		return stream;
	}
}
