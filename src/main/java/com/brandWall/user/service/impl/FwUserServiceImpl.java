package com.brandWall.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brandWall.BaseServiceImpl;
import com.brandWall.shop.jpa.FwSsShopJpa;
import com.brandWall.user.jpa.FwTokenJpa;
import com.brandWall.user.jpa.FwUserJpa;
import com.brandWall.user.jpa.FwUserOtherJpa;
import com.brandWall.user.model.FwToken;
import com.brandWall.user.model.FwUser;
import com.brandWall.user.model.FwUserOther;
import com.brandWall.user.service.FwTokenService;
import com.brandWall.user.service.FwUserService;
import com.brandWall.util.FileUtil;
import com.brandWall.util.LoginType;
import com.brandWall.util.MD5Util;
import com.brandWall.util.Msg;
import com.brandWall.util.QueryHelper;
import com.brandWall.util.ValidateUtil;
import com.brandWall.util.exception.MyException;

@Service
public class FwUserServiceImpl extends BaseServiceImpl<FwUser> implements FwUserService {
	@Inject
	private FwUserJpa fwUserJpa;
	@Inject
	private FwTokenJpa fwTokenJpa;

	@Inject
	private FwUserOtherJpa fwUserOtherJpa;
	

	@Inject
	FwSsShopJpa fwSsShopJpa;
	
	

	@Inject
	private FwTokenService fwTokenService;

