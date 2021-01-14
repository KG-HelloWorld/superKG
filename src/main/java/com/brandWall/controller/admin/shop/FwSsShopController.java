package com.brandWall.controller.admin.shop;

import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brandWall.controller.BaseController;
import com.brandWall.shop.model.FwSsShop;
import com.brandWall.shop.model.ShopFourClassify;
import com.brandWall.shop.service.FwSsShopService;
import com.brandWall.util.Msg;
import com.brandWall.util.exception.MyException;


@RestController
@RequestMapping("api/admin/fwSsShop")
public class FwSsShopController extends BaseController{

	@Resource
	private FwSsShopService service;
	
	/**
	 * 
	 * @throws MyException
	 * @apiGroup open
	 * @api {post} api/admin/fwSsShop/add 添加品牌
	 * @param  String ssPic logo主图
	 * @param  String companyName 公司名字
	 * @param  String ssContacts 联系人
	 * @param  String ssPhone 手机号
	 * @param  String ssName 品牌名
	 * @param  String ssDec 简介
	 * @param  String shopContext 品牌详情
	 * @param  String shopVedio 品牌详情
	 * @apiSuccess (200) {String} message 信息
	 * @apiSuccess (200) {String} code 1001成功，0001错误
	 * @apiSuccess (200) {Object} data 返回的数据
	 * @apiSuccess (200) {Object} data.flage 0是未关闭 ；1是关闭
	 */
	@RequestMapping("add")
	public Msg add(FwSsShop t) throws MyException {
		return service.add(t);
	}
	
	/**
	 * 
	 * @throws MyException
	 * @apiGroup open
	 * @api {post} api/admin/fwSsShop/modify 修改品牌
	 * @param  String ssId 主键
	 * @param  String ssPic logo主图
	 * @param  String companyName 公司名字
	 * @param  String ssContacts 联系人
	 * @param  String ssPhone 手机号
	 * @param  String ssName 品牌名
	 * @param  String ssDec 简介
	 * @param  String shopContext 品牌详情
	 * @param  String shopVedio 品牌视频
	 * @param  String classifyId 品牌分类id
	 * @param  String bindingLabelList[0].labelId 品牌标签id
	 * @apiSuccess (200) {String} message 信息
	 * @apiSuccess (200) {String} code 1001成功，0001错误
	 * @apiSuccess (200) {Object} data 返回的数据
	 * @apiSuccess (200) {Object} data.flage 0是未关闭 ；1是关闭
	 */
	@RequestMapping("modify")
	public Msg modify(FwSsShop t) throws MyException {
		return service.modify(t);
	}
	
	/**
	 * 
	 * @throws MyException
	 * @apiGroup open
	 * @api {post} api/admin/fwSsShop/remove s删除品牌
	 * @param  String ssId 主键
	 * @apiSuccess (200) {String} message 信息
	 * @apiSuccess (200) {String} code 1001成功，0001错误
	 * @apiSuccess (200) {Object} data 返回的数据
	 * @apiSuccess (200) {Object} data.flage 0是未关闭 ；1是关闭
	 */
	@RequestMapping("remove")
	public Msg remove(FwSsShop t) throws MyException {
		return service.remove(t);
	}
	
	/**
	 * 
	 * @throws MyException
	 * @apiGroup open
	 * @api {post} api/admin/fwSsShop/findById 查询品牌详情
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
	 * @api {post} api/admin/fwSsShop/findAll 查询所有品牌
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
	 *
	 * @throws MyException
	 * @apiGroup open
	 * @api {post} api/admin/fwSsShop/auditShop 审核品牌
	 * @param  String statue 审核状态
	 * @param  String ssId 主键
	 * @apiSuccess (200) {String} message 信息
	 * @apiSuccess (200) {String} code 1001成功，0001错误
	 * @apiSuccess (200) {Object} data 返回的数据
	 * @apiSuccess (200) {Object} data.flage 0是未关闭 ；1是关闭
	 */
	@RequestMapping("auditShop")
	public Msg auditShop(FwSsShop t) throws MyException {
		return service.auditShop(t);
	}

	/**
	 *
	 * @throws MyException
	 * @apiGroup open
	 * @api {post} api/admin/fwSsShop/addRecommend 设置推荐品牌
	 * @param  String ssRecommend 推荐状态 0否 1是
	 * @param  String ssId 主键
	 * @apiSuccess (200) {String} message 信息
	 * @apiSuccess (200) {String} code 1001成功，0001错误
	 * @apiSuccess (200) {Object} data 返回的数据
	 * @apiSuccess (200) {Object} data.flage 0是未关闭 ；1是关闭
	 */
	@RequestMapping("addRecommend")
	public Msg addRecommend(FwSsShop t) throws MyException {
		return service.addRecommend(t);
	}
	
}
