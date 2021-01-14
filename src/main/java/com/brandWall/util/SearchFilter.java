package com.brandWall.util;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;
public class SearchFilter {
	public enum Operator {
		EQ, LIKE, GT, LT, GTE, LTE
	}

	public String fieldName;
	public Object value;
	public Operator operator;
	public String relat;//关系

	public SearchFilter(String fieldName, Operator operator, Object value,String relat) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
		this.relat=relat;
	}

	public static Map<String, SearchFilter> parse(Map<String, Object> filterParams) {
		Map<String, SearchFilter> filters = Maps.newHashMap();

		for (Entry<String, Object> entry : filterParams.entrySet()) {
			String[] names = StringUtils.split(entry.getKey(), "_");
			if (names.length != 3) {
				throw new IllegalArgumentException(entry.getKey() + " 查询条件不正确！");
			}
			
			SearchFilter filter = new SearchFilter(names[1], Operator.valueOf(names[0]), entry.getValue(),names[2]);
			filters.put(filter.fieldName, filter);
		}

		return filters;
	}
	
	public static String parses(Map<String, Object> filterParams) {
		String hql="";
		int i=1;
		int size=filterParams.size();
		for (Entry<String, Object> entry : filterParams.entrySet()) {
			String[] names = StringUtils.split(entry.getKey(), "_");
			if (names.length != 3) {
				throw new IllegalArgumentException(entry.getKey() + " 查询条件不正确！");
			}
			
			hql+="";
			if(size==i){
				hql+=" "+names[1]+relat(names[0],entry.getValue());
			}else{
				hql+=" "+names[1]+relat(names[0],entry.getValue())+" "+names[2];
			}
			i++;
		}

		if(StringUtils.isNotBlank(hql)){
			hql=" and("+hql+")";
		}
		return hql;
	}
	
	private static String relat(String name,Object value){
		String rt="";
		switch (name) {
		case "EQ":
			rt="="+judgeType(value);
			break;
		case "LIKE":
			rt="%"+judgeType(value);
			break;
		case "GT":
			rt=">"+judgeType(value);
			break;
		case "LT":
			rt="<"+judgeType(value);
			break;
		case "GTE":
			rt=">="+judgeType(value);
			break;
		case "LTE":
			rt="<="+judgeType(value);
			break;
		}
		return rt;
	}
	
	public static Object judgeType(Object obj){
		return "'"+obj+"'";
	}
}
