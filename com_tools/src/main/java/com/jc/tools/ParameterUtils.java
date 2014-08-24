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

import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author chenzhao
 * @version [版本号, 2012-12-17]


 */
public class ParameterUtils {

    /**
     * 日志通用
     */
	static Log logger = LogFactory.getLog(ParameterUtils.class);
	/**
	 */
	private static Map<String, String> conf = null;
	/**
	 */
	private static String[] mappedUrls = {};

	/*
	static {
		init();
	}*/

	/**
	 * 在配置缓存中，获取指定key的值
	 *
	 * @param name 键名
	 * @return 键值


	 */
	public static String getParam(String name) {
	    return ConfigurationUtils.getString(name);
	    /*
		if (name != null)
			name = name.toUpperCase();
		if (conf.containsKey(name)) {
			return conf.get(name);
		}
		logger.warn("not found '" + name + "' in param cache.You can try to reload the configuration.");
		return null;*/
	}
	
	/**
     * 在配置缓存中，获取指定key的值
     *
     * @param name 键名
     * @return 键值


     */
    public static Integer getInt(String name) {
        /*
        if (name != null)
            name = name.toUpperCase();
        if (conf.containsKey(name)) {
            return Integer.parseInt(conf.get(name).trim());
        }
        logger.warn("not found '" + name + "' in param cache.You can try to reload the configuration.");
        return null;*/
        return ConfigurationUtils.getInt(name);
    }
    
