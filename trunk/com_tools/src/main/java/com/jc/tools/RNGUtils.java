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
import java.util.Calendar;
import java.util.Random;

/**
 * 生成随机文件名
 * 〈功能详细描述〉
 *
 * @author chenzhao
 * @version [版本号, 2012-12-18]


 */
public class RNGUtils {
	
  
    /**
     * 生成随机文件名
     * 〈功能详细描述〉
     *
     * @return


     */
    public String generateRandomFilename(){    
        
        String RandomFilename = "";    
        Random rand = new Random();//生成随机数    
        int random = rand.nextInt();    
            
        Calendar calCurrent = Calendar.getInstance();    
        int intDay = calCurrent.get(Calendar.DATE);    
        int intMonth = calCurrent.get(Calendar.MONTH) + 1;    
        int intYear = calCurrent.get(Calendar.YEAR);    
        String now = String.valueOf(intYear) + "_" + String.valueOf(intMonth) + "_" +    
            String.valueOf(intDay) + "_";    
            
        RandomFilename = now + String.valueOf(random > 0 ? random : ( -1) * random) + ".";    
            
        return RandomFilename;    
    }    
}
