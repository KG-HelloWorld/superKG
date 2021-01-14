package com.brandWall.user.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brandWall.user.model.FwUserOther;

/**
 * 用户第三方信息
 * <p>Title: FwUserOtherJpa</p>
 * <p>Description: 第三方平台的各种标识及用户信息</p>
 * <p>Company: 广东飞威集团</p> 
 * @author 冼灿康
 * @date 2017年11月23日
 */
public interface FwUserOtherJpa extends JpaRepository<FwUserOther, String> {

	/**
	 * 获取一条记录
	 * <p>Title: findByUoOpenIdAndUoSsIdAndUoType</p>
	 * <p>Description: 通过第三方凭证获取user</p>
	 * @param openId 第三方的登陆凭证
	 * @param shopId 店铺id
	 * @param uType 登陆类型
	 * @return
	 */
	public FwUserOther findByUoOpenIdAndUoSsIdAndUoType(String openId, String shopId, Integer uType);
	
	/**
	 * 获取一条记录
	 * <p>Title: findByUoOpenIdAndUoSsIdAndUoType</p>
	 * <p>Description: 通过第三方凭证获取user 支付宝</p>
	 * @param openId 第三方的登陆凭证
	 * @param shopId 店铺id
	 * @param uType 登陆类型
	 * @return
	 */
	public FwUserOther findByUoAliUserIdAndUoSsIdAndUoType(String userID, String shopId, Integer uType);
	
	
	
	
	
	/**
	 * 获取一条记录
	 * <p>Title: findByUoFuIdAndUoType</p>
	 * <p>Description: 根据用户id与类型查找第三方信</p>
	 * @param uId 用户id
	 * @param type 登陆类型
	 * @return
	 */
	public FwUserOther findByUoFuIdAndUoType(String uId, int type);
	
	/**
	 * 获取一条记录
	 * <p>Title: findByUoFuId</p>
	 * <p>Description: 通过用户id获取一个第三方登录信息</p>
	 * @param uId 用户id
	 * @return 用户第三方信息
	 */
	public FwUserOther findByUoFuId(String uId);
	
	public List<FwUserOther> findByUoOpenId(String openId);
	
	
	/**
	 * 获取一条记录
	 * <p>Title: findByUoFuIdAndUoType</p>
	 * <p>Description: 根据用户id与类型查找第三方信</p>
	 * @param openId 第三方id、
	 * @param type 登陆类型
	 * @return
	 */
	public FwUserOther findByUoOpenIdAndUoType(String openId, int type);

	//根据unionId查询
	public List<FwUserOther> findByUoUnionId(String string);
	//根据公众号openid查询
	public List<FwUserOther> findByUoOpenPublic(String openId);

}
