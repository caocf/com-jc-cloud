package com.jc.base.util.component.search;


/**
 * 用于构建二杈树时用的节点
 * @author chenzhao
 *
 */
public class Word {
	/**
	 * 相对的起始位置
	 */
	private int startPos;
	/**
	 * 相对的结束位置
	 */
	private int endPos;
	private int frequency;
	private String content;
	private Word left;
	private Word right;
	
	public int getStartPos() {
		return startPos;
	}
	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}
	public int getEndPos() {
		return endPos;
	}
	public void setEndPos(int endPos) {
		this.endPos = endPos;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Word getLeft() {
		return left;
	}
	public void setLeft(Word left) {
		this.left = left;
	}
	public Word getRight() {
		return right;
	}
	public void setRight(Word right) {
		this.right = right;
	}
}
