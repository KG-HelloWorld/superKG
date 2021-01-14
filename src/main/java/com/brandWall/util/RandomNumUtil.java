package com.brandWall.util;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;



/**
 * @ClassName RandomNumUtil
 * @Description TODO 随机数工具类
 * @author zlp
 * @date 2015年5月7日
 */
public class RandomNumUtil {
	/**
	 * @Description:TODO 获取随机由日期和6位随机数组成的id
	 * @param @return	
	 * @return String 返回id字符
	 * @author FangRuitao
	 * @date 2015年5月7日
	 */
	public static String getCustomId(){
		SimpleDateFormat sdf = new SimpleDateFormat("ddHHmmss");
		String coustomId = sdf.format(new Date());
		
		Random rd = new Random();
		int num;
		do{
			num = rd.nextInt(4);	//获取随机
			coustomId = coustomId+num;
		}while(coustomId.length()<16);
		
		return coustomId;
	}
	
	/**
	 * @Description:TODO 随机生成文件
	 * @param @return
	 * @return String 返回随机文件
	 * @author FangRuitao
	 * @date 2015年5月7日
	 */
	public static String getFileName(){
		return getCustomId();
	}
	/**
	 * 年月日时分秒+6位随机数 产生20位随机数
	 * @return couponId
	 */
	public static String getRandom20(){
		String couponId = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		couponId = sdf.format(new Date());
		Random random = new Random();
		int x = random.nextInt(899999)+100000;
		couponId+=x;
		return couponId;
	}
	
	/**
	 * 产生10位随机ID
	 * @return couponId
	 */
	public static String getRandomId(){
		String couponId = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		couponId = sdf.format(new Date());
		Random random = new Random();
		int x = random.nextInt(899999)+100000;
		couponId+=x;
		return couponId;
	}
	
	/**
	 * 产生token
	 * @param len
	 * @return
	 */
	public static String getToken(int len){
		Random rd = new Random();
		String n="";
		int getNum;
		do {
		   //getNum = Math.abs(rd.nextInt())%10 + 48;//产生数字0-9的随机数
		   getNum = Math.abs(rd.nextInt())%26 + 97;//产生字母a-z的随机数
		   char num1 = (char)getNum;
		   String dn = Character.toString(num1);
		   n += dn;
		 } while (n.length()<len);
		 System.out.println("随机的"+len+"位id是：" + n);
		 return n;
	}
	
	public static String getFourNumber(){
		int num = (int) (Math.random()*9000+1000);
		return ""+num;
	}
	
	public static void main(String[] args) {
		System.out.println(getCustomId());
	}
	
}



