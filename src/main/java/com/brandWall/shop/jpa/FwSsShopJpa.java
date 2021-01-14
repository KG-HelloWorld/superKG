package com.brandWall.shop.jpa;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brandWall.shop.model.FwSsShop;

public interface FwSsShopJpa extends JpaRepository<FwSsShop, String>{

	public FwSsShop findByUsId(String usId);

	public FwSsShop findByUsIdAndSsRemove(String usId,int remove);

	public List<FwSsShop> findByClassifyId(String id);
}