	@Override
	public Msg add(FwUser t) throws MyException {

		if (!ValidateUtil.isValid(t.getUsPhone())) {
			return Msg.MsgError("手机号码为空");
		}

		if (!ValidateUtil.isValid(t.getUsPassword())) {
			return Msg.MsgError("密码为空");
		}

		FwUser user = fwUserJpa.findByUsPhone(t.getUsPhone());
		if (user != null) {
			return Msg.MsgError("该手机号码已存在");
		}

		if (!ValidateUtil.isValid(t.getUsId())) {
			t.setCreateTime(new Date());
		}

		if (ValidateUtil.isValid(t.getUsPic())) {
			t.setUsPic(FileUtil.moveAbsoluteToFile(t.getUsPic()));
		}

		if (ValidateUtil.isValid(t.getUsPassword())) {
			t.setUsPassword(MD5Util.md5(t.getUsPassword()));
		}
		super.add(t);
		String usId = t.getUsId();

		
		return Msg.MsgSuccess();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object findPage(Map<String, Object> map, Map<String, Object> sortMap, Pageable pageable) throws Exception {
		QueryHelper queryHelper = new QueryHelper("select new map(fu as user,m.turnoverAmt as turnoverAmt)",
				FwUser.class, "fu");
		queryHelper.addJoin("FwUserMember", "m");
		queryHelper.addCondition("fu.usId=m.userId");
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getKey().contains("order")) {
				queryHelper.addCondition(
						"fu.usId in (select from FwUserParam " + "where cpbId='" + entry.getKey().replace("other.", "")
								+ "' " + "and upValue like '%" + entry.getValue() + "%')");
				map.remove(entry.getKey());
			}
		}
		queryHelper.addOrderProperty("fu.createTime", false);
		Page<?> page = this.findAll(queryHelper, pageable, map);
		List<Map<String, Object>> list = (List<Map<String, Object>>) page.getContent();
		for (Map<String, Object> mp : list) {
			FwUser user = (FwUser) mp.get("user");

			int num = this.jpa.countRecord("select count(*) from FwUserVipCard where uvcUsId='" + user.getUsId() + "'",
					null);
			int vipFlage = 0;
			if (num > 0) {
				vipFlage = 1;
			}
			mp.put("vipFlage", vipFlage);
		}
		return Msg.MsgSuccess(page);
	}

	@Override
	public Msg findPage(String shopId, Pageable pageable) throws MyException {
		QueryHelper queryHelper = new QueryHelper("select new map(fu as user,m.turnoverAmt as turnoverAmt)",
				FwUser.class, "fu");
		queryHelper.addJoin("FwUserMember", "m");
		queryHelper.addCondition("fu.usId=m.userId");
		if (null != shopId && !("").equals(shopId))
			queryHelper.addCondition("fu.usSsId=?0", shopId);
		return Msg.MsgSuccess(this.findAll(queryHelper, pageable));
	}

	@Override
	public FwUser findByUsPhoneAndUTypeAndFwIsRemoveByPassword(String usPhone, String password, Integer uType,
			Integer isRemove, String shopId) throws MyException {
		if (password == null || password.equals("")) {
			throw new MyException("0002", "参数为null");
		}
		FwUser u = findByUsPhoneAndUTypeAndFwIsRemove(usPhone, uType, isRemove, shopId);
		if (u == null) {
			throw new MyException("0012", "用户不存在");
		}
		if (!u.getUsPassword().equals(MD5Util.md5(password))) {
			throw new MyException("0003", "密码错误");
		}
		return u;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Object addUser(FwUser fwUser, Integer loginType, FwUserOther fwUserOther, String parentId)
			throws MyException {
			
		Map<String, Object> map = new HashMap<String, Object>();
		if (LoginType.isOtherLogin(loginType)) {
			try {
				fwUser.setCreateTime(new Date());
				fwUser.setUsPassword(MD5Util.md5(fwUser.getUsPassword()));
				fwUserJpa.save(fwUser);// 添加用户信息
				fwUserOther.setUoFuId(fwUser.getUsId());
				fwUserOtherJpa.save(fwUserOther);// 添加用户其他信息
			} catch (DataIntegrityViolationException e) {
				// System.out.println("error->save user other catch message: " +
				// e.getMessage());
				fwUserOther = fwUserOtherJpa.findByUoOpenIdAndUoSsIdAndUoType(fwUserOther.getUoOpenId(),
						fwUserOther.getUoSsId(), loginType);
				fwUser = fwUserJpa.findOne(fwUserOther.getUoFuId());
				List<FwToken> tks = fwTokenJpa.findByTkUsId(fwUser.getUsId());
				FwToken tk = null;
				if (!ValidateUtil.isValid(tks)) {
					tk = new FwToken();
					tk = FwToken.getTokenByUser(fwUser);
					fwTokenJpa.save(tk);// 添加用户token
				} else {
					tk = tks.get(0);
				}
				map.put("token", tk.getTkTokenContent());
				return Msg.MsgSuccess(map);
			}
		} else if (LoginType.isDefaultLogin(loginType)) {
			fwUser.setCreateTime(new Date());
			fwUser.setUsPassword(MD5Util.md5(fwUser.getUsPassword()));
			fwUserJpa.save(fwUser);// 添加用户信息
		} else {
			throw new MyException("0009", "没有该登录方式");
		}
		
		if (null != fwUserOther) {
			fwUserOther.setUoFuId(fwUser.getUsId());
			fwUserOtherJpa.save(fwUserOther);// 添加用户其他信息
		}

		

		FwToken tk = FwToken.getTokenByUser(fwUser);
		fwTokenJpa.save(tk);// 添加用户token

		

		map.put("token", tk.getTkTokenContent());

		
		return Msg.MsgSuccess(map);
	}

	@Override
	public FwUser findByQqId(String prove, Integer uType) {
		return null;
	}

	@Override
	public FwUser findByWechatId(String prove, Integer uType) {

		return null;
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Object login(String loginName, String password, String shopId, Integer uType, Integer loginType,
			HttpServletRequest request, String type) throws MyException {

		Map<String, Object> map = new HashMap<String, Object>();
		FwUser user = null;
		if (loginType == LoginType.PHONE.getCode()) {
			user=fwUserJpa.findByUsPhone(loginName);
			if(user==null){
				return Msg.MsgError("用户不存在");
			}
			if (!user.getUsPassword().equals(MD5Util.md5(password))) {
				return Msg.MsgError("密码错误");
			}
//			user = this.findByUsPhoneAndUTypeAndFwIsRemoveByPassword(loginName, password, uType, 0, shopId);
		} else if (loginType == LoginType.USERNAME.getCode()) {
			user = this.findByUsUsernameAndUsSsIdAndFwIsRemoveAndUsTypeByPassword(loginName, shopId, 0, password,
					uType);
		} else {						
			//后台管理员登录
			user = fwUserJpa.findByUsPhone(loginName);
			if(user!=null){
				if (!user.getUsPassword().equals(MD5Util.md5(password))) {
					throw new MyException("0003", "密码错误");
				}
				if(user.getUsType()!=503&&user.getUsType()!=502&&user.getUsType()!=501){
					return Msg.MsgError("您还不是管理员");
				}
			}
//			throw new MyException("0009", "没有该登陆方式 loginType错误");
		}

		if (user == null) {
			return Msg.MsgError("用户不存在");
		} else {
			if (user.getUsState() != 0) {
				return Msg.MsgError("账号被冻结，请联系管理员");
			}
		}

		if (uType == 2 || uType == 3) {// 后台管理员

			List<String> powers = null;
			if (user.getUsType() == 2) {
				user.setSuper(true);
			} 
//			else {
				// 查看权限

//				List<String> pids = (List<String>) this.jpa
				//if (ValidateUtil.isValid(pids)) {
//					powers = (List<String>) this.jpa
//							.select("select new map(upState as upState) from FwUserPower where upId in('"
//									+ pids.get(0).replaceAll(",", "','") + "')");
//				}
//			}
			map.put("powers", powers);
		}
		map.put("user", user);

		user.setUsLoginTime(new Date());
		fwUserJpa.save(user);

		FwToken tk = fwTokenService.updateToken(user.getUsId(), shopId, 1);
		map.put("token", tk.getTkTokenContent());

		return Msg.MsgSuccess(map);
	}

	// @Override
	// public Object loginBySaas(String remake) throws Exception {
	// Map<String, Object> map = new HashMap<String, Object>();
	// FwSsShop shop = fwSsShopJpa.findBySsRemake(remake);
	// if (shop == null) return Msg.MsgError("店铺不存在");
	// FwUser user = fwUserJpa.findByUsSsId(shop.getSsId());
	// if (user == null) {
	// return Msg.MsgError("用户不存在");
	// } else {
	// if (user.getUsState() != 0) {
	// return Msg.MsgError("账号被冻结，请联系管理员");
	// }
	// }
	// if (user.getUsType() == 2 || user.getUsType() == 3) {// 后台管理员
	// map.put("prots", this.jpa.select("from FwSsShopAuth where ssId='" +
	// shop.getSsId() + "'"));
	// map.put("functions", this.jpa.select("from FwSsShopFunction where ssId='"
	// + shop.getSsId() + "'"));
	// }
	// user.setUsLoginTime(new Date());
	// fwUserJpa.save(user);
	// FwToken tk = fwTokenService.updateToken(user.getUsId());
	// map.put("token", AesUtil.encrypt(tk.getTkTokenContent(), remake));
	// return Msg.MsgSuccess(map);
	// }

	@Override
	public FwUser findByUsPhoneAndUTypeAndFwIsRemove(String usPhone, Integer uType, Integer isRemove, String shopId)
			throws MyException {
		if (uType == null || usPhone == null || usPhone.equals("") || uType.equals("")) {
			throw new MyException("0002", "参数为null");
		}
		FwUser u = fwUserJpa.findByUsPhoneAndUsTypeAndFwIsRemove(usPhone, uType, isRemove);

		return u;
	}

	@Override
	@Transactional
	public Object register(String loginName, String password, String shopId, String code, Integer uType,
			Integer loginType, String parentId) throws MyException {
		FwUser fwUser = null;
		if (null == loginType || loginType == LoginType.PHONE.getCode()) {
			fwUser = this.findByUsPhoneAndUTypeAndFwIsRemove(loginName, uType, 0, shopId);
		}
		if (loginType == LoginType.USERNAME.getCode()) {
			fwUser = this.findByUsUsernameAndUsSsIdAndFwIsRemoveAndUsType(loginName, shopId, 0, uType);
		}
		if (fwUser != null) {
			throw new MyException("0005", "用户已存在");
		} else {
			if (null == loginType || loginType == LoginType.PHONE.getCode()) {
				
			} else if (loginType == LoginType.USERNAME.getCode()) {

			} else {
				throw new MyException("0009", "没有该注册方式");
			}

			fwUser = FwUser.getRegisterUser(loginName, password, uType, shopId, loginType);

			return this.addUser(fwUser, loginType, null, parentId);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public Object updateOtherLogin(String account, String ailiUser, String openId, Integer loginType, Integer uType,
			String nikeName, String pic, String shopId, String parentId, String gender, String phone) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		synchronized (this) {
			try {

				if (loginType == null || loginType.equals("")) {
					throw new MyException("0002", "参数为null");
				}
				if (!LoginType.isOtherLogin(loginType)) {
					throw new MyException("0009", "没有该登陆方式");
				}
				FwUserOther fwUserOther = null;
				if (loginType != 4) {
					fwUserOther = fwUserOtherJpa.findByUoOpenIdAndUoType(openId, loginType);
				} else {
					// 获取支付宝
					fwUserOther = fwUserOtherJpa.findByUoAliUserIdAndUoSsIdAndUoType(ailiUser, shopId, loginType);
				}
				if (fwUserOther == null) {
					// 下载支付宝图片
					if (loginType == 4 && ValidateUtil.isValid(pic)) {

						// pic = FileUtil.loadImageUrl(pic);

					} else if (ValidateUtil.isValid(pic)) {
						// pic = FileUtil.dowloadWechatNet(shopId, pic,
						// "png");// 获取网路头像并保存到临时文件
						// pic = FileUtil.uploadImageTemp(pic, shopId);
						pic = FileUtil.moveAbsoluteToFile(pic);// 把临时文件夹的头像放入到正式文件夹中
					}
					FwUser user = FwUser.getTheThirdRegister(openId, nikeName, uType, shopId, pic);
					user.setUsPhone(phone);// 设置电话号码
					if (ValidateUtil.isValid(gender)) {
						try {
							user.setUsGender(Integer.valueOf(gender));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					fwUserJpa.save(user);
					/*
					 * if(loginType==2){//获取用户信息 String
					 * accessToken=templateMsgService.sendAccessTokenrRequest(
					 * shopId); Map<String, String>
					 * userInfos=templateMsgService.sendUserInfoRequest(
					 * accessToken, openId);
					 * System.out.println(Integer.valueOf(userInfos.get("sex"))+
					 * "..............................");
					 * user.setUsGender(Integer.valueOf(userInfos.get("sex")));
					 * }
					 */

					fwUserOther = new FwUserOther(shopId, user.getUsId(), openId, loginType, account, ailiUser);
					Object object = addUser(user, loginType, fwUserOther, parentId);
					// templateMsgService.sendNewMemberMsgTemplate(shopId,
					// user.getUsId(), account);

					map = (Map<String, Object>) ((Msg) object).getData();

					map.put("usId", user.getUsId());
					
					return object;
				} else {
					FwToken fwToken = fwTokenService.updateToken(fwUserOther.getUoFuId(), shopId, 1);
					map.put("token", fwToken.getTkTokenContent());
					map.put("usId", fwToken.getTkUsId());
					
					FwUser user = this.fwUserJpa.findOne(fwToken.getTkUsId());
					/*
					 * if(loginType==2){//获取用户信息 String
					 * accessToken=templateMsgService.sendAccessTokenrMinRequest
					 * (shopId); Map<String, String>
					 * userInfos=templateMsgService.sendUserInfoRequest(
					 * accessToken, openId);
					 * System.out.println(Integer.valueOf(userInfos.get("sex"))+
					 * "..............................");
					 * user.setUsGender(Integer.valueOf(userInfos.get("sex")));
					 * }
					 */
					if (ValidateUtil.isValid(gender)) {
						try {
							user.setUsGender(Integer.valueOf(gender));
							this.fwUserJpa.save(user);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}
			} catch (Exception e) {

			}
		}
		return Msg.MsgSuccess(map);
	}

	@Override
	public FwUser findByUsUsernameAndUsSsIdAndFwIsRemoveAndUsType(String userName, String shopId, Integer isremove,
			Integer uType) throws MyException {
		if (userName == null || userName.equals("") || uType == null || uType.equals("")) {
			throw new MyException("0002", "参数为null");
		}
		List<FwUser> userList = fwUserJpa.findByUsUsernameAndFwIsRemoveAndUsType(userName, isremove, uType);
		if (userList.size() > 1) {
			throw new MyException("1011", "用户重复");
		}
		if (userList.size() == 0) {
			return null;
		} else {
			return userList.get(0);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public FwUser findByUsUsernameAndUsSsIdAndFwIsRemoveAndUsTypeByPassword(String userName, String shopId,
			int isremove, String password, Integer uType) throws MyException {
		if (password == null || password.equals("")) {
			throw new MyException("0002", "参数为null");
		}
		FwUser fwUser = null;
		if (uType == 2) {
			List<FwUser> fwUsers = (List<FwUser>) this.jpa.select("from FwUser where usSsId='" + shopId
					+ "' and usUsername ='" + userName + "' and fwIsRemove=0 and usType in (2,3)");
			if (!ValidateUtil.isValid(fwUsers)) {
				throw new MyException("0004", "用户名不存在");
			}
			fwUser = fwUsers.get(0);
		} else {
			fwUser = this.findByUsUsernameAndUsSsIdAndFwIsRemoveAndUsType(userName, shopId, isremove, uType);
		}

		if (null == fwUser) {
			throw new MyException("0004", "用户名不存在");
		}
		if (!fwUser.getUsPassword().equals(MD5Util.md5(password))) {
			throw new MyException("0003", "登陆密码错误");
		}
		return fwUser;
	}

	@Override
	public Object loginBySaas(String remake) throws Exception {
		return null;
	}

	/**
	 * 统计用户信息
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public Msg countUser(String shopId) {
		Map<String, Object> countMap = new HashMap<>();
		Long lCount = null;
		Long lUserCount = null;
		try {
			// 统计总用户量
			StringBuffer buffer = new StringBuffer("");
			buffer.append("select new map(count(usId) as countUser) from FwUser where usSsId='" + shopId + "'");
			List<?> list = this.find(buffer.toString());
			for (Object object : list) {
				Map<String, Object> map = (Map<String, Object>) object;
				lUserCount = (Long) map.get("countUser");
				countMap.put("userNum", lUserCount);
			}

			// 今天新增的用户量
			buffer.setLength(0);
			buffer.append("select new map(count(usId) as newUserToDay) from FwUser");
			buffer.append(" where to_days(createTime) = to_days(now()) and usSsId='" + shopId + "'");
			list = this.find(buffer.toString());
			for (Object object : list) {
				Map<String, Object> map = (Map<String, Object>) object;
				lCount = (Long) map.get("newUserToDay");
				countMap.put("newUserToDay", lCount);
			}

			// 这周新增的用户量
			buffer.setLength(0);
			buffer.append("select new map(count(usId) as newUserThisWeek) from FwUser");
			buffer.append(
					" where YEARWEEK(date_format(createTime,'%Y-%m-%d'))=YEARWEEK(now()) and usSsId='" + shopId + "'");
			list = this.find(buffer.toString());
			for (Object object : list) {
				Map<String, Object> map = (Map<String, Object>) object;
				lCount = (Long) map.get("newUserThisWeek");
				countMap.put("newUserThisWeek", lCount);
			}

			// 本月新增的用户量
			buffer.setLength(0);
			buffer.append("select new map(count(usId) as newUserThisMonth) from FwUser");
			buffer.append(" where date_format(createTime,'%Y-%m')=date_format(CURDATE(),'%Y-%m') and usSsId='" + shopId
					+ "'");
			list = this.find(buffer.toString());
			for (Object object : list) {
				Map<String, Object> map = (Map<String, Object>) object;
				lCount = (Long) map.get("newUserThisMonth");
				countMap.put("newUserThisMonth", lCount);
			}

			// 本季度新增的用户量
			buffer.setLength(0);
			buffer.append("select new map(count(usId) as newUserThisQuarter) from FwUser");
			buffer.append(" where quarter(createTime) = quarter(now()) and usSsId='" + shopId + "'");
			list = this.find(buffer.toString());
			for (Object object : list) {
				Map<String, Object> map = (Map<String, Object>) object;
				lCount = (Long) map.get("newUserThisQuarter");
				countMap.put("newUserThisQuarter", lCount);
			}

			// 用户性别分布
			buffer.setLength(0);
			buffer.append("SELECT SUM(CASE WHEN usGender=0 THEN 1 ELSE 0 END) as woman,");
			buffer.append("SUM(CASE WHEN usGender=1 THEN 1 ELSE 0 END) as man");
			buffer.append(" from FwUser where usSsId='" + shopId + "'");
			list = this.find(buffer.toString());
			for (Object object : list) {
				Object[] objects = (Object[]) object;
				countMap.put("woman", objects[0]);
				countMap.put("man", objects[1]);
			}

			// 用户年龄分布
			// String[] strYear = { "0to15", "15To18", "19To23", "23TO29",
			// "30TO40", "40TO50", "50above" };
			buffer.setLength(0);
			buffer.append("SELECT SUM(CASE WHEN (YEAR(NOW())-YEAR(us_birthday))<15 THEN 1 ELSE 0 END) '0-15',");
			buffer.append(
					"SUM(CASE WHEN (YEAR(NOW())-YEAR(us_birthday))>=15 AND (YEAR(NOW())-YEAR(us_birthday))<=18 THEN 1 ELSE 0 END) '15-18',");
			buffer.append(
					"SUM(CASE WHEN (YEAR(NOW())-YEAR(us_birthday))>=19 AND (YEAR(NOW())-YEAR(us_birthday))<=23 THEN 1 ELSE 0 END) '19-23',");
			buffer.append(
					"SUM(CASE WHEN (YEAR(NOW())-YEAR(us_birthday))>=23 AND (YEAR(NOW())-YEAR(us_birthday))<=29 THEN 1 ELSE 0 END) '23-29',");
			buffer.append(
					"SUM(CASE WHEN (YEAR(NOW())-YEAR(us_birthday))>=30 AND (YEAR(NOW())-YEAR(us_birthday))<=40 THEN 1 ELSE 0 END) '30-40',");
			buffer.append(
					"SUM(CASE WHEN (YEAR(NOW())-YEAR(us_birthday))>=40 AND (YEAR(NOW())-YEAR(us_birthday))<=50 THEN 1 ELSE 0 END) '40-50',");
			buffer.append("SUM(CASE WHEN (YEAR(NOW())-YEAR(us_birthday))>50 THEN 1 ELSE 0 END) '50above'");
			buffer.append(" FROM fw_user WHERE fw_user.us_birthday IS NOT NULL and us_ss_id='" + shopId + "'");
			list = this.jpa.selectSql(buffer.toString());
			// List<FwUserAgeGroupDto> lstUserDto = new ArrayList<>();
			// FwUserAgeGroupDto userDto = null;
			int num = 0;
			Map<String, Object> objMap = new HashMap<>();
			final String[] names = { "0-15", "15-18", "19-23", "23-29", "30-40", "40-50", "50above" };
			// for (Object object : list) {
			// Map<String, Object> map = (Map<String, Object>) object;
			// for (int i = 0; i < names.length; i++) {
			// userDto = new FwUserAgeGroupDto();
			// userDto.setAgeGroup(names[i]);
			// if (map.get(names[i]) != null) {
			// userDto.setAgeGroupNum(map.get(names[i]).toString());
			// num += Integer.valueOf(map.get(names[i]).toString());
			// } else {
			// userDto.setAgeGroupNum("0");
			// num += 0;
			// }
			// lstUserDto.add(userDto);
			// userDto = null;
			// }
			// }
			// userDto = new FwUserAgeGroupDto();
			// userDto.setAgeGroup("other");
			// userDto.setAgeGroupNum(String.valueOf((lUserCount.intValue() -
			// num)));
			// lstUserDto.add(userDto);
			// objMap.put("yearOldNum", lstUserDto);
			// objMap.put("yearOldTotalNum", lUserCount);
			// countMap.put("yearOld", objMap);

			// 活跃用户
			// buffer.setLength(0);
			// buffer.append("select count(us_id) as activeUser from fw_user ");
			// buffer.append("where DATE_SUB(CURDATE(),INTERVAL 7
			// DAY)<=DATE(us_login_time)");
			// list = this.jpa.selectSql(buffer.toString());
			// for (Object object : list) {
			// Map<String, Object> map = (Map<String, Object>) object;
			// countMap.put("activeUser", map.get("activeUser"));
			// }
			String[] colorData = { "#A1D568", "#ED5454", "#97DAE5", "#FBE3AB", "#D8EAB8", "#FAA486", "#ED87BF",
					"#cccccc" };
			countMap.put("colorData", colorData);
		} catch (MyException e) {
			e.printStackTrace();
		}
		return Msg.MsgSuccess(countMap);
	}

	// ------------------------------2018-04-18新增----------------------

	@Override
	public Msg countAgeAndSex(String shopId) throws MyException {
		Map<String, Object> resultMap = new HashMap<>();
		int ageLimit[][] = new int[][] { { 0, 15 }, { 16, 18 }, { 19, 23 }, { 24, 29 }, { 30, 40 }, { 41, 50 },
				{ 51, 100 }, { 0, 0 } };
		String[] color = { "#A1D568", "#ED5454", "#97DAE5", "#FBE3AB", "#D8EAB8", "#FAA486", "#ED87BF", "#cccccc" };
		List<Map<String, Object>> ageList = new ArrayList<>();
		String hql = "select count(*) from FwUser where fwIsRemove=0 and usSsId='" + shopId + "'";
		float totalNum = (float) jpa.countRecord(hql, null);
		int counted = 0;
		for (int i = 0; i < ageLimit.length; i++) {
			int temp = i != ageLimit.length - 1
					? jpa.countRecord(hql + " and (YEAR(NOW())-YEAR(us_birthday))>=" + ageLimit[i][0]
							+ " and (YEAR(NOW())-YEAR(us_birthday))<=" + ageLimit[i][1], null)
					: (int) totalNum - counted;
			counted += temp;
			Map<String, Object> map = new HashMap<>();
			map.put("min", ageLimit[i][0]);
			map.put("max", ageLimit[i][1]);
			map.put("color", color[i]);
			map.put("precent", String.format("%.2f", temp / totalNum * 100));
			ageList.add(map);
		}
		resultMap.put("ageList", ageList);
		resultMap.put("man", jpa.countRecord(hql + " and usGender=1", null));
		resultMap.put("woman", jpa.countRecord(hql + " and usGender=0", null));
		return Msg.MsgSuccess(resultMap);
	}

	@Override
	public Msg findSaaSAmt(String shopId) {
		// 获取商家信息
		// FwUserApp app = this.userAppJpa.findByShopId(shopId);
		// // 获取商家钱包
		// com.fw.main.entity.user.FwUserWallets wallets =
		// this.saasWallertsJpa.findByUwUsId(app.getUaUsId());
		Map<String, Object> map = new HashMap<>();
		// map.put("uwBalance", wallets.getUwBalance());
		return Msg.MsgSuccess(map);
	}

	/**
	 * 手机登录
	 */
	@Override
	public Msg phoneLogin(String phone, String code, String ssId, HttpServletRequest request) throws Exception {

		
		return Msg.MsgSuccess();
	}

	public Msg findAll(Pageable pageable, Map<String, Object> map,Map<String,Object> sortMap) throws Exception {
		QueryHelper queryHelper = new QueryHelper("select new map(fu as fu,u as uw)",FwUser.class, "fu").addCondition("fu.fwIsRemove=0")
				.addJoin("FwUserWallets", "u").addCondition("u.uwUsId=fu.usId");
		queryHelper=sort(queryHelper, sortMap);
		queryHelper.addOrderProperty("fu.createTime", false);
		if (map.get("startTime") != null && map.get("endTime") != null) {
			queryHelper.addCondition("fu.createTime >= '" + map.get("startTime").toString() + "' and  fu.createTime<='"
					+ map.get("endTime").toString() + "'");
			map.remove("startTime");
			map.remove("endTime");
		} else if (map.get("startTime") != null && map.get("endTime") == null) {
			queryHelper.addCondition("fu.createTime >= '" + map.get("startTime").toString() + "'");
			map.remove("startTime");
		} else if (map.get("startTime") == null && map.get("endTime") != null) {
			queryHelper.addCondition("fu.createTime <='" + map.get("endTime").toString() + "'");
			map.remove("endTime");
		}
		
		
		
		Page<?> page = findAll(queryHelper, pageable, map);
		
		return Msg.MsgSuccess(page);
	}

	/**
	 * 总后台根据用户ID查找会员
	 */
	public Msg findByUsId(String usId) throws Exception {

		if (!ValidateUtil.isValid(usId)) {
			return Msg.MsgError("参数为null");
		}
		FwUser user = fwUserJpa.findOne(usId);
		if (user == null) {
			return Msg.MsgError("用户存在");
		}

		return Msg.MsgSuccess(user);
	}

	/**
	 * 修改用户信息
	 * 
	 * @param fwUser
	 * @return
	 */
	public Msg update(FwUser fwUser) throws MyException {
		FwUser user = fwUserJpa.findOne(fwUser.getUsId());
		if (user == null) {
			return Msg.MsgError("not extsis");
		}

		if (fwUser.getUsUsername() != null || fwUser.getUsUsername() != "") {
			user.setUsUsername(fwUser.getUsUsername());
			user.setUsNickName(fwUser.getUsUsername());
		} else if (fwUser.getUsNickName() != null || fwUser.getUsNickName() != "") {
			user.setUsUsername(fwUser.getUsNickName());
			user.setUsNickName(fwUser.getUsNickName());
		}
		if (ValidateUtil.isValid(fwUser.getUsPic())) {
			user.setUsPic(FileUtil.moveAbsoluteToFile(fwUser.getUsPic()));
		}

		if (fwUser.getUsBirthday() != null) {
			user.setUsBirthday(fwUser.getUsBirthday());
		}

		if (ValidateUtil.isValid(fwUser.getUsPhone())) {
			user.setUsPhone(fwUser.getUsPhone());
		}

		user.setUsGender(fwUser.getUsGender());
		user.setModifyTime(new Date());

		
		if(ValidateUtil.isValid(fwUser.getUsPassword())){
			user.setUsPassword(MD5Util.md5(fwUser.getUsPassword()));
		}
		
		fwUserJpa.save(user);

		

		return Msg.MsgSuccess();
	}

	/**
	 * 删除用户
	 */
	@Override
	public Msg remove(FwUser t) throws MyException {

		FwUser user = fwUserJpa.findOne(t.getUsId());
		user.setFwIsRemove(1);
		fwUserJpa.save(user);
		return Msg.MsgSuccess();
	}

	/**
	 * 用户修改信息
	 */
	public Msg modifyUser(FwUser t, String code) throws MyException {

		FwUser user = fwUserJpa.findOne(t.getUsId());
		if (user == null) {
			return Msg.MsgError("用户不存在");
		}
		

		user.setUsPhone(t.getUsPhone());
		user.setUsUsername(t.getUsUsername());
		user.setUsName(t.getUsUsername());
		user.setUsNickName(t.getUsUsername());

		if (ValidateUtil.isValid(user.getUsPic())) {
			user.setUsPic(FileUtil.moveAbsoluteToFile(user.getUsPic()));
		}
		fwUserJpa.save(user);

		return Msg.MsgSuccess();
	}

	/**
	 * 手机号绑定
	 */
	public Msg bindingPhone(String usId, String phone, String code, Integer uType, String pwd, Integer appFlage)
			throws Exception {

		if (!ValidateUtil.isValid(code)) {
			return Msg.MsgError("验证码不存在");
		}
		

		// 根据用户id查询该用户信息
		FwUser fwUser = fwUserJpa.findOne(usId);// 微信登录的用户数据
		// 根据手机号码去查询用户
		FwUser user = fwUserJpa.findByUsPhone(phone);
		if (user == null) {// 如果为空，表示以前没有注册过
			fwUser.setUsPhone(phone);
			fwUser.setModifyTime(new Date());
			if (ValidateUtil.isValid(pwd)) {
				fwUser.setUsPassword(MD5Util.md5(pwd));
			}
			fwUserJpa.save(fwUser);
		} else if (user != null) {
			
			
			//
			FwUserOther other = fwUserOtherJpa.findByUoFuId(user.getUsId());
			if(other!=null){
				return Msg.MsgError("该手机号已绑定其他用户");
			}
			
			
		}
		return Msg.MsgSuccess();
	}

	
	
	/**
	 * 统计会员总数/今日新增
	 */
	public Msg countUserAndDayUser()throws MyException{
		Map<String,Object> map=new HashMap<>();
		
		QueryHelper queryHelper=new QueryHelper(FwUser.class, "fu")
				.addCondition("fu.createTime=to_days(now())");
		
		map.put("countUser", fwUserJpa.findAll().size());
		map.put("dayUser", find(queryHelper.getListQueryHql()).size());
		return Msg.MsgSuccess(map);
	}

	//总后台查询所有用户
	@SuppressWarnings("unchecked")
	@Override
	public Msg adminFindAll(Pageable pageable, Map<String, Object> map) throws Exception {
		QueryHelper queryHelper = new QueryHelper("select new map(fu as fu)", FwUser.class, "fu")
				.addCondition("fu.fwIsRemove=0").addOrderProperty("fu.createTime", false)
				.addCondition("fu.usType not in (501,502,503)");
		if (map.get("startTime") != null && map.get("endTime") != null) {
			queryHelper.addCondition("fu.createTime >= '" + map.get("startTime").toString() + "' and  fu.createTime<='"
					+ map.get("endTime").toString() + "'");
			map.remove("startTime");
			map.remove("endTime");
		} else if (map.get("startTime") != null && map.get("endTime") == null) {
			queryHelper.addCondition("fu.createTime >= '" + map.get("startTime").toString() + "'");
			map.remove("startTime");
			map.remove("endTime");
		} else if (map.get("startTime") == null && map.get("endTime") != null) {
			queryHelper.addCondition("fu.createTime <='" + map.get("endTime").toString() + "'");
			map.remove("endTime");
			map.remove("startTime");
		}

		if (map.get("type") != null) {
			queryHelper.addCondition("fu.usRole='"+ map.get("type").toString() + "'");
			map.remove("type");
		}
		if (map.get("usUsername") != null) {
			queryHelper.addCondition("fu.usUsername like '%" + map.get("usUsername") + "%'");
			map.remove("usUsername");
		}
		if (map.get("usPhone") != null) {
			queryHelper.addCondition("fu.usPhone like '%" + map.get("usPhone") + "%'");
			map.remove("usPhone");
		}

		Page<?> page = findAll(queryHelper, pageable, map);

		return Msg.MsgSuccess(page);
	}
}
