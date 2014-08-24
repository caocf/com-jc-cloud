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
 * 用于拼接和分解字符串类
 * 〈功能详细描述〉
 *
 * @version [版本号, 2012-12-18]


 */
public class SplitJointResolveUtils {
    
	/**
	 * <p>拼接字符串</p>
	 * @param code  拼接的方式如-,=,+等
	 * @param str  您要拼接的数据
	 * @return


	 */
	public static String splitJoinStr(String code,String[] str){
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<str.length;i++){
		  if(!"".equals(str[i]) && str[i]!=null){	
			  if(i==str.length-1){
				 sb.append(str[i]);
			  }else{
				 sb.append(str[i]).append(code);
			   }
		  }
		}
		return sb.toString();
	}
    /**
     * <p>分解字符串</p>
     * @param code  分解的方式如-,=,+等
     * @param str   您要分解的数据
     * @return      分解的数据,数组


     */
	public static String[] resolveStr(String code,String str){
		String[] strs =null;
		if(!"".equals(str) || str!=null){
			 strs = str.split(code);
		}
		return strs;
	}
	/**
	 * <p>去掉特殊字符</p>
	 * @param code  去掉的字符如-,=,+等
	 * @param str  您要去掉的数据
	 * @return   去掉的数据


	 */
	public static String removeStr(String code,String str){
		if("".equals(str) || str!=null){
		    str = str.replace(code, "").trim();
		}
		return str;
	}
	
}
