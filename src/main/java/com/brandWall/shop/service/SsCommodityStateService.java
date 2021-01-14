package com.brandWall.shop.service;

import com.brandWall.BaseService;
import com.brandWall.shop.model.SsCommodityState;
import com.brandWall.util.Msg;
import com.brandWall.util.exception.MyException;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * @author Administrator
 */
public interface SsCommodityStateService extends BaseService<SsCommodityState> {
    /**
     * 根据id查详情
     * @param id
     * @return
     * @throws MyException
     */
    Msg findById(String id) throws MyException;


    /**
     * 添加展示品牌
     * @param stId
     * @param cIds
     * @return
     * @throws Exception
     */
    Msg addCommodity(String stId, String cIds) throws Exception;

    /**
     * 首页推荐-按照推荐Id查询选中的产品
     * @param pageable
     * @param flage
     * @return
     * @throws Exception
     */
    public Msg findBySearchPage(Pageable pageable,boolean flage, Map<String, Object> map) throws Exception;

    /** 删除首页推荐的产品*/
    public Msg removeCommodity(String stId, String cIds) throws Exception;

    public Msg findIndexAddShop(Pageable pageable, Map<String, Object> map) throws Exception;
}

