package com.brandWall.controller.admin.shop;

import com.brandWall.controller.BaseController;
import com.brandWall.shop.jpa.SsCommodityStateJpa;
import com.brandWall.shop.model.SsCommodityState;
import com.brandWall.shop.service.SsCommodityStateService;
import com.brandWall.util.Msg;
import com.brandWall.util.QueryHelper;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("api/admin/ssCommodityState")
public class SsCommodityStateController extends BaseController {

    @Inject
    private SsCommodityStateService ssCommodityStateService;

    /**
     *
     * @apiGroup admin
     * @api {post} api/admin/ssCommodityState/add 首页推荐-添加分类
     * @apiSuccess {String} ccsStateName 名字
     * @apiSuccess {String} ccstSort 排序
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} code 1001成功，0001错误
     * @apiSuccess (200) {Object} data 返回的数据
     */
    @RequestMapping("add")
    public Msg add(SsCommodityState t) throws Exception {
        return ssCommodityStateService.add(t);
    }

    /**
     *
     * @apiGroup admin
     * @api {post} api/admin/ssCommodityState/modify 首页推荐-修改分类
     * @apiSuccess {String} ccsStateName 名字
     * @apiSuccess {String} ccstSort 排序
     * @apiSuccess {String} id 主键
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} code 1001成功，0001错误
     * @apiSuccess (200) {Object} data 返回的数据
     */
    @RequestMapping("modify")
    public Msg modify(SsCommodityState t) throws Exception {
        return ssCommodityStateService.modify(t);
    }

    /**
     *
     * @apiGroup admin
     * @api {post} api/admin/ssCommodityState/remove 首页推荐-修改分类
     * @apiSuccess {String} ccsStateName 名字
     * @apiSuccess {String} ccstSort 排序
     * @apiSuccess {String} id 主键
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} code 1001成功，0001错误
     * @apiSuccess (200) {Object} data 返回的数据
     */
    @RequestMapping("remove")
    public Msg remove(SsCommodityState t) throws Exception {
        return ssCommodityStateService.remove(t);
    }

    /**
     *
     * @apiGroup admin
     * @api {post} api/admin/ssCommodityState/findCommodityByStId 首页推荐-查询所有分类中的店铺
     * @apiParam {int} page 当前第几页
     * @apiParam {int} size 每页显示多少条
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} code 1001成功，0001错误
     * @apiSuccess (200) {Object} data 返回的数据
     */
    @RequestMapping("findCommodityByStId")
    public Msg findCommodityByStId(Pageable pageable, HttpServletRequest request) throws Exception {
        Map<String, Object> map =  getMapToRequest(request);
        Msg msg = this.ssCommodityStateService.findBySearchPage(pageable,true, map);
        return msg;
    }

    /**
     *
     * @apiGroup admin
     * @api {post} api/admin/ssCommodityState/addCommodity 首页推荐-添加产品
     * @apiParam {String} stId 推荐Id
     * @apiParam {String} cIds 产品Id（多个逗号分隔）
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} code 1001成功，0001错误
     * @apiSuccess (200) {Object} data 返回的数据
     */
    @RequestMapping("addCommodity")
    public Msg addCommodity(String stId, String cIds) throws Exception {
        return ssCommodityStateService.addCommodity(stId, cIds);
    }

    /**
     *
     * @apiGroup admin
     * @api {post} api/admin/ssCommodityState/findAll 查询全部分类
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} code 1001成功，0001错误
     * @apiSuccess (200) {Object} data 返回的数据
     */
    @RequestMapping("findAll")
    public Msg findAll() throws Exception {
        QueryHelper queryHelper = new QueryHelper("select key", SsCommodityState.class, "key")
                .addOrderProperty("key.ccstSort", false);
        String listQueryHql = queryHelper.getListQueryHql();
        List<?> list = ssCommodityStateService.find(listQueryHql);
        return Msg.MsgSuccess(list);
    }

    /**
     *
     * @apiGroup admin
     * @api {post} api/admin/ssCommodityState/findPage 总后台查询全部分类
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} code 1001成功，0001错误
     * @apiSuccess (200) {Object} data 返回的数据
     */
    @RequestMapping("findPage")
    public Msg findPage(Pageable pageable, HttpServletRequest request) throws Exception {
        Map<String, Object> map = getMapToRequest(request);
        return ssCommodityStateService.findPage(map,pageable);
    }

    /**
     *
     * @apiGroup admin
     * @api {post} api/admin/ssCommodityState/removeCommodity 首页推荐-删除产品
     * @apiParam {String} stId 推荐Id
     * @apiParam {String} cIds 产品Id（多个逗号分隔）
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} code 1001成功，0001错误
     * @apiSuccess (200) {Object} data 返回的数据
     */
    @RequestMapping("removeCommodity")
    public Msg removeCommodity(String stId, String cIds) throws Exception {
        return ssCommodityStateService.removeCommodity(stId, cIds);
    }

    /**
     *
     * @apiGroup admin
     * @api {post} api/admin/ssCommodityState/findIndexAddShop 首页推荐-查询所有店铺
     * @apiParam {String} page 页数
     * @apiParam {String} size 页面大小
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} code 1001成功，0001错误
     * @apiSuccess (200) {Object} data 返回的数据
     */
    @RequestMapping("findIndexAddShop")
    public Msg findIndexAddShop(Pageable pageable, HttpServletRequest request) throws Exception {
        Map<String, Object> map = getMapToRequest(request);
        return ssCommodityStateService.findIndexAddShop(pageable, map);
    }
}
