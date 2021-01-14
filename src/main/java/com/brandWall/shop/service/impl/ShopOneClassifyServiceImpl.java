package com.brandWall.shop.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;

import com.brandWall.shop.jpa.*;
import com.brandWall.shop.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.brandWall.BaseServiceImpl;
import com.brandWall.shop.service.ShopOneClassifyService;
import com.brandWall.util.Msg;
import com.brandWall.util.QueryHelper;
import com.brandWall.util.ValidateUtil;
import com.brandWall.util.exception.MyException;

@Service
public class ShopOneClassifyServiceImpl extends BaseServiceImpl<ShopOneClassify> implements ShopOneClassifyService{
	
	@Resource
	private ShopOneClassifyJpa oneClassifyJpa;
	
	@Resource
	private ShopTwoClassifyJpa twoClassifyJpa;

	@Resource
	private ShopThreeClassifyJpa threeClassifyJpa;

	@Resource
	private ShopFourClassifyJpa fourClassifyJpa;
	
	@Resource
	private FwSsShopJpa shopJpa; 
	
	@Override
	public Msg add(ShopOneClassify t) throws MyException {
		if(!ValidateUtil.isValid(t.getClassifyName())){
			return Msg.MsgError("分类名字不能为空");
		}
		
		if(!ValidateUtil.isValid(t.getIocPic())){
			return Msg.MsgError("分类图标不能为空");
		}
		
		t.setCreateTime(new Date());
		oneClassifyJpa.save(t);
		return Msg.MsgSuccess();
	}
	
	@Override
	public Msg modify(ShopOneClassify t) throws MyException {
		if(!ValidateUtil.isValid(t.getId())){
			return Msg.MsgError("id不能为空");
		}
		
		if(!ValidateUtil.isValid(t.getClassifyName())){
			return Msg.MsgError("分类名字不能为空");
		}
		
		if(!ValidateUtil.isValid(t.getIocPic())){
			return Msg.MsgError("分类图标不能为空");
		}
		ShopOneClassify oneClassify = oneClassifyJpa.findOne(t.getId());
		if(oneClassify == null){
			return Msg.MsgError("数据不存在");
		}
		oneClassify.setClassifyName(t.getClassifyName());
		oneClassify.setIocPic(t.getIocPic());
		oneClassify.setModifyTime(new Date());
		oneClassifyJpa.save(oneClassify);
		return Msg.MsgSuccess();
	}
	
	@Override
	public Msg remove(ShopOneClassify t) throws MyException {
		if(!ValidateUtil.isValid(t.getId())){
			return Msg.MsgError("id不能为空");
		}
		//底下是否有商铺
		List<FwSsShop> shopList = shopJpa.findByClassifyId(t.getId());
		if(shopList.size()>0){
			return Msg.MsgError("该分类里有店铺,请先移除");
		}
		//底下是否有二级分类
		List<ShopTwoClassify> twoClassify = twoClassifyJpa.findByOneClassifyId(t.getId());
		if(twoClassify.size()>0){
			return Msg.MsgError("该分类里有二级分类。您需要先删除二级分类才能删除本分类");
		}
		
		ShopOneClassify oneClassify = oneClassifyJpa.findOne(t.getId());
		if(oneClassify == null){
			return Msg.MsgError("数据不存在");
		}
		oneClassify.setRemove(1);
		oneClassifyJpa.save(oneClassify);
		
		return Msg.MsgSuccess();
	}

	@Override
	public Msg findById(String id) throws MyException {
		if(!ValidateUtil.isValid(id)){
			return Msg.MsgError("id不能为空");
		}
		
		return Msg.MsgSuccess(oneClassifyJpa.findOne(id));
	}

	@Override
	public Msg findPage(Map<String, Object> map, Pageable pageable) throws MyException {
		QueryHelper queryHelper = new QueryHelper("SELECT key", ShopOneClassify.class,"key")
				.addCondition("key.remove=0").addOrderProperty("key.createTime", false);
		Page<?> page = findAll(queryHelper, pageable, map);
		return Msg.MsgSuccess(page);
	}

	@Override
	public Msg findAllClassify(Pageable pageable, Map<String, Object> map) throws MyException {
		QueryHelper queryHelper = new QueryHelper("SELECT key", ShopOneClassify.class,"key")
				.addCondition("key.remove=0").addOrderProperty("key.createTime", true);
		Page<ShopOneClassify> page = (Page<ShopOneClassify>) findAll(queryHelper, pageable, map);
		List<ShopOneClassify> list = page.getContent();

		//一级分类下的二级分类
		for (ShopOneClassify shopOneClassify : list) {
			List<ShopTwoClassify> twoClassify = twoClassifyJpa.findByOneClassifyId(shopOneClassify.getId());
			shopOneClassify.setTwoClassifyList(twoClassify);
			//二级分类下的三级分类
			for (ShopTwoClassify shopTwoClassify : twoClassify ) {
				List<ShopThreeClassify> threeClassifyList = threeClassifyJpa.findByTwoClassifyId(shopTwoClassify.getId());
				shopTwoClassify.setThreeClassifyList(threeClassifyList);
				//三级分类下的四级分类
				for (ShopThreeClassify threeClassify : threeClassifyList) {
					List<ShopFourClassify> fourClassifyList = fourClassifyJpa.findByThreeClassifyId(threeClassify.getId());
					threeClassify.setFourClassifyList(fourClassifyList);
				}
			}
		}
		return Msg.MsgSuccess(page);
	}
}
