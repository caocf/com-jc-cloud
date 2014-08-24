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

import java.io.File;

import org.apache.http.entity.mime.content.FileBody;


public class FileBodyEx extends FileBody {

	private String filename;

	public FileBodyEx(File file) {
		super(file);
	}

	public FileBodyEx(File file, String filename) {
		super(file);
		this.filename = filename;
	}

	@Override
	public String getFilename() {
		return this.filename;
	}

}
