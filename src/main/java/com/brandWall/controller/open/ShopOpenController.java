package com.brandWall.controller.open;

import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.brandWall.shop.service.SsBannerService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brandWall.controller.BaseController;
import com.brandWall.shop.model.FwSsShop;
import com.brandWall.shop.service.FwSsShopService;
import com.brandWall.util.Msg;
import com.brandWall.util.exception.MyException;

@RestController
@RequestMapping("api/open/fwSsShop")
public class ShopOpenController extends BaseController{

	@Resource
	private FwSsShopService service;

	@Inject
	private SsBannerService ssBannerService;
	
	/**
	 * 
	 * @throws MyException
	 * @apiGroup open
	 * @api {post} api/open/fwSsShop/findById 查询品牌详情
	 * @param  String ssId 主键
	 * @apiSuccess (200) {String} message 信息
	 * @apiSuccess (200) {String} code 1001成功，0001错误
	 * @apiSuccess (200) {Object} data 返回的数据
	 * @apiSuccess (200) {Object} data.flage 0是未关闭 ；1是关闭
	 */
	@RequestMapping("findById")
	public Msg findById(FwSsShop t) throws MyException {
		return service.findById(t);
	}
	
	/**
	 * 
	 * @throws MyException
	 * @apiGroup open
	 * @api {post} api/open/fwSsShop/findAll 查询所有品牌
	 * @param  String page 页数
	 * @param  String size 页面大小
	 * @param  String map.threeClassifyId 分类id
	 * @param  String map.ssRecommend 是否是推荐，0否 1是
	 * @apiSuccess (200) {String} message 信息
	 * @apiSuccess (200) {String} code 1001成功，0001错误
	 * @apiSuccess (200) {Object} data 返回的数据
	 * @apiSuccess (200) {Object} data.flage 0是未关闭 ；1是关闭
	 */
	@RequestMapping("findAll")
	public Msg findAll(Pageable pageable, HttpServletRequest request) throws MyException {
		Map<String, Object> map = getMapToRequest(request);
		return service.findAllShop(pageable,map);
	}

	/**
	 * @apiGroup banner
	 * @api {post} api/open/fwSsShop/findAllBanner 获取banner列表
	 * @apiParam {String} size 显示条数
	 * @apiParam {String} page 页数
	 * @apiSuccess (200) {String} message 信息
	 * @apiSuccess (200) {String} code 1001成功，0001错误
	 * @apiSuccess (200) {Object} data 返回的数据
	 */
	@RequestMapping("findByAll")
	public Object findByAll(Pageable pageable,Integer status,HttpServletRequest request) throws MyException {
		Map<String, Object> map = getMapToRequest(request);
		return ssBannerService.findAll(getShopId(), pageable,null,null,status,map);
	}
}
