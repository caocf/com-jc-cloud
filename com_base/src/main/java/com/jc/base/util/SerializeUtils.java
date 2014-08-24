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
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * 序列化和反序列化工具类
 * 〈功能详细描述〉
 *
 * @author chenzhao
 * @version [版本号, 2012-12-18]


 */
public class SerializeUtils {



	/**
	 *  序列化对象
	 * 〈功能详细描述〉
	 *
	 * @param object
	 * @return

	
	 */
	public static byte[] serializeObject(Object object) {
		try {
			ByteArrayOutputStream saos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(saos);
			oos.writeObject(object);
			oos.flush();
			return saos.toByteArray();
		} catch (Exception ex) {
			return null;
		}

	}


	/**
	 * 反序列化对象
	 * 〈功能详细描述〉
	 *
	 * @param buf
	 * @return

	
	 */
	public static Object deserializeObject(byte[] buf) {
		try {
			Object object = null;
			ByteArrayInputStream sais = new ByteArrayInputStream(buf);
			ObjectInputStream ois = new ObjectInputStream(sais);
			object = ois.readObject();
			return object;
		} catch (Exception ex) {
			return null;
		}
	}
}
