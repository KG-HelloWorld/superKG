package com.brandWall.controller.open;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.brandWall.controller.BaseController;
import com.brandWall.shop.jpa.FwSsShopJpa;
import com.brandWall.shop.model.FwSsShop;
import com.brandWall.user.jpa.FwUserJpa;
import com.brandWall.user.jpa.FwUserOtherJpa;
import com.brandWall.user.model.FwUser;
import com.brandWall.user.model.FwUserOther;
import com.brandWall.user.service.FwUserService;
import com.brandWall.util.CayUtil;
import com.brandWall.util.Config;
import com.brandWall.util.FileUtil;
import com.brandWall.util.HttpClientUtil;
import com.brandWall.util.Msg;
import com.brandWall.util.QrCode;
import com.brandWall.util.ValidateUtil;
import com.brandWall.util.exception.MyException;

import net.sf.json.JSONObject;

/**
 * @ClassName: OpenCommonController
 * @Function: (注册、登陆、发验证码、验证验证码,忘记密码,第三方登陆)
 * @Description: api/open 是开放的接口,不需要token验证
 * @date: 2016年10月20日 下午5:29:46
 * 
 * @author only_U
 * @version
 * @since JDK 1.8
 */
@Scope(value = "prototype")
@RestController
@RequestMapping("api/open/common")
public class OpenCommonController extends BaseController {

	
	@Inject
	private FwUserService fwUserService;
	
	@Inject
	private FwUserJpa userJpa;
	
	@Inject
	private FwUserOtherJpa userOtherJpa;// 用户第三方


	@Inject
	private FwSsShopJpa shopJpa;
	

	@RequestMapping("findShop")
	public Msg findShop() throws MyException {
		FwSsShop shop = this.getShop(false);
		// shop.setSsWXUrl("http://" + shop.getSsWXUrl() + "/singleWechat/" +
		// shop.getSsId() + "/index.html");
		return Msg.MsgSuccess(shop);
	}

	// // 查找用户其它预设字段
	// @RequestMapping("findUserParam")
	// public Msg findUserParam() throws Exception {
	// return baseService.findUserParam(getShopId());
	// }

	/**
	 * 按照用户Id查找用户信息
	 * 
	 * @author only_U
	 * @date:2017年3月22日 下午9:43:21
	 * @param id
	 * @return
	 */
	@RequestMapping("findUser/{id}")
	public Object findUser(@PathVariable String id) {
		Map<String, Object> map = new HashMap<>();
		FwUser user = userJpa.findOne(id);
		
		map.put("user", user);

		return Msg.MsgSuccess(map);
	}


	/**
	 * 登录
	 * 
	 * @author only_U
	 * @date:2016年10月20日 下午5:20:15
	 * @param loginName
	 *            登录名
	 * @param password
	 *            密码
	 * @param uType
	 *            用户类型(0==>会员,2==>后台管理员,3==>超级管理员)
	 * @param loginType
	 *            登录类型(0==>phone,3==>username)
	 * @return
	 * 
	 */
	@RequestMapping(value = "login")
	public Object login(String loginName, String password, Integer uType, Integer loginType, HttpServletRequest request,
			String type) throws MyException {
		return fwUserService.login(loginName, password, null, uType, loginType, request, type);
	}

	

	/**
	 * 注册
	 * 
	 * @author only_U
	 * @date:2016年10月20日 下午5:20:15
	 * @param loginName
	 *            登录名
	 * @param password
	 *            密码
	 * @param uType
	 *            用户类型(500==>会员,502==>后台管理员,503==>超级管理员)
	 * @param loginType
	 *            登录类型(0==>phone,3==>username)
	 * @return
	 * 
	 */
	@RequestMapping("register")
	public Object register(String loginName, Integer uType, String password, String code, Integer loginType,
			String parentId) throws Exception {
		return fwUserService.register(loginName, password, null, code, uType, loginType, parentId);
	}

	/**
	 * 
	 * @apiGroup open
	 * @api {post} api/open/common/bindingPhone 手机号绑定
	 * @apiParam {Stirng} phone 手机号
	 * @apiPara {String } code 验证码
	 * @apiPara {String } pwd 密码
	 * @apiPara {int} appFlage 1是APP
	 * @apiSuccess (200) {String} message 信息
	 * @apiSuccess (200) {String} code 1001成功，0001错误
	 * @apiSuccess (200) {Object} data 返回的数据
	 */
	@RequestMapping("bindingPhone")
	public Msg bindingPhone(String phone, String code, String pwd, Integer appFlage) throws Exception {
		Integer uType = 500;
		return fwUserService.bindingPhone(getUserId(), phone, code, uType, pwd, appFlage);
	}

	/**
	 * 第三方登陆
	 * 
	 * @author only_U
	 * @date:2016年10月20日 下午5:18:54
	 * @param openId
	 *            第三方登陆令牌
	 * @param userId
	 *            支付宝用户id
	 * @param loginType
	 *            登陆类型(QQ==>1,微信==>2 4 ==> 支付宝)
	 * @param uType
	 *            用户类型(会员==>0)
	 * @param nikeName
	 *            第三方账号昵称
	 * @param pic
	 *            第三方账号头像
	 * @param account
	 *            第三方账号
	 * @return
	 * 
	 */
	@RequestMapping("otherLogin")
	public Object otherLogin(String openId, String userId, Integer loginType, Integer uType, String nikeName,
			String pic, String account, String parentId, String gender, String phone) throws Exception {

		return fwUserService.updateOtherLogin(account, userId, openId, loginType, uType, nikeName, pic, null, parentId,
				gender, phone);
	}

