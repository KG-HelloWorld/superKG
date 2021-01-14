package com.brandWall.user.jpa;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.brandWall.user.model.FwUser;

public interface FwUserJpa extends JpaRepository<FwUser, String> {

	/**
	 * 根据用户名称和密码获取用户信息
	 * 
	 * @return
	 */
	public List<FwUser> findByUsUsernameAndFwIsRemoveAndUsType(String userName, 
			Integer isremove, Integer uType);

	public FwUser findByUsPhoneAndUsTypeAndFwIsRemove(String usPhone, Integer usType, Integer isRemove);

	public List<FwUser> findByUsSsIdAndUsType(String usSsId, int type);

	@Modifying
	@Transactional
	@Query("update FwUser u set u.usPushKey = '' where u.usPushKey = ?1")
	public int update(String tag);

	public FwUser findByUsPhoneAndUsSsId(String usPhone, String usSsId);
	
	
	@Query(value="select a from FwUser a where a.usId in(:ids) ")
	public List<FwUser>  findUsers(@Param(value="ids")Set<String> userList);
	
	@Query(value="select a from FwUser a where a.usSsId =? and isPersonnel=1")
	public List<FwUser> findBySsIdAllIsPersonnel(String ssId);
	
	
	public List<FwUser> findByUnionidAndIsPersonnel(String unionid,Integer isPersonnel);

	
	
	/**
	 * 查询成交会员数
	 */
	@Query(value="select count(*) from FwUser a where a.usPayNum>0")
	public long countUserByPay();

	public FwUser findByUsPhone(String phone);
	
}
