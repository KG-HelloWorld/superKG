package com.brandWall.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MessageUtil {

	public static final String GET_URL = "http://www.gxt106.com/sms.aspx";

	public final static String template = "您好，您的验证码是：code ，15分钟内有效";

	/**
	 * 发送短信
	 * 
	 * @author:Fengzhitao
	 * @description
	 * @Date:2016年12月14日上午10:46:23
	 * @param content
	 *            内容
	 * @param mobile
	 *            手机号码
	 * @return
	 * @throws IOException
	 */
	public static String Send(String content, String mobile) throws IOException {
		if (Config.isReal) {
			
			/*
			 * 威软发送短信
			 */
				String requestUrl = GET_URL + "?action=send&userid=" + URLEncoder.encode(Config.userid, "utf-8") + "&account="
				        + URLEncoder.encode(Config.account, "utf-8") + "&password=" + URLEncoder.encode(Config.accountpassword, "utf-8") + "&mobile="
				        + mobile + "&content=" + URLEncoder.encode(content, "utf-8") + "&sendTime=&extno=";

				System.out.println(content);
				URL getUrl = new URL(requestUrl);
				HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
				connection.connect();
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String lines;
				while ((lines = reader.readLine()) != null) {
					lines += lines;
				}
				reader.close();
				// 断开连接
				connection.disconnect();
				System.out.println(lines);
				return lines;
			}

			
			
		return "123456";
	

	}

	// 上行
	public static String Mo(String userid, String account, String password) throws IOException {

		String requestUrl = GET_URL + "/callApi.aspx?action=query&userid=" + URLEncoder.encode(userid, "utf-8")
				+ "&account=" + URLEncoder.encode(account, "utf-8") + "&password="
				+ URLEncoder.encode(password, "utf-8");

		URL getUrl = new URL(requestUrl);
		HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
		connection.connect();
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String lines;
		while ((lines = reader.readLine()) != null) {

			lines += lines;

		}
		reader.close();
		connection.disconnect();
		return lines;
	}

}
