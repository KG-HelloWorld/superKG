package com.brandWall.util;

import javax.servlet.http.HttpServletRequest;

public class HttpUtil {

	public static String getHost(HttpServletRequest request) {
		String dlUrl = request.getHeader("X-Forwarded-Host");
		if (ValidateUtil.isValid(dlUrl)) {
			System.out.println("dlUrl:" + dlUrl);
			return dlUrl;
		}
		String host = request.getHeader("host");
		System.out.println("host:" + host);
		String[] str = host.split(":");
		return str[0];
	}

	public static String getHostPrefix(HttpServletRequest request) {
		String url = getHost(request);
		System.out.println("url:" + url);
		if (ValidateUtil.isValid(url)) {
			return url.substring(0, url.indexOf("."));
		}
		return "";
	}

	public static String getServerIp(HttpServletRequest request) {
		String ip = request.getLocalAddr();
		return ip;
	}

}
