package com.brandWall.util;

import java.math.BigDecimal;
import java.util.Collection;

public class ValidateUtil {
	/**
	 * 判断字符串有效性
	 */
	public static boolean isValid(String src){
		if(src == null || "".equals(src.trim())||src.equals("undefined")){
			return false ;
		}
		return true ;
	}
	
	/**
	 * 判断整形有效性
	 */
	public static boolean isValid(int src){
		if(src == 0 ){
			return false ;
		}
		return true ;
	}
	
	/**
	 * 判断double有效性
	 */
	public static boolean isValid(double src){
		if(src == 0.0 ){
			return false ;
		}
		return true ;
	}
	
	/**
	 * 判断集合的有效性 
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isValid(Collection col){
		if(col == null || col.isEmpty()){			
			return false ;
		}
		return true ;
	}
	
	@SuppressWarnings("rawtypes")
	public static Collection isList(Collection col){
		if(col == null || col.isEmpty()){			
			return null ;
		}
		return col ;
	}
	
	/**
	 * 判断数组是否有效
	 */
	public static boolean isValid(Object[] arr){
		if(arr == null || arr.length == 0){
			return false ;
		}
		return true ;
	}
	
	/**
	 * 判断字符串有效性
	 */
	public static boolean isValid(BigDecimal src){
		if(src == null || "".equals(src)||src.equals("null")){
			return false ;
		}
		return true ;
	}
	
	public static String id(String col){
		if(col == null || col.isEmpty()){			
			return null ;
		}
		return col ;
	}

}
