package com.jc.base.util.component.search;


import java.util.ArrayList;
import java.util.List;

/**
 * 取频率最高的词，然后两端递归，构建二杈树存储句子中的词语，显示的时候使用中序遍历二杈树
 * 由于极有可能单个字的使用频率比整个词还高，筛选的时候进行了处理
 * 若单个字不处于当前句子开头，先忽略，
 * 若单个的字处在开头，临时mostFrequency仍为0，且单词长度为1，则加入到树中
 * @author 陈钊
 *
 */
public class FrequencyMatch extends BaseMatch{		
	/**
	 * 用于存储二杈树的根节点
	 */
	private Word word;
	/**
	 * 由于在其他函数中会用到，单独提出来做参数
	 */
	private List<String> resultList=new ArrayList<String>();
	@Override
	public List<String> split(String sentence) {		
		word=constructWord(sentence);
		constructList(word);		
		return resultList;
	}
		
	/**使用中序遍历二杈树，构建List
	 * @param word
	 */
	public void constructList(Word word){
		if(word!=null){
			constructList(word.getLeft());
			resultList.add(word.getContent());
			constructList(word.getRight());			
		}
	}
	/**获取句子中使用最频繁的词
	 * @param sentence
	 * @return
	 */
	public Word getMostFrequencyWord(String sentence){		
		int maxLength=dic.getMaxWordLength();		
		String mostFrequencyContent=null;		
		int mostFrequency=0;
		int startPos=0,endPos=0;
		for(int i=0,sentenceLength=sentence.length();i<sentenceLength;i++){
			//当剩余字符长度小于字典中最大长度时
			if(sentenceLength-i<maxLength){
				maxLength=sentenceLength-i;
			}
			for(int j=maxLength;j>0;j--){
				String temp=sentence.substring(i, i+j);
				int tempLength=dic.getFrequency(temp);
				//由于很单个的词频率很大，当他们不是处在开头的时候，先忽略
				if(tempLength>mostFrequency&&temp.length()>1){
					//System.out.println("tempLength-------"+tempLength+"---"+temp);
					mostFrequency=tempLength;
					mostFrequencyContent=temp;
					startPos=i;
					endPos=i+j;
				}
				//单个的词处在开头，mostFrequency仍为0，且单词长度为1
				if(mostFrequency==0&&temp.length()==1&&i==0){
					//System.out.println("tempLength-------"+tempLength+"---"+temp);
					mostFrequency=tempLength;
					mostFrequencyContent=temp;
					startPos=i;
					endPos=i+1;
				}
			}
		}
		if(mostFrequencyContent.length()>0){
			System.out.println("Most---"+mostFrequencyContent+"----"+mostFrequency);
			Word mostFrequencyWord=new Word();			
			mostFrequencyWord.setContent(mostFrequencyContent);
			mostFrequencyWord.setFrequency(mostFrequency);
			mostFrequencyWord.setStartPos(startPos);
			mostFrequencyWord.setEndPos(endPos);
			return mostFrequencyWord;
		}else{
			return null;
		}
	}
	
	/**递归构建二杈树
	 * @param sentence
	 * @return
	 */
	private Word constructWord(String sentence){
		Word word=getMostFrequencyWord(sentence);
		if(word.getStartPos()>0){
			String leftSentence=sentence.substring(0, word.getStartPos());
			System.out.println("leftSentence----"+leftSentence);
			word.setLeft(constructWord(leftSentence));
		}
		if(word.getEndPos()<sentence.length()){			
			String rightSentence=sentence.substring(word.getEndPos(), sentence.length());
			System.out.println("rightSentence----"+rightSentence);
			word.setRight(constructWord(rightSentence));
		}		
		return word;
	}
}
