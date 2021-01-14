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
import com.brandWall.shop.jpa.ShopBindingLabelJpa;
import com.brandWall.shop.jpa.ShopLabelClassifyInfoJpa;
import com.brandWall.shop.jpa.ShopLabelClassifyJpa;
import com.brandWall.shop.model.ShopBindingLabel;
import com.brandWall.shop.model.ShopLabelClassify;
import com.brandWall.shop.model.ShopLabelClassifyInfo;
import com.brandWall.shop.service.ShopLabelClassifyInfoService;
import com.brandWall.util.Msg;
import com.brandWall.util.QueryHelper;
import com.brandWall.util.ValidateUtil;
import com.brandWall.util.exception.MyException;

@Service
public class ShopLabelClassifyInfoServiceImpl extends BaseServiceImpl<ShopLabelClassifyInfo> implements ShopLabelClassifyInfoService{


	@Resource
	private ShopLabelClassifyJpa classifyJpa; 
	
	@Resource
	private ShopLabelClassifyInfoJpa classifyInfoJpa;
	
	@Resource
	private ShopBindingLabelJpa labelJpa;
	
	@Override
	public Msg add(ShopLabelClassifyInfo t) throws MyException {
		if(!ValidateUtil.isValid(t.getLabelName())){
			return Msg.MsgError("标签分类名不能为空");
		}
		
		if(!ValidateUtil.isValid(t.getLabelClassifyId())){
			return Msg.MsgError("标签分类id不能为空");
		}
		t.setCreateTime(new Date());
		classifyInfoJpa.save(t);
		return Msg.MsgSuccess();
	}
	
	@Override
	public Msg modify(ShopLabelClassifyInfo t) throws MyException {
		if(!ValidateUtil.isValid(t.getLabelName())){
			return Msg.MsgError("标签分类名不能为空");
		}
		if(!ValidateUtil.isValid(t.getId())){
			return Msg.MsgError("主键不能为空");
		}
		ShopLabelClassifyInfo info = classifyInfoJpa.findOne(t.getId());
		if(info == null){
			return Msg.MsgError("数据不存在");
		}
		info.setModifyTime(new Date());
		info.setLabelName(t.getLabelName());

		classifyInfoJpa.save(info);
		return Msg.MsgSuccess();
	}
	
	@Override
	public Msg remove(ShopLabelClassifyInfo t) throws MyException {
		if(!ValidateUtil.isValid(t.getId())){
			return Msg.MsgError("主键不能为空");
		}
		ShopLabelClassifyInfo classify = classifyInfoJpa.findOne(t.getId());
		if(classify == null){
			return Msg.MsgError("数据不存在");
		}
		List<ShopBindingLabel> labelList = labelJpa.findByLabelId(t.getId());
		if(labelList.size() > 0){
			return Msg.MsgError("该标签与商铺进行了绑定，请先移除再进行删除");
		}
		classify.setRemove(1);
		classifyInfoJpa.save(classify);
		
		return Msg.MsgSuccess();
	}

	@Override
	public Msg findById(String id) throws MyException {
		if(!ValidateUtil.isValid(id)){
			return Msg.MsgError("主键不能为空");
		}
		ShopLabelClassifyInfo classify = classifyInfoJpa.findOne(id);
		
		return  Msg.MsgSuccess(classify);
	}
	
	@Override
	public Msg findPage(Map<String, Object> map, Pageable pageable) throws MyException {
		QueryHelper queryHelper = new QueryHelper("SELECT key", ShopLabelClassifyInfo.class, "key")
				.addCondition("key.remove=0").addOrderProperty("key.createTime", false);
		Page<?> page = findAll(queryHelper, pageable, map);
		
		return Msg.MsgSuccess(page);
	}


}
