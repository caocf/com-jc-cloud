/**  
 * Description: <类功能描述-必填>Object对象工具类 
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

import java.lang.reflect.Field;
public class ObjectUtils {  
  
    /** 
     * @Description 递归获取父类的属性字符串表示形式 
     * @param clazz 
     * @param object 
     * @param sb 
     * @return 
     * @author chenzhao 
     * @date 2013-1-12 下午3:29:42 
     * @version V1.0 
     */  
    private static StringBuffer toString(Class<?> clazz, Object object, StringBuffer sb) {  
        @SuppressWarnings("rawtypes")  
        Class superClass = clazz.getSuperclass();  
        if (superClass != Object.class) {  
            toString(superClass, object, sb);  
        }  
        Field[] fs = clazz.getDeclaredFields();  
        if (fs != null && fs.length > 0) {  
            try {  
                for (Field f : fs) {  
                    f.setAccessible(true);  
                    if (!"serialVersionUID".equalsIgnoreCase(f.getName())) {  
                        sb.append(f.getName()).append(" = ");  
                        Object o = f.get(object);  
                        if (o instanceof Object[]) {  
                            Object[] os = (Object[]) o;  
                            sb.append("[ ");  
                            for (int i = 0; i < os.length; i++) {  
                                if (i < os.length - 1)  
                                    sb.append(os[i]).append(",");  
                                else  
                                    sb.append(os[i]);  
                            }  
                            sb.append(" ]");  
                        } else {  
                            sb.append(o);  
                        }  
                        sb.append(",");  
                    }  
                }  
            } catch (Exception e) {  
            }  
        }  
        return sb;  
    }  
  
    /** 
     * @Description 获取对象字符串形式的属性值 
     * @param object 
     *            操作对象 
     * @return 字符串表示的对象例如:[ name = zhangsan, age = 14] 
     * @author chenzhao
     * @date 2013-1-12 下午3:29:42 
     * @version V1.0 
     */  
    public static String toString(Object object) {  
        StringBuffer sb = new StringBuffer();  
        sb.append("[ ");  
        toString(object.getClass(), object, sb);  
        if (sb.length() > 2)  
            sb.delete(sb.length() - 1, sb.length());  
        sb.append(" ]");  
        return sb.toString();  
    }  
}  
