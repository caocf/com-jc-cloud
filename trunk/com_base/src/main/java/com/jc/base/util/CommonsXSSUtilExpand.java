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
package com.jc.base.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
 * 非法字符处理扩展类
 * 〈功能详细描述〉
 *
 * @author chenzhao
 * @version [版本号, 2012-12-17]


 */
public class CommonsXSSUtilExpand extends CommonsXSSUtil {


    
    
    /**
     * html脚本注入大全
     */
    private static final List<String> ILLEGAL_HTML_STRING_ARRAY_CONFIG = XssXmlConfigurationUtils.reqHtmlTagTotal();//ConfigurationUtils.getList(SystemConstants.ILLEGAL_HTML_STRING_CONFIG_CONS);
    
    /**
     * 链接 還有其他的正則表達式
     * ① <a\\s+href\\s*=\\s*(\"[^\"]*\"|[^\\s>])\\s*
              ② <a href='(.+?)'
     */
    private static final Pattern HREF_TAG_PATTERN_CONFIG = Pattern.compile(XssXmlConfigurationUtils.reqHrefTag(), Pattern.CASE_INSENSITIVE); //Pattern.compile(ConfigurationUtils.getString(SystemConstants.HREF_TAG_PATTERN_CONFIG_CONS), Pattern.CASE_INSENSITIVE); 
    
    
    /**
     * sql脚本注入大全
     */
    private static final List<String> ILLEGAL_SQL_STRING_ARRAY_CONFIG = XssXmlConfigurationUtils.reqSqlScript();//ConfigurationUtils.getList(SystemConstants.ILLEGAL_SQL_STRING_ARRAY_CONS);//"_", 暂时不用这个
    
    
    /**
     * 是否写入JavaScript脚本
     */
    private static final Pattern SCRIPT_TAG_PATTERN_CONFIG = Pattern.compile(XssXmlConfigurationUtils.reqJavaScript(), Pattern.CASE_INSENSITIVE);//Pattern.compile(ConfigurationUtils.getString(SystemConstants.SCRIPT_TAG_PATTERN_CONS), Pattern.CASE_INSENSITIVE);
    
    /**
     * html 事件函数
     */
    private static final List<String> HTML_SCRIPT_METHOD_EVENTS_CONFIG = XssXmlConfigurationUtils.reqhtmlScriptEvent();//ConfigurationUtils.getList(SystemConstants.HTML_SCRIPT_METHOD_EVENTS_CONS);
    
    
    
    

    /**
     * 判断html注入脚本是否非法
     *  例如，<abbr>，等等，注入的html标签
     * @param content
     * @return 非法字符 返回 true; 反之 返回 false


     */
    public static boolean judgeIllegalHtmlString(String content){
       
        boolean isIllegal = false;
        
        if ( content != null && !"".equals(content) ){
            
             for(String s : ILLEGAL_HTML_STRING_ARRAY_CONFIG){
                 
                 if (content.equals(s)) return isIllegal = true;
             }
            
        }
       return isIllegal;
    }
    
    /**
     * 链接的非法注入 
     *  例如，<a href='../certification/corpCeritfiyAuth!doQueryCorpCertifyInitInfoMotion.do'>，会认为是注入的链接
     * @param content
     * @return 非法字符 返回 true; 反之 返回 false
     * @throws Exception


     */
    public static boolean judgeIllegalHrefAndOther(String content) throws Exception {
        
        Matcher matcher = null;
        String temp = "";
        boolean isIlleal = false;
        
        try {
           matcher = HREF_TAG_PATTERN_CONFIG.matcher(content);
           temp = content;
   
              /**
               * 链接注入判断
               */
               while (matcher.find())
                {
                  
                   return isIlleal=true;
                }
          
             }
              catch (PatternSyntaxException e)
             {
               throw new Exception(e.toString());
             }

   
         return isIlleal;

        }
    
    /**
     * 判断是否非法sql脚本注入
     * @param content
     * @return  非法字符 返回 true; 反之 返回 false
     * @throws Exception


     */
    public static boolean judgeIllegalSqlInject(String content) throws Exception{
        
        boolean returnBool = false;
        
        if (content != null && !"".equals(content)){
            
            for(String s : ILLEGAL_SQL_STRING_ARRAY_CONFIG) {
                
                
                if (content.contains(s)) {
                    
                    returnBool = content.contains(s);
                    return returnBool;
                }
              }
        }
        
       return returnBool;
    }
    
    
    
    /**
     * 是否写入了javascript脚本
     * 例如，<script>alert('test');</script> 等，注入的js脚本
     * @param content
     * @return 是js脚本  返回 true; 反之 返回 false


     */
    public static boolean judgeStripScriptTag(String content) {
        
      boolean isIlleagl = false;
      Matcher m = SCRIPT_TAG_PATTERN_CONFIG.matcher(content);
    
        while (m.find()) {
            
            return isIlleagl = true;
        }
        
        return isIlleagl;
    }
    
    /**
     * html事件函数注入非法字符
     * html标签 触发调用时间，例如， onmouseover 等等
     * @param content
     * @return
     * @throws Exception
     * @see 

     */
    public static boolean judgeStripEventIllegal(String content) throws Exception {
        boolean isIlleagl = false; 
        
        if (content != null && !"".equals(content)){
            
            for(String s : HTML_SCRIPT_METHOD_EVENTS_CONFIG) {
                
                
                if (content.contains(s)) {
                    
                    isIlleagl = content.contains(s);
                    return isIlleagl;
                }
              }
        }
 
        return isIlleagl;
        }
    
    
}

