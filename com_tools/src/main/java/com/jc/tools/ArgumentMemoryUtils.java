package com.jc.tools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * @ClassName: ArgumentMemoryUtils
 * @Description: 全局参数工具类，用来同步数据库数据到内存
 * 
 */
public final class ArgumentMemoryUtils {
	/**
	 * @Fields argumentsMap :存放全局参数的Map
	 */
	private static Map<String, String> argumentsMap = new HashMap<String, String>();
	/**
	 * @Fields dbTablesInfo : 存放数据库表列、表、及其注释
	 */
	private static Map<String, Map<String, String>> dbTablesInfo = new HashMap<String, Map<String, String>>();

	public static Map<String, Map<String, String>> getDbTablesInfo() {
		return dbTablesInfo;
	}

	public static void setDbTablesInfo(
			Map<String, Map<String, String>> dbTablesInfo) {
		ArgumentMemoryUtils.dbTablesInfo = dbTablesInfo;
	}

	/**
	 * @ClassName: ArgumentsMemoryUtilsHolder
	 * @Description: 私有静态类，用来存放单例
	 * 
	 */
	private static class ArgumentsMemoryUtilsHolder {
		static final ArgumentMemoryUtils INSTANCE = new ArgumentMemoryUtils();
	}

	/**
	 * @Title: getInstance
	 * @Description: 单例方法
	 * @param @return
	 * @return ArgumentMemoryUtils
	 * @throws
	 */
	public static ArgumentMemoryUtils getInstance() {
		return ArgumentsMemoryUtilsHolder.INSTANCE;
	}

	/**
	 * <p>
	 * Title: 私有构造方法
	 * </p>
	 * <p>
	 * Description: 为了实现单例
	 * </p>
	 */
	private ArgumentMemoryUtils() {
	}

	/**
	 * @Title: getValueByName
	 * @Description:根据参数名称得到参数值
	 * @param @param name
	 * @param @return
	 * @return String
	 * @throws
	 */
	public String getValueByName(String name) {
		return argumentsMap.get(name);
	}

	public int getIntValueByName(String name) {
		return Integer.parseInt(argumentsMap.get(name));
	}

	/**
	 * @Title: getArgumentsMap
	 * @Description:获得参数map
	 * @param @return
	 * @return Map<String,String>
	 * @throws
	 */
	public Map<String, String> getArgumentsMap() {
		return argumentsMap;
	}

	/**
	 * @Title: setArgumentsMap
	 * @Description: 设置参数map
	 * @param @param argumentsMap
	 * @return void
	 * @throws
	 */
	public void setArgumentsMap(Map<String, String> argumentsMap) {
		ArgumentMemoryUtils.argumentsMap = argumentsMap;
	}

	/**
	 * @Title: getMapByKey
	 * @Description: 根据key获取map
	 * @param @param mapInfo,name
	 * @return Map<String, String>
	 * @throws
	 */
	public Map<String, String> getMapByKey(
			Map<String, Map<String, String>> mapInfo, String name) {
		Map<String, String> mapResult = new HashMap<String, String>();
		try {
			Set<Entry<String, Map<String, String>>> set = mapInfo.entrySet();
			Iterator<Entry<String, Map<String, String>>> ii = set.iterator();
			while (ii.hasNext()) {
				Entry e = ii.next();
				if (e.getKey().toString().trim().equals(name.trim())) {
					mapResult = (Map<String, String>) e.getValue();
					break;
				}
			}
			return mapResult;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * @Title: getMapValue
	 * @Description: 如果map的value为空则获取key
	 * @param @param mapInfo,name
	 * @return String
	 * @throws
	 */
	public String getMapValue(Map<String, String> mapInfo, String name) {
		String mapResult = null;
		for (Map.Entry<String, String> entry : mapInfo.entrySet()) {
			if (entry.getKey().toString().trim().equals(name.trim())) {
				if(entry.getValue()==null)
				{
					mapResult= entry.getKey();
				}
				else
				{
					mapResult= entry.getValue();
				}
				break;
			}
		}
		return mapResult;
	}
}
