package com.brandWall.util;


import java.util.Map;

import org.springframework.data.domain.Page;

public interface PropertyPage<T> extends Page<T> {
	
	/**
	 * 获取额外参数
	 * @return
	 */
	Map<String, Object> getMap();
	
	
	/**
	 * 获取额外参数
	 * @return
	 */
	void setMap(Map<String, Object> map);
	
}
