/**  
 * Description: 集合工具类  
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

import java.util.HashSet;
import java.util.List;

/**
 * 集合工具类 〈功能详细描述〉
 * 
 * @author chenzhao
 * @version [版本号, 2012-12-17]
 */
public class CollectionUtil {

	/**
	 * 合并List重复的键值 参数：list - 返回：List*
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List distinctList(List list) {
		/*
		 * Set set = new HashSet(list); Iterator iterator = set.iterator(); List
		 * tempList = new ArrayList(); int i = 0; while (iterator.hasNext()) {
		 * tempList.add(iterator.next().toString()); i++; } return tempList;
		 */
		@SuppressWarnings("unchecked")
		HashSet h = new HashSet(list);
		list.clear();
		list.addAll(h);
		return list;
	}
}
