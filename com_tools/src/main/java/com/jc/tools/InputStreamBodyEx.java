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

import java.io.InputStream;

import org.apache.http.entity.mime.content.InputStreamBody;

public class InputStreamBodyEx extends InputStreamBody {

	/**
	 * 长度
	 */
	private int length;

	public InputStreamBodyEx(InputStream in, String filename) {
		super(in, filename);
	}

	public InputStreamBodyEx(InputStream in, String filename, int length) {
		super(in, filename);
		this.length = length;
	}


	/**
	 * {方法的功能/动作描述}
	
	 * @return
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
	@Override
	public long getContentLength() {
		return this.length;
	}

}
