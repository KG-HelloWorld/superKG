package com.brandWall.util;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

/**
 * 什么都有的工具类
 * 
 * @author only_U
 *
 */
public class CayUtil {

	/**
	 * ========================== Time ==========================
	 */

	/**
	 * 
	 * @param date
	 * @param cType
	 *            ====>Calendar 的属性参数 例如：Calendar.MINUTE
	 * @param lenght
	 *            ====>整形,表示想要获取多长的cType 类型之后的时间
	 * @return
	 */
	public static Date getTimeByAfter(Date date, Integer cType, Integer lenght) {
		Calendar now = Calendar.getInstance();
		// Calendar.MINUTE
		now.add(cType, lenght);

		return now.getTime();
	}

	/**
	 * ========================= number ============================
	 */

	/**
	 * 获取随机随机数字字符串
	 * 
	 * @param lenght
	 *            获取随机数字字符串的长度
	 * @return
	 */
	public static String getRandomNumberByLenght(Integer lenght) {
		if (lenght < 0) {
			lenght = 1;
		}
		return String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, lenght - 1)));
		// return "123456";
	}

	/**
	 * 获取UUID 去“-”,↑大写
	 * 
	 * @return
	 */
	public static String getUUId() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().toUpperCase().replace("-", "");
	}

	public static StringBuffer getInterfaceMethod(HttpServletRequest request) {

		return request.getRequestURL();
	}

	public static String getUrl(String url) {
		if (url == null || url.equals("")) {
			return null;
		}
		if (url.startsWith("http:")) {
			return url;
		}
		return Config.getFileRootUrl() + url;
	}

	public static String getFolderByDate() {
		Integer[] folder = new Integer[3];
		Calendar calendar = Calendar.getInstance();
		folder[0] = calendar.get(Calendar.YEAR);
		folder[1] = calendar.get(Calendar.MONTH);
		folder[2] = calendar.get(Calendar.DAY_OF_MONTH);
		return folder[0] + "//" + folder[1] + "//" + folder[2];
	}

	public static void main(String[] args) {

		String str = getRandomNumberByLenght(4);

		System.out.println(str);

	}

}
