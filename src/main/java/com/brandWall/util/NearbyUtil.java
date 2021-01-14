package com.brandWall.util;

public class NearbyUtil {

	/**
	 * 生成以中心点为中心的四方形经纬度
	 * 
	 * @param lat
	 *            纬度
	 * @param lon
	 *            精度
	 * @param raidus
	 *            半径（以米为单位）
	 * @return minLat, minLng, maxLat, maxLng
	 */
	public static double[] getAround(double lat, double lon, int radius) {

		Double latitude = lat;
		Double longitude = lon;

		Double degree = (24901 * 1609) / 360.0;
		double raidusMile = radius;

		Double dpmLat = 1 / degree;
		Double radiusLat = dpmLat * raidusMile;
		Double minLat = latitude - radiusLat;
		Double maxLat = latitude + radiusLat;

		Double mpdLng = degree * Math.cos(latitude * (Math.PI / 180));
		Double dpmLng = 1 / mpdLng;
		Double radiusLng = dpmLng * raidusMile;
		Double minLng = longitude - radiusLng;
		Double maxLng = longitude + radiusLng;
		return new double[] { minLat, minLng, maxLat, maxLng };
	}

	public static String[] getAroundString(double lat, double lon, int radius) {

		Double latitude = lat;
		Double longitude = lon;

		Double degree = (24901 * 1609) / 360.0;
		double raidusMile = radius*1000;

		Double dpmLat = 1 / degree;
		Double radiusLat = dpmLat * raidusMile;
		Double minLat = latitude - radiusLat;
		Double maxLat = latitude + radiusLat;

		Double mpdLng = degree * Math.cos(latitude * (Math.PI / 180));
		Double dpmLng = 1 / mpdLng;
		Double radiusLng = dpmLng * raidusMile;
		Double minLng = longitude - radiusLng;
		Double maxLng = longitude + radiusLng;
		return new String[] { minLat.toString(), minLng.toString(), maxLat.toString(), maxLng.toString() };
	}

	/**
	 * 计算两经纬度点之间的距离（单位：米）
	 * 
	 * @param lng1
	 *            经度
	 * @param lat1
	 *            纬度
	 * @param lng2
	 * @param lat2
	 * @return
	 */
	public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
		double radLat1 = Math.toRadians(lat1);
		double radLat2 = Math.toRadians(lat2);
		double a = radLat1 - radLat2;
		double b = Math.toRadians(lng1) - Math.toRadians(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m)
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	public static void main(String[] args) {
		double d = getDistance(113.2794,23.25818, 113.38424, 22.93772);
		System.out.println(d+"---");

		
	}

	/*
	 * select * from mobile_user where mu_latitude <> 0 and mu_longitud >
	 * #left_lat# and mu_longitud < #right_lat# and mu_latitude > #down_lon# and
	 * mu_latitude < #top_lon# and mu_u_id <> #uid# order by ACOS(SIN((#lat# *
	 * 3.1415) / 180 ) * SIN((mu_latitude * 3.1415) / 180 ) +COS((#lat# *
	 * 3.1415) / 180 ) * COS((mu_latitude * 3.1415) / 180 )COS((#lon# * 3.1415)
	 * / 180 - (mu_longitud * 3.1415) / 180 ) ) 6380 asc limit #start#,#end#
	 */

}
