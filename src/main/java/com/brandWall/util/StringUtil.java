package com.brandWall.util;

import java.io.File;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringUtil {
	/**
	 * 获取UUID
	 * 
	 * @return
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replace("-", "");
		return uuid;
	}

	/**
	 * MD5加密
	 * 
	 * @param text
	 *            要加密的字符串
	 * @return
	 */
	public static String md5(String text) {
		try {
			StringBuffer buffer = new StringBuffer();
			char[] chars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
			byte[] bytes = text.getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] targ = md.digest(bytes);
			for (byte b : targ) {
				buffer.append(chars[(b >> 4) & 0x0F]);
				buffer.append(chars[b & 0x0F]);
			}
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取随机用户名
	 * 
	 * @author wsy
	 * @time 2016年6月15日下午4:29:49
	 * @return 随机用户名
	 */
	public static String getUsername() {
		String[] zn = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
				"t", "u", "u", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D",
				"E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
				"Z" };
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 9; i++) {
			buffer.append(zn[random.nextInt(zn.length)]);
		}

		return buffer.toString();
	}

	/**
	 * 获取当前时间的年月日当做上传文件的路径
	 * 
	 * @return
	 */
	public static String getFolderByDate() {
		Integer[] folder = new Integer[3];
		Calendar calendar = Calendar.getInstance();
		folder[0] = calendar.get(Calendar.YEAR);
		folder[1] = calendar.get(Calendar.MONTH);
		folder[2] = calendar.get(Calendar.DAY_OF_MONTH);
		return folder[0] + File.separator + folder[1] + File.separator + folder[2];
	}

	/**
	 * 使用当前时间戳加三位随机数生成订单号
	 * 
	 * @return
	 */
	public static String getOrderNum() {
		long time = System.currentTimeMillis();
		int i = (int) (Math.random() * 900) + 100;
		return String.valueOf(time) + String.valueOf(i);
	}

	/**
	 * 获取随机数
	 * 
	 * @param num
	 *            位数
	 * @return
	 */
	public static String getRandomNumber(int num) {
		int[] str = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < num; i++) {
			sb.append(str[new Random().nextInt(str.length)]);
		}
		return sb.toString();
	}

	// 过滤微信昵称表情
	public static String StringFilter(String source) throws PatternSyntaxException {
		if (source == null) {
			return source;
		}
		Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
				Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
		Matcher emojiMatcher = emoji.matcher(source);
		if (emojiMatcher.find()) {
			source = emojiMatcher.replaceAll("");
			return source;
		}
		return source;
	}

	// 自动生成名字（中文）
	public static String getRandomJianHan(int len) {
		String ret = "";
		for (int i = 0; i < len; i++) {
			String str = null;
			int hightPos, lowPos; // 定义高低位
			Random random = new Random();
			hightPos = (176 + Math.abs(random.nextInt(39))); // 获取高位值
			lowPos = (161 + Math.abs(random.nextInt(93))); // 获取低位值
			byte[] b = new byte[2];
			b[0] = (new Integer(hightPos).byteValue());
			b[1] = (new Integer(lowPos).byteValue());
			try {
				str = new String(b, "GBK"); // 转成中文
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			ret += str;
		}
		return ret;
	}

	/**
	 * java生成随机数字和字母组合10位数
	 * 
	 * @param length[生成随机数的长度]
	 * @return
	 */
	public static String getRandomNickname(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			// 输出字母还是数字
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 字符串
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 取得大写字母还是小写字母
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}

	public static String generateShortUuid() {
		String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
				"q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
				"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z" };
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();

	}

	
	public static String genUuid() {
		Random random = new Random();
		String result = "";
		for (int i = 0; i < 8; i++) {
			// 首字母不能为0
			result += (random.nextInt(9) + 1);
		}
		return result;
	}

	public static void main(String[] args) {
		
		BigDecimal b=new BigDecimal(49.4);
		int i = b.divide(new BigDecimal(50),2,BigDecimal.ROUND_UP).intValue();
		System.out.println(i);
		
	}


}