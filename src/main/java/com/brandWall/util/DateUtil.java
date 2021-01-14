package com.brandWall.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

public class DateUtil {
	/** 年月日时分秒(无下划线) yyyyMMddHHmmss */
	public static final String dtLong = "yyyyMMddHHmmss";

	/** 完整时间 yyyy-MM-dd HH:mm:ss */
	public static final String simple = "yyyy-MM-dd HH:mm:ss";

	/** 年月日(无下划线) yyyyMMdd */
	public static final String dtShort = "yyyy-MM-dd";

	/** 忽略时分秒的Date类型 */
	public static final String special = "yyyy-MM-dd 00:00:00";

	/**
	 * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号
	 * 
	 * @return 以yyyyMMddHHmmss为格式的当前系统时间
	 */
	public static String getOrderNum() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(dtLong);
		return df.format(date);
	}

	public static Date GetAfterDate(int minute) {
		Calendar currentDate = new GregorianCalendar();
		currentDate.add(Calendar.MINUTE, minute);
		return (Date) currentDate.getTime().clone();
	}

	/**
	 * 获取num分钟之后的时间
	 * 
	 * @author wsy
	 * @time 2016年6月21日上午11:10:25
	 * @param num
	 * @return
	 */
	public static Date getNumMinutesLater(int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, num);
		return calendar.getTime();
	}

	/**
	 * 获取num天之后的时间
	 * 
	 * @param num
	 * @return
	 */
	public static Date getNumDayLater(int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, num);
		return calendar.getTime();
	}

	/**
	 * 得到几天前的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * 获取相隔时间的时间列表
	 * 
	 * @param startDate
	 * @param sEndDate
	 * @return
	 */
	public static List<String> getDateListBetweenToDate(String startDate, String sEndDate) {
		List<String> lstDate = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (ValidateUtil.isValid(startDate) && ValidateUtil.isValid(sEndDate)) {
			Date beginDate = new Date();
			Date endDate = new Date();
			try {
				// 开始时间转Date类型
				beginDate = sdf.parse(startDate);
				lstDate.add(sdf.format(beginDate));
				// 结束时间转Date类型
				endDate = sdf.parse(sEndDate);
				Calendar cal = Calendar.getInstance();
				// 使用给定的 Date 设置此 Calendar 的时间
				cal.setTime(beginDate);
				boolean bContinue = true;
				while (bContinue) {
					// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
					cal.add(Calendar.DAY_OF_MONTH, 1);
					// 测试此日期是否在指定日期之后
					if (endDate.after(cal.getTime())) {
						lstDate.add(sdf.format(cal.getTime()));
					} else {
						break;
					}
				}
				lstDate.add(sdf.format(endDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return lstDate;
	}

	
	/**
	 * 给时间加上几个小时
	 * 
	 * @param day
	 *            当前时间 格式：yyyy-MM-dd HH:mm:ss
	 * @param hour
	 *            需要加的时间
	 * @return
	 */
	public static String addDateMinut(String day, int hour) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(day);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (date == null)
			return "";
		System.out.println("front:" + format.format(date)); // 显示输入的日期
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, hour);// 24小时制
		date = cal.getTime();
		System.out.println("after:" + format.format(date)); // 显示更新后的日期
		cal = null;
		return format.format(date);

	}

	/**
	 * 根据day获取日期开始时间，1为明天，-1为昨天，以此类推(0为今天)
	 * 
	 * @param day
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static Date getTodayStartTime(int day) {
		Calendar currentDate = new GregorianCalendar();
		currentDate.add(Calendar.DATE, day);// 把日期往后增加一天.整数往后推,负数往前移动
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		return (Date) currentDate.getTime().clone();
	}

	/**
	 * 根据date获取日期开始时间，1为明天，-1为昨天，以此类推(0为今天)
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getDateStartTime(Date date, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.DATE, day);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		return now.getTime();
	}

	/**
	 * 根据date获取日期结束时间，1为明天，-1为昨天，以此类推(0为今天)
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getDateEndStartTime(Date date, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.DATE, day);
		now.set(Calendar.HOUR_OF_DAY, 23);
		now.set(Calendar.MINUTE, 59);
		now.set(Calendar.SECOND, 59);
		return now.getTime();
	}

	/**
	 * 根据day获取日期结束时间，1为明天，-1为昨天，以此类推(0为今天)
	 * 
	 * @param day
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static Date getTodayEndTime(int day) {
		Calendar currentDate = new GregorianCalendar();
		currentDate.add(Calendar.DATE, day);// 把日期往后增加一天.整数往后推,负数往前移动
		currentDate.set(Calendar.HOUR_OF_DAY, 23);
		currentDate.set(Calendar.MINUTE, 59);
		currentDate.set(Calendar.SECOND, 59);
		return (Date) currentDate.getTime().clone();
	}

	/**
	 * 获取系统某个日期(精确到毫秒)，格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getOtherDateFormatter(Date date) {
		DateFormat df = new SimpleDateFormat(simple);
		return df.format(date);
	}
	
	/**
	 * 获取系统某个日期(精确到毫秒)，格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getOtherDateFormatterToString(Date date) {
		DateFormat df = new SimpleDateFormat(dtLong);
		return df.format(date);
	}

	/**
	 * 获取系统当前日期(精确到毫秒)，格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getDateFormatter() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(simple);
		return df.format(date);
	}

	/**
	 * 获取系统当期年月日(精确到天)，格式：yyyyMMdd
	 * 
	 * @return
	 */
	public static String getDate() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(dtShort);
		return df.format(date);
	}

	/**
	 * 获取系统当期年月日(精确到天)，格式：yyyyMMdd hh:mm:ss
	 * 
	 * @return
	 */
	public static String getNowDateString() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(simple);
		return df.format(date);
	}

	/**
	 * 获取系统当期年月日(精确到天)，格式：yyyyMMdd
	 * 
	 * @return
	 */
	public static String getAfterDate(Date date) {

		DateFormat df = new SimpleDateFormat(dtShort);
		return df.format(date);
	}

	/**
	 * 获取系统当期年月日(精确到天)，格式：yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getDateString(Date date) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}

	/**
	 * 产生随机的三位数
	 * 
	 * @return
	 */
	public static String getThree() {
		Random rad = new Random();
		return rad.nextInt(100000000) + "";
	}

	/**
	 * 得到几天前的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static String getDateBeforeToString(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return getAfterDate(now.getTime());
	}

	/**
	 * 得到几天后的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static String getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return getAfterDate(now.getTime());
	}

	/**
	 * 得到传入时间所在周的周几
	 * 
	 * @param d
	 * @param weekDay
	 *            1周日 7周六
	 * @return
	 */
	public static Date getDayOfWeek(Date d, int weekDay) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DAY_OF_WEEK, weekDay);
		return now.getTime();
	}

	/**
	 * 得到几月前的时间
	 * 
	 * @param d
	 * @param month
	 * @return
	 */
	public static Date getDateBeforeMonth(Date d, int month) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.MONTH, now.get(Calendar.MONTH) - month);
		return now.getTime();
	}

	/**
	 * 得到几月后的时间
	 * 
	 * @param d
	 * @param month
	 * @return
	 */
	public static Date getDateAfterMonth(Date d, int month) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.MONTH, now.get(Calendar.MONTH) + month);
		return now.getTime();
	}

	/**
	 * 得到虚岁
	 * 
	 * @param birthday
	 *            yyyy-MM-dd
	 * @return
	 */
	public static int getBirthdayByAge(String birthdayStr) {
		boolean valid = ValidateUtil.isValid(birthdayStr);
		if (!valid) {
			return -1;
		}
		Calendar now = Calendar.getInstance();
		int birthday = Integer.parseInt(birthdayStr.substring(0, birthdayStr.indexOf("-")));
		return (now.getWeekYear() - birthday) + 1;
	}

	/**
	 * 将Date格式日期转换为String格式日期
	 * 
	 * @param date
	 *            类型
	 * @return
	 */
	public static String getDateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String d = sdf.format(date); // 转换成字符串类型
		return d;
	}
	
	/**
	 * 将Date格式日期转换为String格式日期  不带时分秒
	 * 
	 * @param date
	 *            类型
	 * @return
	 */
	public static String getDateToStringNoHMS(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String d = sdf.format(date); // 转换成字符串类型
		return d;
	}

	/**
	 * 将String格式日期转换为Date格式日期
	 * 
	 * @param date
	 *            类型
	 * @return
	 */
	public static Date getStringToDate(String d) {
		if (d == null || d == "") {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			date = sdf.parse(d);// 转回Date类型
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 将String格式日期转换为Date格式日期
	 * 
	 * @param date
	 *            类型
	 * @return
	 */
	public static Date getStringToDateNoHMS(String d) {
		if (d == null || d == "") {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			date = sdf.parse(d);// 转回Date类型
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 将String格式日期转换为Date格式日期
	 * 
	 * @param date
	 *            类型
	 * @return
	 */
	public static Date getStringToDateYMD(String d) {
		if (d == null || d == "") {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			date = sdf.parse(d);// 转回Date类型
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date getStringToDate(String d, String regex) {
		if (d == null || d == "") {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(regex);
		Date date = new Date();
		try {
			date = sdf.parse(d);// 转回Date类型
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 转换格式(2016/06/06) --》 (2016:06:06 00:00:00)
	 * 
	 * @param date
	 * @return
	 */
	public static String convertFormatDate(String date, boolean flag) {
		boolean valid = ValidateUtil.isValid(date);
		if (valid) {
			String date4 = null;
			try {
				String date2 = null;
				if (flag) {
					date2 = date + " 00:00:00";
				} else {
					date2 = date + " 23:59:59";
				}
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

				Date date3 = sdf.parse(date2);
				date4 = getDateToString(date3);

			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date4;
		} else {
			return null;
		}

	}

	/**
	 * 判断某个日期是不是今天
	 */
	public static boolean isToday(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (null != date && sdf.format(date).equals(sdf.format(new Date()))) {
			return true;
		}
		return false;
	}

	public static int getAge(Date birthDay) throws Exception {
		// 获取当前系统时间
		Calendar cal = Calendar.getInstance();
		// 如果出生日期大于当前时间，则抛出异常
		if (cal.before(birthDay)) {
			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}
		// 取出系统当前时间的年、月、日部分
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		// 将日期设置为出生日期
		cal.setTime(birthDay);
		// 取出出生日期的年、月、日部分
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		// 当前年份与出生年份相减，初步计算年龄
		int age = yearNow - yearBirth;
		// 当前月份与出生日期的月份相比，如果月份小于出生月份，则年龄上减1，表示不满多少周岁
		if (monthNow <= monthBirth) {
			// 如果月份相等，在比较日期，如果当前日，小于出生日，也减1，表示不满多少周岁
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth)
					age--;
			} else {
				age--;
			}
		}
		System.out.println("age:" + age);
		return age;
	}

	public static String getDateAsLast(long date) {
		return getDateAsLast(new Date(date));
	}

	public static String getDateAsLast(Date date) {
		if (date == null) {
			return null;
		}
		long cut = System.currentTimeMillis() - date.getTime();
		String str = "分钟前";
		if (cut < 60 * 60 * 1000) {
			str = cut / (60 * 1000) + "分钟前";
		} else if (cut < 60 * 60 * 1000 * 24) {
			str = cut / (60 * 60 * 1000) + "小时前";
		} else {
			str = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
		}
		return str;
	}

	public static String getDateAsLast(String date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(simple);
		try {
			return getDateAsLast(format.parse(date));
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
     * @title: dateCompare
     * @description: 比较日期大小
     * @param date1 日期1
     * @param date2 日期2
     * @return
     */
    public static int dateCompare(Date date1, Date date2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateFirst = dateFormat.format(date1);
        String dateLast = dateFormat.format(date2);
        int dateFirstIntVal = Integer.parseInt(dateFirst);
        int dateLastIntVal = Integer.parseInt(dateLast);
        if (dateFirstIntVal == dateLastIntVal) {
            return 1;
        } else if (dateFirstIntVal == dateLastIntVal-1) {
            return -1;
        }
        return 0;
    }


	/**
	 * 获取明天的当前时间
	 */
	public static Date getNextDay(Date date, Integer timeNum) {
		Calendar c = Calendar.getInstance();
		c.setTime(date); // 设置当前日期
		c.add(Calendar.HOUR, timeNum); // 日期分钟加1,Calendar.DATE(天),Calendar.HOUR(小时)
		Date time = c.getTime(); // 结果
		return time;
	}

	/**
	 * 获取当前月份
	 * 
	 * @param args
	 */
	public static String getMethod() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		return String.valueOf(month);
	}


	//0當月  1過去一個月
	public static String getBeforeMethod(int type) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		if(type==1){
			c.add(Calendar.MONTH, -1);
		}else{
			c.add(Calendar.MONTH, 0);
		}
		Date m = c.getTime();
		String mon = format.format(m);
		System.out.println(mon);
		return mon;
	}

	// 0本年   1過去一年  
	public static String getBeforeYear(int type) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		if(type==1){
			c.add(Calendar.YEAR, -1);
		}else{
			c.add(Calendar.YEAR, 0);
		}
		Date y = c.getTime();
		String year = format.format(y);
		System.out.println("过去一年：" + year);
		return year;
	}
	
	/**
     * date2比date1多的天数
     * @param date1    
     * @param date2
     * @return    
     */
    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
       int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年            
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            
            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }

	
	public static Map<String, String> buildData(int type, String tm) throws Exception {
		String startTm = null;
		String endTm = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		Calendar cal = Calendar.getInstance();
		Date time = sdf.parse(tm);
		cal.setTime(time);


		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}

		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		startTm = sdf.format(cal.getTime());
		cal.add(Calendar.DATE, 6);
		endTm = sdf.format(cal.getTime());

		

		Map<String, String> map = new HashMap<>();
		map.put("startTm", startTm);
		map.put("endTm", endTm);
		return map;
	}

	
	public static Date geLastWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getThisWeekMonday(date));
		cal.add(Calendar.DATE, -7);
		return cal.getTime();
	}
 
	public static Date getThisWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 获得当前日期是一个星期的第几天
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		return cal.getTime();
	}
 
	
	/**
	 * 
	 * @param date
	 * @param cType
	 *            ====>Calendar 的属性参数 例如：Calendar.MINUTE
	 * @param lenght
	 *            ====>整形,表示想要获取多长的cType 类型之后的时间
	 * @return
	 */
	public static String getTimeByAfter(){
		
		Date date = new Date();
		  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		  sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		  String time = sdf.format(date);
		  try {
			  date=sdf.parse(time);
			long time1=date.getTime()+60*60*1000;
			date=new Date(time1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  DateFormat df = new SimpleDateFormat(dtLong);
		return df.format(date);
	}
	
	
	/**
     * 两个时间之间相差距离多少天
     * @param one 时间参数 1：
     * @param two 时间参数 2：
     * @return 相差天数
     */
    public static long getDistanceDays(String str1, String str2) throws Exception{
    	  DateFormat df=null;

    	df= new SimpleDateFormat("yyyy-MM-dd");
 
       
        Date one;
        Date two;
        long days=0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff ;
            if(time1<time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }
	
    /**
    *
    * 计算两个日期相差的月份数
    *
    * @param date1 日期1
    * @param date2 日期2
    * @param pattern 日期1和日期2的日期格式
    * @return 相差的月份数
    * @throws ParseException
    */
    public static int countMonths(String date1,String date2,String pattern) throws ParseException{
    SimpleDateFormat sdf=new SimpleDateFormat(pattern);
    Calendar c1=Calendar.getInstance();
    Calendar c2=Calendar.getInstance();
    c1.setTime(sdf.parse(date1));
    c2.setTime(sdf.parse(date2));
    int year =c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR);
    //开始日期若小月结束日期
    if(year<0){
    year=-year;
    return year*12+c1.get(Calendar.MONTH)-c2.get(Calendar.MONTH);
    }
    return year*12+c2.get(Calendar.MONTH)-c1.get(Calendar.MONTH);
    }


	/**
	 * 根据年 月 获取对应的月份 天数
	 * */
	public static int getDaysByYearMonth(int year,int moneh) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, moneh - 1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	
    /**
     * 获取当月最后一天
     * @param args
     */
    public static String getMonethOverDay(){
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 	  	   	    
	    //获取当前月最后一天
	    Calendar ca = Calendar.getInstance();    
	    ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
	    String last = format.format(ca.getTime());
	    return last;
	}
    
 
    
    public static String getCurrYearLast(){
        Calendar currCal=Calendar.getInstance();
        int year = currCal.get(Calendar.YEAR);
        currCal.clear();
        currCal.set(Calendar.YEAR,year);
        currCal.roll(Calendar.DAY_OF_YEAR,-1);
        
        Date date = currCal.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String d = sdf.format(date); // 转换成字符串类型
    	
        return d;
    }
	
    
    public static String getNewDateHHMM(Date date){
    	SimpleDateFormat format = new SimpleDateFormat("HH:mm");
    	return format.format(date);
    	
    }
    
    public static String getNewDateHHMMss(Date date){
    	SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    	return format.format(date);
    	
    }
    
    public static List<Date> twoDataAllDay(Date bigtime,Date endtime){
    	 //定义一个接受时间的集合
        List<Date> lDate = new ArrayList<Date>();
        lDate.add(bigtime);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(bigtime);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(endtime);
        // 测试此日期是否在指定日期之后
        while (endtime.after(calBegin.getTime()))  {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
    	return lDate;
    }
    
 
    
    
    /**
     * 判断某一时间是否在一个区间内
     * 
     * @param sourceTime
     *            时间区间,半闭合,如[10:00-20:00)
     * @param curTime
     *            需要判断的时间 如10:00
     * @return 
     * @throws IllegalArgumentException
     */
    public static boolean isInTime(String sourceTime, String curTime) {
        if (sourceTime == null || !sourceTime.contains("-") || !sourceTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }
        if (curTime == null || !curTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + curTime);
        }
        String[] args = sourceTime.split("-");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            long now = sdf.parse(curTime).getTime();
            long start = sdf.parse(args[0]).getTime();
            long end = sdf.parse(args[1]).getTime();
            if (args[1].equals("00:00")) {
                args[1] = "24:00";
            }
            if (end < start) {
                if (now >= end && now < start) {
                    return false;
                } else {
                    return true;
                }
            } 
            else {
                if (now >= start && now < end) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }
     
    }

    
    
    /**
     * 指定日期加上天数后的日期
     * @param num 为增加的天数
     * @param newDate 创建时间
     * @return
     * @throws ParseException 
     */
    public static String plusDay(int num,String newDate) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date  currdate = format.parse(newDate);
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
        currdate = ca.getTime();
        String enddate = format.format(currdate);
        return enddate;
    }

    public static String addDate(Date date,long day) throws ParseException {
    	 long time = date.getTime(); // 得到指定日期的毫秒数
    	 day = day*24*60*60*1000; // 要加上的天数转换成毫秒数
    	 time+=day; // 相加得到新的毫秒数
    	 Date date2 = new Date(time);
    	
    	 return  getDateToStringNoHMS(date2); // 将毫秒数转换成日期
    }
    
    /**
     * 	获取当前时间的前五分钟
     */
    public static String getFrontTime(int time) {
		Calendar beforeTime = Calendar.getInstance();
		beforeTime.add(Calendar.MINUTE, -time);// 5分钟之前的时间
		Date beforeD = beforeTime.getTime();
		String before= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(beforeD);  // 前五分钟时间
		
		
		return before;
    }
    
	public static void main(String[] args) throws Exception {
		String time =getDateToString(getNumMinutesLater(-5));
		System.out.println(time);
		
		
        
	}

	
}
