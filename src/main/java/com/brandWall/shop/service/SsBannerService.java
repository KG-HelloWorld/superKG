package com.brandWall.shop.service;

import java.util.Map;

import com.brandWall.BaseService;
import com.brandWall.shop.model.SsBanner;
import com.brandWall.util.Msg;
import com.brandWall.util.exception.MyException;
import org.springframework.data.domain.Pageable;


public interface SsBannerService extends BaseService<SsBanner> {
	
	
	

	public Msg addBanner(SsBanner banner) throws MyException;

	public Msg delBanner(SsBanner banner) throws MyException;

	public Msg findAll(String shopId, Pageable pageable, Integer enable,Integer way,Integer status,Map<String,Object> map) throws MyException;

	public Msg optionState(String id);
}
