package com.brandWall.shop.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brandWall.shop.model.ShopLabelClassifyInfo;

public interface ShopLabelClassifyInfoJpa extends JpaRepository<ShopLabelClassifyInfo, String>{

	List<ShopLabelClassifyInfo> findByLabelClassifyId(String id);

}
