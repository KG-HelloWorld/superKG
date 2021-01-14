package com.brandWall.shop.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brandWall.shop.model.ShopTwoClassify;

public interface ShopTwoClassifyJpa extends JpaRepository<ShopTwoClassify, String>{

	List<ShopTwoClassify> findByOneClassifyId(String id);

}
