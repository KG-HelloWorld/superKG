package com.brandWall.controller.open;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brandWall.controller.BaseController;
import com.brandWall.shop.service.SsBannerService;
import com.brandWall.util.exception.MyException;

@RestController("OpenDataController")
@RequestMapping("api/open/data")
public class OpenDataController extends BaseController{

	
	@Inject
	private SsBannerService ssBannerService;
	/**
	 * @apiGroup banner
	 * @api {post} api/open/data/findByAll 获取banner列表
	 * @apiParam {String} size 显示条数
	 * @apiParam {String} page 页数
	 * @apiSuccess (200) {String} message 信息
	 * @apiSuccess (200) {String} code 1001成功，0001错误
	 * @apiSuccess (200) {Object} data 返回的数据
	 */
	@RequestMapping("findByAll")
	public Object findByAll(Pageable pageable,Integer status,HttpServletRequest request) throws MyException {
		Map<String, Object> map = getMapToRequest(request);
		return ssBannerService.findAll(null, pageable,1,null,status,map);
	}
}
