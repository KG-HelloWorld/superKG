package com.brandWall.util;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 
 * 
 * @ClassName: Config
 * @Function: 配置文件
 * @Description: .
 * 
 * @date: 2016年11月22日 上午11:48:53
 * 
 * @author only_U
 * @version
 * @since JDK 1.8
 */
@Component("configSingle")
@ConfigurationProperties(locations = "classpath:singleConfig.properties", prefix = "config")
public class Config {

	

	@Value("${config.isReal}")
	public static Boolean isReal;

	@Value("${config.proName}")
	public static String proName;// 项目名称

	@Value("${config.proUrl}")
	public static String proUrl;

	@Value("${config.fileSavePath}")
	public static String fileSavePath;// 上传文件路径
	//
	// @Value("${config.fileApi}")
	// public static String fileApi;// 上传文件服务器地址
	//
	@Value("${config.fileRootUrl}")
	public static String fileRootUrl;// 访问路径

	@Value("${config.fileTempPath}")
	public static String fileTempPath;// 图片上传临时文件夹

	@Value("${config.fileTempUrl}")
	public static String fileTempUrl;// 临时文件访问路径



	@Value("${config.pageUrl}")
	public static String pageUrl;// PC端模板页面地址

	@Value("${Config.pmTemplate}")
	public static String pmTemplate;// 短信模板

	@Value("${config.fileApi}")
	public static String fileApi;

	@Value("${config.pageWxUrl}")
	public static String pageWxUrl;// 微信端模板页面地址

	@Value("${config.pageMinAPPUrl}")
	public static String pageMinAPPUrl;// 小程序模板存放地址

	@Value("${config.packageUrl}")
	public static String packageUrl;// Android与iOS打包地址

	@Value("${config.pageCopy}")
	public static String pageCopy;// 页面地址备份一份到服务器的地址

	@Value("${config.pageDirectory}")
	public static String pageDirectory;// 模板页面存放目录

	@Value("${config.userDirectory}")
	public static String userDirectory;// 用户正式使用的项目存放路径

	@Value("${config.wechatGaveFile}")
	public static String wechatGaveFile;// 微信公众号授权文件

	@Value("${config.wechatGaveFileUrl}")
	public static String wechatGaveFileUrl;// 微信公众号授权文件URL地址
	@Value("${config.fileTempLocation}")
	public static String fileTempLocation;

	@Value("${config.fileLocationPath}")
	public static String fileLocationPath;// 本地文件存储流

	@Value("${config.adAddUrl}")
	public static String adAddUrl;// 广告新增地址链接

	@Value("${config.adModifyUrl}")
	public static String adModifyUrl;// 广告修改地址链接

	@Value("${config.supportInfo}")
	public static String supportInfo;// 支持信息

	@Value("${config.saasUsAppUrl}")
	public static String saasUsAppUrl;// 获取用户所有的店铺信息

	@Value("${config.updatePersonCard}")
	public static String updatePersonCard;// 单商家修改名片的调用地址

	@Value("${config.saasTrade}")
	public static String saasTrade;// 获取saas的行业（分类）信息

	@Value("${config.selectPersonCard}")
	public static String selectPersonCard;// 获取saas名片

	@Value("${config.saasOrder}")
	public static String saasOrder;// 车猪猪应用开店

	@Value("${config.imgWay}")
	public static int imgWay;// 图片方式 0是自己服务器 1是腾讯云

	@Value("${config.fileCopyPath}")
	public static String fileCopyPath;// 图片复制地址

	@Value("${config.kdnEBusinessId}")
	public static String kdnEBusinessId;// 快递鸟电商ID

	@Value("${config.kdnAppKey}")
	public static String kdnAppKey;// 电商加密私钥，快递鸟提供，注意保管，不要泄漏

	@Value("${config.copyImgToTxy}")
	public static String copyImgToTxy;// #是否要COPY数据到腾讯云；1是copy到腾讯云

	@Value("${config.singlePc}")
	public static String singlePc;// 单商家pc端生成地址

	@Value("${config.installUrl}")
	public static String installUrl; // 安装商链接

	@Value("${config.installApiId}")
	public static String installApiId;// 安装商APPID

