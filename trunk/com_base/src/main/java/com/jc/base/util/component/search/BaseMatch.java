package com.jc.base.util.component.search;

import java.util.List;

/**
 * 匹配的抽象基类
 * @author chenzhao
 *
 */
public abstract class BaseMatch {
	protected Dictionary dic=null;
	public void setDic(Dictionary dic) {
		this.dic = dic;
	}
	/**将list转为string
	 * @param resultList
	 * @return
	 */
	public String toString(List<String> resultList){
		String resultString="";
		for(int i=0,length=resultList.size();i<length;i++){
			String word=resultList.get(i);
			resultString=resultString+word+" ("+dic.getFrequency(word)+")\n";
		}
		return resultString;
	}
	abstract public List<String> split(String sentence);
}
