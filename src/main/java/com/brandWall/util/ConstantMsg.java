package com.brandWall.util;


/**
 * 返回消息常量
 * 
 * @author 王翠
 * 
 */
public final class ConstantMsg {

	public final static String MSG_SUCCESS = "1001";// 操作成功

	public final static String MSG_SUCCESS_CONTANT = "操作成功";// 成功内容

	public final static String MSG_NOLOGIN = "0000";// 未登录

	public final static String MSG_NOLOGIN_CONTANT = "用户未登录";// 未登录内容

	public final static String MSG_ERROR = "0001";// 操作失败

	public final static String MSG_ERROR_CONTANT = "操作失败";// 操作失败内容

	public static void main(String[] args) {
//		Msg msg=Msg.MsgSuccess(null);
//		System.out.println("code="+msg.getCode()+";msg="+msg.getMessage());
		
		
		int[][] arr=new int[100][100];
		for(int i=0;i<arr.length;i++){
			for(int j=0;j<arr.length;j++){
				System.out.println(arr[i][i]);
			}
		}
		
	}
}