	@Value("${config.installApiSecret}")
	public static String installApiSecret; // 安装商秘钥

	// ----------------------------小程序帐号
	@Value("${config.minappAppid}")
	public static String minappAppid;// 小程序appid
	@Value("${config.minappSecret}")
	public static String minappSecret;// 小程序Secret
	@Value("${config.minappKey}")
	public static String minappKey;// 小程序Key
	@Value("${config.minappMchId}")
	public static String minappMchId;// 微信第三方平台商户id
	@Value("${config.minappCertPath}")
	public static String minappCertPath;// 微信第三方平台支付证书路径
	@Value("${config.minappCertPsd}")
	public static String minappCertPsd;// 微信第三方平台支付证书密码
	// ----------------------------微信APP平台-----------
	@Value("${config.wechatAppAppId}")
	public static String wechatAppAppId;// 微信第三方平台appid
	@Value("${config.wechatAppSecret}")
	public static String wechatAppSecret;// 微信第三方平台secret
	@Value("${config.wechatAppMchId}")
	public static String wechatAppMchId;// 微信第三方平台商户id
	@Value("${config.wechatAppCertPath}")
	public static String wechatAppCertPath;// 微信第三方平台支付证书路径
	@Value("${config.wechatAppCertPsd}")
	public static String wechatAppCertPsd;// 微信第三方平台支付证书密码
	@Value("${config.wechatAppkey}")
	public static String wechatAppkey;//微信key
	// ----------------------------微信第三方平台-----------
	@Value("${config.wechatThirdkey}")
	public static String wechatThirdkey;// 微信第三方平台key

	@Value("${config.wechatThirdAppId}")
	public static String wechatThirdAppId;// 微信第三方平台appid
	@Value("${config.wechatThirdSecret}")
	public static String wechatThirdSecret;// 微信第三方平台secret
	@Value("${config.wechatThirdMchId}")
	public static String wechatThirdMchId;// 微信第三方平台商户id
	@Value("${config.wechatThirdCertPath}")
	public static String wechatThirdCertPath;// 微信第三方平台支付证书路径
	@Value("${config.wechatThirdCertPsd}")
	public static String wechatThirdCertPsd;// 微信第三方平台支付证书密码
	
	@Value("${config.userid}")
	public static String userid;//短信用户id
	@Value("${config.accountpassword}")
	public static String accountpassword;//短信密码
	@Value("${config.account}")
	public static String account;//短信账号
	
	
	

	
	
	//---------------------百度api-----------------------------
	@Value("${config.bdAppid}")
	public static String bdAppid;// 百度apid
	@Value("${config.bdKey}")
	public static String bdKey;// 百度key
	@Value("${config.secretKey}")
	public static String secretKey;// 百度secretKey
	
	@Value("${config.GT}")
	public static String GT;//缓存的key
	
	//--------------------工行支付-----------------------------------
	@Value("${config.icbcAppId}")
	public static String icbcAppId;//工行appid
	@Value("${config.icbcAPIGWPUBLICKEY}")
	public static String icbcAPIGWPUBLICKEY;//工行网关公钥
	@Value("${config.icbcMYPRIVATEKEY}")
	public static String icbcMYPRIVATEKEY;//工行合作方私钥
	@Value("${config.icbcServiceUrl}")
	public static String icbcServiceUrl;//工行请求链接
	@Value("${config.icbcMerId}")
	public static String icbcMerId;//工行商户号
	@Value("${config.icbcMerIdYun}")
	public static String icbcMerIdYun;//工行商户号 运费
	
	
	
	public static String getInstallUrl() {
		return installUrl;
	}

	public static void setInstallUrl(String installUrl) {
		Config.installUrl = installUrl;
	}

	public static String getInstallApiId() {
		return installApiId;
	}

	public static void setInstallApiId(String installApiId) {
		Config.installApiId = installApiId;
	}

	public static String getInstallApiSecret() {
		return installApiSecret;
	}

	public static void setInstallApiSecret(String installApiSecret) {
		Config.installApiSecret = installApiSecret;
	}

	public static Boolean getIsReal() {
		return isReal;
	}

	public static void setIsReal(Boolean isReal) {
		Config.isReal = isReal;
	}

	public static String getProName() {
		return proName;
	}

