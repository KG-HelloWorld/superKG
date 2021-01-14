package com.brandWall.shop.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brandWall.shop.model.ShopFourClassify;
import com.brandWall.shop.model.ShopThreeClassify;

public interface ShopThreeClassifyJpa extends JpaRepository<ShopThreeClassify, String>{

	List<ShopThreeClassify> findByTwoClassifyId(String id);

	
}
