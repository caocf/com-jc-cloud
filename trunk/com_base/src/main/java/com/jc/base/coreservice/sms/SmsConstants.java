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
package com.jc.base.coreservice.sms;

/**
 * 
 * 短信发送常量池<br>
 * 
 * 
 * @author chenzhao
 * @version 1.0 , 2012-12-17
 * @see

 */
public class SmsConstants {

    /**
     * 发送成功
     */
    public static final int SEND_SUCCESS = 1;
    /**
     * 发送失败
     */
    public static final int FAILED_TO_SEND = 0;
    /**
     * 超过最大发送次数
     */
    public static final int EXCEED_MAX_SEND_TIMES = -1;

    /**
     * 调用sunivo提供的接口发送成功后返回码
     */
    public static final int SUNIVO_SOAP_SECCESS = 1003;

    /**
     * 调用TIANZAI提供的接口发送成功后返回码
     */
    public static final String TIANZAI_HTTP_SECCESS = "00/1";

}