	public static void setProName(String proName) {
		Config.proName = proName;
	}

	public static String getProUrl() {
		return proUrl;
	}

	public static void setProUrl(String proUrl) {
		Config.proUrl = proUrl;
	}

	public static String getFileSavePath() {
		return fileSavePath;
	}

	public static void setFileSavePath(String fileSavePath) {
		Config.fileSavePath = fileSavePath;
	}

	public static String getFileApi() {
		return fileApi;
	}

	public static void setFileApi(String fileApi) {
		Config.fileApi = fileApi;
	}

	public static String getFileRootUrl() {
		return fileRootUrl;
	}

	public static void setFileRootUrl(String fileRootUrl) {
		Config.fileRootUrl = fileRootUrl;
	}

	public static String getFileTempPath() {
		return fileTempPath;
	}

	public static void setFileTempPath(String fileTempPath) {
		Config.fileTempPath = fileTempPath;
	}

	public static String getFileTempUrl() {
		return fileTempUrl;
	}

	public static void setFileTempUrl(String fileTempUrl) {
		Config.fileTempUrl = fileTempUrl;
	}

	/*
	 * public static String getProjectName() { return projectName; }
	 * 
	 * public static void setProjectName(String projectName) {
	 * Config.projectName = projectName; }
	 */

	// public static String getFileApi() {
	// return fileApi;
	// }
	//
	// public static void setFileApi(String fileApi) {
	// Config.fileApi = fileApi;
	// }


	public static String getPageUrl() {
		return pageUrl;
	}

	public static void setPageUrl(String pageUrl) {
		Config.pageUrl = pageUrl;
	}

	public static String getPageWxUrl() {
		return pageWxUrl;
	}

	public static void setPageWxUrl(String pageWxUrl) {
		Config.pageWxUrl = pageWxUrl;
	}

	public static String getPageMinAPPUrl() {
		return pageMinAPPUrl;
	}

	public static void setPageMinAPPUrl(String pageMinAPPUrl) {
		Config.pageMinAPPUrl = pageMinAPPUrl;
	}

	public static String getPackageUrl() {
		return packageUrl;
	}

	public static void setPackageUrl(String packageUrl) {
		Config.packageUrl = packageUrl;
	}

	public static String getPageCopy() {
		return pageCopy;
	}

	public static void setPageCopy(String pageCopy) {
		Config.pageCopy = pageCopy;
	}

	public static String getPageDirectory() {
		return pageDirectory;
	}

	public static void setPageDirectory(String pageDirectory) {
		Config.pageDirectory = pageDirectory;
	}

	public static String getUserDirectory() {
		return userDirectory;
	}

	public static void setUserDirectory(String userDirectory) {
		Config.userDirectory = userDirectory;
	}

	public static String getWechatGaveFile() {
		return wechatGaveFile;
	}

	public static void setWechatGaveFile(String wechatGaveFile) {
		Config.wechatGaveFile = wechatGaveFile;
	}

	public static String getWechatGaveFileUrl() {
		return wechatGaveFileUrl;
	}

	public static void setWechatGaveFileUrl(String wechatGaveFileUrl) {
		Config.wechatGaveFileUrl = wechatGaveFileUrl;
	}

	public static String getFileTempLocation() {
		return fileTempLocation;
	}

	public static void setFileTempLocation(String fileTempLocation) {
		Config.fileTempLocation = fileTempLocation;
	}

	public static String getFileLocationPath() {
		return fileLocationPath;
	}

	public static void setFileLocationPath(String fileLocationPath) {
		Config.fileLocationPath = fileLocationPath;
	}

	public static String getAdAddUrl() {
		return adAddUrl;
	}

	public static void setAdAddUrl(String adAddUrl) {
		Config.adAddUrl = adAddUrl;
	}

	public static String getAdModifyUrl() {
		return adModifyUrl;
	}

	public static void setAdModifyUrl(String adModifyUrl) {
		Config.adModifyUrl = adModifyUrl;
	}

	public static String getSupportInfo() {
		return supportInfo;
	}

	public static void setSupportInfo(String supportInfo) {
		Config.supportInfo = supportInfo;
	}

