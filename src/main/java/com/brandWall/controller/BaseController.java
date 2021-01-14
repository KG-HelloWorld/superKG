package com.brandWall.controller;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RestController;

import com.brandWall.shop.jpa.FwSsShopJpa;
import com.brandWall.shop.model.FwSsShop;
import com.brandWall.user.jpa.FwTokenJpa;
import com.brandWall.user.jpa.FwUserJpa;
import com.brandWall.user.model.FwToken;
import com.brandWall.user.model.FwUser;
import com.brandWall.util.Config;
import com.brandWall.util.ConstantMsg;
import com.brandWall.util.Msg;
import com.brandWall.util.ValidateUtil;
import com.brandWall.util.exception.MyException;

@RestController
public class BaseController {

	@Inject
	private HttpServletRequest request;

	@Inject
	private FwSsShopJpa fwSsShopJpa;

	@Inject
	private FwUserJpa fwUserJpa;

	@Inject
	private FwTokenJpa fwTokenJpa;

	public String getShopId() throws MyException {
		return getShop(false).getSsId();
	}

	public String findShopIdByCode(Integer code) throws MyException {
		// FwSsShop shop = fwSsShopJpa.findBySsCode(code);
		// if(null == shop){
		// throw new MyException("店铺不存在");
		// }
		// return shop.getSsId();
		return null;
	}

	public FwSsShop getShop(boolean needVer) throws MyException {
		FwUser user = null;

		user = getUser();

		if (user == null) {
			throw new MyException("0000", "未登录");
		}
		// if (user.getUsType() != 502) {
		// return null;
		// } else {//商家类型
		FwSsShop shop = fwSsShopJpa.findByUsIdAndSsRemove(user.getUsId(),0);
		if (shop == null) {
			throw new MyException(Msg.MsgSuccess("not extsis"));
		}
		// if (needVer && shop.getSsType() != 3) {
		// String tips[] = new String[] { "您未认证店铺", "您的店铺正在认证中", "您的店铺认证不通过" };
		// throw new MyException(tips[shop.getSsType()] + "，不能进行此操作");
		// }
		return shop;
		// }
	}

	/**
	 * 查找用户ID可为空
	 * 
	 * @return
	 * @throws MyException
	 */
	public String getUserIdNull() throws MyException {
		String token = getToken();
		if (!ValidateUtil.isValid(token)) {
			return "";
		}

		FwToken fwToken = fwTokenJpa.findByTkTokenContentAndTkUsSsId(token, getShopId());
		if (fwToken == null) {
			return "";
		}
		FwUser user = fwUserJpa.findOne(fwToken.getTkUsId());
		return user.getUsId();
	}

	public FwUser getUser() throws MyException {
		String token = getToken();
		if (token == null || token.equals("")) {
			throw new MyException("0000", "Token 参数为空");
		}

		FwToken fwToken = fwTokenJpa.findByTkTokenContent(token);
		if (fwToken == null) {
			throw new MyException("0000", "token 不存在或者已更新");
		}

		if (fwToken.getTkExpirationTime().before(new Date())) {
			throw new MyException("0000", "token 过期");
		}

		return fwUserJpa.findOne(fwToken.getTkUsId());
	}

	/**
	 * 获取用户传入的token
	 * 
	 * @return
	 */
	public String getToken() {
		return request.getParameter("token");
	}

	public String getRemake() {

		if (Config.isReal.equals("true")) {// 正式版本用这里
			String string = request.getHeader("remark");
			if (!ValidateUtil.isValid(request.getHeader("remark"))) {
				return request.getParameter("remark");
			}
			return request.getHeader("remark");

		} else {
			// 测试版本用这个

			// return "dstfi6sjucb164caee3853";
			// return "dsigxjfcmzb165cb71784b";
			// return "dstbwiscdwv163b0a1c72a";
			// return "dsi4lbmderu1649203c280";
			// return "dsTBwIScDWV163B0A1C72A";//直营店
			// return "dsNfOtBtWYK1612CD02976";//协望

			// return "dsnfotbtwyk1612cd02976";//协望
			return "all";// 栋
		}
	}

	/**
	 * 获取用户id
	 * 
	 * @return
	 */
	public String getUserId() throws MyException {
		FwUser fu = this.getUser();
		if (null == fu) {
			throw new MyException(ConstantMsg.MSG_NOLOGIN, "用户不存在");
		}
		return fu.getUsId();
	}

	public String getCompanyId() throws MyException {
		FwUser fu = this.getUser();
		if (null == fu) {
			throw new MyException(ConstantMsg.MSG_NOLOGIN, "用户不存在");
		}
		return fu.getCompanyId();
	}

	/**
	 * 获取用户id
	 */
	public String getOpenUserId() throws MyException {
		String token = getToken();
		if (token == null || token.equals("")) {
			return null;
		}

		FwToken fwToken = fwTokenJpa.findByTkTokenContentAndTkUsSsId(token, getShopId());
		if (fwToken == null) {
			return null;
		}

		if (fwToken.getTkExpirationTime().before(new Date())) {
			return null;
		}

		FwUser fu = fwUserJpa.findOne(fwToken.getTkUsId());
		if (null == fu) {
			return null;
		}
		return fu.getUsId();
	}

	public Map<String, Object> getMapToRequest(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String key = (String) params.nextElement();
			if (key.contains("map")) {
				Object obj = request.getParameter(key);
				if (key.contains("int_")) {
					obj = Integer.valueOf(obj.toString());
				} else {
					obj = String.valueOf(obj);
				}
				map.put(key.split("map.")[1], obj);
			}
		}
		return map;
	}

	public Map<String, Object> getOtherMapToRequest(Map<String, Object> map, HttpServletRequest request) {
		if (null == map) {
			map = new HashMap<>();
		}
		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String key = (String) params.nextElement();
			if (key.contains("other")) {
				Object obj = request.getParameter(key);
				map.put(key, obj);
			}
		}
		return map;
	}

	public Map<String, Object> getSort(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String key = (String) params.nextElement();
			if (key.contains("sort.") || key.contains("sort_")) {
				Object obj = request.getParameter(key);
				map.put(key.split("sort.")[1], obj);
			}
		}
		return map;
	}
	
	/**
	 * 不带map
	 */
	public Map<String, Object> getRequest(HttpServletRequest request,Class<?> object) {
		Map<String, Object> map = new HashMap<>();
		Enumeration<String> params = request.getParameterNames();
		// 获取实体类的所有属性
		Field[] fields  =  object.getDeclaredFields();
		while (params.hasMoreElements()) {
			String key = (String) params.nextElement();						
			   for (Field field : fields) {//
					if (key!=null) {
						String name = field.getName();
						if(key.equals(field.getName())){
							Object obj = request.getParameter(key);
							if (key.contains("int_")) {
								obj = Integer.valueOf(obj.toString());
							} else {
								obj = String.valueOf(obj);
							}
							map.put(key, obj);
						}
					}				   
				}						
		}
		return map;
	}
	
}
