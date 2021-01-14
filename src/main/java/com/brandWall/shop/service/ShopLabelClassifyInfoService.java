package com.brandWall.shop.service;

import com.brandWall.BaseService;
import com.brandWall.shop.model.ShopLabelClassifyInfo;
import com.brandWall.util.Msg;
import com.brandWall.util.exception.MyException;

public interface ShopLabelClassifyInfoService extends BaseService<ShopLabelClassifyInfo>{

	Msg findById(String id) throws MyException;
}
