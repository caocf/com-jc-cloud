package com.jc.base.util.component.search;


import java.util.ArrayList;
import java.util.List;

/**正向最大匹配
 * @author chenzhao
 *
 */
public class ForwardMatch extends BaseMatch {
	@Override
	public List<String> split(String sentence) {
		int position=0;
		int maxLength=dic.getMaxWordLength();
		int targetLength=maxLength;
		int restLength=sentence.length();
		List<String> resultList=new ArrayList<String>();
		
		while(restLength>0){
			//判断要找的词长度是否比句子剩余长度大
			if(targetLength>restLength){
				targetLength=restLength;
			}
			String word=sentence.substring(position, position+targetLength);
			//若要找的词语在字典中存在，或该词语长度为1
			if(dic.containsKey(word)||1==targetLength){
				resultList.add(word);
				position+=targetLength;
				targetLength=maxLength;
				restLength=sentence.length()-position;
			}else{
				targetLength--;
			}			
		}
		return resultList;
	}
}
