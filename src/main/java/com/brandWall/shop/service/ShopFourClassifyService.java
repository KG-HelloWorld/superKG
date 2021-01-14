package com.brandWall.shop.service;

import com.brandWall.BaseService;
import com.brandWall.shop.model.ShopFourClassify;
import com.brandWall.util.Msg;
import com.brandWall.util.exception.MyException;

public interface ShopFourClassifyService extends BaseService<ShopFourClassify>{

	Msg findById(String id) throws MyException;
}
