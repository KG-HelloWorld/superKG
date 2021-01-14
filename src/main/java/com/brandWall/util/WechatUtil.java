package com.brandWall.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("deprecation")
public class WechatUtil {

	static String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
	static String spbill_create_ip = "http://localhost:9092";

	public static String sendRequest(Map<String, Object> map) throws Exception {
		map.put("check_name", "NO_CHECK");
		// 拼接发送的xml
		String result = "<xml>";
		result += "<mch_appid>" + map.get("mch_appid") + "</mch_appid>";
		result += "<mchid>" + map.get("mchid") + "</mchid>";
		result += "<nonce_str>" + map.get("nonce_str") + "</nonce_str>";
		result += "<partner_trade_no>" + map.get("partner_trade_no") + "</partner_trade_no>";
		result += "<openid>" + map.get("openid") + "</openid>";
		result += "<check_name>" + map.get("check_name") + "</check_name>";
		result += "<amount>" + map.get("amount") + "</amount>";
		result += "<desc>" + map.get("desc") + "</desc>";
		result += "<spbill_create_ip>" + map.get("spbill_create_ip") + "</spbill_create_ip>";
		result += "<sign>" + createSign(map) + "</sign>";
		result += "</xml>";
		String responseBody = sendUrl(result, url, map.get("cerPath").toString(), map.get("mchid").toString());
		return responseBody;
	}

	/**
	 * 发送企业付款请求，包含PKCS12信息
	 * 
	 * @param data
	 *            封装好的xml
	 * @return
	 */
	public static String sendUrl(String data, String url, String cerPath, String mchid) throws Exception {
		String result = "";
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		FileInputStream instream = new FileInputStream(new File(cerPath));
		try {
			keyStore.load(instream, mchid.trim().toCharArray());
		} finally {
			instream.close();
		}
		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchid.trim().toCharArray()).build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		try {

			HttpPost httpost = new HttpPost(url);

			httpost.addHeader("Connection", "keep-alive");

			httpost.addHeader("Accept", "*/*");

			httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

			httpost.addHeader("Host", "api.mch.weixin.qq.com");

			httpost.addHeader("X-Requested-With", "XMLHttpRequest");

			httpost.addHeader("Cache-Control", "max-age=0");

			httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");

			httpost.setEntity(new StringEntity(data, "UTF-8"));

			System.out.println("executing request" + httpost.getRequestLine());

			CloseableHttpResponse response = httpclient.execute(httpost);

			try {
				HttpEntity entity = response.getEntity();

				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				if (entity != null) {
					System.out.println("Response content length: " + entity.getContentLength());
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
					String text;
					while ((text = bufferedReader.readLine()) != null) {
						System.out.println(text);
						result += text;
					}

					return result;

				}
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
		return result;
	}

	public static String createSign(Map<String, Object> map) {
		String sign = "";
		String keyValue = "";
		keyValue = "amount=" + map.get("amount") + "&";
		keyValue += "check_name=" + map.get("check_name") + "&";
		keyValue += "desc=" + map.get("desc") + "&";
		keyValue += "mch_appid=" + map.get("mch_appid") + "&";
		keyValue += "mchid=" + map.get("mchid") + "&";
		keyValue += "nonce_str=" + map.get("nonce_str") + "&";
		keyValue += "openid=" + map.get("openid") + "&";
		keyValue += "partner_trade_no=" + map.get("partner_trade_no") + "&";
		keyValue += "spbill_create_ip=" + map.get("spbill_create_ip") + "&";

		String signTemp = keyValue + "key=" + map.get("key");

		sign = getMd5(signTemp.trim()).toUpperCase();

		return sign;
	}

	// 静态方法，便于作为工具类
	public static String getMd5(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// 32位加密
			return buf.toString();
			// 16位的加密
			// return buf.toString().substring(8, 24);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取一定长度的随机字符串
	 * 
	 * @param length
	 *            指定字符串长度
	 * @return 一定长度的字符串
	 */
	public static String getRandomStringByLength(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 金钱转换成分
	 * 
	 * @param money
	 * @return
	 */
	public static int moneyToCent(BigDecimal money) {
		int int_money = 0;
		if (money != null) {
			int_money = money.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))
					.intValue();
		}
		return int_money;
	}

}
