package com.brandWall.util;

import java.util.List;

import com.brandWall.util.exception.MyException;

/**
 * 判断对象null
 * 
 * @author 石小�?
 *
 */
public class Assert {

	public Assert() {
	}

	/**
	 * 对象为空返回错误
	 * 
	 * @param object
	 *            对象
	 * @param message
	 *            错误信息
	 */
	public static void notObjectNull(Object object, String message) throws MyException {
		if (object == null) {
			throw new MyException(message);
		}
	}

	/**
	 * 集合为空或大小为0返回错误
	 * 
	 * @param object
	 *            对象
	 * @param message
	 *            错误信息
	 */
	public static void notListSize(List<?> list, String message) throws MyException {
		if (list == null) {
			throw new MyException(message);
		}
		if (list.size() <= 0) {
			throw new MyException(message);
		}
	}

	/**
	 * 判断参数是否为空
	 * 
	 * @param object
	 *            对象
	 * @param message
	 *            错误信息
	 */
	public static void notParam(Object param, String message) throws MyException {
		if (param == null) {
			throw new MyException(message);
		}
		if (param.equals("") || param.equals(" ") || param.equals("undifend")) {
			throw new MyException(message);
		}
	}

	/**
	 * 判断参数是否等于�?
	 * 
	 * @param object
	 *            对象
	 * @param message
	 *            错误信息
	 */
	public static void notIntParam(int param, int intParam, String message) throws MyException {
		if (param == intParam) {
			throw new MyException(message);
		}

	}

}
