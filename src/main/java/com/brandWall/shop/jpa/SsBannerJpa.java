package com.brandWall.shop.jpa;

import java.util.List;

import com.brandWall.shop.model.SsBanner;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SsBannerJpa extends JpaRepository<SsBanner, String> {

	public int deleteByBSsId(String bSsId);

	
	public List<SsBanner> findByBSsId(String ssId);
	
	
	
}
