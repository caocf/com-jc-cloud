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
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author chenzhao
 * @version [版本号, 2012-12-18]


 */
public class StringLimitUtils {
    /**     */
    private static final int LIMIT_LENGTH=10;
    /**     */
    private static final int LIMIT_LENGTH_9=9;
    /**     */
    private static final int LIMIT_LENGTH_20=20;
    
    
    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param str
     * @return


     */
    public static String getLimitDefault(String str){
        return getLimit(str,LIMIT_LENGTH);
    }
    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param str
     * @return


     */
    public static String getLimit9(String str){
        return getLimit(str,LIMIT_LENGTH_9);
       
    }
    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param str
     * @return


     */
    public static String getLimit20(String str) {
        // TODO Auto-generated method stub
        return getLimit(str,LIMIT_LENGTH_20);
    }
    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param str
     * @param length
     * @return


     */
    private static String getLimit(String str,int length) {
        // TODO Auto-generated method stub
        if(StringUtils.isEmpty(str)||str.length()<=length){
            return str;
        }else{
            return str.substring(0,length)+"...";
        }
    }
     
    

}

