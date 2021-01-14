package com.brandWall.user.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;

import com.brandWall.BaseService;
import com.brandWall.user.model.FwUser;
import com.brandWall.user.model.FwUserOther;
import com.brandWall.util.Msg;
import com.brandWall.util.exception.MyException;

public interface FwUserService extends BaseService<FwUser> {

	/**
	 * 登陆使用
	 * 
	 * @param usPhone
	 * @param password
	 * @param uType
	 * @param isRemove
	 * @param shopId
	 */
	public FwUser findByUsPhoneAndUTypeAndFwIsRemoveByPassword(String usPhone, String password, Integer uType,
			Integer isRemove, String shopId) throws MyException;

	/**
	 * 获取判断
	 * 
	 * @param usPhone
	 * @param uType
	 * @param isRemove
	 * @param shopId
	 */
	public FwUser findByUsPhoneAndUTypeAndFwIsRemove(String usPhone, Integer uType, Integer isRemove, String shopId)
			throws MyException;

	public FwUser findByUsUsernameAndUsSsIdAndFwIsRemoveAndUsType(String userName, String shopId, Integer isremove,
			Integer uType) throws MyException;

	public FwUser findByUsUsernameAndUsSsIdAndFwIsRemoveAndUsTypeByPassword(String userName, String shopId,
			int isremove, String password, Integer uType) throws MyException;

	public Object addUser(FwUser fwUser, Integer loginType, FwUserOther fwUserOther, String parentId)
			throws MyException;

	public FwUser findByQqId(String prove, Integer uType);

	public FwUser findByWechatId(String prove, Integer uType);

	public Object login(String loginName, String password, String shopId, Integer uType, Integer loginType,
			HttpServletRequest request, String type) throws MyException;
	
	/**
	 * 手机号码登录
	 * @param phone 手机
	 * @param code 验证码
	 * @param type 类型
	 * @param way
	 * @return
	 */
	public Msg  phoneLogin(String phone,String code,String ssId, HttpServletRequest request)throws Exception;
	
	public Object loginBySaas(String remake) throws Exception;

	public Object register(String loginName, String password, String shopId, String code, Integer uType,
			Integer loginType, String parentId) throws MyException;

	public Object updateOtherLogin(String account,String aliUserId, String openId, Integer loginType, Integer uType, String nikeName,
	        String pic, String shopId, String partId,String gender,String phone) throws Exception;

	/**
	 * 统计用户信息
	 * 
	 * @return
	 */
	Msg countUser(String shopId);

	//--------------------------2018-04-18新增----------------------------------------

	public Msg countAgeAndSex(String shopId) throws MyException ;

	public Msg findSaaSAmt(String shopId);
	


	
	public Msg findAll(Pageable pageable,Map<String,Object> map,Map<String,Object> sortMap)throws Exception;
	
	/**
	 * 总后台根据用户ID查找会员
	 */
	public Msg findByUsId(String usId) throws Exception ;
	
	/**
	 * 修改用户信息
	 * @param fwUser
	 * @return
	 */
	public Msg update(FwUser fwUser)throws MyException ;
	
	/**
	 * 用户修改信息
	 */
	public Msg modifyUser(FwUser t,String code)throws MyException;
	

	public Msg bindingPhone(String userId, String phone, String code, Integer uType, String pwd, Integer appFlage)throws Exception;


	/**
	 * 统计会员总数/今日新增
	 */
	public Msg countUserAndDayUser()throws MyException;

	/**
	 * 总后台查询所有用户
	 * @param pageable
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Msg adminFindAll(Pageable pageable, Map<String, Object> map) throws Exception;

}