	public static String getSaasUsAppUrl() {
		return saasUsAppUrl;
	}

	public static void setSaasUsAppUrl(String saasUsAppUrl) {
		Config.saasUsAppUrl = saasUsAppUrl;
	}

	public static String getUpdatePersonCard() {
		return updatePersonCard;
	}

	public static void setUpdatePersonCard(String updatePersonCard) {
		Config.updatePersonCard = updatePersonCard;
	}

	public static String getSaasTrade() {
		return saasTrade;
	}

	public static void setSaasTrade(String saasTrade) {
		Config.saasTrade = saasTrade;
	}

	public static String getSelectPersonCard() {
		return selectPersonCard;
	}

	public static void setSelectPersonCard(String selectPersonCard) {
		Config.selectPersonCard = selectPersonCard;
	}

	public static String getSaasOrder() {
		return saasOrder;
	}

	public static void setSaasOrder(String saasOrder) {
		Config.saasOrder = saasOrder;
	}

	public static int getImgWay() {
		return imgWay;
	}

	public static void setImgWay(int imgWay) {
		Config.imgWay = imgWay;
	}

	public static String getFileCopyPath() {
		return fileCopyPath;
	}

	public static void setFileCopyPath(String fileCopyPath) {
		Config.fileCopyPath = fileCopyPath;
	}

	public static String getKdnEBusinessId() {
		return kdnEBusinessId;
	}

	public static void setKdnEBusinessId(String kdnEBusinessId) {
		Config.kdnEBusinessId = kdnEBusinessId;
	}

	public static String getKdnAppKey() {
		return kdnAppKey;
	}

	public static void setKdnAppKey(String kdnAppKey) {
		Config.kdnAppKey = kdnAppKey;
	}

	public static String getCopyImgToTxy() {
		return copyImgToTxy;
	}

	public static void setCopyImgToTxy(String copyImgToTxy) {
		Config.copyImgToTxy = copyImgToTxy;
	}

	public static String getSinglePc() {
		return singlePc;
	}

	public static void setSinglePc(String singlePc) {
		Config.singlePc = singlePc;
	}

	public static String getPmTemplate() {
		return pmTemplate;
	}

	public static void setPmTemplate(String pmTemplate) {
		Config.pmTemplate = pmTemplate;
	}

	public static String getMinappAppid() {
		return minappAppid;
	}

	public static void setMinappAppid(String minappAppid) {
		Config.minappAppid = minappAppid;
	}

	public static String getMinappSecret() {
		return minappSecret;
	}

	public static void setMinappSecret(String minappSecret) {
		Config.minappSecret = minappSecret;
	}

	public static String getMinappKey() {
		return minappKey;
	}

	public static void setMinappKey(String minappKey) {
		Config.minappKey = minappKey;
	}

	public static String getMinappMchId() {
		return minappMchId;
	}

	public static void setMinappMchId(String minappMchId) {
		Config.minappMchId = minappMchId;
	}

	public static String getMinappCertPath() {
		return minappCertPath;
	}

	public static void setMinappCertPath(String minappCertPath) {
		Config.minappCertPath = minappCertPath;
	}

	public static String getMinappCertPsd() {
		return minappCertPsd;
	}

	public static void setMinappCertPsd(String minappCertPsd) {
		Config.minappCertPsd = minappCertPsd;
	}

	public static String getWechatAppAppId() {
		return wechatAppAppId;
	}

	public static void setWechatAppAppId(String wechatAppAppId) {
		Config.wechatAppAppId = wechatAppAppId;
	}

	public static String getWechatAppSecret() {
		return wechatAppSecret;
	}

	public static void setWechatAppSecret(String wechatAppSecret) {
		Config.wechatAppSecret = wechatAppSecret;
	}

	public static String getWechatAppMchId() {
		return wechatAppMchId;
	}

	public static void setWechatAppMchId(String wechatAppMchId) {
		Config.wechatAppMchId = wechatAppMchId;
	}

	public static String getWechatAppCertPath() {
		return wechatAppCertPath;
	}

	public static void setWechatAppCertPath(String wechatAppCertPath) {
		Config.wechatAppCertPath = wechatAppCertPath;
	}

	public static String getWechatAppCertPsd() {
		return wechatAppCertPsd;
	}

