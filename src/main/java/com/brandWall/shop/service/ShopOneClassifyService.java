package com.brandWall.shop.service;

import com.brandWall.BaseService;
import com.brandWall.shop.model.ShopOneClassify;
import com.brandWall.util.Msg;
import com.brandWall.util.exception.MyException;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * @author Administrator
 */
public interface ShopOneClassifyService extends BaseService<ShopOneClassify>{

	/**
	 * 根据id查询详情
	 * @param id
	 * @return
	 * @throws MyException
	 */
	Msg findById(String id) throws MyException;


	/**
	 * 查询一级分类底下所有分类
	 * @param pageable
	 * @param map
	 * @return
	 * @throws MyException
	 */
	Msg findAllClassify(Pageable pageable, Map<String, Object> map)throws MyException;

}
