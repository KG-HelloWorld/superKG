package com.brandWall.shop.jpa;

import com.brandWall.shop.model.SsCommoditySearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SsCommoditySearchJpa extends JpaRepository<SsCommoditySearch, String> {

    SsCommoditySearch findByCcsCdiIdAndCcsCcstId(String cId, String stId);

    List<SsCommoditySearch> findByCcsCcstId(String ccstId);
}
