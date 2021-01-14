package com.brandWall.util;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class SignUtil {

	public static Map<String,String> singData(String ticket, String url) throws Exception {
		Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
//        String string1;
        String signature = "";
        
        signature=getSignature(ticket, nonce_str, timestamp, url);
        
		ret.put("url", url);
        ret.put("jsapi_ticket", ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        return ret;
	}
    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
	 /**
     * 获得分享链接的签名。
     * @param ticket
     * @param nonceStr
     * @param timeStamp
     * @param url
     * @return
     * @throws Exception
     */
    public static String getSignature(String ticket, String nonceStr, String timeStamp, String url) throws Exception {
        String sKey = "jsapi_ticket=" + ticket
                + "&noncestr=" + nonceStr + "&timestamp=" + timeStamp
                + "&url=" + url;
        System.out.println(sKey);
        return getSignature(sKey);
    }


    /**
     * 验证签名。
     * 
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static String getSignature(String sKey) throws Exception {
        String ciphertext = null;
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digest = md.digest(sKey.toString().getBytes());
        ciphertext = byteToStr(digest);
        return ciphertext.toLowerCase();
    }

 /** 
     * 将字节数组转换为十六进制字符串 
     *  
     * @param byteArray 
     * @return 
     */ 
    private static String byteToStr(byte[] byteArray) {  
        String strDigest = "";  
        for (int i = 0; i < byteArray.length; i++) {  
            strDigest += byteToHexStr(byteArray[i]);  
        }  
        return strDigest;  
    }  
  /** 
     * 将字节转换为十六进制字符串 
     *  
     * @param mByte 
     * @return 
     */ 
    private static String byteToHexStr(byte mByte) {  
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };  
        char[] tempArr = new char[2];  
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];  
        tempArr[1] = Digit[mByte & 0X0F];  

        String s = new String(tempArr);  
        return s;  
    }
}