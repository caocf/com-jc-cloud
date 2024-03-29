package com.jc.base.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 *  读入内存Dictionary
 * 
 * @author chenzhao
 * @version [版本号, 2012-12-17]


 */
public final class DictionaryMemoryUtils {
	
	/** 
	  * @Fields dictionarysMap : 存放系统字典的Map,nickName和value,name
	  */ 
	private static Map<String,Map<String,String>> dictionarysMap = new HashMap<String,Map<String,String>>();


	/** 
	  * <p>Title: 私有构造方法</p> 
	  * <p>Description:为了实现单例 </p>  
	  */ 
	private DictionaryMemoryUtils(){
	}
	/** 
	  * @ClassName: DictionaryMemoryUtilsHolder 
	  * @Description:私有静态类，用来存放单例 
	  *  
	  */
	private static class DictionaryMemoryUtilsHolder{
		static final DictionaryMemoryUtils INSTANCE = new DictionaryMemoryUtils();
	}
	
	/** 
	  * @Title: getInstance 
	  * @Description: 获得单例对象
	  * @param @return
	  * @return DictionaryMemoryUtils
	  * @throws 
	  */
	public static DictionaryMemoryUtils getInstance(){
		return DictionaryMemoryUtilsHolder.INSTANCE;
	}
	
	/** 
	  * @Title: getDictionarysMap 
	  * @Description:获得字典map 
	  * @param @return
	  * @return Map<String,Map<String,String>>
	  * @throws 
	  */
	public Map<String, Map<String, String>> getDictionarysMap() {
		return dictionarysMap;
	}

	/** 
	  * @Title: setDictionarysMap 
	  * @Description:设置字典map 
	  * @param @param dictionarysMap
	  * @return void
	  * @throws 
	  */
	public void setDictionarysMap(
			Map<String, Map<String, String>> dictionarysMap) {
		DictionaryMemoryUtils.dictionarysMap = dictionarysMap;
	}

	
	/** 
	  * @Title: getMapByNickName 
	  * @Description: 根据nickName获得value与name的键值对 
	  * @param @param nickName
	  * @param @return
	  * @return Map<String,String>
	  * @throws 
	  */
	public Map<String,String> getMapByNickName(String nickName){
		return dictionarysMap.get(nickName);
	}
	
	/** 
	  * @Title: getNameByValue 
	  * @Description: 根据value获得name 
	  * @param @param value
	  * @param @return
	  * @return String
	  * @throws 
	  */
	public String getNameByValue(String value){
		Map<String,String> valueNameMap = new HashMap<String,String>();
		Set<String> keys = dictionarysMap.keySet();
		for(Iterator<String> iter=keys.iterator();iter.hasNext();){
			valueNameMap.putAll(dictionarysMap.get(iter.next()));
		}
		return valueNameMap.get(value);
	}
	
}
