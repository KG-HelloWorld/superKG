package com.brandWall.shop.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.brandWall.BaseServiceImpl;
import com.brandWall.shop.jpa.FwSsShopJpa;
import com.brandWall.shop.jpa.ShopThreeClassifyJpa;
import com.brandWall.shop.jpa.ShopTwoClassifyJpa;
import com.brandWall.shop.model.FwSsShop;
import com.brandWall.shop.model.ShopThreeClassify;
import com.brandWall.shop.model.ShopTwoClassify;
import com.brandWall.shop.service.ShopTwoClassifyService;
import com.brandWall.util.Msg;
import com.brandWall.util.QueryHelper;
import com.brandWall.util.ValidateUtil;
import com.brandWall.util.exception.MyException;

@Service
public class ShopTwoClassifyServiceImpl extends BaseServiceImpl<ShopTwoClassify> implements ShopTwoClassifyService{
	
	@Resource
	private ShopThreeClassifyJpa threeClassifyJpa;
	
	@Resource
	private ShopTwoClassifyJpa twoClassifyJpa;
	
	@Resource
	private FwSsShopJpa shopJpa; 
	
	@Override
	public Msg add(ShopTwoClassify t) throws MyException {
		if(!ValidateUtil.isValid(t.getClassifyName())){
			return Msg.MsgError("分类名字不能为空");
		}
		
		if(!ValidateUtil.isValid(t.getOneClassifyId())){
			return Msg.MsgError("一级分类id不能为空");
		}
		
		t.setCreateTime(new Date());
		twoClassifyJpa.save(t);
		return Msg.MsgSuccess();
	}
	
	@Override
	public Msg modify(ShopTwoClassify t) throws MyException {
		if(!ValidateUtil.isValid(t.getId())){
			return Msg.MsgError("id不能为空");
		}
		
		if(!ValidateUtil.isValid(t.getClassifyName())){
			return Msg.MsgError("分类名字不能为空");
		}
		
		ShopTwoClassify twoClassify = twoClassifyJpa.findOne(t.getId());
		if(twoClassify == null){
			return Msg.MsgError("数据不存在");
		}
		twoClassify.setClassifyName(t.getClassifyName());
		twoClassify.setModifyTime(new Date());
		twoClassifyJpa.save(twoClassify);
		return Msg.MsgSuccess();
	}
	
	@Override
	public Msg remove(ShopTwoClassify t) throws MyException {
		if(!ValidateUtil.isValid(t.getId())){
			return Msg.MsgError("id不能为空");
		}
		//底下是否有商铺
		List<FwSsShop> shopList = shopJpa.findByClassifyId(t.getId());
		if(shopList.size()>0){
			return Msg.MsgError("该分类里有店铺,请先移除");
		}
		//底下是否有三级分类
		List<ShopThreeClassify> threeClassifyList = threeClassifyJpa.findByTwoClassifyId(t.getId());
		if(threeClassifyList.size()>0){
			return Msg.MsgError("该分类里有三级分类。您需要先删除三级分类才能删除本分类");
		}
		
		ShopTwoClassify twoClassify = twoClassifyJpa.findOne(t.getId());
		if(twoClassify == null){
			return Msg.MsgError("数据不存在");
		}
		twoClassify.setRemove(1);
		twoClassifyJpa.save(twoClassify);
		
		return Msg.MsgSuccess();
	}

	@Override
	public Msg findById(String id) throws MyException {
		if(!ValidateUtil.isValid(id)){
			return Msg.MsgError("id不能为空");
		}
		
		return Msg.MsgSuccess(twoClassifyJpa.findOne(id));
	}
	
	@Override
	public Msg findPage(Map<String, Object> map, Pageable pageable) throws MyException {
		QueryHelper queryHelper = new QueryHelper("SELECT key", ShopTwoClassify.class,"key")
				.addCondition("key.remove=0").addOrderProperty("key.createTime", false);
		Page<?> page = findAll(queryHelper, pageable, map);
		return Msg.MsgSuccess(page);
	}
}
