package com.brandWall.shop.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brandWall.shop.model.ShopBindingLabel;

public interface ShopBindingLabelJpa extends JpaRepository<ShopBindingLabel, String>{

	List<ShopBindingLabel> findByLabelId(String labelId);

	List<ShopBindingLabel> findByShopId(String ssId);
}
