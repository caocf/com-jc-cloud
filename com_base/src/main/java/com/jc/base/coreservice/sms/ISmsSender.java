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
 * 短信发送器接口<br>
 * 
 * 
 * @author chenzhao
 * @version 1.0 , 2012-12-17
 * @see

 */
public interface ISmsSender {

    /**
     * 短信发送具体方法
     * 
     * @param telephone 发送至的手机号码
     * @param smsConents 发送内容
     * @return 1 表示成功；0表示失败；-1表示超过当天发送次数
     * @throws Exception
     * @see

     */
    int doSend(String telephone, String smsConents) throws Exception;

}
