/**  
 * Description: <类功能描述-必填>默认的日期格式为“yyyy-MM-dd” 
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;


public class DateJsonValueProcessor implements JsonValueProcessor {
	
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd"; 
	
    private DateFormat dateFormatter;
    
    public DateJsonValueProcessor() {
    	this.dateFormatter = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
    }
	
	/**  
     * 
     * @param datePattern 日期格式 
     */  
    public DateJsonValueProcessor(String datePattern) {  
    	this.dateFormatter = new SimpleDateFormat(datePattern);  
    }

	/**
	 * {方法的功能/动作描述}
	
	 * @param value
	 * @param config
	 * @return
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
	@Override
	public Object processArrayValue(Object value, JsonConfig config) {
		return process(value);  
	}

	/**
	 * {方法的功能/动作描述}
	
	 * @param key
	 * @param value
	 * @param config
	 * @return
	 * @exception   {说明在某情况下,将发生什么异常}
	 * @Author       ChenZhao
	 */
	@Override
	public Object processObjectValue(String key, Object value, JsonConfig config) {
		return process(value);  
	}


    /**
     * {方法的功能/动作描述}
    
     * @param value
     * @return
     * @exception   {说明在某情况下,将发生什么异常}
     * @Author       ChenZhao
     */
    private Object process(Object value) {
    	if(value == null) {
    		return "";
    	}
    	
        return dateFormatter.format((Date)value);  
    }  

}
