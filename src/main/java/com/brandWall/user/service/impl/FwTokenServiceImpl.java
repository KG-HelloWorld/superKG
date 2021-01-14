package com.brandWall.user.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.brandWall.BaseServiceImpl;
import com.brandWall.user.jpa.FwTokenJpa;
import com.brandWall.user.model.FwToken;
import com.brandWall.user.service.FwTokenService;
import com.brandWall.util.CayUtil;

@Service
public class FwTokenServiceImpl extends BaseServiceImpl<FwToken> implements FwTokenService {

	@Inject
	private FwTokenJpa fwTokenJpa;

	@Override
	public List<FwToken> findByUsId(String usId) {
		return fwTokenJpa.findByTkUsId(usId);
	}

	@Override
	public FwToken updateToken(String usId, String shopId,Integer way) {
		if (null==way) {
			way = 0;
		}
		List<FwToken> fwTokens  = fwTokenJpa.findByTkUsIdAndUtType(usId,way);
		FwToken fwToken = null;
		if (fwTokens.size()==0) {
			fwToken = new FwToken();
			fwToken.setTkUsId(usId);
			fwToken.setTkUsSsId(shopId);
			Date date = new Date();
			fwToken.setTkCreateTime(date);
			fwToken.setTkModifyTime(date);
		}else{
			fwToken = fwTokens.get(0);
			for(int i=1;i<fwTokens.size();i++){
				this.fwTokenJpa.delete(fwTokens.get(i));
			}
		}
		
		fwToken.setUtType(way);
		fwToken.setTkTokenContent(CayUtil.getUUId());
		fwToken.setTkExpirationTime(CayUtil.getTimeByAfter(new Date(), Calendar.MONTH, 1));
		fwTokenJpa.save(fwToken);
		return fwToken;
	}

}
