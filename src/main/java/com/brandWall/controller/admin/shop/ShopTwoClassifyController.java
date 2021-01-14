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
import com.brandWall.shop.model.ShopTwoClassify;
import com.brandWall.shop.service.ShopTwoClassifyService;
import com.brandWall.util.Msg;
import com.brandWall.util.exception.MyException;

@RestController
@RequestMapping("api/admin/shopTwoClassify")
public class ShopTwoClassifyController extends BaseController{


	@Resource
	private ShopTwoClassifyService service;
	
	/**
	 * 
	 * @throws MyException
	 * @apiGroup open
	 * @api {post} api/admin/shopTwoClassify/add 添加二级分类
	 * @param  String classifyName 分类名
	 * @param  String iconPic 图标
	 * @apiSuccess (200) {String} message 信息
	 * @apiSuccess (200) {String} code 1001成功，0001错误
	 * @apiSuccess (200) {Object} data 返回的数据
	 * @apiSuccess (200) {Object} data.flage 0是未关闭 ；1是关闭
	 */
	@RequestMapping("add")
	public Msg add(ShopTwoClassify t) throws MyException {
		return service.add(t);
	}
	
	/**
	 * 
	 * @throws MyException
	 * @apiGroup open
	 * @api {post} api/admin/shopTwoClassify/modify 修改二级分类
	 * @param  String classifyName 分类名
	 * @param  String iconPic 图标
	 * @param  String id 主键
	 * @apiSuccess (200) {String} message 信息
	 * @apiSuccess (200) {String} code 1001成功，0001错误
	 * @apiSuccess (200) {Object} data 返回的数据
	 * @apiSuccess (200) {Object} data.flage 0是未关闭 ；1是关闭
	 */
	@RequestMapping("modify")
	public Msg modify(ShopTwoClassify t) throws MyException {
		return service.modify(t);
	}
	
	/**
	 * 
	 * @throws MyException
	 * @apiGroup open
	 * @api {post} api/admin/shopTwoClassify/remove s删除二级分类
	 * @param  String id 主键
	 * @apiSuccess (200) {String} message 信息
	 * @apiSuccess (200) {String} code 1001成功，0001错误
	 * @apiSuccess (200) {Object} data 返回的数据
	 * @apiSuccess (200) {Object} data.flage 0是未关闭 ；1是关闭
	 */
	@RequestMapping("remove")
	public Msg remove(ShopTwoClassify t) throws MyException {
		return service.remove(t);
	}
	
	/**
	 * 
	 * @throws MyException
	 * @apiGroup open
	 * @api {post} api/admin/shopTwoClassify/findById 查询二级分类详情
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
	 * @api {post} api/admin/shopTwoClassify/findAll 根据一级分类id查询所有二级分类
	 * @param  String page 页数
	 * @param  String size 页面大小
	 * @param  String map.oneClassifyId 一级分类id
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