    /**
     * 在配置缓存中，获取指定key的值
     * @param name 键名
     * @return 键值


     */
    public static Boolean getBoolean(String name) {
        /*
        if (name != null)
            name = name.toUpperCase();
        if (conf.containsKey(name)) {
            if(conf.get(name).equalsIgnoreCase("true")){
                return true;
            }else if(conf.get(name).equalsIgnoreCase("false")){
                return false;
            }
        }
        logger.warn("not found '" + name + "' in param cache.You can try to reload the configuration.");
        return false;*/
        String booleanString = ConfigurationUtils.getString(name);
        if(booleanString.equalsIgnoreCase("true")){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 把资源文件中的配置转换成List<String>
     * @param name  keyName
     * @param splitStr 分割符
     * @return List<String>


     */
    public static List<String> getList(String name,String splitStr) {
        if (name != null)
            name = name.toUpperCase();
        if (conf.containsKey(name)) {
            String liststr[] = conf.get(name).split(splitStr);
            return Arrays.asList(liststr);
        }
        logger.warn("not found '" + name + "' in param cache.You can try to reload the configuration.");
        return null;
    }
    
    
    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param key
     * @return


     */
    public static BigDecimal getBigDecimal(String key) {
        if (key != null)
            key = key.toUpperCase();
        if (conf.containsKey(key)) {
            return new BigDecimal(conf.get(key));
        }
        logger.warn("not found '" + key + "' in param cache.You can try to reload the configuration.");
        return null;
    }

	/**
	 * 在配置缓存中，获取指定key的值，若未得到该值，则使用默认值
	 *
	 * @param name 键名
	 * @param def 默认值
	 * @return 键值,同默认值的类型


	 */
	@SuppressWarnings("unchecked")
	public static <S> S getParam(String name, S def) {
		String val = getParam(name);
		if (StringUtils.isNotEmpty(val)) {
			try {
				if (def instanceof String) {
					return (S) val;
				}
				if (def instanceof Integer) {
					return (S) Integer.valueOf(val);
				}
				if (def instanceof Float) {
					return (S) Float.valueOf(val);
				}
				if (def instanceof Double) {
					return (S) Double.valueOf(val);
				}
				if (def instanceof Boolean) {
					return (S) Boolean.valueOf(val);
				}
			} catch (Exception ex) {
				logger.warn("convert " + name + "=" + val + " with default value: " + def + " failed");
			}
		}
		return def;
	}
/**
	 * 在配置缓存中，获取指定key的值，若未得到该值，则使用默认值
	 *
	 * @param name 键名
	 * @param def 默认值
	 * @return 键值,同默认值的类型


	 */
	@SuppressWarnings("unchecked")
	public static <S> List<S> getParam(String name, List<S> def) {
		String val = getParam(name);
		List<S> ret = def.subList(0, def.size());
		if (StringUtils.isNotEmpty(val)) {
			try {
				String[] vals = val.split(getParam(name + "_SEPARATOR", ","));
				for (int idx = 0, len = vals.length; idx < len; idx++) {
					ret.add((S) vals[idx]);
				}
				return ret;
			} catch (Exception ex) {
				logger.warn("convert " + name + "=" + val + " with default value: " + def + " failed");
			}
		}
		return def;
	}

	/**
	 * 获取应用程序的根路径
	 * 在web应用中，通常为...webapps/project/web目录。
	 *
	 * @return 完整路径
	 * @see #getAppPath(String...)
	
	 */
	public static String getAppPath() {
		return getAppPath("");
	}

	/**
	 * 获取相对于应用程序路径的地址。
	 * 不存在该路径则进行建立。
	 * 在web应用中，通常为...webapps/project/web/...relativePath目录。
	 *
	 * @param relativePath 相对路径
	 * @return 完整路径
	 * @see #getAppPath(String[], Boolean)
	
	 */
	public static String getAppPath(String... relativePath) {
		return getAppPath(relativePath, true);
	}

	/**
	 * 获取相对于应用程序的路径
	 * 在web应用中，通常为...webapps/project/web/...relativePath目录。
	 *
	 * @param relativePath 相对路径
	 * @param createPath   若不存在该路径，是否进行新建
	 * @return 完整路径


	 */
	public static String getAppPath(String[] relativePath, Boolean createPath) {
		String filePath = System.getProperty("admin.root");
        if (filePath == null) filePath = "";
		for (String path : relativePath) {
			if (StringUtils.isNotBlank(path)) {
				filePath += (path + (path.endsWith("/") || path.endsWith("\\") ? "" : "/"));
			}
		}
		filePath=filePath.replace("//","/").replace("\\\\","\\");
		if (createPath) {
			File dir = new File(filePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		return filePath;
	}

	/**
	 * 取得映射后的地址
	 *
	 * @param url 访问url,为截取了最后一个/之前的内容。
	 * @return 转换后的url


     * 
	 */
	public static String getMappedURL(String url) {
		url = url.substring(0, url.lastIndexOf("/") + 1);
		for (int i = 0, len = mappedUrls.length; i < len;) {
			Pattern ptrn = Pattern.compile(mappedUrls[i], Pattern.CASE_INSENSITIVE);
			Matcher m = ptrn.matcher(url);
			i++;
			if (m.find()) {
				return mappedUrls[i];
			}
		}
		return url;
	}

	/**
	 * @param request
	 * @return 转换后的url
	 * @see #getMappedURL(String url)


	 */
	public static String getMappedURL(HttpServletRequest request) {
		return getMappedURL(request.getRequestURL().toString());
	}

	/**
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 *
	 * @param request
	 * @return

	
	 */
	public static Map<String, String> parseRequestParam(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			map.put(key, request.getParameter(key));
		}
		return map;
	}

	/**
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 *
	 * @param param
	 * @param split
	 * @return

	
	 */
	public static Map<String, String> parseParam(String param, String split) {
		String[] val = StringUtils.splitPreserveAllTokens(param, split);
		Map<String, String> map = new HashMap<String, String>();
		for (int j = 0; j < val.length; j++) {
			int m = j, n = ++j;
			if ((StringUtils.isBlank(val[m]))
					|| (StringUtils.isBlank(val[n]))) {
				continue;
			}
			map.put(val[m], val[n].trim());
		}
		return map;
	}

	
	/**
	 * 设置配置缓存集合
	 * 〈功能详细描述〉
	 *
	 * @param resource  资源对象

	
	 */
	private static void setCache(ResourceBundle resource) {
		for (String key : resource.keySet()) {
			conf.put(key.toUpperCase(), resource.getString(key));
		}
	}


	/**
	 * 设置配置缓存集合
	 * 〈功能详细描述〉
	 *
	 * @param resPath 配置文件地址

	
	 */
	private static void setCache(String resPath) {
		ResourceBundle resource = getConf(resPath);
		if (null != resource) {
			setCache(resource);
		}
	}

	/**
	 * 获取资源文件
	 *
	 * @param resPath 配置文件地址
	 * @return 资源对象


	 */
	private static ResourceBundle getConf(String resPath) {
		ResourceBundle resource = null;
		try {
			resource = ResourceBundle.getBundle(resPath, new ResourceBundle.Control(){
				@Override
				public long getTimeToLive(String baseName, Locale locale){
					return ResourceBundle.Control.TTL_DONT_CACHE;
				}
				@Override
				public boolean needsReload(String baseName,
                           Locale locale,
                           String format,
                           ClassLoader loader,
                           ResourceBundle bundle,
                           long loadTime){
					return true;
				}
			});
		} catch (MissingResourceException ex) {
			logger.warn("Can't find resource file " + resPath);
		}
		return resource;
	}


	/**
	 * 返回配置缓存
	 *
	 * @return 配置缓存Map


	 */
	public static Map<String, String> getCache() {
		return conf;
	}

	/**
	 * 配置非字符串的数据类型
	 */
}

