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

/**
 *  通用枚举类 需要使用的可以往这个类添加枚举<br> 
 * 〈功能详细描述〉
 *
 * @author chenzhao
 * @version [1.0, 2012-12-17]
 * @see 
 * @since [1.0]
 */
public class EnumSetState {

    /**
     * 申请状态<br> 
     * 〈功能详细描述〉
     *
     * @author chenzhao
     * @version [1.0, 2012-12-17]
     * @see 
     * @since [1.0]
     */
    public enum ApplyStateZh{  
        新申请,  
        审核中,  
        审核已通过,  
        审核未通过,
        已取消,
        已拒绝;  
 
        public static ApplyStateZh fromInt(int num){  
            switch (num) {  
                case 1: { return ApplyStateZh.新申请; }  
                case 2: { return ApplyStateZh.审核中; }  
                case 3: { return ApplyStateZh.审核已通过; }  
                case 4: { return ApplyStateZh.审核未通过; }  
                case 5: { return ApplyStateZh.已取消; } 
                case 6: { return ApplyStateZh.已拒绝; } 
                default:return null;  
            }  
        }  
    }  
    
    /**
     * 服务过程审核状态<br> 
     * 〈功能详细描述〉
     *
     * @author chenzhao
     * @version [1.0, 2012-12-17]
     * @see 
     * @since [1.0]
     */
    public enum ServiceProcessStateZh{  
        正在服务中,  
        服务已暂停,  
        服务已结束,  
        等待开始的服务;
 
        public static ServiceProcessStateZh fromProcessInt(int num){  
            switch (num) {  
                case 1: { return ServiceProcessStateZh.正在服务中; }  
                case 2: { return ServiceProcessStateZh.服务已暂停; }  
                case 3: { return ServiceProcessStateZh.服务已结束; }  
                case 4: { return ServiceProcessStateZh.等待开始的服务; }  
                default:return null;  
            }  
        }  
    }  
    
    /**
     * 服务过程编码<br> 
     * 〈功能详细描述〉
     *
     * @author chenzhao
     * @version [1.0, 2012-12-17]


     */
    public enum ServiceProcessCode{  
        S01,
        S05,  
        S11,   
        S19;
 
        public static ServiceProcessCode fromProcessCodeInt(int num){  
            switch (num) {  
                case 1200: { return ServiceProcessCode.S11; }  
                case 1201: { return ServiceProcessCode.S05; }  
                case 1262: { return ServiceProcessCode.S01; }  
                case 1202: { return ServiceProcessCode.S19; }  
                default:return null;  
            }  
        }  
    }  
}

