package com.brandWall.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PropertyPageImpl<T>  extends PageImpl<T> implements PropertyPage<T> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7592576093665967718L;
	Map<String, Object> map =new HashMap<>();
	

	public PropertyPageImpl(List<T> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}


	public Map<String, Object> getMap() {
		return map;
	}




	public void setMap(Map map) {
		this.map = map;
	}

}
