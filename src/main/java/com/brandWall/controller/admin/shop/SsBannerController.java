package com.brandWall.controller.admin.shop;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.brandWall.controller.BaseController;
import com.brandWall.shop.model.SsBanner;
import com.brandWall.shop.service.SsBannerService;
import com.brandWall.util.exception.MyException;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController("adminSsBannerController")
@RequestMapping("api/admin/banner")
public class SsBannerController extends BaseController {

	@Inject
	private SsBannerService ssBannerService;

	/**
	 * @apiGroup banner
		 * @api {post} api/admin/banner/updateBanner 添加,修改banner图
	 * @apiParam {String} bId bannerID
	 * @apiParam {String} bName 名称
	 * @apiParam {String} bPic 图片
	 * @apiParam {String} bAboutId 关联商品id
	 * @apiParam {String} bEnabled 是否启用 0 否 1 是
	 * @apiSuccess (200) {String} message 信息
	 * @apiSuccess (200) {String} code 1001成功，0001错误
	 * @apiSuccess (200) {Object} data 返回的数据
	 */
	@RequestMapping("updateBanner")
	public Object updateBanner(SsBanner banner) throws MyException {
//		banner.setBSsId(getShopId());
		return ssBannerService.addBanner(banner);
	}

	/**
	 * @apiGroup banner
	 * @api {post} api/admin/banner/delBanner 删除banner
	 * @apiParam {String} bId 主键
	 * @apiSuccess (200) {String} message 信息
	 * @apiSuccess (200) {String} code 1001成功，0001错误
	 * @apiSuccess (200) {Object} data 返回的数据
	 */
	@RequestMapping("delBanner")
	public Object delBanner(SsBanner banner) throws MyException {
		return ssBannerService.delBanner(banner);
	}

	/**
	 * @apiGroup banner
	 * @api {post} api/admin/banner/findByAll 获取banner列表
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
	
	
	
	
	
	/**
	 * @apiGroup banner
	 * @api {post} api/admin/banner/optionState 禁用banner图
	 * @apiParam {String} id banner主键
	 * @apiSuccess (200) {String} message 信息
	 * @apiSuccess (200) {String} code 1001成功，0001错误
	 * @apiSuccess (200) {Object} data 返回的数据
	 */
	@RequestMapping("optionState")
	public Object optionState(String id) throws MyException {
		return ssBannerService.optionState(id);
	}

}