	@RequestMapping("phone-login")
	public Object phoneLogin(HttpServletRequest request, String phone, String code) throws MyException, Exception {
		return fwUserService.phoneLogin(phone, code, getShopId(), request);
	}

	/**
	 * @apiGroup open
	 * @api {get} /api/open/common/getMinAppOpenid 小程序第三方登录-获取openid
	 * @apiParam {String} code 授权code
	 * @apiSuccess (200) {String} message 信息
	 * @apiSuccess (200) {String} code 1001成功，0001错误
	 * @apiSuccess (200) {Object} data 返回的数据
	 */
	@RequestMapping("getMinAppOpenid")
	public Object getopenid(String code) throws MyException {
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + Config.minappAppid + "&secret="
				+ Config.minappSecret + "&grant_type=authorization_code&js_code=" + code;
		String text = HttpClientUtil.requestByGetMethod(url);
		JSONObject jsonObject = JSONObject.fromObject(text);
		try {
			if (jsonObject != null) {
				System.out.println("-------测试--------");
				System.out.println(text);
				List<FwUserOther> userotherList = userOtherJpa.findByUoOpenId(jsonObject.getString("openid"));
				if (userotherList.size() > 0) {
					if (jsonObject.get("unionid") != null) {
						FwUserOther other = userotherList.get(0);
						if (!ValidateUtil.isValid(other.getUoUnionId())) {
							System.out.println("进来了");
							other.setUoUnionId(jsonObject.getString("unionid"));
							userOtherJpa.save(other);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Msg.MsgSuccess(jsonObject.getString("openid"));
	}

	

	

	/**
	 * 上传图片到临时文件夹下
	 * 
	 * @author only_U
	 * @date:2016年10月25日 上午9:32:50
	 * @param request
	 *            input-->name-->imgFile
	 * @return
	 * 
	 */
	@RequestMapping("uploadImageTemp")
	public Object uploadImageTemp(MultipartHttpServletRequest request) throws MyException {
		MultipartFile file = request.getFile("imgFile");
		return Msg.MsgSuccess(FileUtil.uploadImageTemp(file, CayUtil.getFolderByDate()));
	}

	/**
	 * 上传图片到临时文件夹下
	 * 
	 * @author only_U
	 * @date:2016年10月25日 上午9:32:50
	 * @param request
	 *            input-->name-->imgFile
	 * @return
	 * 
	 */
	@RequestMapping("uploadImageTempSaaS")
	public Object uploadImageTempSaaS(MultipartHttpServletRequest request) throws MyException {
		MultipartFile file = request.getFile("imgFile");
		return Msg.MsgSuccess(FileUtil.uploadImageTemp(file, "admin1"));
	}

	@RequestMapping("uploadImageFile/{cer}/{name}")
	public Object uploadImageFile(MultipartHttpServletRequest request, @PathVariable("cer") String cer,
			@PathVariable("name") String name) throws MyException {
		MultipartFile file = request.getFile("file");
		return Msg.MsgSuccess(FileUtil.uploadFile(file, getShopId() + "//" + cer, name));
	}

	@RequestMapping("uploadGaveFile")
	public Object uploadGaveFile(MultipartHttpServletRequest request) {
		MultipartFile file = request.getFile("file");
		return Msg.MsgSuccess(FileUtil.uploadFile(file));
	}

	/**
	 * @apiGroup open
	 * @api {get} api/open/common/getHtml 获取Html内容
	 * @apiParam {String} url html地址
	 * @apiSuccess (200) {String} message 信息
	 * @apiSuccess (200) {Object} data 1001成功，0001错误
	 */
	@RequestMapping("getHtml")
	public Object getHtml(String url) {
		return Msg.MsgSuccess(FileUtil.getHtml(url));
	}

	

	@RequestMapping("qrCode")
	public Object qrCode(String url) throws MyException {
		String dz = QrCode.qrCode(this.getShopId() + "//" + CayUtil.getFolderByDate(), url);
		return Msg.MsgSuccess(dz);
	}

	/**
	 * @apiGroup common
	 * @api {post} api/open/common/addVisitNum 添加店铺访问量
	 * @apiSuccess (200) {String} message 信息
	 * @apiSuccess (200) {String} code 1001成功，0001错误
	 * @apiSuccess (200) {Object} data 返回的数据
	 */
	@RequestMapping("/addVisitNum")
	public Object addVisitNum() throws MyException {
		FwSsShop shop = shopJpa.findOne(getShopId());
		if (shop != null) {
			// shop.setSsVisitNum(shop.getSsVisitNum() + 1);
			shopJpa.save(shop);
		}
		return Msg.MsgSuccess(shop);
	}

	/**
	 * @apiGroup common
	 * @api {post} api/open/common/getHtmlByUrl 根据Url获取页面信息
	 * @apiParam {String} url 请求地址
	 * @apiSuccess (200) {String} message 信息
	 * @apiSuccess (200) {String} code 1001成功，0001错误
	 * @apiSuccess (200) {Object} data 返回的数据
	 */
	@RequestMapping("/getHtmlByUrl")
	public Object getHtmlByUrl(String url) {
		String html = HttpClientUtil.getHtml(url);
		System.out.println(html);
		return Msg.MsgSuccess(html);
	}

	

}
