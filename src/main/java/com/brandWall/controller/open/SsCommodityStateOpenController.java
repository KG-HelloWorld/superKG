package com.brandWall.controller.open;


import com.brandWall.controller.BaseController;
import com.brandWall.shop.model.SsCommodityState;
import com.brandWall.shop.service.SsCommodityStateService;
import com.brandWall.util.Msg;
import com.brandWall.util.QueryHelper;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/open/ssCommodityState")
public class SsCommodityStateOpenController extends BaseController {

    @Inject
    private SsCommodityStateService ssCommodityStateService;


    /**
     *
     * @apiGroup admin
     * @api {post} api/open/ssCommodityState/findAll 查询全部分类
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
     * @api {post} api/open/ssCommodityState/findCommodityByStId 首页推荐-查询所有分类中的店铺
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
}
