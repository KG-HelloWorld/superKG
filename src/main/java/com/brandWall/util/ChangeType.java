package com.brandWall.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 格式转换
 * 
 * @author 王翠
 * 
 */
public class ChangeType {
	/** 定义数组存放数字对应的大写 */
	private final static String[] STR_NUMBER = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };

	/** 定义数组存放位数的大写 */
	private final static String[] STR_MODIFY = { "", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟" };

	/**
	 * 转化整数部分
	 * 
	 * @param tempString
	 * @return 返回整数部分
	 */
	private static String getInteger(String tempString) {
		/** 用来保存整数部分数字串 */
		String strInteger = null;//
		/** 记录"."所在位置 */
		int intDotPos = tempString.indexOf(".");
		int intSignPos = tempString.indexOf("-");
		if (intDotPos == -1)
			intDotPos = tempString.length();
		/** 取出整数部分 */
		strInteger = tempString.substring(intSignPos + 1, intDotPos);
		strInteger = new StringBuffer(strInteger).reverse().toString();
		StringBuffer sbResult = new StringBuffer();
		for (int i = 0; i < strInteger.length(); i++) {
			sbResult.append(STR_MODIFY[i]);
			sbResult.append(STR_NUMBER[strInteger.charAt(i) - 48]);
		}

		sbResult = sbResult.reverse();
		replace(sbResult, "零拾", "零");
		replace(sbResult, "零佰", "零");
		replace(sbResult, "零仟", "零");
		replace(sbResult, "零万", "万");
		replace(sbResult, "零亿", "亿");
		replace(sbResult, "零零", "零");
		replace(sbResult, "零零零", "零");
		/** 这两句不能颠倒顺序 */
		replace(sbResult, "零零零零万", "");
		replace(sbResult, "零零零零", "");
		/** 这样读起来更习惯. */
		replace(sbResult, "壹拾亿", "拾亿");
		replace(sbResult, "壹拾万", "拾万");
		/** 删除个位上的零 */
		if (sbResult.charAt(sbResult.length() - 1) == '零' && sbResult.length() != 1)
			sbResult.deleteCharAt(sbResult.length() - 1);
		if (strInteger.length() == 2) {
			replace(sbResult, "壹拾", "拾");
		}
		/** 将结果反转回来. */
		return sbResult.toString();
	}

	/**
	 * 转化小数部分 例：输入22.34返回叁肆
	 * 
	 * @param tempString
	 * @return
	 */
	private static String getFraction(String tempString) {
		String strFraction = null;
		int intDotPos = tempString.indexOf(".");
		/** 没有点说明没有小数，直接返回 */
		if (intDotPos == -1)
			return "";
		strFraction = tempString.substring(intDotPos + 1);
		StringBuffer sbResult = new StringBuffer(strFraction.length());
		for (int i = 0; i < strFraction.length(); i++) {
			sbResult.append(STR_NUMBER[strFraction.charAt(i) - 48]);
		}
		return sbResult.toString();
	}

	/**
	 * 判断传入的字符串中是否有.如果有则返回点
	 * 
	 * @param tempString
	 * @return
	 */
	private static String getDot(String tempString) {
		return tempString.indexOf(".") != -1 ? "点" : "";
	}

	/**
	 * 判断传入的字符串中是否有-如果有则返回负
	 * 
	 * @param tempString
	 * @return
	 */
	private static String getSign(String tempString) {
		return tempString.indexOf("-") != -1 ? "负" : "";
	}

	/**
	 * 将一个数字转化为金额
	 * 
	 * @param tempNumber
	 *            传入一个double的变量
	 * @return 返一个转换好的字符串
	 */
	public static String numberToChinese(double tempNumber) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.#########");
		String pTemp = String.valueOf(df.format(tempNumber));
		StringBuffer sbResult = new StringBuffer(
				getSign(pTemp) + getInteger(pTemp) + getDot(pTemp) + getFraction(pTemp));
		return sbResult.toString();
	}

	public static String numberToChinese(BigDecimal tempNumber) {
		return numberToChinese(tempNumber.doubleValue());
	}

	/**
	 * 替代字符
	 * 
	 * @param pValue
	 * @param pSource
	 * @param pDest
	 */
	private static void replace(StringBuffer pValue, String pSource, String pDest) {
		if (pValue == null || pSource == null || pDest == null)
			return;
		/** 记录pSource在pValue中的位置 */
		int intPos = 0;
		do {
			intPos = pValue.toString().indexOf(pSource);
			/** 没有找到pSource */
			if (intPos == -1)
				break;
			pValue.delete(intPos, intPos + pSource.length());
			pValue.insert(intPos, pDest);
		} while (true);
	}

	/**
	 * 日期 获取当前时间
	 * 
	 * @return
	 */
	public Date getCurrentTime() {
		Date now = new Date();
		return now;

	}

	public static Date getCurrentDate() {
		return new java.sql.Date(new java.util.Date().getTime());
	}

	/**
	 * 转换null为""
	 * 
	 * @param value要转换的值
	 * @return
	 */
	public static Object IsNull(Object value) {
		if (null != value && !value.equals("null")) {
			return value;
		}
		return "";
	}

	/**
	 * 今天年-月-日
	 * 
	 * @return
	 */
	public static String YearMonthDay() {
		Date now = new Date();
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
		return myFmt.format(now);
	}

	public static String monthDay() {
		Date now = new Date();
		SimpleDateFormat myFmt = new SimpleDateFormat("MM-dd");
		return myFmt.format(now);
	}

	/**
	 * 取昨天的值 格式：年-月-日
	 * 
	 * @return
	 */
	public static String YesterdayYearMonthDay() {
		Date Yesterday = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
		return myFmt.format(Yesterday);
	}

	/**
	 * 取明天的值,格式，年-月-日
	 * 
	 * @return
	 */
	public static String TomorrowYearMonthDay() {
		Date Yesterday = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24);
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
		return myFmt.format(Yesterday);
	}

	public static Date StringTrunDate(String value) throws ParseException {
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
		return myFmt.parse(value);

	}

	public static Date StringTrunDateTime(String value) throws ParseException {
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return myFmt.parse(value);
	}

	public static String DateTrunStringTime(Date value) throws ParseException {
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return myFmt.format(value);
	}

	/**
	 * 时间转字符串
	 * 
	 * @param value
	 * @return
	 * @throws ParseException
	 */
	public static String DateTurnString(Date value) throws ParseException {
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
		return myFmt.format(value);
	}

	public static String DateTurnMoth(Date value) throws ParseException {
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM");
		return myFmt.format(value);
	}

	public static String DateTurnDay(Date value) throws ParseException {
		SimpleDateFormat myFmt = new SimpleDateFormat("dd");
		return myFmt.format(value);
	}

	public static Map<String, String> commonReportDate(Date date, String value) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String yearStart = "";
		String yearEnd = "";
		String monthStart = "";
		String monthEnd = "";
		String tomorrowMonth = "";
		String tomorrowYear = "";
		SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfYM = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat sdfY = new SimpleDateFormat("yyyy");
		Calendar c = Calendar.getInstance();
		String years = sdfY.format(date) + "-12-" + value;
		String months = sdfYM.format(date) + "-" + value;

		if (date.getTime() < sdfYMD.parse(years).getTime()) {
			yearStart = (Integer.valueOf(sdfY.format(date)) - 1) + "-12-" + value;
			if (date.getTime() < sdfYMD.parse(months).getTime()) {
				c.setTime(date);
				c.add(Calendar.MONTH, -1);
				monthStart = sdfYM.format(c.getTime()) + "-" + value;
			} else {
				monthStart = sdfYM.format(date) + "-" + value;
			}
			c.setTime(sdfYMD.parse(monthStart));
			c.add(Calendar.MONTH, 1);
			monthEnd = sdfYMD.format(c.getTime());
		} else {
			yearStart = sdfY.format(date) + "-12-" + value;
			if (date.getTime() < sdfYMD.parse(months).getTime()) {
				c.setTime(date);
				c.add(Calendar.MONTH, -1);
				monthStart = sdfYM.format(c.getTime()) + "-" + value;
			} else {
				monthStart = sdfYM.format(date) + "-" + value;
			}
			c.setTime(sdfYMD.parse(monthStart));
			c.add(Calendar.MONTH, 1);
			monthEnd = sdfYMD.format(c.getTime());
		}
		c.setTime(sdfYMD.parse(yearStart));
		c.add(Calendar.YEAR, 1);
		yearEnd = sdfYMD.format(c.getTime());

		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, 1);// 计算选中日期下一天的日期
		tomorrowMonth = sdfYMD.format(c.getTime());

		tomorrowYear = monthEnd;// 计算年累的结束日期

		map.put("startYear", yearStart);
		map.put("endYear", yearEnd);
		map.put("startMonth", monthStart);
		map.put("endMonth", monthEnd);// 月报的月累结束日期
		map.put("tomorrowMonth", tomorrowMonth);// 日报的年累与月累结束日期
		map.put("tomorrowYear", tomorrowYear);// 月报的年累结束日期
		return map;
	}

	public static String zeroToNull(String value) {
		if (value.equals("0") || value.equals("0.0")) {
			return "";
		}
		return value;
	}

	public static List<Map<String, String>> circleSpaceDate(String startDate, String endDate, String value)
			throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String yearStart = "";// 年开始时间
		String yearEnd = "";// 年结束时间
		String monthStart = "";// 月开始时间
		String monthEnd = "";// 月结束时间
		SimpleDateFormat sdfYM = new SimpleDateFormat("yyyy-MM");
		// SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfY = new SimpleDateFormat("yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(sdfYM.parse(startDate));
		while (!(sdfYM.parse(endDate)).before(c.getTime())) {
			Map<String, String> map = new HashMap<String, String>();
			yearStart = Integer.valueOf(sdfY.format(c.getTime())) - 1 + "-12-" + value;
			yearEnd = sdfYM.format(c.getTime()) + "-" + value;
			monthEnd = yearEnd;
			c.add(Calendar.MONTH, -1);
			monthStart = sdfYM.format(c.getTime()) + "-" + value;
			map.put("yearStart", yearStart);
			map.put("yearEnd", yearEnd);
			map.put("monthStart", monthStart);
			map.put("monthEnd", monthEnd);
			list.add(map);
			c.add(Calendar.MONTH, 2);

		}
		return list;
	}

	/**
	 * 时间转毫秒数
	 * 
	 * @throws ParseException
	 */
	public static long hhmmTurnTime(Date value) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		String str = sdf.format(value);
		SimpleDateFormat rd = new SimpleDateFormat("yyyyMMddHHmm");
		long returnlong = rd.parse(str).getTime();// 毫秒
		return returnlong;
	}

	/**
	 * 合成时间
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static long syntheticDate(String dm, long hm) throws ParseException {
		long relong = 0;
		SimpleDateFormat rd = new SimpleDateFormat("yyyy-MM-dd");
		long dml = rd.parse(dm).getTime();// 毫秒
		relong = dml + hm;
		return relong;
	}

	/**
	 * 时间差
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static long timepoor(long start, long end) {
		long minutes = 0;
		minutes = (end - start) / (1000 * 60);
		return minutes;
	}

	public static long datePoor(Date d) {
		long dates = 0;
		long start = new Date().getTime();
		long end = d.getTime();
		dates = (end - start) / (1000 * 60 * 60 * 24);
		return dates;
	}

	@SuppressWarnings("static-access")
	public static String dateToStringDatepus(int ps) {
		String r = "";
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, ps);
		SimpleDateFormat rd = new SimpleDateFormat("yyyy-MM-dd");
		r = String.valueOf(rd.format(c.getTime()));
		return r;
	}

	@SuppressWarnings("static-access")
	public static String dateToStringpus(int ps) {
		String r = "";
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, ps);
		SimpleDateFormat rd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		r = String.valueOf(rd.format(c.getTime()));
		return r;
	}

	public static float obejctToFloat(Object o) {
		if (null == o) {
			return 0;
		} else {
			return Float.valueOf(String.valueOf(o));
		}
	}

	/**
	 * 百分比
	 * 
	 * @param value
	 * @return
	 */
	public static String percentage(float value) {
		if (value < 0.5 && value > 0) {
			return "<0.1%";
		}
		BigDecimal rv = new BigDecimal(value).setScale(1, BigDecimal.ROUND_HALF_UP);
		float rvi = Float.valueOf(String.valueOf(rv));
		if (rvi > 0) {
			return rv + "%";
		}
		return "0%";
	}

	public static String percentage(Object value) {
		if (null == value) {
			return "0%";
		}
		float fv = Float.valueOf(String.valueOf(value));
		return percentage(fv);
	}

	public static String percentagegh(float value, float value2) {
		if (value2 == 0) {
			return "（0%）";
		}
		float va = value / value2 * 100;

		if (va == 0.0 || va == 0 || va == -0.0 || va == -0) {
			return "（0%）";
		}
		if (va < 0.1) {
			return "（<0.1%）";
		}
		return "（" + String.format("%.2f", va) + "%）";
	}

	public static String percentagegh(Object value, Object value2) {
		if (null == value || null == value2) {
			return "（0%）";
		}
		float fv = Float.valueOf(String.valueOf(value));
		float fv2 = Float.valueOf(String.valueOf(value2));
		return percentagegh(fv, fv2);
	}

	public static String percentagecs(float value, float value2) {
		if (value2 == 0) {
			return "0%";
		}
		float va = value / value2 * 100;
		if (va == 0.0 || va == 0 || va == -0.0 || va == -0) {
			return "0%";
		}
		if (va < 0.1) {
			return "<0.1%";
		}
		return String.format("%.2f", va) + "%";
	}

	public static String percentagecs(Object value, Object value2) {
		if (null == value || null == value2) {
			return "0%";
		}
		float fv = Float.valueOf(String.valueOf(value));
		float fv2 = Float.valueOf(String.valueOf(value2));
		return percentagecs(fv, fv2);
	}

	/**
	 * 保留两位小数
	 */
	public static String towFormat(float value) {
		if (value == 0.0 || value == 0 || value == -0.0 || value == -0) {
			return "0";
		}
		return String.format("%.2f", value);
	}

	public static String towFormat(Object value) {
		if (null == value) {
			return "0";
		}
		float fv = Float.valueOf(String.valueOf(value));
		return towFormat(fv);
	}

	public static String towFormatgh(float value, float value2) {
		if (value2 == 0) {
			return "（0）";
		}
		float va = value / value2;
		if (va == 0.0 || va == 0 || va == -0.0 || va == -0) {
			return "（0）";
		}
		return "（" + String.format("%.2f", va) + "）";
	}

	public static String towFormatgh(Object value, Object value2) {
		if (null == value || null == value2) {
			return "（0）";
		}
		float fv = Float.valueOf(String.valueOf(value));
		float fv2 = Float.valueOf(String.valueOf(value2));
		return towFormatgh(fv, fv2);
	}

	public static String towFormatcs(float value, float value2) {
		if (value2 == 0) {
			return "0";
		}
		float va = value / value2;
		if (va == 0.0 || va == 0 || va == -0.0 || va == -0) {
			return "0";
		}
		return String.format("%.2f", va);
	}

	public static String towFormatcs(Object value, Object value2) {
		if (null == value || null == value2) {
			return "0";
		}
		float fv = Float.valueOf(String.valueOf(value));
		float fv2 = Float.valueOf(String.valueOf(value2));
		return towFormatcs(fv, fv2);
	}

	/***
	 * MD5加码 生成32位md5码
	 */
	public static String string2MD5(String inStr) {
		char hexDigits[] = { '0', '1', '&', '2', '3', '4', 'A', 'B', 'C', 'D', 'E', 'F', '5', '6', '7', '8', '9', '#',
				'*' };
		try {
			byte[] btInput = inStr.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int hqDate(String da) throws ParseException {
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM");
		Date date = myFmt.parse(da);
		GregorianCalendar gCalendar = new GregorianCalendar();
		gCalendar.setTime(date);
		int dayOfMonth = gCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return dayOfMonth;
	}

	/**
	 * 合并两个数组，并排序
	 * 
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static float[] findSZ(float[] arr1, float[] arr2) {
		// Set是不允许重复的，所以将数组的值全部放在Set对象中
		Set set = new HashSet<Float>();

		for (int i = 0; i < arr1.length; i++) {
			set.add(arr1[i]);
		}

		for (int i = 0; i < arr2.length; i++) {
			set.add(arr2[i]);
		}

		Iterator i = set.iterator();
		float[] arrays = new float[set.size()];
		int num = 0;
		while (i.hasNext()) {
			float a = Float.valueOf(String.valueOf(i.next()));
			arrays[num] = a;
			num = num + 1;
		}

		// 对结果进行排序
		Arrays.sort(arrays);
		return arrays;
	}

	public static String startTimeReport(String curTime, int type) {
		if (type == 1) {
			curTime = curTime + "-01";
		} else {
			curTime = curTime + "-01-01";
		}
		return curTime;
	}

	public static String endTimeReport(String curTime, int type) throws ParseException {
		String endTime = "";
		if (type == 0) {
			endTime = datepus(curTime, 1);
		} else if (type == 1) {
			endTime = monthpus(curTime, 1);
		} else {
			endTime = yearpus(curTime, 1);
		}
		return endTime;
	}

	@SuppressWarnings("static-access")
	public static String datepus(String start, int ps) throws ParseException {
		SimpleDateFormat rd = new SimpleDateFormat("yyyy-MM-dd");
		String r = "";
		Calendar c = Calendar.getInstance();
		c.setTime(rd.parse(start));
		c.add(Calendar.DATE, ps);
		r = String.valueOf(rd.format(c.getTime()));
		return r;
	}

	@SuppressWarnings("static-access")
	public static String monthpus(String start, int ps) throws ParseException {
		SimpleDateFormat rd = new SimpleDateFormat("yyyy-MM-dd");
		String r = "";
		Calendar c = Calendar.getInstance();
		c.setTime(rd.parse(start));
		c.add(Calendar.MONTH, ps);
		r = String.valueOf(rd.format(c.getTime()));
		return r;
	}

	@SuppressWarnings("static-access")
	public static String yearpus(String start, int ps) throws ParseException {
		SimpleDateFormat rd = new SimpleDateFormat("yyyy-MM-dd");
		String r = "";
		Calendar c = Calendar.getInstance();
		c.setTime(rd.parse(start));
		c.add(Calendar.YEAR, ps);
		r = String.valueOf(rd.format(c.getTime()));
		return r;
	}

	public static boolean isValidDate(String sDate) {
		if ((sDate != null)) {
			return false;
		}
		String r = "";
		try {
			SimpleDateFormat rd = new SimpleDateFormat("yyyy-MM");

			Calendar c = Calendar.getInstance();
			c.setTime(rd.parse(sDate));
			r = String.valueOf(rd.format(c.getTime()));
		} catch (Exception e) {
			return false;
		}
		if (r.equals(sDate)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 封装SQL或HQL语句
	 * 
	 * @param sh
	 *            原始sh语句
	 * @param vargs
	 *            值
	 * @return
	 */
	public static String buildString(String sh, Object... vargs) {
		String rt = "";
		String[] rts = sh.split("\\?");// 函数个数
		int vl = vargs.length;// 参数的长度
		for (int i = 0; i < vl; i++) {
			rt += rts[i] + vargs[i];
		}
		if (rts.length > vl) {
			rt += rts[vl];
		}
		return rt;
	}

	/**
	 * 对象转换成map
	 * 
	 * @param thisObj
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map getValue(Object thisObj) {
		Map map = new HashMap();
		Class c;
		try {
			c = Class.forName(thisObj.getClass().getName());
			Method[] m = c.getMethods();
			for (int i = 0; i < m.length; i++) {
				String method = m[i].getName();
				if (method.startsWith("get")) {
					try {
						Object value = m[i].invoke(thisObj);
						if (value != null) {
							String key = method.substring(3);
							key = key.substring(0, 1).toLowerCase() + key.substring(1);
							map.put(key, value);
						}
					} catch (Exception e) {
						System.out.println("error:" + method);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 取明天的值,格式，年-月-日
	 * 
	 * @return
	 */
	public static String TomorrowYearDay() {
		Date Yesterday = new Date(System.currentTimeMillis() + 1000 * 60 * 60
				* 24);
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyyMMddHHmmss");
		return myFmt.format(Yesterday);
	}

	public static String curDay() throws ParseException {
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyyMMddHHmmss");
		return myFmt.format(new Date());
	}
}
