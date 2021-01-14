package com.brandWall.shop.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brandWall.BaseServiceImpl;
import com.brandWall.shop.jpa.FwSsShopJpa;
import com.brandWall.shop.jpa.ShopBindingLabelJpa;
import com.brandWall.shop.jpa.ShopFourClassifyJpa;
import com.brandWall.shop.jpa.ShopLabelClassifyInfoJpa;
import com.brandWall.shop.jpa.ShopLabelClassifyJpa;
import com.brandWall.shop.jpa.ShopOneClassifyJpa;
import com.brandWall.shop.jpa.ShopThreeClassifyJpa;
import com.brandWall.shop.jpa.ShopTwoClassifyJpa;
import com.brandWall.shop.model.FwSsShop;
import com.brandWall.shop.model.ShopBindingLabel;
import com.brandWall.shop.model.ShopFourClassify;
import com.brandWall.shop.model.ShopLabelClassify;
import com.brandWall.shop.model.ShopLabelClassifyInfo;
import com.brandWall.shop.model.ShopOneClassify;
import com.brandWall.shop.model.ShopThreeClassify;
import com.brandWall.shop.model.ShopTwoClassify;
import com.brandWall.shop.service.FwSsShopService;
import com.brandWall.util.Msg;
import com.brandWall.util.QueryHelper;
import com.brandWall.util.ValidateUtil;
import com.brandWall.util.exception.MyException;

@Service
public class FwSsShopServiceImpl extends BaseServiceImpl<FwSsShop> implements FwSsShopService{

	@Resource
	private FwSsShopJpa shopJpa;
	
	@Resource
	private ShopBindingLabelJpa bindingLabelJpa;
	
	@Resource
	private ShopOneClassifyJpa oneClassifyJpa;
	
	@Resource
	private ShopTwoClassifyJpa twoClassifyJpa;
	
	@Resource
	private ShopThreeClassifyJpa threeClassifyJpa;
	
	@Resource
	private ShopFourClassifyJpa fourClassifyJpa;
	
	@Resource
	private ShopLabelClassifyJpa labelClassifyJpa;

	@Resource
	private ShopLabelClassifyInfoJpa labelClassifyInfoJpa;
	
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Msg add(FwSsShop shop) throws MyException {
		if (!ValidateUtil.isValid(shop.getSsPic())) {
			return Msg.MsgError("logo不能为空");
		}
		if(!ValidateUtil.isValid(shop.getSsName())){
			return Msg.MsgError("品牌名字不能为空");
		}
		if(!ValidateUtil.isValid(shop.getCompanyName())){
			return Msg.MsgError("公司名字不能为空");
		}
		if(!ValidateUtil.isValid(shop.getSsPhone())){
			return Msg.MsgError("联系电话不能为空");
		}
		if(!ValidateUtil.isValid(shop.getSsContacts())){
			return Msg.MsgError("联系人不能为空");
		}
		if(!ValidateUtil.isValid(shop.getSsContacts())){
			return Msg.MsgError("联系人不能为空");
		}
		if(!ValidateUtil.isValid(shop.getShopContext())){
			return Msg.MsgError("品牌详情不能为空");
		}
//		if(!ValidateUtil.isValid(shop.getShopVedio())){
//			return Msg.MsgError("品牌视频不能为空");
//		}
		if(!ValidateUtil.isValid(shop.getClassifyId())){
			return Msg.MsgError("请为该品牌选择一个分类");
		}
		List<ShopBindingLabel> bindingLabelList = shop.getBindingLabelList();
		if(bindingLabelList.size() <= 0 || bindingLabelList == null){
			return Msg.MsgError("请为该品牌选择一个标签");
		}
		shop.setCreateTime(new Date());
		//存主表
		shopJpa.save(shop);
		
		for (ShopBindingLabel shopBindingLabel : bindingLabelList) {
			shopBindingLabel.setShopId(shop.getSsId());
			bindingLabelJpa.save(shopBindingLabel);
		}
		
		return Msg.MsgSuccess();
	}
	
	@Override
	public Msg modify(FwSsShop shop) throws MyException {
		if (!ValidateUtil.isValid(shop.getSsId())) {
			return Msg.MsgError("主键不能为空");
		}
		if (!ValidateUtil.isValid(shop.getSsPic())) {
			return Msg.MsgError("logo不能为空");
		}
		if(!ValidateUtil.isValid(shop.getSsName())){
			return Msg.MsgError("品牌名字不能为空");
		}
		if(!ValidateUtil.isValid(shop.getCompanyName())){
			return Msg.MsgError("公司名字不能为空");
		}
		if(!ValidateUtil.isValid(shop.getSsPhone())){
			return Msg.MsgError("联系电话不能为空");
		}
		if(!ValidateUtil.isValid(shop.getSsContacts())){
			return Msg.MsgError("联系人不能为空");
		}
		if(!ValidateUtil.isValid(shop.getSsContacts())){
			return Msg.MsgError("联系人不能为空");
		}
		if(!ValidateUtil.isValid(shop.getShopContext())){
			return Msg.MsgError("品牌详情不能为空");
		}
		if(!ValidateUtil.isValid(shop.getShopVedio())){
			return Msg.MsgError("品牌视频不能为空");
		}
		if(!ValidateUtil.isValid(shop.getClassifyId())){
			return Msg.MsgError("请为该品牌选择一个分类");
		}
		List<ShopBindingLabel> bindingLabelList = shop.getBindingLabelList();
		if(bindingLabelList.size() <= 0 || bindingLabelList == null){
			return Msg.MsgError("请为该品牌选择一个标签");
		}
		FwSsShop newShop = shopJpa.findOne(shop.getSsId());
		if(newShop == null){
			return Msg.MsgError("数据不存在");
		}
		newShop.setModifyTime(new Date());
		//存主表
		shopJpa.save(newShop);
		
		//删除旧的标签，保存新的标签
		List<ShopBindingLabel> bindingLabel = bindingLabelJpa.findByShopId(newShop.getSsId());
		for (ShopBindingLabel shopBindingLabel : bindingLabel) {
			bindingLabelJpa.delete(shopBindingLabel);
		}
		for (ShopBindingLabel shopBindingLabel : bindingLabelList) {
			shopBindingLabel.setShopId(newShop.getSsId());
			bindingLabelJpa.save(shopBindingLabel);
		}
		
		return Msg.MsgSuccess();
	}

