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

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.SubnodeConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;

/**
 * 用xml做配置文件工具类，主要是作为过滤非法字符
 * 〈功能详细描述〉
 *
 * @author chenzhao
 * @version [版本号, 2012-12-17]


 */
public abstract class XssXmlConfigurationUtils {


    /**
     * 日志通用
     */
    private static final Logger logger = Logger.getLogger(XssXmlConfigurationUtils.class);
    
    /**
     */
    private static XMLConfiguration xmlConfig = null;
    
    static {
        try {
            xmlConfig =   new XMLConfiguration("xssFilterConfig.xml");
        }
        catch (ConfigurationException e) {
            
            logger.error(e);
        }
    }
    

    /**
     * 获取所有html标签
     * 〈功能详细描述〉
     *
     * @return


     */
    public static List<String> reqHtmlTagTotal() {
        List<String> valuesList = null;
        valuesList = xmlConfig.getList("Elements.elementNode(0).values"); 
        return valuesList;
        
    }
    
    /**
     * 获取所有html的js脚本事件
     * 〈功能详细描述〉
     *
     * @return


     */
    public static List<String> reqhtmlScriptEvent() {
        List<String> valuesList = null;
        valuesList = xmlConfig.getList("Elements.elementNode(1).values"); 
        
     
        reqHrefTag();
        
        return valuesList;
        
    }

    
    /**
     * 获取非法注入链接正则表达式
     * 〈功能详细描述〉
     *
     * @return


     */
    public static String reqHrefTag() {
        String valuesString = "";
        SubnodeConfiguration sub = xmlConfig.configurationAt("Elements.elementNode(2)"); 
        valuesString = sub.getString("values"); 
       return valuesString;
    }
    
    /**
     *  获取非法注入sql脚本
     * 〈功能详细描述〉
     *
     * @return


     */
    public static List<String> reqSqlScript() {
        List<String> valuesList = null;
        valuesList = xmlConfig.getList("Elements.elementNode(3).values"); 
        return valuesList;
    }
    
    /**
     * 获取写入javascirpt脚本的正则表达
     * 〈功能详细描述〉
     *
     * @return


     */
    public static String reqJavaScript() {
        String valuesString = "";
        SubnodeConfiguration sub = xmlConfig.configurationAt("Elements.elementNode(4)"); 
        valuesString = sub.getString("values"); 
       return valuesString;
    }
    
    
}

