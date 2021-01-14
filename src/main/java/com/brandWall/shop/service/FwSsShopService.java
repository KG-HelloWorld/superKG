package com.brandWall.shop.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.brandWall.BaseService;
import com.brandWall.shop.model.FwSsShop;
import com.brandWall.util.Msg;
import com.brandWall.util.exception.MyException;

public interface FwSsShopService extends BaseService<FwSsShop>{

	//查详情
	Msg findById(FwSsShop t) throws MyException;
	
	//店铺列表
	Msg findAllShop(Pageable pageable,Map<String, Object> map) throws MyException;

	//审核店铺
	Msg auditShop(FwSsShop t) throws MyException;

	/**设置推荐店铺*/
	Msg addRecommend(FwSsShop t) throws MyException;

}
