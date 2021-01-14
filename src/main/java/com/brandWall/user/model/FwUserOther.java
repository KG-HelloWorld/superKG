package com.brandWall.user.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;

import com.brandWall.util.ValidateUtil;

@Entity
@Table(name = "fw_user_other")
@Scope(value = "prototype")
public class FwUserOther implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "uo_id")
	private String uoId;// 主键
	@Column(name = "uo_ss_id")
	private String uoSsId;// 门店ID
	@Column(name = "uo_fu_id")
	private String uoFuId;// 用户ID
	@Column(name = "uo_type")
	private int uoType;// 2是微信登录；1是QQ登录  4,支付宝 3微信公众号
	@Column(name = "uo_open_id")
	private String uoOpenId;// 开发平台ID
	@Column(name = "uo_account")
	private String uo_account;// 第三方账号
	@Column(name = "uo_ali_user_id")
	private String uoAliUserId;//支付宝用户id
	@Column(name = "uo_union_id")
	private String uoUnionId;//支付宝用户id
	@Column(name = "uo_open_public")
	private String uoOpenPublic;//公众号openid
	
	
	public void setUoId(String uoId) {
		this.uoId = ValidateUtil.id(uoId);
	}

	public String getUoId() {
		return uoId;
	}

	public void setUoSsId(String uoSsId) {
		this.uoSsId = uoSsId;
	}

	public String getUoSsId() {
		return uoSsId;
	}

	public void setUoFuId(String uoFuId) {
		this.uoFuId = uoFuId;
	}

	public String getUoFuId() {
		return uoFuId;
	}

	public void setUoType(int uoType) {
		this.uoType = uoType;
	}

	public int getUoType() {
		return uoType;
	}

	public void setUoOpenId(String uoOpenId) {
		this.uoOpenId = uoOpenId;
	}

	public String getUoOpenId() {
		return uoOpenId;
	}

	public String getUo_account() {
		return uo_account;
	}

	public void setUo_account(String uo_account) {
		this.uo_account = uo_account;
	}

	public FwUserOther() {
	}
	
	
	
	
	public String getUoAliUserId() {
		return uoAliUserId;
	}

	public void setUoAliUserId(String uoAliUserId) {
		this.uoAliUserId = uoAliUserId;
	}



	public String getUoUnionId() {
		return uoUnionId;
	}

	public void setUoUnionId(String uoUnionId) {
		this.uoUnionId = uoUnionId;
	}

	public String getUoOpenPublic() {
		return uoOpenPublic;
	}

	public void setUoOpenPublic(String uoOpenPublic) {
		this.uoOpenPublic = uoOpenPublic;
	}

	public FwUserOther(String uoSsId, String uoFuId, String uoOpenId, Integer uoType, String account,String uoUserId) {
		this.uoFuId = uoFuId;
		this.uoOpenId = uoOpenId;
		this.uoSsId = uoSsId;
		this.uoType = uoType;
		this.uo_account = account;
		this.uoAliUserId = uoUserId;
	}
	
	
	
}
