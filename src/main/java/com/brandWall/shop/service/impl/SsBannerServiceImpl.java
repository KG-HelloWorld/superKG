package com.brandWall.shop.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.brandWall.BaseServiceImpl;
import com.brandWall.shop.jpa.SsBannerJpa;
import com.brandWall.shop.model.SsBanner;
import com.brandWall.shop.service.SsBannerService;
import com.brandWall.util.FileUtil;
import com.brandWall.util.Msg;
import com.brandWall.util.QueryHelper;
import com.brandWall.util.ValidateUtil;
import com.brandWall.util.exception.MyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service
public class SsBannerServiceImpl extends BaseServiceImpl<SsBanner> implements SsBannerService {

	@Inject
	private SsBannerJpa ssBannerJpa;

	@Override
	public Msg addBanner(SsBanner banner) throws MyException {
		if (banner.getBId() != null && ssBannerJpa.findOne(banner.getBId()) == null) {
			return Msg.MsgError("not exists");
		}
		if (!ValidateUtil.isValid(banner.getBPic()) || !ValidateUtil.isValid(banner.getBName())) {
			return Msg.MsgError("图片不能为空");
		}
//		if (!ValidateUtil.isValid(banner.getBAboutId()) && ssCommodityInfoJpa.findOne(banner.getBAboutId()) == null) {
//			return Msg.MsgError("not exists");
//		}
		if(!ValidateUtil.isValid(banner.getBId())){
			banner.setBCreateTime(new Date());
		}
		banner.setBPic(FileUtil.moveAbsoluteToFile(banner.getBPic()));
		ssBannerJpa.save(banner);
		return Msg.MsgSuccess();
	}

	/**
	 * 删除banner图
	 */
	@Override
	public Msg delBanner(SsBanner banner) throws MyException {
		String[] ids = banner.getBId().split(",");
		for (String id : ids) {
			SsBanner ssBanner = ssBannerJpa.findOne(id);
			if (ssBanner == null) {
				throw new MyException("0014", "参数异常,不存在数据");
			}
			
			this.ssBannerJpa.delete(id);
//			this.jpa.remove(banner);
		}
		return Msg.MsgSuccess();
	}

	@SuppressWarnings({ "unchecked", "static-access" })
	@Override
	public Msg findAll(String shopId, Pageable pageable, Integer enable,Integer way,Integer status,Map<String,Object> map) throws MyException {
		
		Page<?> page=null;
	
		
			QueryHelper queryHelper = new QueryHelper(SsBanner.class, "b");
			//queryHelper.addCondition("b.bSsId='" + shopId + "'");
			if (enable != null) {
				queryHelper.addCondition("b.bEnabled=" + enable);
			}
			if(null!=way){
				queryHelper.addCondition("b.prot=" + way);
			}
			
			if(status==null){
				status=0;
			}
			if(status!=null&&status!=-1){
				queryHelper.addCondition("b.bStatus=" + status);		
			}

			queryHelper.addOrderProperty("b.sort", false);	
			page = findAll(queryHelper, pageable,map);

		return Msg.MsgSuccess(page);

	}

	/**
	 * 禁用banner图
	 */
	@Override
	public Msg optionState(String id) {
		SsBanner banner = ssBannerJpa.findOne(id);
		if (banner == null) {
			return Msg.MsgError("not exists");
		}
		banner.setBEnabled(banner.getBEnabled() == 1 ? 0 : 1);
		ssBannerJpa.save(banner);
		return Msg.MsgSuccess();
	}

}
