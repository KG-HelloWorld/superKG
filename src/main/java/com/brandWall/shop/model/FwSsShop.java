package com.brandWall.shop.model;



import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;

import com.brandWall.user.model.FwUser;
import com.brandWall.util.FileUtil;

@Entity
@Table(name = "fw_ss_shop")
@Scope(value = "prototype")
public class FwSsShop {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "ss_id")
	private String ssId;//主键
	@Column(name = "ss_name")
	private String ssName;//店铺
	@Column(name = "ss_pic")
	private String ssPic;//logo图
	
	@Column(name = "ss_province")
	private String ssProvince;//省
	
	@Column(name = "ss_city")
	private String ssCity;//城市
	
	@Column(name = "ss_district")
	private String ssDistrict;//地区
	
	@Column(name = "ss_address")
	private String ssAddress;//详细地址
	
	@Column(name = "ss_phone")
	private String ssPhone;//手机号码
	
	@Column(name = "ss_contacts")
	private String ssContacts;//联系人	
	@Column(name = "ss_saler_type")
	private String ssSalerType;//商家销售类型
	@Column(name = "ss_type")
	private int ssType;//类型 0垃圾司机  1云浮司机
	@Column(name = "ss_dec")
	private String ssDec;//简介
	@Column(name = "create_time")
	private Date createTime;//创建时间
	@Column(name = "modify_time")
	private Date modifyTime;//修改时间
	
	@Column(name = "shop_sign_board_pic")
	private String shopSignBoardPic;//店铺门头照图片
	
	@Column(name = "business_license_pic")
	private String businessLicensePic;//驾照正面
	
	@Column(name = "business_license")
	private String businessLicense;//驾照反面
	
	@Column(name = "us_id")
	private String usId;//用户id
	
	@Column(name = "ss_street")
	private String ssStreet;//
	
	
	@Column(name = "industry_id")
	private String industryId;//街道id

	@Column(name = "statue")
	private int statue;//0是新建 1是审核通过 2是审核不通过
	
	@Column(name = "reason")
	private String reason;//不通过原因
	
	@Column(name = "company_name")
	private String companyName;//公司名
	
	@Column(name = "zip_code")
	private String zipCode;//邮编
	
	@Column(name = "ss_longitude")
	private String ssLongitude;// 经度
	@Column(name = "ss_latitude")
	private String ssLatitude;// 纬度
	
	@Column(name = "ss_permit_img")
	private String ssPermitImg;//开户许可证
	@Column(name = "ss_person_pic")
	private String ssPersonPic;//身份证正面照片
	@Column(name = "ss_side_pic")
	private String ssSidePic;//身份证反面照片
	@Column(name = "ss_recommend")
	private int ssRecommend;//是否推荐 0不是推荐 1是推荐
	
	@Column(name = "ss_remove")
	private int ssRemove;//是否删除 0否 1是
	
	@Column(name = "province_id")
	private String provinceId;//省id
	
	@Column(name = "city_id")
	private String cityId;//市id
	
	@Column(name = "district_id")
	private String districtId;//区id
	
	@Column(name = "shop_vedio")
	private String shopVedio;//品牌视频
	
	@Column(name = "shop_context")
	private String shopContext;//品牌详情
	
	@Column(name = "classify_id")
	private String classifyId;//分类id
	
	@Transient
	private String businessLicensePicUrl;//全路径
	
	@Transient
	private String businessLicenseUrl;//驾照反面全路径
	
	@Transient
	private String shopSignBoardPicUrl;//店铺门头照图片
	
	@Transient
	private String ssPicUrl;//店铺主图
	
	@Transient
	private String ssPermitImgUrl;//开户许可证全路径
	
	@Transient
	private String ssPersonPicUrl;//身份证正面照片全路径
	
	@Transient
	private String ssSidePicUrl;//身份证反面照片全路径
	
	@Transient
	private FwUser user;
	
	@Transient
	private List<ShopBindingLabel> bindingLabelList;//标签集合
	
	@Transient
	private ShopOneClassify oneClassify;//一级分类对象
	
	@Transient
	private ShopTwoClassify twoClassify;//二级分类对象
	
	@Transient
	private ShopThreeClassify threeClassify;//三级分类对象
	
	@Transient
	private ShopFourClassify fourClassify;//四级分类对象
	
	@Transient
	private String dist;//距离

	@Transient
	private int isCheck;//是否已选择 0否 1是

	public int getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(int isCheck) {
		this.isCheck = isCheck;
	}

	public ShopTwoClassify getTwoClassify() {
		return twoClassify;
	}

	public void setTwoClassify(ShopTwoClassify twoClassify) {
		this.twoClassify = twoClassify;
	}

	public ShopThreeClassify getThreeClassify() {
		return threeClassify;
	}

	public void setThreeClassify(ShopThreeClassify threeClassify) {
		this.threeClassify = threeClassify;
	}

	public ShopFourClassify getFourClassify() {
		return fourClassify;
	}

	public void setFourClassify(ShopFourClassify fourClassify) {
		this.fourClassify = fourClassify;
	}

	public ShopOneClassify getOneClassify() {
		return oneClassify;
	}

	public void setOneClassify(ShopOneClassify oneClassify) {
		this.oneClassify = oneClassify;
	}

	public List<ShopBindingLabel> getBindingLabelList() {
		return bindingLabelList;
	}

	public void setBindingLabelList(List<ShopBindingLabel> bindingLabelList) {
		this.bindingLabelList = bindingLabelList;
	}

	public String getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(String classifyId) {
		this.classifyId = classifyId;
	}

	public String getShopContext() {
		return shopContext;
	}

	public void setShopContext(String shopContext) {
		this.shopContext = shopContext;
	}

	public String getShopVedio() {
		return shopVedio;
	}

	public void setShopVedio(String shopVedio) {
		this.shopVedio = shopVedio;
	}

	public String getSsId() {
		return ssId;
	}

	public void setSsId(String ssId) {
		this.ssId = ssId;
	}

	public String getSsName() {
		return ssName;
	}

	public void setSsName(String ssName) {
		this.ssName = ssName;
	}

	public String getSsPic() {
		return ssPic;
	}

	public void setSsPic(String ssPic) {
		this.ssPic = ssPic;
	}

	public String getSsProvince() {
		return ssProvince;
	}

	public void setSsProvince(String ssProvince) {
		this.ssProvince = ssProvince;
	}

	public String getSsCity() {
		return ssCity;
	}

	public void setSsCity(String ssCity) {
		this.ssCity = ssCity;
	}

	public String getSsDistrict() {
		return ssDistrict;
	}

	public void setSsDistrict(String ssDistrict) {
		this.ssDistrict = ssDistrict;
	}

	public String getSsAddress() {
		return ssAddress;
	}

	public void setSsAddress(String ssAddress) {
		this.ssAddress = ssAddress;
	}

	public String getSsPhone() {
		return ssPhone;
	}

	public void setSsPhone(String ssPhone) {
		this.ssPhone = ssPhone;
	}

	public String getSsContacts() {
		return ssContacts;
	}

	public void setSsContacts(String ssContacts) {
		this.ssContacts = ssContacts;
	}

	

	public int getSsType() {
		return ssType;
	}

	public void setSsType(int ssType) {
		this.ssType = ssType;
	}

	public String getShopSignBoardPic() {
		return shopSignBoardPic;
	}

	public void setShopSignBoardPic(String shopSignBoardPic) {
		this.shopSignBoardPic = shopSignBoardPic;
	}

	public String getBusinessLicensePic() {
		return businessLicensePic;
	}

	public void setBusinessLicensePic(String businessLicensePic) {
		this.businessLicensePic = businessLicensePic;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getUsId() {
		return usId;
	}

	public void setUsId(String usId) {
		this.usId = usId;
	}



	public int getStatue() {
		return statue;
	}

	public void setStatue(int statue) {
		this.statue = statue;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getBusinessLicensePicUrl() {
		return FileUtil.bUrl(businessLicensePic);
	}

	public String getShopSignBoardPicUrl() {
		return FileUtil.bUrl(shopSignBoardPic);
	}

	public String getSsPicUrl() {
		return FileUtil.bUrl(ssPic);
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getSsLongitude() {
		return ssLongitude;
	}

	public void setSsLongitude(String ssLongitude) {
		this.ssLongitude = ssLongitude;
	}

	public String getSsLatitude() {
		return ssLatitude;
	}

	public void setSsLatitude(String ssLatitude) {
		this.ssLatitude = ssLatitude;
	}

	public String getSsDec() {
		return ssDec;
	}

	public void setSsDec(String ssDec) {
		this.ssDec = ssDec;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}



	public String getSsPermitImg() {
		return ssPermitImg;
	}

	public void setSsPermitImg(String ssPermitImg) {
		this.ssPermitImg = ssPermitImg;
	}

	public String getSsPersonPic() {
		return ssPersonPic;
	}

	public void setSsPersonPic(String ssPersonPic) {
		this.ssPersonPic = ssPersonPic;
	}

	public String getSsSidePic() {
		return ssSidePic;
	}

	public void setSsSidePic(String ssSidePic) {
		this.ssSidePic = ssSidePic;
	}

	public String getSsPermitImgUrl() {
		return FileUtil.bUrl(ssPermitImg);
	}

	public String getSsPersonPicUrl() {
		return FileUtil.bUrl(ssPersonPic);
	}

	public String getSsSidePicUrl() {
		return FileUtil.bUrl(ssSidePic);
	}

	public int getSsRecommend() {
		return ssRecommend;
	}

	public void setSsRecommend(int ssRecommend) {
		this.ssRecommend = ssRecommend;
	}

	public String getSsSalerType() {
		return ssSalerType;
	}

	public void setSsSalerType(String ssSalerType) {
		this.ssSalerType = ssSalerType;
	}

	public FwUser getUser() {
		return user;
	}

	public void setUser(FwUser user) {
		this.user = user;
	}

	public int getSsRemove() {
		return ssRemove;
	}

	public void setSsRemove(int ssRemove) {
		this.ssRemove = ssRemove;
	}



	public String getDist() {
		return dist;
	}

	public void setDist(String dist) {
		this.dist = dist;
	}

	public String getBusinessLicenseUrl() {
		return FileUtil.bUrl(businessLicense);
	}



	
	public String getSsStreet() {
		return ssStreet;
	}

	public void setSsStreet(String ssStreet) {
		this.ssStreet = ssStreet;
	}

	public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}
	
	
}
