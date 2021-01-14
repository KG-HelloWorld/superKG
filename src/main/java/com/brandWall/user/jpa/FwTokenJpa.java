package com.brandWall.user.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brandWall.user.model.FwToken;

public interface FwTokenJpa extends JpaRepository<FwToken, String> {

	public List<FwToken> findByTkUsIdAndUtType(String usId,Integer type);
	
	public List<FwToken> findByTkUsId(String usId);

	public FwToken findByTkTokenContentAndTkUsSsId(String token, String ssId);

	public FwToken findByTkTokenContent(String token);
}
