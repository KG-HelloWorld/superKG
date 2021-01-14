package com.brandWall.user.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;

import com.brandWall.util.FileUtil;
import com.brandWall.util.LoginType;
import com.brandWall.util.StringUtil;
import com.brandWall.util.ValidateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "fw_user")
@Scope(value = "prototype")
public class FwUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "us_id")
	private String usId;//
	@Column(name = "us_ss_id")
	private String usSsId;// 门店ID
	@Column(name = "us_username")
	private String usUsername;// 用户名
	@Column(name = "us_nick_name")
	private String usNickName;// 昵称
	@Column(name = "us_name")
	private String usName;// 真实姓名
	@Column(name = "us_pic")
	private String usPic;// 头像
	@Column(name = "us_gender")
	private int usGender;// 性别(0是女 1是男 2是未知)
	@Column(name = "us_card_num")
	private String usCardNum;// 身份证号码
	@Column(name = "us_card_pic")
	private String usCardPic;// 身份证（正面）
	@Column(name = "us_card_pic_os")
	private String usCardPicOs;// 身份证（反面）
	@Column(name = "us_password")
	private String usPassword;// 密码
	@Column(name = "us_phone")
	private String usPhone;// 手机号码
	@Column(name = "us_address")
	private String usAddress;// 地址
	@Column(name = "us_birthday")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date usBirthday;// 生日
	@Column(name = "us_type")
	private int usType;// 用户类型 503是超级管理员 ；502是普通管理 3是区域管理员
	@Column(name = "us_push_key")
	private String usPushKey; // 推送标示
	@Column(name = "us_login_time", columnDefinition = "DATE DEFAULT CURRENT_DATE") //
	private Date usLoginTime;// 最后登录时间
	@Column(name = "create_time") //
	private Date createTime;// 创建时间
	@Column(name = "modify_time", columnDefinition = "DATE DEFAULT CURRENT_DATE ON UPDATE CURRENT_TIMESTAMP") //
	private Date modifyTime;// 修改时间
	@Column(name = "fw_is_remove")
	private int fwIsRemove;// 删除
	@Column(name = "us_state")
	private int usState;// 用户状态（0==未冻结，1==冻结）
	@Column(name = "us_role")
	private String usRole;// 用户角色
	@Column(name = "us_rong_info")
	private int usRongInfo;// 是否已更新融云信息
	@Column(name = "company_id")
	private String companyId;//公司id
	@Column(name = "is_personnel")
	private int isPersonnel;//是否工作人员 0否 1是
	@Column(name = "is_staff")
	private int isStaff;//是否员工 0否 1是
	@Column(name = "unionid")
	private String unionid;//unionid
	@Column(name = "us_profile")
	private String usProfile;
	@Column(name = "us_pay_num")
	private int usPayNum;//购买次数
	@Column(name = "us_new")
	private int usNew;//消息未读数量		
	@Column(name = "us_buy_new")
	private int usBuyNew;//购物未查看数量
	/**
	 * ===================================
	 * 
	 * @param 華麗的分割綫
	 *            (以下字段不保存进数据库) ===================================
	 */
	@Transient
	private int usGrade;//等级
	
	@Transient
	private BigDecimal usInteger;//积分

	@Transient
	private boolean isSuper = false;//判断是否是超级管理员(true==是；false==否)


	@Transient
	private String usPicUrl;

	public void setUsRongInfo(int usRongInfo) {
		this.usRongInfo = usRongInfo;
	}

	public int getUsRongInfo() {
		return usRongInfo;
	}
     
	public String getUsPicUrl() {
		return FileUtil.bUrl(usPic);
	}

	public void setUsPicUrl(String usPicUrl) {
		this.usPicUrl = usPicUrl;
	}

	public void setUsId(String usId) {
		this.usId = ValidateUtil.id(usId);
	}

	public String getUsId() {
		return usId;
	}

	public void setUsSsId(String usSsId) {
		this.usSsId = usSsId;
	}

	public String getUsSsId() {
		return usSsId;
	}

	public void setUsUsername(String usUsername) {
		this.usUsername = usUsername;
	}

	public String getUsUsername() {
		return usUsername;
	}

	public void setUsNickName(String usNickName) {
		this.usNickName = usNickName;
	}

	public String getUsNickName() {
		return usNickName;
	}

	public void setUsName(String usName) {
		this.usName = usName;
	}

	public String getUsName() {
		return usName;
	}

	public String getUsPic() {
		return usPic;
	}

	public void setUsPic(String usPic) {
		this.usPic = usPic;
	}

	public void setUsGender(int usGender) {
		this.usGender = usGender;
	}

	public int getUsGender() {
		return usGender;
	}

	public void setUsCardNum(String usCardNum) {
		this.usCardNum = usCardNum;
	}

	public String getUsCardNum() {
		return usCardNum;
	}

	public void setUsCardPic(String usCardPic) {
		this.usCardPic = usCardPic;
	}

	public String getUsCardPic() {
		return usCardPic;
	}

	public void setUsCardPicOs(String usCardPicOs) {
		this.usCardPicOs = usCardPicOs;
	}

	public String getUsCardPicOs() {
		return usCardPicOs;
	}

	public void setUsPassword(String usPassword) {
		this.usPassword = usPassword;
	}

	public String getUsPassword() {
		return usPassword;
	}

	public void setUsPhone(String usPhone) {
		this.usPhone = usPhone;
	}

	public String getUsPhone() {
		return usPhone;
	}

	public void setUsType(int usType) {
		this.usType = usType;
	}

	public int getUsType() {
		return usType;
	}

	public String getUsPushKey() {
		return usPushKey;
	}

	public void setUsPushKey(String usPushKey) {
		this.usPushKey = usPushKey;
	}

	public void setUsLoginTime(Date usLoginTime) {
		this.usLoginTime = usLoginTime;
	}

	public Date getUsLoginTime() {
		return usLoginTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setFwIsRemove(int fwIsRemove) {
		this.fwIsRemove = fwIsRemove;
	}

	public int getFwIsRemove() {
		return fwIsRemove;
	}

	public String getUsAddress() {
		return usAddress;
	}

	public void setUsAddress(String usAddress) {
		this.usAddress = usAddress;
	}

	public Date getUsBirthday() {
		return usBirthday;
	}

	public void setUsBirthday(Date usBirthday) {
		this.usBirthday = usBirthday;
	}
//
//	public List<FwUserParam> getList() {
//		return list;
//	}
//
//	public void setList(List<FwUserParam> list) {
//		this.list = list;
//	}

	public int getUsState() {
		return usState;
	}

	public void setUsState(int usState) {
		this.usState = usState;
	}

	public String getUsRole() {
		return usRole;
	}

	public void setUsRole(String usRole) {
		this.usRole = usRole;
	}

//	public FwUserRole getUserRole() {
//		return userRole;
//	}
//
//	public void setUserRole(FwUserRole userRole) {
//		this.userRole = userRole;
//	}
//
//	public List<FwUserPower> getUserPower() {
//		return userPower;
//	}
//
//	public List<FwUserRole> getLstUserRole() {
//		return lstUserRole;
//	}
//
//	public void setLstUserRole(List<FwUserRole> lstUserRole) {
//		this.lstUserRole = lstUserRole;
//	}
//
//	public void setUserPower(List<FwUserPower> userPower) {
//		this.userPower = userPower;
//	}

	public boolean isSuper() {
		return isSuper;
	}

	public void setSuper(boolean isSuper) {
		this.isSuper = isSuper;
	}
	 
	
	
	public String getUsProfile() {
		return usProfile;
	}

	public void setUsProfile(String usProfile) {
		this.usProfile = usProfile;
	}
	

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	
	
//	public FwUserAuthenticationMerchant getMerchant() {
//		return merchant;
//	}
//
//	public void setMerchant(FwUserAuthenticationMerchant merchant) {
//		this.merchant = merchant;
//	}
//	
	
	
	

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public int getIsPersonnel() {
		return isPersonnel;
	}

	public void setIsPersonnel(int isPersonnel) {
		this.isPersonnel = isPersonnel;
	}	



	public int getUsGrade() {
		return usGrade;
	}

	public void setUsGrade(int usGrade) {
		this.usGrade = usGrade;
	}

	public int getIsStaff() {
		return isStaff;
	}

	public void setIsStaff(int isStaff) {
		this.isStaff = isStaff;
	}

	public int getUsPayNum() {
		return usPayNum;
	}

	public void setUsPayNum(int usPayNum) {
		this.usPayNum = usPayNum;
	}
	
	public int getUsNew() {
		return usNew;
	}

	public void setUsNew(int usNew) {
		this.usNew = usNew;
	}

	public int getUsBuyNew() {
		return usBuyNew;
	}

	public void setUsBuyNew(int usBuyNew) {
		this.usBuyNew = usBuyNew;
	}

	public BigDecimal getUsInteger() {
		return usInteger;
	}

	public void setUsInteger(BigDecimal usInteger) {
		this.usInteger = usInteger;
	}

	public static FwUser getRegisterUser(String loginName, String password, int uType, String ssId, Integer loginType) {
		FwUser u = new FwUser();
		u.setFwIsRemove(0);
		// 暂时是手机号码
		if (loginType == LoginType.PHONE.getCode()) {
			u.setUsPhone(loginName);
		} else if (loginType == LoginType.USERNAME.getCode()) {
			u.setUsUsername(loginName);
		}
		if (!ValidateUtil.isValid(u.getUsUsername())) {
			u.setUsUsername(StringUtil.getUsername());
		}
		u.setUsPassword(password);
		if (ssId == null) {
			ssId = "0";
		}
		u.setUsNickName("未设置");
		u.setUsSsId(ssId);
		u.setUsType(uType);

		return u;
	}

	public static FwUser getTheThirdRegister(String openId, String name, int uType, String shopId, String pic) {
		String username = name.replaceAll("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", "");
		FwUser u = new FwUser();
		u.setUsUsername(username);
		u.setUsNickName(username);
		u.setUsPic(pic);
		u.setUsSsId(shopId);
		u.setFwIsRemove(0);
		u.setUsType(uType);
		return u;
	}

}
