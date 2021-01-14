package com.brandWall.util;

import com.brandWall.util.exception.MyException;

public class Sample {
	//设置APPID/AK/SK
    public static final String APP_ID = Config.bdAppid;
    public static final String API_KEY = Config.bdKey;
    public static final String SECRET_KEY = Config.secretKey;
   	
	public static String lpr(String imgPath)throws MyException{
		// 初始化一个AipOcr
  //      AipOcr client = new AipOcr(APP_ID, API_KEY,SECRET_KEY);
//		 AipOcr client = 
//        // 可选：设置网络连接参数
//        client.setConnectionTimeoutInMillis(2000);
//        client.setSocketTimeoutInMillis(60000);
//
//        // 调用接口
//        String path =imgPath;
//        JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
//        boolean flag=false;
//        try {        	
//        	if(res.get("error_code")!=null){
//        		flag=false;
//        		System.out.println(res.get("error_code"));
//        	}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}        
//        if(!flag){
//        	return null;
//        }      		
		return null;
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(lpr("D://2 (2).jpg"));
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
