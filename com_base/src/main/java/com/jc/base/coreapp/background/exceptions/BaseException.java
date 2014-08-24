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
package com.jc.base.coreapp.background.exceptions;

/**
 * 自定义异常基类 <br>
 * 异常一般分为检查性异常和非检查性异常 检查性包括一些逻辑错误，用户输入数据不合法 非检查性为数据连接、内存溢出
 * 
 * @author chenzhao
 * @version 1.0 , 2012-12-17
 * @see

 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public BaseException() {
        super();
    }

    /**
     * 
     * @param msg
     */
    public BaseException(String msg) {
        super(msg);
    }

    /**
     * 
     * @param cause
     */
    public BaseException(Throwable cause) {
        super(cause);
    }

    /**
     * 
     * @param msg
     * @param cause
     */
    public BaseException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
