package com.brandWall.shop.service;

import com.brandWall.BaseService;
import com.brandWall.shop.model.ShopThreeClassify;
import com.brandWall.util.Msg;
import com.brandWall.util.exception.MyException;

public interface ShopThreeClassifyService extends BaseService<ShopThreeClassify>{

	Msg findById(String id) throws MyException;
}
