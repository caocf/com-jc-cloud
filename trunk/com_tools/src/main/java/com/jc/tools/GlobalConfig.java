/**  
 * Description: 读取配置文件 
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

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public final class GlobalConfig {

    /**
     * real application true 在正常列路径的使用情况下获取，读出未经编码处理的配置文件的所有内容。<br> 
     * 读取的配置文件的后缀名必须是'.properties'，文件中的字符必需属于字符集ISO-8859-1，否则请调用本类的方法getNativeProperties。
     * 配置文件名尽量符合国际化标准，比如简体中文的为xxx_zh_CN.properties、繁体中文的为xxx_zh_TW.properties（请参考类java.util.Locale）。
     * 
     * @param uri
     * @return 一个Properies对象，它存放者配置文件的所有配置信息
     */
    public static final Properties getProperties(String uri) {
        Properties properties = new Properties();
        ClassLoader classLoader = GlobalConfig.class.getClassLoader();//得到当前类的类加载器
        
        InputStream is = classLoader.getResourceAsStream(uri + '_' + Locale.getDefault() + ".properties");

        if (is == null) is = classLoader.getResourceAsStream(uri + ".properties");

        try {
            properties.load(is);
            is.close();
        } catch (IOException e) { 
        //TODO 处理异常或Log日志记录 陈钊 2011-10-28 
        }

        return properties;
    }
    
    /**
     * {方法的功能/动作描述}
    
     * @param request
     * @param uri
     * @return
     * @exception   {说明在某情况下,将发生什么异常}
     * @Author       ChenZhao
     */
    public static final Properties getPropertiesFromServletContainer(HttpServletRequest request,String uri) {
         ServletContext context = request.getSession().getServletContext();
         InputStream in = context.getResourceAsStream(uri + "_" + Locale.getDefault() +  ".properties");//("/conf/dhtmlxgridtabletest.properties");  
         if (in == null) in = context.getResourceAsStream(uri + ".properties");
         Properties p = new Properties();  

         try {
             p.load(in);  
              in.close();
        } catch (IOException e) {
        }

           return p;
    }
    
    /**
     * 在web应用的上下文环境action中获取<br> 
     * 〈功能详细描述〉
     *
     * @param request
     * @param proprotieName 
     *      获取路径名称,EX.dhtmlxgridtabletest ,全称是，/conf/dhtmlxgridtabletest.properties
     *      注意名称的大小写，要跟实际文件名的大小写一样;路径是项目根目录下的 '/'
     * @return
     * @throws Exception
     */
    public static final Properties getPropertiesContext(HttpServletRequest request,String proprotieName) throws Exception{
        Properties p = null;
        ServletContext context = request.getSession().getServletContext();
        InputStream in = context.getResourceAsStream("/properties/" + proprotieName + ".properties"); 
    
        try {
            p = new Properties();  
            p.load(in); 
        } catch (IOException e) {
        }finally{
            in.close();
        }
        return p;
    }

    /**
     * {方法的功能/动作描述}
    
     * @param original
     * @return
     * @exception   {说明在某情况下,将发生什么异常}
     * @Author       ChenZhao
     */
    private static final String toNative(String original) {
        String nativeOne = null;
        try { nativeOne = new String(original.getBytes("8859_1")); }
        catch (UnsupportedEncodingException e) { 
        }
        return nativeOne;
    }

    /**
     * {方法的功能/动作描述}
    
     * @param properties
     * @return
     * @exception   {说明在某情况下,将发生什么异常}
     * @Author       ChenZhao
     */
    private static final Properties toNative(Properties properties) {
        Properties nativeOne = new Properties();
        for (Iterator it = properties.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry)it.next();
            nativeOne.put(toNative((String)entry.getKey()), toNative((String)entry.getValue()));
        }
        properties.clear();
        return nativeOne;
    }

    /**
     * {方法的功能/动作描述}
    
     * @param originals
     * @return
     * @exception   {说明在某情况下,将发生什么异常}
     * @Author       ChenZhao
     */
    public static final String[] toStrings(Object[] originals) {
        String[] strings = new String[originals.length];
        for (int i = 0; i < originals.length; i++) strings[i] = originals[i].toString();
        return strings;
    }
  
    /**
     * 读出经编码处理的配置文件的所有内容。
     * 读取的配置文件的后缀名必须是'.properties'，文件中的字符可属于任意字符集，但效率不如本类的方法getProperties。
     * 配置文件名尽量符合国际化标准，比如简体中文的为xxx_zh_CN.properties、繁体中文的为xxx_zh_TW.properties（请参考类java.util.Locale）。
     * @param uri 配置文件在包中的相对路径（请参考类java.lang.ClassLoader的方法getResourceAsStream(String)的参数格式）。
     * @return 一个Properies对象，它存放者配置文件的所有配置信息
     */
    public static final Properties getNativeProperties(String uri) {
        return toNative(getProperties(uri));
    }
    
    /**
     * 读出未经编码处理的配置文件的某个配置值。
     * @param uri 配置文件在包中的相对路径。
     * @param key 配置键。
     * @return
     */
    public static final String getProperty(String uri, String key) {
        return getProperties(uri).getProperty(key);
    }
    
    /**
     * 读出经编码处理的配置文件的某个配置值。
     * @param uri 配置文件在包中的相对路径。
     * @param key 配置键。
     * @return
     */
    public static final String getNativeProperty(String uri, String key) {
        return getNativeProperties(uri).getProperty(key);
    }
}