	public static void setWechatAppCertPsd(String wechatAppCertPsd) {
		Config.wechatAppCertPsd = wechatAppCertPsd;
	}

	public static String getWechatAppkey() {
		return wechatAppkey;
	}

	public static void setWechatAppkey(String wechatAppkey) {
		Config.wechatAppkey = wechatAppkey;
	}

	public static String getWechatThirdkey() {
		return wechatThirdkey;
	}

	public static void setWechatThirdkey(String wechatThirdkey) {
		Config.wechatThirdkey = wechatThirdkey;
	}

	public static String getWechatThirdAppId() {
		return wechatThirdAppId;
	}

	public static void setWechatThirdAppId(String wechatThirdAppId) {
		Config.wechatThirdAppId = wechatThirdAppId;
	}

	public static String getWechatThirdSecret() {
		return wechatThirdSecret;
	}

	public static void setWechatThirdSecret(String wechatThirdSecret) {
		Config.wechatThirdSecret = wechatThirdSecret;
	}

	public static String getWechatThirdMchId() {
		return wechatThirdMchId;
	}

	public static void setWechatThirdMchId(String wechatThirdMchId) {
		Config.wechatThirdMchId = wechatThirdMchId;
	}

	public static String getWechatThirdCertPath() {
		return wechatThirdCertPath;
	}

	public static void setWechatThirdCertPath(String wechatThirdCertPath) {
		Config.wechatThirdCertPath = wechatThirdCertPath;
	}

	public static String getWechatThirdCertPsd() {
		return wechatThirdCertPsd;
	}

	public static void setWechatThirdCertPsd(String wechatThirdCertPsd) {
		Config.wechatThirdCertPsd = wechatThirdCertPsd;
	}

	public static String getGT() {
		return GT;
	}

	public static void setGT(String gT) {
		GT = gT;
	}

	public static String getBdAppid() {
		return bdAppid;
	}

	public static void setBdAppid(String bdAppid) {
		Config.bdAppid = bdAppid;
	}

	public static String getBdKey() {
		return bdKey;
	}

	public static void setBdKey(String bdKey) {
		Config.bdKey = bdKey;
	}

	public static String getSecretKey() {
		return secretKey;
	}

	public static void setSecretKey(String secretKey) {
		Config.secretKey = secretKey;
	}

	public static String getUserid() {
		return userid;
	}

	public static void setUserid(String userid) {
		Config.userid = userid;
	}

	public static String getAccountpassword() {
		return accountpassword;
	}

	public static void setAccountpassword(String accountpassword) {
		Config.accountpassword = accountpassword;
	}

	public static String getAccount() {
		return account;
	}

	public static void setAccount(String account) {
		Config.account = account;
	}

	public static String getIcbcAppId() {
		return icbcAppId;
	}

	public static void setIcbcAppId(String icbcAppId) {
		Config.icbcAppId = icbcAppId;
	}

	public static String getIcbcAPIGWPUBLICKEY() {
		return icbcAPIGWPUBLICKEY;
	}

	public static void setIcbcAPIGWPUBLICKEY(String icbcAPIGWPUBLICKEY) {
		Config.icbcAPIGWPUBLICKEY = icbcAPIGWPUBLICKEY;
	}

	public static String getIcbcMYPRIVATEKEY() {
		return icbcMYPRIVATEKEY;
	}

	public static void setIcbcMYPRIVATEKEY(String icbcMYPRIVATEKEY) {
		Config.icbcMYPRIVATEKEY = icbcMYPRIVATEKEY;
	}

	public static String getIcbcServiceUrl() {
		return icbcServiceUrl;
	}

	public static void setIcbcServiceUrl(String icbcServiceUrl) {
		Config.icbcServiceUrl = icbcServiceUrl;
	}

	public static String getIcbcMerId() {
		return icbcMerId;
	}

	public static void setIcbcMerId(String icbcMerId) {
		Config.icbcMerId = icbcMerId;
	}

	public static String getIcbcMerIdYun() {
		return icbcMerIdYun;
	}

	public static void setIcbcMerIdYun(String icbcMerIdYun) {
		Config.icbcMerIdYun = icbcMerIdYun;
	}



}
