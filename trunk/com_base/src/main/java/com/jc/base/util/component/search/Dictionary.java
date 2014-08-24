package com.jc.base.util.component.search;

import java.io.*;
import java.util.HashMap;

/**
 * 读取文本，通过HashMap构建字典类
 * 
 * @author Catcher
 * 
 */
public class Dictionary {
	/**
	 * 使用HashMap存储字典
	 */
	private HashMap<String, Integer> dic = new HashMap<String, Integer>();
	private int maxWordLength = 0;
	private int maxWordFrequency = 0;
	private long wordNumber = 0;

	public Dictionary() {
		loadDictionary("dict.txt");
	}

	public int getMaxWordLength() {
		return maxWordLength;
	}

	public int getFrequency(String word) {
		if (containsKey(word))
			return (Integer) dic.get(word);
		else
			return 0;
	}

	/**
	 * 向字典中添加单词
	 * 
	 * @param newWord
	 */
	public void addWord(String lineString) {
		try {
			String[] strings = lineString.split(" ");
			String newWord = strings[0].trim();
			int frequency = Integer.parseInt(strings[1].trim());
			// System.out.println("----------"+frequency);
			if (newWord.length() > maxWordLength) {
				maxWordLength = newWord.length();
			}
			if (dic.containsKey(newWord)) {
				int preFrequency = (Integer) dic.get(newWord);
				dic.put(newWord, new Integer(preFrequency + frequency));
			} else {
				dic.put(newWord, new Integer(frequency));
				wordNumber++;
			}
			if (dic.get(newWord) > maxWordFrequency) {
				maxWordFrequency = dic.get(newWord);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * 读取文件，加载字典
	 * 
	 * @param dictPath
	 */
	public void loadDictionary(String dictPath) {
		BufferedReader reader = null;
		try {
			InputStream inputStream = getClass().getResourceAsStream(dictPath);
			reader = new BufferedReader(new InputStreamReader(inputStream,
					"UTF-8"));
			// reader=new BufferedReader(new FileReader(dictPath));
			String lineString = reader.readLine();
			while (lineString != null && !lineString.trim().equals("")) {
				//System.out.println(lineString);
				addWord(lineString);
				lineString = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断字典中是否包含某个单词，方便外部使用
	 * 
	 * @param word
	 * @return
	 */
	public boolean containsKey(String word) {
		return dic.containsKey(word);
	}
}