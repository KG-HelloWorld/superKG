package com.brandWall.user.service;

import java.util.List;

import com.brandWall.BaseService;
import com.brandWall.user.model.FwToken;

public interface FwTokenService extends BaseService<FwToken> {

	public List<FwToken> findByUsId(String usId);

	public FwToken updateToken(String usId, String shopId,Integer way);
}
