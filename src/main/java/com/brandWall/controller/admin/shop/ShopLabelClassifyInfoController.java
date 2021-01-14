package com.brandWall.controller.admin.shop;

import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brandWall.controller.BaseController;
import com.brandWall.shop.model.ShopLabelClassify;
import com.brandWall.shop.model.ShopLabelClassifyInfo;
import com.brandWall.shop.service.ShopLabelClassifyInfoService;
import com.brandWall.util.Msg;
import com.brandWall.util.exception.MyException;

@RestController
@RequestMapping("api/admin/shopLabelClassifyInfo")
public class ShopLabelClassifyInfoController extends BaseController{

	@Resource
	private ShopLabelClassifyInfoService service;
	
	/**
	 * 
	 * @throws MyException
	 * @apiGroup open
	 * @api {post} api/admin/shopLabelClassifyInfo/add 添加标签分类
	 * @param  String name 分类名
	 * @apiSuccess (200) {String} message 信息
	 * @apiSuccess (200) {String} code 1001成功，0001错误
	 * @apiSuccess (200) {Object} data 返回的数据
	 * @apiSuccess (200) {Object} data.flage 0是未关闭 ；1是关闭
	 */
	@RequestMapping("add")
	public Msg add(ShopLabelClassifyInfo t) throws MyException {
		return service.add(t);
	}
	
	/**
	 * 
	 * @throws MyException
	 * @apiGroup open
	 * @api {post} api/admin/shopLabelClassifyInfo/modify 修改标签分类
	 * @param  String name 分类名
	 * @apiSuccess (200) {String} message 信息
	 * @apiSuccess (200) {String} code 1001成功，0001错误
	 * @apiSuccess (200) {Object} data 返回的数据
	 * @apiSuccess (200) {Object} data.flage 0是未关闭 ；1是关闭
	 */
	@RequestMapping("modify")
	public Msg modify(ShopLabelClassifyInfo t) throws MyException {
		return service.modify(t);
	}
	
	/**
	 * 
	 * @throws MyException
	 * @apiGroup open
	 * @api {post} api/admin/shopLabelClassifyInfo/remove s删除标签分类
	 * @param  String name 分类名
	 * @apiSuccess (200) {String} message 信息
	 * @apiSuccess (200) {String} code 1001成功，0001错误
	 * @apiSuccess (200) {Object} data 返回的数据
	 * @apiSuccess (200) {Object} data.flage 0是未关闭 ；1是关闭
	 */
	@RequestMapping("remove")
	public Msg remove(ShopLabelClassifyInfo t) throws MyException {
		return service.remove(t);
	}
	
	/**
	 * 
	 * @throws MyException
	 * @apiGroup open
	 * @api {post} api/admin/shopLabelClassifyInfo/findById 查询标签分类详情
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
	 * @api {post} api/admin/shopLabelClassifyInfo/findAll 查询所有标签
	 * @param  String page 页数
	 * @param  String size 页面大小
	 * @param  String map.labelClassifyId fen分类id
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
