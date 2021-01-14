package com.brandWall.util.exception;

import com.brandWall.util.Msg;

public class MyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String retCd = "0000"; // 异常对应的返回码
	private String msgDes = "未知错误"; // 异常对应的描述信�?

	public MyException() {

		super();
	}

	public MyException(String message) {
		super(message);
		msgDes = message;
	}

	public MyException(String retCd, String msgDes) {
		super();
		this.retCd = retCd;
		this.msgDes = msgDes;
	}
	
	public MyException(Msg msg){
		super();
		this.retCd = msg.getCode();
		this.msgDes = msg.getMessage();
	}

	public String getRetCd() {
		return retCd;
	}

	public String getMsgDes() {
		return msgDes;
	}

	@Override
	public String toString() {
		return "{retCd:" + retCd + ", msgDec:" + msgDes + "}";
	}

}
