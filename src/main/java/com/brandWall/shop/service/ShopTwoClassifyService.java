package com.brandWall.shop.service;

import com.brandWall.BaseService;
import com.brandWall.shop.model.ShopTwoClassify;
import com.brandWall.util.Msg;
import com.brandWall.util.exception.MyException;

public interface ShopTwoClassifyService extends BaseService<ShopTwoClassify>{

	Msg findById(String id) throws MyException;
}
