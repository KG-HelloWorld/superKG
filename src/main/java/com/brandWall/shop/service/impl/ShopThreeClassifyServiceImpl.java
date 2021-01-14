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
import com.brandWall.shop.jpa.ShopFourClassifyJpa;
import com.brandWall.shop.jpa.ShopThreeClassifyJpa;
import com.brandWall.shop.model.FwSsShop;
import com.brandWall.shop.model.ShopFourClassify;
import com.brandWall.shop.model.ShopThreeClassify;
import com.brandWall.shop.service.ShopThreeClassifyService;
import com.brandWall.util.Msg;
import com.brandWall.util.QueryHelper;
import com.brandWall.util.ValidateUtil;
import com.brandWall.util.exception.MyException;

@Service
public class ShopThreeClassifyServiceImpl extends BaseServiceImpl<ShopThreeClassify> implements ShopThreeClassifyService{

	
	@Resource
	private ShopThreeClassifyJpa threeClassifyJpa;
	
	@Resource
	private ShopFourClassifyJpa fourClassifyJpa;
	
	@Resource
	private FwSsShopJpa shopJpa; 
	
	@Override
	public Msg add(ShopThreeClassify t) throws MyException {
		if(!ValidateUtil.isValid(t.getClassifyName())){
			return Msg.MsgError("分类名字不能为空");
		}
		
		if(!ValidateUtil.isValid(t.getTwoClassifyId())){
			return Msg.MsgError("二级分类id不能为空");
		}
		
		t.setCreateTime(new Date());
		threeClassifyJpa.save(t);
		return Msg.MsgSuccess();
	}
	
	@Override
	public Msg modify(ShopThreeClassify t) throws MyException {
		if(!ValidateUtil.isValid(t.getId())){
			return Msg.MsgError("id不能为空");
		}
		
		if(!ValidateUtil.isValid(t.getClassifyName())){
			return Msg.MsgError("分类名字不能为空");
		}
		
//		if(!ValidateUtil.isValid(t.getTwoClassifyId())){
//			return Msg.MsgError("二级分类id不能为空");
//		}
		ShopThreeClassify threeClassify = threeClassifyJpa.findOne(t.getId());
		if(threeClassify == null){
			return Msg.MsgError("数据不存在");
		}
		threeClassify.setClassifyName(t.getClassifyName());
		threeClassify.setModifyTime(new Date());
		threeClassifyJpa.save(threeClassify);
		return Msg.MsgSuccess();
	}
	
	@Override
	public Msg remove(ShopThreeClassify t) throws MyException {
		if(!ValidateUtil.isValid(t.getId())){
			return Msg.MsgError("id不能为空");
		}
		//底下是否有商铺
		List<FwSsShop> shopList = shopJpa.findByClassifyId(t.getId());
		if(shopList.size()>0){
			return Msg.MsgError("该分类里有店铺,请先移除");
		}
		//底下是否有四级分类
		List<ShopFourClassify> fourClassifyList = fourClassifyJpa.findByThreeClassifyId(t.getId());
		if(fourClassifyList.size()>0){
			return Msg.MsgError("该分类里有四级分类。您需要先删除四级分类才能删除本分类");
		}
		
		ShopThreeClassify threeClassify = threeClassifyJpa.findOne(t.getId());
		if(threeClassify == null){
			return Msg.MsgError("数据不存在");
		}
		threeClassify.setRemove(1);
		threeClassifyJpa.save(threeClassify);
		
		return Msg.MsgSuccess();
	}

	@Override
	public Msg findById(String id) throws MyException {
		if(!ValidateUtil.isValid(id)){
			return Msg.MsgError("id不能为空");
		}
		
		return Msg.MsgSuccess(threeClassifyJpa.findOne(id));
	}
	
	@Override
	public Msg findPage(Map<String, Object> map, Pageable pageable) throws MyException {
		QueryHelper queryHelper = new QueryHelper("SELECT key", ShopThreeClassify.class,"key")
				.addCondition("key.remove=0").addOrderProperty("key.createTime", false);
		Page<?> page = findAll(queryHelper, pageable, map);
		return Msg.MsgSuccess(page);
	}

}
