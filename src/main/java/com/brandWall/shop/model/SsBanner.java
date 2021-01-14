package com.brandWall.shop.model;

import java.util.Date;
import java.util.Map;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.GeneratedValue;

import com.brandWall.util.FileUtil;
import com.brandWall.util.ValidateUtil;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;


@Entity
@Table(name = "ss_banner")
@Scope(value = "prototype")
public class SsBanner implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "b_id")
	private String bId;//
	@Column(name = "b_name")
	private String bName;// 名称
	@Column(name = "b_pic")
	private String bPic;// 图片
	@Column(name = "b_ss_id")
	private String bSsId;// 店铺id
	@Column(name = "b_about_id")
	private String bAboutId;// 关联商品id
	@Column(name = "b_enabled")
	private int bEnabled;// 是否启用 0 否 1 是
	@Column(name = "prot")
	private int prot;//0是小程序 1是pc
	@Column(name = "b_create_time", columnDefinition = "DATE DEFAULT CURRENT_DATE") //
	private Date bCreateTime;//
	@Column(name = "b_modify_time", columnDefinition = "DATE DEFAULT CURRENT_DATE ON UPDATE CURRENT_TIMESTAMP") //
	private Date bModifyTime;//
	@Column(name = "type")
	private int type;// 类型（0是产品；1是资讯；2是论坛）3是商家  4是房源  5招聘 6车市 7娱乐 8美食 9酒店  10活动      11投票   66开屏页    69服务  70科普  71转让店铺
	//
	@Column(name = "b_dec")
	private String dec;// 描述
	
	@Column(name = "b_status")
	private int bStatus;//类型 0是默认  (1是商会  2商城 3是积分 4是便民服务  5是平台服务) 
	
	@Column(name = "b_dafang_status")
	private String bDafangStatus;//行业分类id
	
	@Column(name = "sort")
	private int sort;//
	
	
	/**
	 * ============以下字段不保存进数据库=============
	 */
	@Transient
	private String bPicUrl;
	@Transient
	private String aboutName;
	@Transient
	private Map<String, Object> picMin;
	
	
	
	
	
	@Transient
	private String typeName;

	
	
	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getAboutName() {
		return aboutName;
	}

	public void setAboutName(String aboutName) {
		this.aboutName = aboutName;
	}

	public String getbPicUrl() {
		return FileUtil.bUrl(bPic);
	}

	public void setbPicUrl(String bPicUrl) {
		this.bPicUrl = bPicUrl;
	}

	public void setBId(String bId) {
		this.bId = ValidateUtil.id(bId);
	}

	public String getBId() {
		return bId;
	}

	public void setBName(String bName) {
		this.bName = bName;
	}

	public String getBName() {
		return bName;
	}

	public void setBPic(String bPic) {
		this.bPic = bPic;
	}

	public String getBPic() {
		return bPic;
	}

	public void setBSsId(String bSsId) {
		this.bSsId = bSsId;
	}

	public String getBSsId() {
		return bSsId;
	}

	public void setBAboutId(String bAboutId) {
		this.bAboutId = bAboutId;
	}

	public String getBAboutId() {
		return bAboutId;
	}

	public void setBEnabled(int bEnabled) {
		this.bEnabled = bEnabled;
	}

	public int getBEnabled() {
		return bEnabled;
	}

	public void setBCreateTime(Date bCreateTime) {
		this.bCreateTime = bCreateTime;
	}

	public Date getBCreateTime() {
		return bCreateTime;
	}

	public void setBModifyTime(Date bModifyTime) {
		this.bModifyTime = bModifyTime;
	}

	public Date getBModifyTime() {
		return bModifyTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Map<String, Object> getPicMin() {
		return FileUtil.slUrl(getbPicUrl());
	}

	public String getDec() {
		return dec;
	}

	public void setDec(String dec) {
		this.dec = dec;
	}

	public int getProt() {
		return prot;
	}

	public void setProt(int prot) {
		this.prot = prot;
	}

	public int getbStatus() {
		return bStatus;
	}

	public void setbStatus(int bStatus) {
		this.bStatus = bStatus;
	}

	public String getbDafangStatus() {
		return bDafangStatus;
	}

	public void setbDafangStatus(String bDafangStatus) {
		this.bDafangStatus = bDafangStatus;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}



}
