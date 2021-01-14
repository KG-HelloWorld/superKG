package com.brandWall.controller.admin.user;

import com.brandWall.controller.BaseController;
import com.brandWall.user.service.FwUserService;
import com.brandWall.util.Msg;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("api/admin/fwUser")
public class FwUserController extends BaseController{

    @Resource
    private FwUserService userService;

    /**
     *
     * @throws Exception
     * @apiGroup admin
     * @api {post} api/admin/user/adminFindAll 总后台获取所有用户信息
     * @param  String map.startTime 开始时间
     * @param  String map.endTime 结束时间
     * @param  String map.usUsername 用户名
     * @param  String map.usPhone 手机号
     * @param  String page 页码
     * @param  String size 页面大小
     * @apiSuccess (200) {String} message 信息
     * @apiSuccess (200) {String} code 1001成功，0001错误
     * @apiSuccess (200) {Object} data 返回的数据
     */
    @RequestMapping("adminFindAll")
    public Msg adminFindAll(Pageable pageable, HttpServletRequest request) throws Exception{
        Map<String, Object> map = getMapToRequest(request);
        return userService.adminFindAll(pageable, map);
    }


}
