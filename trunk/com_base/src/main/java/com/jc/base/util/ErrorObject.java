package com.jc.base.util;

import java.io.Serializable;

/**
 * User: qiaolei Date: 13-4-27 Time: 下午3:46
 */
public class ErrorObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public ErrorObject(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ErrorObject{" + "message='" + message + '\'' + '}';
	}
}