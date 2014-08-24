/**  
 * Description: <类功能描述-必填>枚举工具类 
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

import org.apache.commons.lang.ObjectUtils;



public abstract class EnumUtils {

	/**
	 * 从指定的枚举类中根据property搜寻匹配指定值的枚举实例<br> 
	 * 〈功能详细描述〉
	 *
	 * @param enumClass
	 * @param property
	 * @param propValue
	 * @return
	 */
	public static <T extends Enum<T>> T fromEnumProperty(Class<T> enumClass, String property, Object propValue) {
		T[] enumConstants = enumClass.getEnumConstants();
		for (T t : enumConstants) {
			Object constantPropValue;
			try {
				constantPropValue = BeanUtils.getDeclaredFieldValue(t, property);
				if (ObjectUtils.equals(constantPropValue, propValue)) {
					return t;
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return null;

	}
	
    /**
     * 从指定的枚举类中根据名称匹配指定值<br> 
     * 〈功能详细描述〉
     *
     * @param enumClass
     * @param constantName
     * @return
     */
    public static <T extends Enum<T>> T fromEnumConstantName(Class<T> enumClass, String constantName) {
        T[] enumConstants = enumClass.getEnumConstants();
        for (T t : enumConstants) {
            if (((Enum<?>) t).name().equals(constantName)) {
                return t;
            }
        }
        return null;
    }
	
}

