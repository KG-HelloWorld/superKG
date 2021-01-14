package com.brandWall.controller.open;

import com.brandWall.controller.BaseController;
import com.brandWall.shop.service.ShopLabelClassifyService;
import com.brandWall.util.Msg;
import com.brandWall.util.exception.MyException;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
@RequestMapping("api/open/shopLabelClassify")
public class ShopLabelClassifyOpenController extends BaseController {

    @Resource
    private ShopLabelClassifyService service;

    /**
     *
     * @throws MyException
     * @apiGroup open
     * @api {post} api/open/shopLabelClassify/findById 查询标签分类详情
     * @param  String id 主键
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} code 1001成功，0001错误
     * @apiSuccess (200) {Object} data 返回的数据
     * @apiSuccess (200) {Object} data.flage 0是未关闭 ；1是关闭
     */
    @RequestMapping("findById")
    public Msg findById(String id) throws MyException {
        return service.findById(id);
    }

    /**
     *
     * @throws MyException
     * @apiGroup open
     * @api {post} api/open/shopLabelClassify/findAll 查询所有标签分类
     * @param  String page 页数
     * @param  String size 页面大小
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} code 1001成功，0001错误
     * @apiSuccess (200) {Object} data 返回的数据
     * @apiSuccess (200) {Object} data.flage 0是未关闭 ；1是关闭
     */
    @RequestMapping("findAll")
    public Msg findAll(Pageable pageable, HttpServletRequest request) throws MyException {
        Map<String, Object> map = getMapToRequest(request);
        return service.findPage(map,pageable);
    }
}
