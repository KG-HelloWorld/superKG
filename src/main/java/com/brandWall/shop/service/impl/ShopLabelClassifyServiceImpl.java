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
import com.brandWall.shop.jpa.ShopLabelClassifyInfoJpa;
import com.brandWall.shop.jpa.ShopLabelClassifyJpa;
import com.brandWall.shop.model.ShopLabelClassify;
import com.brandWall.shop.model.ShopLabelClassifyInfo;
import com.brandWall.shop.service.ShopLabelClassifyService;
import com.brandWall.util.Msg;
import com.brandWall.util.QueryHelper;
import com.brandWall.util.ValidateUtil;
import com.brandWall.util.exception.MyException;


@Service
public class ShopLabelClassifyServiceImpl extends BaseServiceImpl<ShopLabelClassify> implements ShopLabelClassifyService{

	@Resource
	private ShopLabelClassifyJpa classifyJpa; 
	
	@Resource
	private ShopLabelClassifyInfoJpa classifyInfoJpa;
	
	@Override
	public Msg add(ShopLabelClassify t) throws MyException {
		if(!ValidateUtil.isValid(t.getName())){
			return Msg.MsgError("标签分类名不能为空");
		}
		t.setCreateTime(new Date());
		classifyJpa.save(t);
		return Msg.MsgSuccess();
	}
	
	@Override
	public Msg modify(ShopLabelClassify t) throws MyException {
		if(!ValidateUtil.isValid(t.getName())){
			return Msg.MsgError("标签分类名不能为空");
		}
		if(!ValidateUtil.isValid(t.getId())){
			return Msg.MsgError("主键不能为空");
		}
		t.setModifyTime(new Date());
		classifyJpa.save(t);
		return Msg.MsgSuccess();
	}
	
	@Override
	public Msg remove(ShopLabelClassify t) throws MyException {
		if(!ValidateUtil.isValid(t.getId())){
			return Msg.MsgError("主键不能为空");
		}
		ShopLabelClassify classify = classifyJpa.findOne(t.getId());
		if(classify == null){
			return Msg.MsgError("数据不存在");
		}
		List<ShopLabelClassifyInfo> classifyInfoList = classifyInfoJpa.findByLabelClassifyId(t.getId());
		if(classifyInfoList.size() > 0){
			return Msg.MsgError("该分类中有标签，您需要先删除里面的标签才能进行删除");
		}
		
		classify.setRemove(1);
		classifyJpa.save(classify);
		
		return Msg.MsgSuccess();
	}

	@Override
	public Msg findById(String id) throws MyException {
		if(!ValidateUtil.isValid(id)){
			return Msg.MsgError("主键不能为空");
		}
		ShopLabelClassify classify = classifyJpa.findOne(id);
		
		return  Msg.MsgSuccess(classify);
	}
	
	@Override
	public Msg findPage(Map<String, Object> map, Pageable pageable) throws MyException {
		QueryHelper queryHelper = new QueryHelper("SELECT key", ShopLabelClassify.class, "key")
				.addCondition("key.remove=0").addOrderProperty("key.createTime", false);
		Page<ShopLabelClassify> page = (Page<ShopLabelClassify>) findAll(queryHelper, pageable, map);
		List<ShopLabelClassify> list = page.getContent();
		for (ShopLabelClassify shopLabelClassify: list) {
			List<ShopLabelClassifyInfo> classifyInfoList = classifyInfoJpa.findByLabelClassifyId(shopLabelClassify.getId());
			shopLabelClassify.setClassifyInfoList(classifyInfoList);
		}
		return Msg.MsgSuccess(page);
	}
}
