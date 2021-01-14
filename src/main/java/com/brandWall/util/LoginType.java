package com.brandWall.util;

public enum LoginType {

	PHONE(0, "手机登陆"), QQ(1, "QQ第三方登陆"), WECHAR(2, "微信第三方登陆"), USERNAME(3, "用户名登陆");
	private Integer code;

	private String name;

	LoginType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static boolean isOtherLogin(Integer loginType) {
		if (loginType == QQ.code || loginType == WECHAR.code) {
			return true;
		}
		return false;
	}
	
	public static boolean isDefaultLogin(Integer loginType){
		if (loginType == PHONE.code || loginType == USERNAME.code) {
			return true;
		}
		return false;
	}

	public static boolean isAllLogin(Integer loginType) {
		if (isDefaultLogin(loginType) || isOtherLogin(loginType)) {
			return true;
		}
		return false;
	}

	
	
	
}
