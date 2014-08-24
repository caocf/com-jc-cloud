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

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;


/**
 * 敏感词工具类
 * 〈功能详细描述〉
 *
 * @author chenzhao
 * @version [版本号, 2012-12-18]


 */
public abstract class SensitiveWordUtil {

    /**
     * 日志通用
     */
	private static Logger logger = Logger.getLogger(SensitiveWordUtil.class);// 日志记录器
	/**
	 */
	private static Set<String> treeSet;;// 存储词库
	//private static String path = ConfigurationUtils.getString("wordPath");// 词库文件路径
	//private static BufferedReader br=null;

	/**
	 * 构造函数
	 */
	private SensitiveWordUtil() {
	}


	/**
	 * 装载词库到内存
	 
	static {
		try {
			br = new BufferedReader(new InputStreamReader(SensitiveWordUtil.class.getResourceAsStream(path)));
			String line = null;
			while (((line = br.readLine()) != null)) {
				hashSet.add(line);
			}
			br.close();
		} catch (Exception e) {
			logger.error(e);
		}
	}
	*/
	
    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param keyWord


     */
    public static void treeSetAdd(String keyWord) {
        if(treeSet!=null){
            treeSet.add(keyWord);
        }
    }
    
    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param OldkeyWord
     * @param newKeyWord


     */
    public static void treeSetEdit(String OldkeyWord,String newKeyWord) {
        if(treeSet!=null){
            if(treeSet.contains(OldkeyWord)){
                treeSet.remove(OldkeyWord);
            }
            treeSet.add(newKeyWord);
        }
    }
    
    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param keyWord


     */
    public static void treeSetDel(String keyWord) {
        if(treeSet.contains(keyWord)){
            treeSet.remove(keyWord);
        }
    }



	/**
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 *
	 * @return

	
	 */
	public static Set<String> getTreeSet() {
		return treeSet;
	}


	/**
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 *
	 * @param treeSet

	
	 */
	public static void setTreeSet(Set<String> treeSet) {
		SensitiveWordUtil.treeSet = treeSet;
	}



	/**
	 * 搜索关键字
	 * 〈功能详细描述〉
	 *
	 * @param array
	 * @return

	
	 */
	public static String[] getSensitiveWords(String[] array) {
		return getSensitiveWords(Arrays.asList(array));
	}





	/**
	 * 搜索关键字
	 * 〈功能详细描述〉
	 *
	 * @param collection
	 * @return

	
	 */
	public static String[] getSensitiveWords(Collection<String> collection) {
		StringBuilder builder = new StringBuilder();
		Iterator<String> ite = collection.iterator();
		String s = null;
		while (ite.hasNext()) {
			s = ite.next();
			if (treeSet.contains(s)) {
				builder.append(s);
				builder.append(",");
			}
		}
		return builder.toString().split(",");
	}

	/**
	 * 判断是否有关键字
	 * 〈功能详细描述〉
	 *
	 * @param collection
	 * @return

	
	 */
	public static boolean haveSensitiveWord(Collection<String> collection) {
		Iterator<String> ite = collection.iterator();
		String s = null;
		while (ite.hasNext()) {
			s = ite.next();
			if (treeSet.contains(s)) {
				return true;
			}
		}
		return false;
	}
}
