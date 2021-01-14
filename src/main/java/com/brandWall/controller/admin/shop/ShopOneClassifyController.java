package com.brandWall.controller.admin.shop;

import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brandWall.controller.BaseController;
import com.brandWall.shop.model.ShopOneClassify;
import com.brandWall.shop.service.ShopOneClassifyService;
import com.brandWall.util.Msg;
import com.brandWall.util.exception.MyException;

@RestController
@RequestMapping("api/admin/shopOneClassify")
public class ShopOneClassifyController extends BaseController{

	@Resource
	private ShopOneClassifyService service;
	
	/**
	 * 
	 * @throws MyException
	 * @apiGroup open
	 * @api {post} api/admin/shopOneClassify/add 添加一级分类
	 * @param  String classifyName 分类名
	 * @param  String iconPic 图标
	 * @apiSuccess (200) {String} message 信息
	 * @apiSuccess (200) {String} code 1001成功，0001错误
	 * @apiSuccess (200) {Object} data 返回的数据
	 * @apiSuccess (200) {Object} data.flage 0是未关闭 ；1是关闭
	 */
	@RequestMapping("add")
	public Msg add(ShopOneClassify t) throws MyException {
		return service.add(t);
	}
	
	/**
	 * 
	 * @throws MyException
	 * @apiGroup open
	 * @api {post} api/admin/shopOneClassify/modify 修改一级分类
	 * @param  String classifyName 分类名
	 * @param  String iconPic 图标
	 * @param  String id 主键
	 * @apiSuccess (200) {String} message 信息
	 * @apiSuccess (200) {String} code 1001成功，0001错误
	 * @apiSuccess (200) {Object} data 返回的数据
	 * @apiSuccess (200) {Object} data.flage 0是未关闭 ；1是关闭
	 */
	@RequestMapping("modify")
	public Msg modify(ShopOneClassify t) throws MyException {
		return service.modify(t);
	}
	
	/**
	 * 
	 * @throws MyException
	 * @apiGroup open
	 * @api {post} api/admin/shopOneClassify/remove s删除一级分类
	 * @param  String id 主键
	 * @apiSuccess (200) {String} message 信息
	 * @apiSuccess (200) {String} code 1001成功，0001错误
	 * @apiSuccess (200) {Object} data 返回的数据
	 * @apiSuccess (200) {Object} data.flage 0是未关闭 ；1是关闭
	 */
	@RequestMapping("remove")
	public Msg remove(ShopOneClassify t) throws MyException {
		return service.remove(t);
	}
	
	/**
	 * 
	 * @throws MyException
	 * @apiGroup open
	 * @api {post} api/admin/shopOneClassify/findById 查询一级分类详情
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
	 * @api {post} api/admin/shopOneClassify/findAll 查询所有一级分类
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

	/**
	 *
	 * @throws MyException
	 * @apiGroup open
	 * @api {post} api/admin/shopOneClassify/findAllClassify 查询所有一级分类下的所有分类
	 * @param  String page 页数
	 * @param  String size 页面大小
	 * @apiSuccess (200) {String} message 信息
	 * @apiSuccess (200) {String} code 1001成功，0001错误
	 * @apiSuccess (200) {Object} data 返回的数据
	 * @apiSuccess (200) {Object} data.flage 0是未关闭 ；1是关闭
	 */
	@RequestMapping("findAllClassify")
	public Msg findAllClassify(Pageable pageable, HttpServletRequest request) throws MyException {
		Map<String, Object> map = getMapToRequest(request);

		return service.findAllClassify(pageable, map);
	}
	
}
