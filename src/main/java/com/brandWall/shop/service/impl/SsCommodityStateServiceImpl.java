package com.brandWall.shop.service.impl;

import com.brandWall.BaseServiceImpl;
import com.brandWall.shop.jpa.SsCommoditySearchJpa;
import com.brandWall.shop.jpa.SsCommodityStateJpa;
import com.brandWall.shop.model.FwSsShop;
import com.brandWall.shop.model.SsCommoditySearch;
import com.brandWall.shop.model.SsCommodityState;
import com.brandWall.shop.service.SsCommodityStateService;
import com.brandWall.util.Msg;
import com.brandWall.util.QueryHelper;
import com.brandWall.util.ValidateUtil;
import com.brandWall.util.exception.MyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SsCommodityStateServiceImpl extends BaseServiceImpl<SsCommodityState> implements SsCommodityStateService {

    @Resource
    private SsCommodityStateJpa ssCommodityStateJpa;

    @Resource
    private SsCommoditySearchJpa ssCommoditySearchJpa;

    @Override
    public Msg add(SsCommodityState ssCommodityState) throws MyException {
        if(!ValidateUtil.isValid(ssCommodityState.getCcsStateName())){
            return Msg.MsgError("名字不能为空");
        }

        if(!ValidateUtil.isValid(ssCommodityState.getCcstSort())){
            return Msg.MsgError("请填写排序");
        }
        ssCommodityStateJpa.save(ssCommodityState);

        return Msg.MsgSuccess();
    }

    @Override
    public Msg modify(SsCommodityState ssCommodityState) throws MyException {
        if(!ValidateUtil.isValid(ssCommodityState.getCcstId())){
            return Msg.MsgError("主键不能为空");
        }
        if(!ValidateUtil.isValid(ssCommodityState.getCcsStateName())){
            return Msg.MsgError("名字不能为空");
        }

        if(!ValidateUtil.isValid(ssCommodityState.getCcstSort())){
            return Msg.MsgError("请填写排序");
        }

        SsCommodityState state = ssCommodityStateJpa.findOne(ssCommodityState.getCcstId());
        if(state == null){
            return Msg.MsgError("数据不存在");
        }
        state.setCcsStateName(ssCommodityState.getCcsStateName());
        state.setCcstSort(ssCommodityState.getCcstSort());

        ssCommodityStateJpa.save(state);

        return Msg.MsgSuccess();
    }

    @Override
    public Msg remove(SsCommodityState ssCommodityState) throws MyException {
        if(!ValidateUtil.isValid(ssCommodityState.getCcstId())){
            return Msg.MsgError("主键不能为空");
        }
        SsCommodityState commodityState = ssCommodityStateJpa.findOne(ssCommodityState.getCcstId());
        if(commodityState == null){
            return Msg.MsgError("数据不存在");
        }
        List<SsCommoditySearch> searchList = ssCommoditySearchJpa.findByCcsCcstId(commodityState.getCcstId());
        if(searchList.size() > 0){
            return Msg.MsgError("该分类底下有品牌，删除请先移除");
        }
        ssCommodityStateJpa.delete(commodityState);

        return Msg.MsgSuccess();
    }

    @Override
    public Msg findById(String id) throws MyException {
        if(!ValidateUtil.isValid(id)){
            return Msg.MsgError("主键不能为空");
        }

        return Msg.MsgSuccess(ssCommodityStateJpa.findOne(id));
    }

    @Override
    public Msg findPage(Map<String, Object> map, Pageable pageable) throws MyException {
        QueryHelper queryHelper = new QueryHelper("SELECT key", SsCommodityState.class, "key")
                .addOrderProperty("key.ccstSort", false);

        return Msg.MsgSuccess(findAll(queryHelper,pageable,map));
    }

    @Override
    public Msg addCommodity(String stId, String cIds) throws MyException {

        if (ValidateUtil.isValid(cIds)) {
            String[] cdIds = cIds.split(",");
            for (String cId : cdIds) {
                SsCommoditySearch search = this.ssCommoditySearchJpa.findByCcsCdiIdAndCcsCcstId(cId, stId);
                if (null == search) {
                    search = new SsCommoditySearch();
                    search.setCcsCcstId(stId);
                    search.setCcsCdiId(cId);
                    this.ssCommoditySearchJpa.save(search);
                }
            }
        }
        return Msg.MsgSuccess();
    }

    @Override
    public Msg findBySearchPage(Pageable pageable, boolean flage, Map<String, Object> map)
            throws Exception {
        QueryHelper queryHelper = new QueryHelper("select shop ", FwSsShop.class, "shop")
                .addCondition("shop.statue=1").addCondition("shop.ssRemove=0");;
        if (flage) {
            queryHelper.addJoin("SsCommoditySearch", "search");
            queryHelper.addCondition("shop.ssId = search.ccsCdiId");
            if(map.get("sId") != null){
                queryHelper.addCondition("search.ccsCcstId='" + map.get("sId").toString() + "'");
            }
        }
        return Msg.MsgSuccess(this.findAll(queryHelper, pageable));
    }

    @Override
    public Msg removeCommodity(String stId, String cIds) throws Exception {
        if (ValidateUtil.isValid(cIds)) {
            List<SsCommoditySearch> list = new ArrayList<>();
            String[] cdIds = cIds.split(",");
            for (String cId : cdIds) {
                SsCommoditySearch search = this.ssCommoditySearchJpa.findByCcsCdiIdAndCcsCcstId(cId, stId);
                if (null != search) {
                    list.add(search);
                }
            }
            this.ssCommoditySearchJpa.delete(list);
        }
        return Msg.MsgSuccess();
    }

    @Override
    public Msg findIndexAddShop(Pageable pageable, Map<String, Object> map) throws Exception {
        if(map.get("ccsCcstId") == null) {
            return Msg.MsgError("请传入分类id");
        }
        String ccsCcstId = map.get("ccsCcstId").toString();
        map.remove("ccsCcstId");

        //查所有的店铺
        QueryHelper queryHelper = new QueryHelper("SELECT key", FwSsShop.class, "key")
                .addCondition("key.statue=1").addCondition("key.ssRemove=0");;
        Page<FwSsShop> page = (Page<FwSsShop>) findAll(queryHelper, pageable, map);
        List<FwSsShop> shopList = page.getContent();

        //根据分类id查询本分类中的店铺
        List<SsCommoditySearch> searchList = ssCommoditySearchJpa.findByCcsCcstId(ccsCcstId);

        //两个集合比较，哪些店铺在本分类中是选中的
        for (FwSsShop shop : shopList) {
            for (SsCommoditySearch search : searchList) {
                if(search.getCcsCdiId().equals(shop.getSsId())){
                    shop.setIsCheck(1);
                }
            }
        }

        return Msg.MsgSuccess(page);
    }


}
