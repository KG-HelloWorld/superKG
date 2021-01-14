package com.brandWall.shop.service;

import com.brandWall.BaseService;
import com.brandWall.shop.model.ShopLabelClassify;
import com.brandWall.util.Msg;
import com.brandWall.util.exception.MyException;

public interface ShopLabelClassifyService extends BaseService<ShopLabelClassify>{

	Msg findById(String id) throws MyException;
}
