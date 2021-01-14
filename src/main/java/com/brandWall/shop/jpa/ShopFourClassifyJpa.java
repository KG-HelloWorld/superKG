package com.brandWall.shop.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brandWall.shop.model.ShopFourClassify;

public interface ShopFourClassifyJpa extends JpaRepository<ShopFourClassify, String>{

	List<ShopFourClassify> findByThreeClassifyId(String id);
}
