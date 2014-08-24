package com.jc.base.util.component.search;

import java.util.ArrayList;
import java.util.List;

/**逆向最大匹配
 * @author chenzhao
 *
 */
public class BackwardMatch extends BaseMatch {
	@Override
	public List<String> split(String sentence) {
		List<String> resultList=new ArrayList<String>();
		int maxLength=dic.getMaxWordLength();
		int targetLength=maxLength;
		int restLength=sentence.length();
		int position=sentence.length();
		
		while(restLength>0){
			//判断要找的词长度是否比句子剩余长度大
			if(targetLength>restLength){
				targetLength=restLength;
			}
			String word=sentence.substring(position-targetLength,position);
			//若要找的词语在字典中存在，或该词语长度为1
			if(dic.containsKey(word)||1==targetLength){
				resultList.add(word);
				position-=targetLength;
				targetLength=maxLength;
				restLength=position;
			}else{
				targetLength--;
			}
		}
		return reverseList(resultList);
	}
	
	/**
	 * 由于逆向的，分词后拿到的list中的数据是反向的，需逆转
	 * @param list
	 * @return
	 */
	public List<String> reverseList(List<String> list){
		List<String> resultList=new ArrayList<String>();
		int length=list.size()-1;
		for(int i=length;i>=0;i--){
			resultList.add(length-i, list.get(i));
		}
		return resultList;
	}
}