	@Override
	public Msg remove(FwSsShop t) throws MyException {
		if (!ValidateUtil.isValid(t.getSsId())) {
			return Msg.MsgError("主键不能为空");
		}
		FwSsShop shop = shopJpa.findOne(t.getSsId());
		if(shop == null){
			return Msg.MsgError("数据不存在");
		}
		shop.setSsRemove(1);
		shopJpa.save(shop);
		
		return Msg.MsgSuccess();
	}
	
	@Override
	public Msg findById(FwSsShop t) throws MyException {
		if(!ValidateUtil.isValid(t.getSsId())){
			return Msg.MsgError("店铺不能为空");
		}
		FwSsShop shop = shopJpa.findOne(t.getSsId());
		if(shop == null){
			return Msg.MsgError("数据不存在");
		}
		//返分类
		ShopOneClassify oneClassify = oneClassifyJpa.findOne(shop.getClassifyId());
		shop.setOneClassify(oneClassify);
		
		ShopTwoClassify twoClassify = twoClassifyJpa.findOne(shop.getClassifyId());
		shop.setTwoClassify(twoClassify);
		
		ShopThreeClassify threeClassify = threeClassifyJpa.findOne(shop.getClassifyId());
		shop.setThreeClassify(threeClassify);
		
		ShopFourClassify fourClassify = fourClassifyJpa.findOne(shop.getClassifyId());
		shop.setFourClassify(fourClassify);
			
		//返标签
		List<ShopBindingLabel> bindLabelList = bindingLabelJpa.findByShopId(t.getCityId());
		for (ShopBindingLabel shopBindingLabel : bindLabelList) {
			//标签对象
			ShopLabelClassifyInfo labelInfo = labelClassifyInfoJpa.findOne(shopBindingLabel.getLabelId());
			shopBindingLabel.setLabelClassifyInfo(labelInfo);
			
			//标签分类对象
			ShopLabelClassify labelClassify = labelClassifyJpa.findOne(labelInfo.getLabelClassifyId());
			shopBindingLabel.setLabelClassify(labelClassify);
		}
		
		
		return Msg.MsgSuccess(shop);
	}

	@Override
	public Msg findAllShop(Pageable pageable, Map<String, Object> map) throws MyException {
		QueryHelper queryHelper = new QueryHelper("SELECT key", FwSsShop.class, "key")
				.addCondition("key.statue=1").addCondition("key.ssRemove=0");;
		
		//是否是总后台查询
		if(map.get("isAdmin") != null){
			queryHelper = new QueryHelper("SELECT key", FwSsShop.class, "key")
					.addCondition("key.ssRemove=0");
			map.remove("isAdmin");
		}
		//按分类查询
		if(map.get("classifyId") != null){
			queryHelper.addCondition("key.classifyId='" + map.get("classifyId").toString() + "'");
			map.remove("classifyId");
		}
		
		//按标签查询
		if(map.get("labelId") != null){
			queryHelper.addJoin("ShopBindingLabel", "bl");
			queryHelper.addCondition("bl.shopId=key.ssId");
			queryHelper.addCondition("bl.labelId in ("+ map.get("labelId").toString() +")");
			map.remove("labelId");
		}
		Page<?> page =  findAll(queryHelper, pageable, map);
		
		return Msg.MsgSuccess(page);
	}

	@Override
	public Msg auditShop(FwSsShop t) throws MyException {
		if (!ValidateUtil.isValid(t.getSsId())) {
			return Msg.MsgError("主键不能为空");
		}
		if (!ValidateUtil.isValid(t.getStatue())) {
			return Msg.MsgError("请传入审核状态");
		}
		FwSsShop shop = shopJpa.findOne(t.getSsId());
		if(shop == null ){
			return Msg.MsgError("数据不存在");
		}
		shop.setStatue(t.getStatue());
		shopJpa.save(shop);
		return Msg.MsgSuccess();
	}

	@Override
	public Msg addRecommend(FwSsShop t) throws MyException {
		if (!ValidateUtil.isValid(t.getSsId())) {
			return Msg.MsgError("主键不能为空");
		}
		FwSsShop shop = shopJpa.findOne(t.getSsId());
		if(shop == null ){
			return Msg.MsgError("数据不存在");
		}
		if (!ValidateUtil.isValid(t.getSsRecommend())) {
			return Msg.MsgError("请选择你要推荐的店铺");
		}
		shop.setStatue(t.getStatue());
		shopJpa.save(shop);
		return Msg.MsgSuccess();
	}

}
